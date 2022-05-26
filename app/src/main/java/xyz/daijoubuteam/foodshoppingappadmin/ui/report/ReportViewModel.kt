package xyz.daijoubuteam.foodshoppingappadmin.ui.report

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery

class ReportViewModel : ViewModel() {
    private var _eatery = MutableLiveData<Eatery>(null)
    val eatery: LiveData<Eatery>
        get() = _eatery

    val month =  MutableLiveData("1")
    val year = MutableLiveData("2021")

    init {
        viewModelScope.launch {
            _eatery = MainApplication.eatery
        }
    }
}