package com.sebastianjoya.twitunab.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sebastianjoya.twitunab.model.entity.Twit
import com.sebastianjoya.twitunab.model.entity.User
import com.sebastianjoya.twitunab.model.repository.TwitRepository

class UserTwitsActivityViewModel(application: Application):AndroidViewModel(application) {
    private val twitRepository: TwitRepository = TwitRepository()
    var twits: LiveData<List<Twit>> = twitRepository.twits
    lateinit var user:User

    var newMessage:String = ""

    fun loadData(){
        twitRepository.loadTwitsByUser(user)
    }

}