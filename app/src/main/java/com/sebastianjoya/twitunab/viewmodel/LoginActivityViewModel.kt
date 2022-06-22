package com.sebastianjoya.twitunab.viewmodel

import androidx.lifecycle.ViewModel
import com.sebastianjoya.twitunab.model.entity.User

class LoginActivityViewModel: ViewModel() {
    val user: User = User(email = "juansjoya@gmail.com")
    var password:String = "123456789"
}