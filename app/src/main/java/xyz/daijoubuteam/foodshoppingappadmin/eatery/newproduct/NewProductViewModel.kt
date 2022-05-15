package xyz.daijoubuteam.foodshoppingappadmin.eatery.newproduct

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class NewProductViewModel : ViewModel() {
    private val eatery = MainApplication.eatery
    private val _message = MutableLiveData("")
    private val _ingredients = MutableLiveData<ArrayList<String>>()
    val ingredients: LiveData<ArrayList<String>>
        get() = _ingredients
    val message: LiveData<String>
        get() = _message
    val ingredient = MutableLiveData("")

    fun onAddIngredient() {
        viewModelScope.launch {
            try {
                if (eatery.value == null) {
                    throw Exception("Eatery not found")
                }
                if(!ingredient.value.isNullOrEmpty() && )
                _ingredients.value?.add(ingredient.toString())
                ingredient.value = ""
            } catch (exception: Exception) {
                exception.message?.let { onShowMessage(it) }
            }
        }
    }
    fun onShowMessage(msg: String){
        _message.value = msg
    }

    fun uploadUserAvatar(uri: Uri) {
        viewModelScope.launch {
            try {
                val uploadAvatarResult = userRepository.uploadAvatar(uri)
                if (uploadAvatarResult.isFailure) {
                    throw if (uploadAvatarResult.exceptionOrNull() == null) uploadAvatarResult.exceptionOrNull()!!
                    else Exception("Upload image failed")
                }
                user.value?.photoUrl = uploadAvatarResult.getOrNull().toString()
                user.value?.let {
                    val updateResult = userRepository.updateCurrentUserInfo(it)
                    if (updateResult.isFailure)
                        throw if (uploadAvatarResult.exceptionOrNull() == null) uploadAvatarResult.exceptionOrNull()!!
                        else Exception("Upload image failed")
                    else if(updateResult.isSuccess) {
                        onShowMessage("Upload successful")
                    }
                }
            } catch (e: Exception) {
                onShowMessage(e.message)
            }
        }
    }
}