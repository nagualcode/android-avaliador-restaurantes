package com.infnet.tp3restaurantes.viewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.infnet.tp3restaurantes.R
import com.infnet.tp3restaurantes.database.ReviewDaoFirestore
import com.infnet.tp3restaurantes.database.ReviewUtil
import com.infnet.tp3restaurantes.database.UserDao
import kotlinx.android.synthetic.main.user_reviews_fragment.*

class ReviewsFragment : Fragment() {

    private lateinit var viewModel: ReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_reviews_fragment, container, false)
        val userReviewsViewModelFactory = ReviewsViewModelFactory(ReviewDaoFirestore())
        viewModel = ViewModelProvider(this, userReviewsViewModelFactory).get(ReviewsViewModel::class.java)

        viewModel.reviews.observe(viewLifecycleOwner){
            userReviewsList.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                it
            )
            userReviewsList.setOnItemClickListener { parent, view, position, id ->
                val getScore = it.get(position)
                ReviewUtil.selectedReview = getScore
            }
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                labelUserNameText.text = it.name + " " + it.lastname
            }
            else if(UserDao.firebaseAuth.currentUser == null) {
                findNavController().navigate(R.id.loginFragment)
                labelUserNameText.text = null
            }
        })
        viewModel.getUser()

        viewModel.getUserReviews()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnNewReview.setOnClickListener{
            ReviewUtil.selectedReview = null
            findNavController().navigate(R.id.createReview)
        }
        btnAllReviews.setOnClickListener{
            findNavController().navigate(R.id.allReviewsFragment)
        }
        btnLogOut.setOnClickListener{
            viewModel.doLogOut()
        }
    }
}