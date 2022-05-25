package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.editprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery

class EditProfileViewModel() : ViewModel(){
    val eatery = MutableLiveData<Eatery>()
    init {
        eatery.value = MainApplication.eatery.value
    }
}