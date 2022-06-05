package xyz.daijoubuteam.foodshoppingappadmin.ui.report

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentReportBinding
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.adapter.OrderAdapter
import java.util.*

class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding

    private val viewModel: ReportViewModel by lazy {
        val viewModelFactory = ReportViewModelFactory()
        ViewModelProvider(this, viewModelFactory)[ReportViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupTotalOrderListViewAdapter()
        setupMonthAndYearDropDownMenu()
        setupOrderListViewAdapter()
        return binding.root
    }

    private fun setupTotalOrderListViewAdapter() {
        viewModel.mediatorLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
            }
        }
    }

    private fun setupOrderListViewAdapter() {
        binding.ordersRecyclerView.adapter = OrderAdapter(OrderAdapter.OnClickListener{
            findNavController().navigate(ReportFragmentDirections.actionNavigationReportToOrderDetailFragment(it))
        })
        val adapter = binding.ordersRecyclerView.adapter as OrderAdapter
        adapter.submitList(viewModel.filteredOrders.value)
        viewModel.filteredOrders.observe(viewLifecycleOwner) {
            if(it != null) {
                adapter.submitList(it)
            }
        }
    }

    private fun setupMonthAndYearDropDownMenu() {
        val months = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        val monthAdapter = ArrayAdapter(this.requireContext(), R.layout.item_dropdown, months)
        val years = listOf(
            (viewModel.calendar.get(Calendar.YEAR) - 2).toString(),
            (viewModel.calendar.get(Calendar.YEAR) - 1).toString(),
            (viewModel.calendar.get(Calendar.YEAR)).toString())
        val yearAdapter = ArrayAdapter(this.requireContext(), R.layout.item_dropdown, years)
        binding.monthAuto.setAdapter(monthAdapter)
        binding.monthAuto.threshold = 100
        binding.yearAuto.setAdapter(yearAdapter)
        binding.yearAuto.threshold = 100
    }
}