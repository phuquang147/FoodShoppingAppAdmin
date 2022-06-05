package xyz.daijoubuteam.foodshoppingappadmin.ui.report

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.repositories.OrderRepository
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ReportViewModel : ViewModel() {
    private val orderRepository = OrderRepository()
    val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: DateFormat = SimpleDateFormat("MM")
    private val date = Date()
    val month = MutableLiveData(dateFormat.format(date).toString())
    val year = MutableLiveData(calendar.get(Calendar.YEAR).toString())
    private var wholeOrderList = MutableLiveData<List<Order>>(null)
    val mediatorLiveData = MediatorLiveData<List<Order>>()
    var totalRevenue: Double = 0.0
    var totalRevenueLabel = MutableLiveData("")
    var totalOrderLabel = MutableLiveData("")

    val filteredOrders = MutableLiveData<List<Order>>()

    init {
        viewModelScope.launch {
            var orders = orderRepository.getOrderList(MainApplication.eatery.value?.id.toString())
            wholeOrderList = orders.getOrNull()!!
            createFilteredOrders()
            if (month.value?.get(0)?.equals('0') == true) month.value = month.value!![1].toString()
        }
    }

    private fun createFilteredOrders() {
        mediatorLiveData.addSource(wholeOrderList) {
            filteredOrders.value = it.filter { order ->
                (order.orderTime?.toDate()?.month?.plus(1)).toString() == month.value
                        && (order.orderTime?.toDate()?.year?.plus(1900)).toString() == year.value
            }.sortedBy { filteredOrder -> filteredOrder.orderTime }
            updateTotalRevenue()
        }
        mediatorLiveData.addSource(month) {
            filteredOrders.value = wholeOrderList.value?.filter { order ->
                (order.orderTime?.toDate()?.month?.plus(1)).toString() == it
                        && (order.orderTime?.toDate()?.year?.plus(1900)).toString() == year.value
            }?.sortedBy { filteredOrder -> filteredOrder.orderTime }
            updateTotalRevenue()
        }
        mediatorLiveData.addSource(year) {
            filteredOrders.value = wholeOrderList.value?.filter { order ->
                (order.orderTime?.toDate()?.month?.plus(1)).toString() == month.value
                        && (order.orderTime?.toDate()?.year?.plus(1900)).toString() == it
            }?.sortedBy { filteredOrder -> filteredOrder.orderTime }
            updateTotalRevenue()
        }
    }

    private fun updateTotalRevenue(){
        totalRevenue = 0.0
        if(filteredOrders.value != null && filteredOrders.value?.size!! > 0){
            for(order in filteredOrders.value!!){
                totalRevenue += order.totalPrice!!
            }
            totalOrderLabel.value = filteredOrders.value!!.size.toString()
        }
        else{
            totalOrderLabel.value = "0"
        }
        totalRevenueLabel.value = NumberFormat.getCurrencyInstance().format(totalRevenue)
    }
}