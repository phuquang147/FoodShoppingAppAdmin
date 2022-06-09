package xyz.daijoubuteam.foodshoppingappadmin.repositories

import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import xyz.daijoubuteam.foodshoppingappadmin.model.Order

class OrderRepository {
    private val db = Firebase.firestore

    fun getOrderList(eateryId: String): Result<MutableLiveData<List<Order>>> {
        return try {
            val docRef = db.collection("eateries").document(eateryId)
                .collection("orders")
            val orders: MutableLiveData<List<Order>> = MutableLiveData<List<Order>>()
            docRef.addSnapshotListener { value, error ->
                if (value != null) {
                    val orderList = mutableListOf<Order>()
                    for (document in value.documents) {
                        val orderRef: DocumentReference =
                            document.data?.get("orderId") as DocumentReference
                        orderRef.addSnapshotListener { orderValue, e ->
                            val order = orderValue?.toObject(Order::class.java)
                            if (order != null) {
                                order.orderPath = orderRef
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    orderList.removeIf {
                                        it.id == order.id
                                    }
                                    orderList.add(order)
                                }
                                orders.value = orderList
                                orders.value =
                                    (orders.value as MutableList<Order>).sortedByDescending { it.orderTime }
                            }
                        }
                    }
                }
            }
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun updateOrderStatus(status: String?, orderPath: DocumentReference): Result<Boolean> {
        return try {
            orderPath.update("status", status)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}