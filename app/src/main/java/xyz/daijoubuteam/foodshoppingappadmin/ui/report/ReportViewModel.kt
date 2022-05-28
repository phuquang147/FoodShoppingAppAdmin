package xyz.daijoubuteam.foodshoppingappadmin.ui.report

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.repositories.OrderRepository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

class ReportViewModel : ViewModel() {
    private val orderRepository = OrderRepository()
    private val _errMessage = MutableLiveData("")
    val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: DateFormat = SimpleDateFormat("MM")
    private val date =  Date()
    val month =  MutableLiveData(dateFormat.format(date).toString())
    val year = MutableLiveData(calendar.get(Calendar.YEAR).toString())
    var orderList = MutableLiveData<List<Order>>(null)
    var wholeOrderList = MutableLiveData<List<Order>>(null)

    var filteredOrders = MutableLiveData<List<Order>>(null)

    init {
        viewModelScope.launch {
            var orders = orderRepository.getOrderList(MainApplication.eatery.value?.id.toString())
            wholeOrderList = orders.getOrNull()!!
            filteredOrders = orders.getOrNull()!!
            if(month.value?.get(0)?.equals('0') == true) month.value = month.value!![1].toString()
        }
    }

    private fun onShowError(msg: String?){
        this._errMessage.value = msg
    }
}