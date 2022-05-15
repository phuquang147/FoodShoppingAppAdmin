package xyz.daijoubuteam.foodshoppingappadmin.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.model.Product

class EateryRepository {
    private val db = Firebase.firestore

    suspend fun getEateryByUsername(username: String):Result<Eatery?>{
        return try {
            withTimeout(2000){
                val docRef = db.collection("eateries").whereEqualTo("username", username)
                val documentSnapShot = docRef.get().await()
                if (documentSnapShot.documents.isEmpty()) {
                    throw Exception("Wrong username")
                }
                val eatery = documentSnapShot.documents[0].toObject(Eatery::class.java)
                eatery?.documentId = documentSnapShot.documents[0].id
                Result.success(eatery)
            }
        }catch (exception: Exception){
            Result.failure(exception)
        }
    }

    suspend fun getProductList(documentId: String): Result<List<Product>?> {
        val products : MutableLiveData<List<Product>> = MutableLiveData()
        return try {
            val docRef = db.collection("eateries").document(documentId)
                .collection("products")
            val documentSnapShot = docRef.get().await()
            products.value = documentSnapShot.toObjects(Product::class.java)
            Result.success(products.value)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}