package com.infnet.tp3restaurantes.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId

class User (
    var name: String? = null,
    var lastname: String? = null,
    var email: String? = null,
    var firebaseUser : FirebaseUser? = null,
    @DocumentId
    var uid: String? = null
)