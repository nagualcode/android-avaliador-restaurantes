package com.infnet.tp3restaurantes.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.infnet.tp3restaurantes.model.Review

interface ReviewDao {
    fun insert(review: Review) : Task<DocumentReference>

    fun delete(review: Review) : Task<Void>

    fun all(): Query

    fun allForAllUsers(): Task<QuerySnapshot>

    fun read(key: String): Query

    fun edit(review: Review): Task<Void>
}