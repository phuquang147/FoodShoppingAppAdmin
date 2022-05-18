package xyz.daijoubuteam.foodshoppingappadmin.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrdersViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            return OrdersViewModel() as T
        }
        throw IllegalArgumentException("Unknown OrdersViewModel class")
    }
}