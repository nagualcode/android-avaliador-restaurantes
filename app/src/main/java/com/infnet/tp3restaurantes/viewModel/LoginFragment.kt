package com.infnet.tp3restaurantes.viewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.infnet.tp3restaurantes.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(R.id.userReviewsFragment)
            }
        })

        viewModel.msg.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrBlank())
                makeToast(it)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            val email = inputEmailEdit.text.toString()
            val password = inputPasswordEdit.text.toString()
            if(!email.isNullOrBlank() && !password.isNullOrBlank()) {
                viewModel.testEntries(email, password)
            }
            else {
                makeToast("Enter email and password!")
            }
        }
        btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

}