package xyz.daijoubuteam.foodshoppingappadmin.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReportViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            return ReportViewModel() as T
        }
        throw IllegalArgumentException("Unknown ReportViewModel class")
    }
}