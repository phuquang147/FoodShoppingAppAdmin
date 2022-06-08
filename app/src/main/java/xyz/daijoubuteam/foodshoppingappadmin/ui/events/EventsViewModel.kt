package xyz.daijoubuteam.foodshoppingappadmin.ui.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.model.Event
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EventRepository

class EventsViewModel: ViewModel() {
    private val eventRepository = EventRepository()

    private var _eventList = MutableLiveData<List<Event>>(null)
    val eventList: LiveData<List<Event>>
        get() = _eventList

    private val _message = MutableLiveData("")
    val message: LiveData<String>
        get() = _message

    init {
        viewModelScope.launch {
            val events = eventRepository.getEventList()
            _eventList = events.getOrNull()!!
        }
    }

    fun onSubscribe(event: Event) {
        try {
            event.id?.let { eventRepository.addEateryToEvent(it) }
            onShowMessage("Subscribe successful")
        } catch (e: Exception) {
            onShowMessage(e.message)
        }
    }

    fun onUnSubscribe(event: Event) {
        try {
            event.id?.let { eventRepository.deleteEateryInEvent(it) }
            onShowMessage("Unsubscribe successful")
        } catch (e: Exception) {
            onShowMessage(e.message)
        }
    }

    fun onShowMessage(msg: String?) {
        _message.value = msg
    }

    fun onShowMessageComplete() {
        _message.value = ""
    }
}