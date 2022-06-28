package com.sebastianjoya.twitunab.model.entity

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class User(
    var id: String = "",
    var document: String = "",
    var name: String = "",
    var email: String = "",
    var urlPhoto: String = "https://images.vexels.com/media/users/3/135246/isolated/preview/df491bf444acfa945630c22389140d4a-icono-de-sombra-de-usuario.png",
): Serializable {

}