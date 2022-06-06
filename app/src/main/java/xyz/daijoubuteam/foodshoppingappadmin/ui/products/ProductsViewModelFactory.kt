package xyz.daijoubuteam.foodshoppingappadmin.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel() as T
        }
        throw IllegalArgumentException("Unknown SignupViewModel class")
    }
}