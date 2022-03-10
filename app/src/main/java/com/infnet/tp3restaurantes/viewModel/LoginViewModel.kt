package com.infnet.tp3restaurantes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infnet.tp3restaurantes.database.UserDao

class LoginViewModel : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun testEntries(email: String, password: String) {
        UserDao.verifyUser(email, password).addOnSuccessListener {
            _status.value = true
        }.addOnFailureListener {
            _msg.value = it.message
        }
    }
}