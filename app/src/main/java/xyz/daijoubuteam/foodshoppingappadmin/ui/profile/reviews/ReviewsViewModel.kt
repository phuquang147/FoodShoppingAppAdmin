package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication

class ReviewsViewModel : ViewModel() {
    private var _reviewList = MutableLiveData<ArrayList<String>>(null)
    val reviewList: LiveData<ArrayList<String>>
        get() = _reviewList

    init {
        viewModelScope.launch {
            _reviewList.value = MainApplication.eatery.value?.reviews!!
        }
    }
}