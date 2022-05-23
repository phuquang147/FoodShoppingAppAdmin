package xyz.daijoubuteam.foodshoppingappadmin.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return binding.root
    }

}