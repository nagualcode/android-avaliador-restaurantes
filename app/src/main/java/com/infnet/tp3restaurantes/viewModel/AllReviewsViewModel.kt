package com.infnet.tp3restaurantes.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infnet.tp3restaurantes.database.ReviewDao
import com.infnet.tp3restaurantes.model.Review

class AllReviewsViewModel(private val reviewDao: ReviewDao) : ViewModel() {
    private val _reviews = MutableLiveData<MutableList<Review>>()
    val reviews: LiveData<MutableList<Review>> = _reviews

    fun listGlobalReviews () {
        reviewDao.allForAllUsers().addOnSuccessListener {
            val reviewsDb = it.toObjects(Review::class.java)
            _reviews.value = reviewsDb
        }.addOnFailureListener {
            Log.i("ListReviewsFragment", "${it.message}")
        }

    }
}