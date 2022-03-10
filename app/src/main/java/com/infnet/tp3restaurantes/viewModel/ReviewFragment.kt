package com.infnet.tp3restaurantes.viewModel

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.infnet.tp3restaurantes.R
import com.infnet.tp3restaurantes.database.ReviewDaoFirestore
import com.infnet.tp3restaurantes.database.CypherString
import kotlinx.android.synthetic.main.review_fragment.*

class ReviewFragment : Fragment() {

    private lateinit var viewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.review_fragment, container, false)

        val doReviewViewModelFactory = ReviewViewModelFactory(ReviewDaoFirestore())

        viewModel = ViewModelProvider(this, doReviewViewModelFactory).get(ReviewViewModel::class.java)

        viewModel.let {
            it.message.observe(viewLifecycleOwner){ message ->
                if(!message.isNullOrBlank()){
                    makeToast(message)
                }
            }
            it.status.observe(viewLifecycleOwner){ status ->
                if(status) {
                    findNavController().popBackStack(R.id.userReviewsFragment, false)
                }
            }
        }
        return view
    }

    private fun calculateScore(
        a: String,
        b: String,
        c: String,
        d: String,
        e: String,
        f: String
    ): Int {
        var answersCounter = 0
        val listOfAnswers = listOf(a, b, c, d, e, f)
        var i = 0

        while (i < listOfAnswers.count()) {
            if (listOfAnswers.get(i) == "Sim") {
                answersCounter++
            }
            i++
        }

        return 100 * answersCounter / listOfAnswers.count()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewBackCadAv.setOnClickListener{
            findNavController().popBackStack()
        }

        btnSendReview.setOnClickListener {
            val businessName = CypherString()
            businessName.setClearText(inputBusinessName.text.toString())
            val neighbourhood = CypherString()
            neighbourhood.setClearText(inputNeighbourhood.text.toString())
            val answer1 = if(answerYes1.isChecked) "Sim" else "Não"
            val answer2 = if(answerYes2.isChecked) "Sim" else "Não"
            val answer3 = if(answerYes3.isChecked) "Sim" else "Não"
            val answer4 = if(answerYes4.isChecked) "Sim" else "Não"
            val answer5 = if(answerYes5.isChecked) "Sim" else "Não"
            val answer6 = if(answerYes6.isChecked) "Sim" else "Não"
            val score = calculateScore(answer1, answer2, answer3, answer4, answer5, answer6)

            viewModel.doReview(businessName, neighbourhood, answer1, answer2, answer3,
                                      answer4, answer5, answer6, score)
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }


}