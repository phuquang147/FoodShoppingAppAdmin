package xyz.daijoubuteam.foodshoppingappadmin.ui.products

import android.os.Bundle
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
import xyz.daijoubuteam.foodshoppingappadmin.ui.products.adapter.ProductAdapter


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

        hideActionBar()
        setupProductListViewAdapter()
        setupNavigateToProfileAndAddressFragment()
        addProductRecyclerDivider()
        return binding.root
    }

    private fun setupProductListViewAdapter() {
        binding.productRecyclerView.adapter = ProductAdapter(ProductAdapter.OnClickListener{
            findNavController().navigate(ProductsFragmentDirections.actionNavigationHomeToEditProductFragment(it))
        })
        val adapter = binding.productRecyclerView.adapter as ProductAdapter
        adapter.submitList(viewModel.productList.value)
        viewModel.productList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            }
        }
    }
    private fun setupNavigateToProfileAndAddressFragment() {
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ProductsFragmentDirections.actionNavigationHomeToNewProductFragment())
        }
    }

    private fun addProductRecyclerDivider() {
        val layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false).apply {
            binding.productRecyclerView.layoutManager = this
        }

        DividerItemDecoration(this.context, layoutManager.orientation).apply {
            binding.productRecyclerView.addItemDecoration(this)
        }
    }

    private fun hideActionBar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }
}