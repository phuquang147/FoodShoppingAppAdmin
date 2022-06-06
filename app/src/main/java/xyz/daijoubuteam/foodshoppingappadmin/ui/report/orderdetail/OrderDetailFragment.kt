package xyz.daijoubuteam.foodshoppingappadmin.ui.report.orderdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEditOrderBinding
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentOrderDetailBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.adapter.ProductInOrderAdapter
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder.EditOrderFragmentArgs
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder.EditOrderViewModel
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder.EditOrderViewModelFactory
import xyz.daijoubuteam.foodshoppingappadmin.utils.hideKeyboard

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
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        hideBottomNavigationView()
        setupProductInOrderListViewAdapter()

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

    private fun hideBottomNavigationView() {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.VISIBLE
    }
}