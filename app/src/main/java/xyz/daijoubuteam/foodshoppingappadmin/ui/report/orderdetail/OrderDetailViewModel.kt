package xyz.daijoubuteam.foodshoppingappadmin.ui.report.orderdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.repositories.OrderRepository
import java.text.NumberFormat

class OrderDetailViewModel(orderProperty: Order) : ViewModel() {
    val selectedProperty = MutableLiveData<Order>()
    var total = ""

    init {
        selectedProperty.value = orderProperty
        total = selectedProperty.value?.totalPrice.toString()
    }
}