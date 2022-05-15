package xyz.daijoubuteam.foodshoppingappadmin.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import xyz.daijoubuteam.foodshoppingappadmin.authentication.login.LoginViewModel
import xyz.daijoubuteam.foodshoppingappadmin.authentication.login.LoginViewModelFactory
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentProductsBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.ProductAdapter

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var eateryProperty: Eatery

    private val viewModel: ProductsViewModel by lazy {
        val viewModelFactory = ProductsViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[ProductsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater)
        binding.lifecycleOwner = this
//        binding.viewmodel = viewModel
        setupProductListViewAdapter()
        return binding.root
    }

    private fun setupProductListViewAdapter() {
//        binding.forYouProductRecyclerView.adapter = ProductAdapter(ProductAdapter.OnClickListener{
//            findNavController().navigate(DetailEateryFragmentDirections.actionDetailEateryFragmentToProductToBagFragment(it))
//        })
        val adapter = binding.forYouProductRecyclerView.adapter as ProductAdapter
        viewModel.productList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            }
        }
    }
}