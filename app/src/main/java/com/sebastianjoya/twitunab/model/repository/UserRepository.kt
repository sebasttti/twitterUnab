package com.sebastianjoya.twitunab.model.repository

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sebastianjoya.twitunab.model.entity.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserRepository {

    private val USERS_COLLECTION:String = "users"
    var users: MutableLiveData<List<User>> = MutableLiveData()
    var user: MutableLiveData<User?> = MutableLiveData()
    private val firestore : FirebaseFirestore = Firebase.firestore
    private val auth = Firebase.auth

    init{}

    fun loadUsers(){

        firestore.collection(USERS_COLLECTION).get().addOnSuccessListener { result ->

            val usersList: ArrayList<User> = arrayListOf()
            if (!result.isEmpty){
                for(document in result.documents){
                    val eachUser: User? = document.toObject(User::class.java)
                    eachUser?.let {
                        it.id = document.id
                        usersList.add(it)
                    }

                }
            }

            users.value=usersList


        }
    }

    fun getUserById(id:String){

        firestore.collection(USERS_COLLECTION).document(id).get().addOnSuccessListener {
                result->
            val thisUser:User? =result.toObject(User::class.java)
            println("Usuario: ${thisUser}")
            thisUser?.let{
                it.id = id
                user.value = it
            }
        }
    }

    fun addUser(thisUser: User, photoUri: Uri?): LiveData<Boolean> {
        val stateAdd:MutableLiveData<Boolean> = MutableLiveData()

        photoUri?.let{

            val storageReference = Firebase.storage.reference.child(USERS_COLLECTION)
            val time = SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.US).format(Date())
            val name = time + thisUser.name + ".jpg"
            storageReference.child(name).putFile(photoUri).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                        url->
                    thisUser.urlPhoto = url.toString()
                    firestore.collection(USERS_COLLECTION).document(thisUser.id).set(thisUser).addOnSuccessListener {
                        stateAdd.value = true
                    }.addOnFailureListener{
                        stateAdd.value=false
                    }

                }
            }.addOnFailureListener{
                exc->
                println("SignUp: ${exc.message}")
            }
        }?:run{
            firestore.collection(USERS_COLLECTION).document(thisUser.id).set(thisUser).addOnSuccessListener {
                stateAdd.value = true
            }.addOnFailureListener{
                stateAdd.value=false
            }
        }

        return stateAdd
    }

    fun updateUser(thisUser: User):LiveData<Boolean>{
        val stateUpdate:MutableLiveData<Boolean> = MutableLiveData()
        firestore.collection(USERS_COLLECTION).document(thisUser.id).set(thisUser).addOnSuccessListener {
            stateUpdate.value = true
        }.addOnFailureListener{
            stateUpdate.value=false
        }

        return stateUpdate
    }

    fun deleteUser(thisUser: User):LiveData<Boolean>{
        val stateDelete:MutableLiveData<Boolean> = MutableLiveData()
        firestore.collection(USERS_COLLECTION).document(thisUser.id).delete().addOnSuccessListener {
            loadUsers()
            stateDelete.value=true
        }.addOnFailureListener{
            stateDelete.value=false
        }

        return stateDelete

    }

    fun login(email: String, password: String):LiveData<User?>{
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            println("Datos correctos")
            getUserById(auth.uid!!)
        }.addOnFailureListener{
            user.value=null
        }
        return user
    }

    fun signUp(thisUser: User,photoUri: Uri?,password:String):LiveData<User?>{

        auth.createUserWithEmailAndPassword(thisUser.email,password).addOnSuccessListener {
            thisUser.id = auth.uid!!
            addUser(thisUser,photoUri)
        }.addOnFailureListener{
           println("SignUp: ${it.message}")
           user.value = null
        }

        return user
    }


}