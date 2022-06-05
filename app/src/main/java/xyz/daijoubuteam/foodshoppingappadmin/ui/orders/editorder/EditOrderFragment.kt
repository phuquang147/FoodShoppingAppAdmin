package xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentEditOrderBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.adapter.ProductInOrderAdapter
import xyz.daijoubuteam.foodshoppingappadmin.utils.hideKeyboard

class EditOrderFragment : Fragment() {
    private lateinit var binding: FragmentEditOrderBinding
    private lateinit var orderProperty: Order
    private val viewModel: EditOrderViewModel by lazy {
        val application = requireNotNull(activity).application
        orderProperty = EditOrderFragmentArgs.fromBundle(requireArguments()).orderSelected
        val viewModelFactory = EditOrderViewModelFactory(orderProperty, application)
        ViewModelProvider(this, viewModelFactory)[EditOrderViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_order, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        hideBottomNavigationView()
        setupProductInOrderListViewAdapter()
        setupSaveClick()
        setupMessageObserver()

        val statuses = resources.getStringArray(R.array.status_list)
        val adapter =
            ArrayAdapter(this.requireContext(), R.layout.status_drop_down_layout, statuses)
        binding.statusDropDown.setAdapter(adapter)
        binding.statusDropDown.threshold = 100

        return binding.root
    }

    private fun setupMessageObserver() {
        viewModel.message.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                hideKeyboard()
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                viewModel.onShowMessageComplete()
            }
        }
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

    private fun setupSaveClick() {
        binding.btnSave.setOnClickListener {
            viewModel.updateStatus()
        }
    }
}