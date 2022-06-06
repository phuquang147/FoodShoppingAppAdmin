package xyz.daijoubuteam.foodshoppingappadmin.ui.report.orderdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder.EditOrderViewModel

class OrderDetailViewModelFactory(
    private val orderProperty: Order
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)) {
            return OrderDetailViewModel(orderProperty) as T
        }
        throw IllegalArgumentException("Unknown OrderDetailViewModel class")
    }
}