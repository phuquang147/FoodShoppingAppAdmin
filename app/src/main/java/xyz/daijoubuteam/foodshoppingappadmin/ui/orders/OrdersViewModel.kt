package xyz.daijoubuteam.foodshoppingappadmin.ui.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.repositories.OrderRepository

class OrdersViewModel : ViewModel() {
    private val orderRepository = OrderRepository()
    private var _orderList = MutableLiveData<List<Order>>(null)
    val orderList: LiveData<List<Order>>
        get() = _orderList

    init {
        viewModelScope.launch {
            val orders = orderRepository.getOrderList(MainApplication.eatery.value?.id.toString())
            _orderList = orders.getOrNull()!!
        }
    }
}