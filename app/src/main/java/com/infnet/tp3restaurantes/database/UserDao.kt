package com.infnet.tp3restaurantes.database

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.infnet.tp3restaurantes.model.User

object UserDao {
    val firebaseAuth = FirebaseAuth.getInstance()
    private val collection = FirebaseFirestore.getInstance().collection("users")

    fun createUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    fun setUser(uid: String, name: String, lastname: String): Task<Void> {
        return collection.document(uid).set(User(name, lastname))
    }

    fun verifyUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    fun signOut(){
        firebaseAuth.signOut()
    }

    fun verifyFirebaseUser(): Task<DocumentSnapshot> {
        val firebaseUser = firebaseAuth.currentUser
        return collection.document(firebaseUser!!.uid).get()
    }

    fun updateUser(name: String, lastname: String, uid: String): Task<Void> {
        collection.document(uid).update("name", name)
        return collection.document(uid).update("lastname", lastname)
    }
}