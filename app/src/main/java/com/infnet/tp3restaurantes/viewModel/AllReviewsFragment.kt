package com.infnet.tp3restaurantes.viewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.infnet.tp3restaurantes.R
import com.infnet.tp3restaurantes.database.ReviewDaoFirestore
import kotlinx.android.synthetic.main.all_reviews_fragment.*

class AllReviewsFragment : Fragment() {
    private lateinit var viewModel: AllReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.all_reviews_fragment, container, false)
        val userReviewsViewModelFactory = AllReviewsViewModelFactory(ReviewDaoFirestore())
        viewModel = ViewModelProvider(this, userReviewsViewModelFactory).get(
            AllReviewsViewModel::class.java)

        viewModel.reviews.observe(viewLifecycleOwner) {
            allReviewsList.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                it
            )
        }
        viewModel.listGlobalReviews()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        allReviewsBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}