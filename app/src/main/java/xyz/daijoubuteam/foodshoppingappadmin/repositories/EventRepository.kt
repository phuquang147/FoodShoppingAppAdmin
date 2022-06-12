package xyz.daijoubuteam.foodshoppingappadmin.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
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
                        event.subscribed =
                            event.idEateryList?.any { it.id == MainApplication.eatery.value?.id } == true
                        val index = eventList.indexOfFirst { it.id == event.id }
                        if (index >= 0)
                            eventList[index] = event.copy()
                        else
                            eventList.add(event)
                        events.value = eventList
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
            MainApplication.eatery.value?.let { it ->
                db
                    .collection("events")
                    .document(eventId)
                    .update(
                        "idEateryList",
                        (FieldValue.arrayUnion(MainApplication.eatery.value!!.eateryPath))
                    )
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun deleteEateryInEvent(eventId: String): Result<Boolean> {
        return try {
            MainApplication.eatery.value?.let { it ->
                db
                .collection("events")
                .document(eventId)
                .update(
                    "idEateryList",
                    (FieldValue.arrayRemove(MainApplication.eatery.value!!.eateryPath))
                )
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}