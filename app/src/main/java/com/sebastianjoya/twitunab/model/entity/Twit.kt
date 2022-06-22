package com.sebastianjoya.twitunab.model.entity

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

class Twit(
    @JvmField @Exclude
    var id: String = "",
    var date: String = "",
    var message: String = "",
    var idUser: String = "",
    var idStatus: String = ""
) {
}