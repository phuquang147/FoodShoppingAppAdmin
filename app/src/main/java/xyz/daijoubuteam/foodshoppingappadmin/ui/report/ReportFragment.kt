package xyz.daijoubuteam.foodshoppingappadmin.ui.report

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentReportBinding
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.adapter.OrderAdapter
import xyz.daijoubuteam.foodshoppingappadmin.ui.profile.adapter.ReviewAdapter
import java.time.ZoneId
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
        setupMonthOnChange()
        setupYearOnChange()
        return binding.root
    }

    private fun setupTotalOrderListViewAdapter() {
        viewModel.wholeOrderList.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.orderList.value = it
            }
        }
    }

    private fun setupOrderListViewAdapter() {
        binding.ordersRecyclerView.adapter = OrderAdapter(OrderAdapter.OnClickListener{})
        val adapter = binding.ordersRecyclerView.adapter as OrderAdapter
        adapter.submitList(viewModel.filteredOrders.value)
        viewModel.filteredOrders.observe(viewLifecycleOwner) {
            if(it != null) {
                adapter.submitList(it)
                Log.i("filterOrder", viewModel.filteredOrders.value.toString())
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

    private fun setupMonthOnChange(){
        binding.monthAuto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                filterOrders()
            }
        })
    }

    private fun setupYearOnChange(){
        binding.yearAuto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                filterOrders()
            }
        })
    }

    fun filterOrders(){
        viewModel.filteredOrders.value = viewModel.orderList.value?.filter {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                (it.orderTime?.toDate()?.month?.plus(1)).toString() == viewModel.month.value
                       && it.orderTime?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()?.year.toString() == viewModel.year.value
            } else {
                throw Exception("Invalid sdk version")
            }
        }
        Log.i("filteredOrders", viewModel.orderList.value.toString())
        Log.i("filteredOrders", viewModel.filteredOrders.value.toString())
    }
}