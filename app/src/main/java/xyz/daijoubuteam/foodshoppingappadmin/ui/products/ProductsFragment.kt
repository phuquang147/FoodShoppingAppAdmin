package xyz.daijoubuteam.foodshoppingappadmin.ui.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentProductsBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.ProductAdapter
import xyz.daijoubuteam.foodshoppingappadmin.R

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding

    private val viewModel: ProductsViewModel by lazy {
        val viewModelFactory = ProductsViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[ProductsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setupProductListViewAdapter()
        return binding.root
    }

    private fun setupProductListViewAdapter() {
        binding.productRecyclerView.adapter = ProductAdapter(ProductAdapter.OnClickListener{
//            findNavController().navigate(DetailEateryFragmentDirections.actionDetailEateryFragmentToProductToBagFragment(it))
        })
        val adapter = binding.productRecyclerView.adapter as ProductAdapter
        adapter.submitList(viewModel.productList.value)
        viewModel.productList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            }
        }
    }
}