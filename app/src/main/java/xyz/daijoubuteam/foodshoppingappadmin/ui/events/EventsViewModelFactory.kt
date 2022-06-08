package xyz.daijoubuteam.foodshoppingappadmin.ui.events
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EventsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventsViewModel::class.java)) {
            return EventsViewModel() as T
        }
        throw IllegalArgumentException("Unknown EventsViewModel class")
    }
}