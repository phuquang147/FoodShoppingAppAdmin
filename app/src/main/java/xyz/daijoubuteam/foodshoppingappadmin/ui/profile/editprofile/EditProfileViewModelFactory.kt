package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException

class EditProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java))
            return EditProfileViewModel() as T
        throw IllegalArgumentException("Unknown EditProfileViewModel class")
    }
}