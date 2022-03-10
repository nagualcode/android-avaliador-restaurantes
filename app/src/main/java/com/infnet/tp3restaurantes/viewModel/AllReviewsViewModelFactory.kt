package com.infnet.tp3restaurantes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infnet.tp3restaurantes.database.ReviewDao
import java.lang.IllegalArgumentException

class AllReviewsViewModelFactory(private val reviewDao: ReviewDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AllReviewsViewModel::class.java))
            return AllReviewsViewModel(reviewDao) as T
        throw IllegalArgumentException("error!")
    }
}