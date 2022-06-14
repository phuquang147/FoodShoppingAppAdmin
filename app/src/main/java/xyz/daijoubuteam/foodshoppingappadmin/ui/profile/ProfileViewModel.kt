package xyz.daijoubuteam.foodshoppingappadmin.ui.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class ProfileViewModel : ViewModel() {
    var eatery = MutableLiveData<Eatery>(MainApplication.eatery.value)
}