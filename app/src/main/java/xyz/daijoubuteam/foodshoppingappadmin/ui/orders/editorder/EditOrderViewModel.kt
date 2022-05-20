package xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.repositories.OrderRepository

class EditOrderViewModel(orderProperty: Order, app: Application) : AndroidViewModel(app) {
    private val orderRepository = OrderRepository()
    private val _selectedProperty = MutableLiveData<Order>()
    val selectedProperty: LiveData<Order>
        get() = _selectedProperty

    init {
        _selectedProperty.value =
            orderRepository.getProductListInOrder(orderProperty).getOrNull()?.value
    }
}