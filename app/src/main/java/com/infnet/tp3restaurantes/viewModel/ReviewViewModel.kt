package com.infnet.tp3restaurantes.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infnet.tp3restaurantes.database.ReviewDao
import com.infnet.tp3restaurantes.database.CypherString
import com.infnet.tp3restaurantes.model.Review

class ReviewViewModel(private val reviewDao: ReviewDao) : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    init {
        _status.value = false
        _message.value = null
    }

    fun doReview(
        businessName: CypherString, neighbourhood: CypherString, answer1: String,
        answer2: String, answer3: String, answer4: String,
        answer5: String, answer6: String, score: Int
    ){

        val doScore = Review(businessName, neighbourhood, answer1, answer2, answer3,
                                  answer4, answer5, answer6, score)
        reviewDao.insert(doScore).addOnSuccessListener {
            _status.value = true
            _message.value = "Saved!"
        }.addOnFailureListener{
            _message.value = "Error!"
            Log.e("DaoFirebase", "${it.message}")
        }
    }
}