package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.EateryAddress
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import java.lang.Exception
import java.lang.IllegalArgumentException

class EditProfileViewModelFactory(private val eateryAddress: EateryAddress?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java))
            return EditProfileViewModel(eateryAddress) as T
        throw IllegalArgumentException("Unknown EditProfileViewModel class")
    }
}