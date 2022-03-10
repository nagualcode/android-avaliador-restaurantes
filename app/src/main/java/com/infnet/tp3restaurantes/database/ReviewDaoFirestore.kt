package com.infnet.tp3restaurantes.database

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.infnet.tp3restaurantes.model.Review

class ReviewDaoFirestore: ReviewDao {
    private val collection = FirebaseFirestore.getInstance().collection("reviews")
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun insert(review: Review): Task<DocumentReference> {
        review.userId = firebaseAuth.currentUser.uid
        return collection.add(review)
    }

    override fun delete(review: Review): Task<Void> {
        return collection.document(review.id!!).delete()
    }

    override fun all(): Query {
        return collection.whereEqualTo("userId", firebaseAuth.currentUser.uid)
    }

    override fun allForAllUsers(): Task<QuerySnapshot> {
        return collection.orderBy("neighbourhood").get()
    }

    override fun read(key: String): Query {
        return collection.whereEqualTo("id", key)
    }

    override fun edit(review: Review): Task<Void> {
        return collection.document(review.id!!).set(review)
    }
}