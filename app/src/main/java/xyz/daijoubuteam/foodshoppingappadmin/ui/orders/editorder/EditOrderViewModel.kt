package xyz.daijoubuteam.foodshoppingappadmin.ui.orders.editorder

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.repositories.OrderRepository

class EditOrderViewModel(orderProperty: Order, app: Application) : AndroidViewModel(app) {
    val orderRepository = OrderRepository()
    val selectedProperty = MutableLiveData<Order>()
    private val _message = MutableLiveData("")
    val message: LiveData<String>
        get() = _message

    init {
        selectedProperty.value =
            orderRepository.getProductListInOrder(orderProperty).getOrNull()?.value
    }

    fun updateStatus(){
        try {
            orderRepository.updateOrderStatus(
                selectedProperty.value?.status,
                selectedProperty.value?.orderPath!!
            )
            onShowMessage("Update success")
        } catch (e: Exception) {
            onShowMessage(e.message)
        }
    }

    fun onShowMessage(msg: String?) {
        _message.value = msg
    }

    fun onShowMessageComplete(){
        _message.value = ""
    }
}