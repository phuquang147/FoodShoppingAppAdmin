package xyz.daijoubuteam.foodshoppingappadmin.ui.report.orderdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.repositories.OrderRepository
import java.text.NumberFormat

class OrderDetailViewModel(orderProperty: Order) : ViewModel() {
    private val orderRepository = OrderRepository()
    val selectedProperty = MutableLiveData<Order>()
    var total = ""

    init {
        selectedProperty.value =
            orderRepository.getProductListInOrder(orderProperty).getOrNull()?.value
        total = NumberFormat.getCurrencyInstance().format(selectedProperty.value?.totalPrice)
    }
}