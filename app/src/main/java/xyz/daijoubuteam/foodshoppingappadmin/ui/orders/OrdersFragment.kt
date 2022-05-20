package xyz.daijoubuteam.foodshoppingappadmin.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentOrdersBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.adapter.OrderAdapter

class OrdersFragment : Fragment() {
    private lateinit var binding: FragmentOrdersBinding

    private val viewModel: OrdersViewModel by lazy {
        val viewModelFactory = OrdersViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[OrdersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        addOrderRecyclerDivider()
        setupOrderListViewAdapter()

        return binding.root
    }

    private fun setupOrderListViewAdapter() {
        binding.ordersRecyclerView.adapter = OrderAdapter(OrderAdapter.OnClickListener {
            findNavController().navigate(
                OrdersFragmentDirections.actionNavigationOrdersToEditOrderFragment(
                    it
                )
            )
        })
        val adapter = binding.ordersRecyclerView.adapter as OrderAdapter
        adapter.submitList(viewModel.orderList.value)
        viewModel.orderList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            }
        }
    }

    private fun addOrderRecyclerDivider() {
        val layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false).apply {
            binding.ordersRecyclerView.layoutManager = this
        }

        DividerItemDecoration(this.context, layoutManager.orientation).apply {
            binding.ordersRecyclerView.addItemDecoration(this)
        }
    }
}