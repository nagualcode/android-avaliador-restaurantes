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
import kotlinx.android.synthetic.main.new_user_fragment.*

class NewUserFragment : Fragment() {
    private lateinit var viewModel: NewUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_user_fragment, container, false)
        viewModel = ViewModelProvider(this).get(NewUserViewModel::class.java)
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it)
                findNavController().popBackStack()
        })

        viewModel.msg.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank())
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignup.setOnClickListener {
            val password = inputPasswordEdit1.text.toString()
            val password2 = inputPasswordEdit2.text.toString()
            if(password.length >= 6){
                if(password == password2){
                    val email = inputEmailEdit1.text.toString()
                    val name = inputName.text.toString()
                    val lastname = inputLastNameEdit.text.toString()
                    viewModel.doSignUp(email, password, name, lastname)
                }else{
                    Toast.makeText(requireContext(), "password mismatch!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "password minimum length is 6!", Toast.LENGTH_SHORT).show()
            }

        }
    }

}