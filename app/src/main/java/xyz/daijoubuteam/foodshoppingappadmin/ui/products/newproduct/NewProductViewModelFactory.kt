package xyz.daijoubuteam.foodshoppingappadmin.ui.products.newproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewProductViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewProductViewModel::class.java)) {
            return NewProductViewModel() as T
        }
        throw IllegalArgumentException("Unknown NewProductViewModel class")
    }
}