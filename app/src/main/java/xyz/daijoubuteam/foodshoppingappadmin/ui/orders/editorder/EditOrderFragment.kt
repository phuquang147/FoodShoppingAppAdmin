package xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import xyz.daijoubuteam.foodshoppingappadmin.MainActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            checkForNavigate()
        }
        callback.isEnabled = true
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
        addProductInOrderRecyclerDivider()
        setupSaveClick()
        setupMessageObserver()

        val activity = requireActivity() as MainActivity
        activity.setAppBarTitle("Order Detail")

        val statuses = resources.getStringArray(R.array.status_list)
        val adapter =
            ArrayAdapter(this.requireContext(), R.layout.status_drop_down_layout, statuses)
        binding.statusDropDown.setAdapter(adapter)
        binding.statusDropDown.threshold = 100
        if(viewModel.originalStatus == "Cancelled" || viewModel.originalStatus == "Completed") disableDropDown()

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

    private fun setupSaveClick() {
        binding.btnSave.setOnClickListener {
            alertSaveStatus()
        }
    }

    private fun disableDropDown() {
        binding.statusMenu.isEnabled = false
    }

    private fun checkForNavigate() {
        if (viewModel.originalStatus != viewModel.selectedProperty.value?.status) {
            MaterialAlertDialogBuilder(this.requireContext())
                .setTitle(resources.getString(R.string.save_order))
                .setMessage(resources.getString(R.string.save_confirm))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                .setNegativeButton(resources.getString(R.string.no)) { _, _ ->
                    viewModel.selectedProperty.value?.status = viewModel.originalStatus
                    findNavController().navigateUp()
                }
                .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                    dialog.cancel()
                    alertSaveStatus(true)
                }
                .show()
        } else {
            findNavController().navigateUp()
        }
    }

    private fun alertSaveStatus(isNavigateUp: Boolean = false) {
        if (viewModel.selectedProperty.value?.status == "Cancelled" || viewModel.selectedProperty.value?.status == "Completed") {
            MaterialAlertDialogBuilder(this.requireContext())
                .setTitle(resources.getString(R.string.save_order))
                .setMessage(resources.getString(R.string.save_confirm_alert))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    viewModel.updateStatus()
                    disableDropDown()
                    if(isNavigateUp) findNavController().navigateUp()
                }
                .show()
        } else {
            if(isNavigateUp) findNavController().navigateUp()
            viewModel.updateStatus()
        }
    }
}