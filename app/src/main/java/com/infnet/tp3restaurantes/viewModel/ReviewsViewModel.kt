package com.infnet.tp3restaurantes.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infnet.tp3restaurantes.database.ReviewDao
import com.infnet.tp3restaurantes.database.UserDao
import com.infnet.tp3restaurantes.model.Review
import com.infnet.tp3restaurantes.model.User

class ReviewsViewModel(private val reviewDao: ReviewDao) : ViewModel() {
    private val _reviews = MutableLiveData<MutableList<Review>>()
    val reviews: LiveData<MutableList<Review>> = _reviews
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    fun getUserReviews () {
        reviewDao.all().addSnapshotListener { value, error ->
            if (error != null) {
                Log.i("FirebaseFirestore", "${error.message}")
            } else {
                if (value != null && !value.isEmpty) {
                    _reviews.value = value.toObjects(Review::class.java)
                }
            }
        }
    }

    fun getUser() {
        UserDao.verifyFirebaseUser().addOnSuccessListener {
            val theUser = it.toObject(User::class.java)
            theUser!!.firebaseUser = UserDao.firebaseAuth.currentUser
            theUser.email = UserDao.firebaseAuth.currentUser.email
            _user.value = theUser!!
        }
    }

    fun doLogOut() {
        UserDao.signOut()
        _user.value = null
    }
}