package com.sebastianjoya.twitunab.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sebastianjoya.twitunab.model.entity.User
import com.sebastianjoya.twitunab.model.repository.UserRepository

class LoginActivityViewModel: ViewModel() {
    val user: User = User(email = "felipe@gmail.com")
    var password:String = "123456789"
    private val userRepository: UserRepository = UserRepository()

    fun login(): LiveData<User?> {
        return userRepository.login(user.email,password)
    }
}