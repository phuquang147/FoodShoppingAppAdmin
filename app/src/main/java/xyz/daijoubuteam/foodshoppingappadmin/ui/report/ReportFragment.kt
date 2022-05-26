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
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.FragmentReportBinding

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
        binding.viewmodel = viewModel
        setupMonthAndYearDropDownMenu()
        return binding.root
    }

    private fun setupMonthAndYearDropDownMenu() {
        val months = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        val monthAdapter = ArrayAdapter(this.requireContext(), R.layout.item_dropdown, months)
        val years = listOf("2021", "2022")
        val yearAdapter = ArrayAdapter(this.requireContext(), R.layout.item_dropdown, years)
        binding.monthAuto.setAdapter(monthAdapter)
        binding.monthAuto.threshold = 100
        binding.yearAuto.setAdapter(yearAdapter)
        binding.yearAuto.threshold = 100

        binding.monthAuto.setOnItemClickListener { _, _, position, _ ->
            val value = monthAdapter.getItem(position) ?: ""
            Log.i("buttonclick", value)
        }

        binding.yearAuto.setOnItemClickListener { _, _, position, _ ->
            val value = yearAdapter.getItem(position) ?: ""
            Log.i("buttonclick", value)
        }
    }
}