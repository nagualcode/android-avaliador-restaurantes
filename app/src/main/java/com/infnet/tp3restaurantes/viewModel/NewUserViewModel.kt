package com.infnet.tp3restaurantes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infnet.tp3restaurantes.database.UserDao

class NewUserViewModel : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun doSignUp(email: String, password: String, name: String, lastname: String) {
        UserDao.createUser(email, password).addOnSuccessListener {
            val uid = it.user!!.uid
            UserDao.setUser(uid, name, lastname).addOnSuccessListener {
                _status.value = true
                _msg.value = "Sign Up Successfully!"
            }.addOnFailureListener {
                _msg.value = "${it.message}"
            }

        }.addOnFailureListener {
            _msg.value = "${it.message}"
        }
    }
}