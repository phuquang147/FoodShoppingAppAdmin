package xyz.daijoubuteam.foodshoppingappadmin.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery

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
                Result.success(eatery)
            }
        }catch (exception: Exception){
            Result.failure(exception)
        }
    }
}