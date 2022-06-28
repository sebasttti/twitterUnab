package com.sebastianjoya.twitunab.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sebastianjoya.twitunab.model.entity.User
import com.sebastianjoya.twitunab.model.repository.UserRepository

class SignUpActivityViewModel:ViewModel() {
    var user:User = User()
    var password:String = ""
    private val userRepository:UserRepository = UserRepository()

    fun signUp(photoUri:Uri?):LiveData<User?>{
        return userRepository.signUp(user,photoUri,password)
    }

}