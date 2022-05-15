package xyz.daijoubuteam.foodshoppingappadmin.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery

class EateryRepository {
    private val db = Firebase.firestore

    suspend fun getEateryByUsername(username: String):Result<Eatery?>{
        return try {
            withTimeout(2000){
                val docRef = db.collection("eateries").document(username)
                val documentSnapShot = docRef.get().await()
                val eatery = documentSnapShot.toObject<Eatery>()
                Result.success(eatery)
            }
        }catch (exception: Exception){
            Result.failure(exception)
        }
    }

//    fun getCurrentEateryLiveData(): Result<LiveData<Eatery>>{
//        return try {
//            val docRef = db.collection("eateries").document("test@gmail.com")
//            val eatery = MutableLiveData<Eatery>()
//            docRef.addSnapshotListener { value, error ->
//                eatery.value = value?.toObject()
//            }
//            Result.success(eatery)
//        }catch (e: Exception){
//            Result.failure(e)
//        }
//    }
}