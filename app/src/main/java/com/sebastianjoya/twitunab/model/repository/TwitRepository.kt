package com.sebastianjoya.twitunab.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sebastianjoya.twitunab.model.entity.Twit
import com.sebastianjoya.twitunab.model.entity.User

class TwitRepository {
    private val TWITS_COLLECTION:String = "twits"
    var twits: MutableLiveData<List<Twit>> = MutableLiveData()
    var twit: MutableLiveData<Twit?> = MutableLiveData()
    private val firestore : FirebaseFirestore = Firebase.firestore

    init{}

    fun loadTwitsByUser(thisUser:User){

        var userId = thisUser.id
        firestore.collection(TWITS_COLLECTION).get().addOnSuccessListener { result ->

            val twitsList: ArrayList<Twit> = arrayListOf()
            if (!result.isEmpty){
                for(document in result.documents){
                    val eachTwit: Twit? = document.toObject(Twit::class.java)
                    eachTwit?.let {
                        if (it.idUser == userId){
                            it.id = document.id
                            twitsList.add(it)
                        }
                    }
                }
            }

            twits.value=twitsList
        }
    }

    fun addTwit(thisUser: User):LiveData<String>{
        val twitIdObserver:MutableLiveData<String> = MutableLiveData();
        firestore.collection(TWITS_COLLECTION).add(thisUser).addOnSuccessListener {
                response->
            twitIdObserver.value = response.id
        }.addOnFailureListener{
            twitIdObserver.value=""
        }
        return twitIdObserver
    }

    fun updateTwit(thisTwit: Twit):LiveData<Boolean>{

        val stateUpdate:MutableLiveData<Boolean> = MutableLiveData()
        firestore.collection(TWITS_COLLECTION).document(thisTwit.id).set(thisTwit).addOnSuccessListener {
            stateUpdate.value = true
        }.addOnFailureListener{
            stateUpdate.value=false
        }

        return stateUpdate

    }

    fun deleteTwit(thisTwit: Twit,user:User):LiveData<Boolean>{

        val stateDelete:MutableLiveData<Boolean> = MutableLiveData()
        firestore.collection(TWITS_COLLECTION).document(thisTwit.id).delete().addOnSuccessListener {
            loadTwitsByUser(user)
            stateDelete.value=true
        }.addOnFailureListener{
            stateDelete.value=false
        }

        return stateDelete

    }
}