package xyz.daijoubuteam.foodshoppingappadmin.ui.report.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentOrderDetailBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.adapter.ProductInOrderAdapter

class OrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private lateinit var orderProperty: Order
    private val viewModel: OrderDetailViewModel by lazy {
        orderProperty = OrderDetailFragmentArgs.fromBundle(requireArguments()).orderSelected
        val viewModelFactory = OrderDetailViewModelFactory(orderProperty)
        ViewModelProvider(this, viewModelFactory)[OrderDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        hideBottomNavigationView()
        setupProductInOrderListViewAdapter()
        addProductInOrderRecyclerDivider()
        setupBackButtonClick()

        return binding.root
    }

    private fun setupProductInOrderListViewAdapter() {
        binding.productsInOrderRecyclerView.adapter = ProductInOrderAdapter()
        val adapter = binding.productsInOrderRecyclerView.adapter as ProductInOrderAdapter
        adapter.submitList(viewModel.selectedProperty.value?.orderItems)
        viewModel.selectedProperty.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it.orderItems)
            }
        }
    }

    private fun addProductInOrderRecyclerDivider() {
        val layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false).apply {
            binding.productsInOrderRecyclerView.layoutManager = this
        }

        DividerItemDecoration(this.context, layoutManager.orientation).apply {
            binding.productsInOrderRecyclerView.addItemDecoration(this)
        }
    }

    private fun hideBottomNavigationView() {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
    }

    private fun setupBackButtonClick() {
        binding.imageChevronleft.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.VISIBLE
    }
}