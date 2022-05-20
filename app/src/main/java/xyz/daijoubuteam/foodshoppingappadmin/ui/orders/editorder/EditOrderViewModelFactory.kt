package xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.daijoubuteam.foodshoppingappadmin.model.Order

class EditOrderViewModelFactory(
    private val orderProperty: Order,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditOrderViewModel::class.java)) {
            return EditOrderViewModel(orderProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

