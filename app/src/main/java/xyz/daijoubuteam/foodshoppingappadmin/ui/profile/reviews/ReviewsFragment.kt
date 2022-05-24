package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.reviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentProductsBinding
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentReviewsBinding
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.ProductsViewModel
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.ProductsViewModelFactory
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.IngredientAdapter
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.ProductAdapter
import xyz.daijoubuteam.foodshoppingappadmin.ui.profile.adapter.ReviewAdapter


class ReviewsFragment : Fragment() {
    private lateinit var binding: FragmentReviewsBinding

    private val viewModel: ReviewsViewModel by lazy {
        val viewModelFactory = ReviewsViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[ReviewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        hideActionBar()
        setupReviewListViewAdapter()
        addReviewRecyclerDivider()
        setupNavigateToProfileFragment()
        return binding.root
    }

    private fun setupReviewListViewAdapter() {
        binding.reviewsRecyclerView.adapter = ReviewAdapter()
        val adapter = binding.reviewsRecyclerView.adapter as ReviewAdapter?
        adapter?.submitList(viewModel.reviewList.value)
        viewModel.reviewList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter?.submitList(null)
                adapter?.submitList(it)
            }
        }
    }

    private fun setupNavigateToProfileFragment() {
        binding.imageChevronleft.setOnClickListener {
            findNavController().navigate(ReviewsFragmentDirections.actionReviewsFragmentToNavigationProfile())
        }
    }

    private fun addReviewRecyclerDivider() {
        val layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false).apply {
            binding.reviewsRecyclerView.layoutManager = this
        }

        DividerItemDecoration(this.context, layoutManager.orientation).apply {
            binding.reviewsRecyclerView.addItemDecoration(this)
        }
    }

    private fun hideActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }
}