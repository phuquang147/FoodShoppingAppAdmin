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
    private val orderRepository = OrderRepository()
    val selectedProperty = MutableLiveData<Order>()
    private val _message = MutableLiveData("")
    val message: LiveData<String>
        get() = _message
    var originalStatus = ""

    init {
        selectedProperty.value = orderProperty
        originalStatus = selectedProperty.value?.status.toString()
    }

    fun updateStatus() {
        return try {
            orderRepository.updateOrderStatus(
                selectedProperty.value?.status,
                selectedProperty.value?.orderPath!!
            )
            originalStatus = selectedProperty.value!!.status.toString()
            onShowMessage("Update success")
        } catch (e: Exception) {
            onShowMessage(e.message)
        }
    }

    private fun onShowMessage(msg: String?) {
        _message.value = msg
    }

    fun onShowMessageComplete() {
        _message.value = ""
    }
}