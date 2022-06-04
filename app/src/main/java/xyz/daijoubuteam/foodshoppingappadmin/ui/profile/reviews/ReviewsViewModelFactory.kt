package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReviewsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsViewModel::class.java)) {
            return ReviewsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ReviewViewModel class")
    }
}