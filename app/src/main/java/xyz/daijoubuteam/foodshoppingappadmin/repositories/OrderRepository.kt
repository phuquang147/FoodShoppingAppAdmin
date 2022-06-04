package xyz.daijoubuteam.foodshoppingappadmin.repositories

import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import xyz.daijoubuteam.foodshoppingappadmin.model.Order
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.model.ProductInOrder

class OrderRepository {
    private val db = Firebase.firestore

    fun getOrderList(eateryId: String): Result<MutableLiveData<List<Order>>> {
        val orders: MutableLiveData<List<Order>> = MutableLiveData<List<Order>>()

        return try {
            val docRef = db.collection("eateries").document(eateryId)
                .collection("orders")

            docRef.addSnapshotListener { value, error ->
                if (value != null) {
                    val orderList = arrayListOf<Order>()
                    for (document in value.documents) {
                        val orderRef: DocumentReference =
                            document.data?.get("orderId") as DocumentReference
                        val customerRef = orderRef.parent.parent
                        val customerName = MutableLiveData("")
                        customerRef?.addSnapshotListener { value, error ->
                            customerName.value =
                                "${value?.get("firstname")} ${value?.get("lastname")}"
                        }
                        orderRef.addSnapshotListener { orderValue, e ->
                            val order = orderValue?.toObject(Order::class.java)
                            if (order != null) {
                                order.customerName = customerName
                                order.orderPath = orderRef
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    orderList.removeIf {
                                        it.id == order.id
                                    }
                                    orderList.add(order)
                                }
                                orders.value = orderList
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

    fun getProductListInOrder(order: Order): Result<MutableLiveData<Order>> {
        val newOrder: MutableLiveData<Order> = MutableLiveData<Order>(order)
        return try {
            val productList = arrayListOf<ProductInOrder>()
            for (orderItem in order.orderItems!!) {
                val productRef = orderItem.productId
                productRef?.addSnapshotListener { value, error ->
                    val productInOrder = orderItem.copy()
                    val product = MutableLiveData<Product>(value?.toObject(Product::class.java))
                    productInOrder.product = product
                    productList.add(productInOrder)
                    newOrder.value?.orderItems = productList
                }
            }
            Result.success(newOrder)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun updateOrderStatus(status: String?, orderPath: DocumentReference): Result<Boolean>{
        return try {
            orderPath.update("status", status)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}