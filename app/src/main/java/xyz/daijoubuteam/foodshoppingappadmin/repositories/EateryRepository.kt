package xyz.daijoubuteam.foodshoppingappadmin.repositories

import android.net.Uri
import android.provider.SyncStateContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import com.google.firebase.storage.ktx.storage
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.utils.Constants

class EateryRepository {
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    suspend fun getEateryByUsername(username: String): Result<Eatery?> {
        return try {
            withTimeout(2000) {
                val docRef = db.collection("eateries").whereEqualTo("username", username)
                val documentSnapShot = docRef.get().await()
                if (documentSnapShot.documents.isEmpty()) {
                    throw Exception("Wrong username")
                }
                val eatery = documentSnapShot.documents[0].toObject(Eatery::class.java)
                eatery?.eateryId = documentSnapShot.documents[0].id
                Result.success(eatery)
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    fun getProductList(documentId: String): Result<MutableLiveData<List<Product>>> {
        val products: MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
        return try {
            val docRef = db.collection("eateries").document(documentId)
                .collection("products")
            docRef.addSnapshotListener { value, error ->
                val productList = arrayListOf<Product>()
                for (document in value!!.documents) {
                    val product = document.toObject(Product::class.java)
                    product?.let {
                        product.id = document.id
                        productList.add(productList.size, product)
                    }
                }
                products.value = productList
            }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadDescriptionImage(uri: Uri): Result<String> {
        return try {
            val storageRef = storage.reference
            val imageRef = storageRef.child("images/${uri.lastPathSegment}")
            imageRef.putFile(uri).await()
            val downloadUrl = imageRef.downloadUrl.await()
            Log.i("uri", downloadUrl.toString())
            Result.success(downloadUrl.toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun createNewProduct(documentId: String, product: Product): Result<Product> {
        return try {
            db.collection("eateries").document(documentId).collection("products").add(product)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun deleteProduct(eateryId: String, productId: String): Result<String> {
        return try {
            db.collection("eateries").document(eateryId)
                .collection("products").document(productId)
                .delete()
                .addOnSuccessListener {
                    Result.success("Delete product success")
                }
                .addOnFailureListener {
                    throw Exception("Delete product failed")
                }
            Result.success("Delete product success!")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun updateProductInfo(eateryId: String, productId: String, product: Product): Result<Boolean> {
        return try {
            db.collection("eateries").document(eateryId).collection("products")
                .document(productId)
                .set(product)
                .addOnSuccessListener {
                    Result.success("Update product success")
                }
                .addOnFailureListener {
                    throw Exception("Update product failed")
                }
            Result.success(true)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    fun updateEateryInfo(eateryId: String, eatery: Eatery): Result<Boolean> {
        return try {
            db.collection("eateries").document(eateryId)
                .set(eatery)
                .addOnSuccessListener {
                    Result.success("Update eatery success")
                }
                .addOnFailureListener {
                    throw Exception("Update eatery failed")
                }
            Result.success(true)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}