package xyz.daijoubuteam.foodshoppingappadmin.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Event

class EventRepository {
    private val db = Firebase.firestore

    fun getEventList(): Result<MutableLiveData<List<Event>>> {
        val events: MutableLiveData<List<Event>> = MutableLiveData<List<Event>>()
        return try {
            val docRef = db.collection("events")
            docRef.addSnapshotListener { value, _ ->
                val eventList = arrayListOf<Event>()

                for (eventDoc in value?.documents!!) {
                    val event = eventDoc.toObject(Event::class.java)
                    if (event != null) {
                        docRef
                        .document(eventDoc.id)
                        .collection("eateries")
                        .whereEqualTo("eateryId", MainApplication.eatery.value?.id)
                        .addSnapshotListener { valueEvent, _ ->
                            if (valueEvent != null) {
                                event.subscribed = (valueEvent.documents.isNotEmpty())
                                val index = eventList.indexOfFirst { it.id == event.id }
                                if (index >= 0)
                                    eventList[index] = event.copy()
                                else
                                    eventList.add(event)
                            }
                            events.value = eventList
                        }
                    }
                }
            }
            Result.success(events)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun addEateryToEvent(eventId: String): Result<Boolean> {
        return try {
            MainApplication.eatery.value?.let {
                db
                .collection("events")
                .document(eventId)
                .collection("eateries")
                .add(it)
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun deleteEateryInEvent(eventId: String): Result<Boolean> {
        var result = false
        return try {
            val docRef =
                db
                .collection("events")
                .document(eventId)
                .collection("eateries")

            docRef
                .whereEqualTo("eateryId", MainApplication.eatery.value?.id)
                .get()
                .addOnCompleteListener {
                    if (it.result.documents.isEmpty()) {
                        throw Exception("Unsubscribe failed!")
                    }
                    docRef
                    .document(it.result.documents[0].id)
                    .delete()
                    .addOnSuccessListener {
                        result = true
                    }
                }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}