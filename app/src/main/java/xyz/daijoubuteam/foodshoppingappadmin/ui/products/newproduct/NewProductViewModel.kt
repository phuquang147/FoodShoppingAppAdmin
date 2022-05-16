package xyz.daijoubuteam.foodshoppingappadmin.ui.products.newproduct

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class NewProductViewModel : ViewModel() {
    private val eateryRepository = EateryRepository()
    var product = MutableLiveData<Product>()
    private val eatery = MainApplication.eatery
    private val _message = MutableLiveData("")
    private val _ingredients : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val ingredients: LiveData<ArrayList<String>>
        get() = _ingredients
    val message: LiveData<String>
        get() = _message
    val ingredient = MutableLiveData("")
    init {
        _ingredients.value = arrayListOf()
    }
    fun onAddIngredient() {
        viewModelScope.launch {
            try {
                if (eatery.value == null) {
                    throw Exception("Eatery not found")
                }
                if(!ingredient.value.isNullOrEmpty() && ingredient.value!!.isNotBlank()
                    && !_ingredients.value?.contains(ingredient.value.toString())!!
                ){
                    _ingredients.value?.add(ingredient.value.toString())
                    ingredient.value = ""
                }

            } catch (exception: Exception) {
                exception.message?.let { onShowMessage(it) }
            }
        }
    }
    fun onShowMessage(msg: String?){
        _message.value = msg
    }

    fun uploadProductImage(uri: Uri) {
        viewModelScope.launch {
            try {
                val uploadImageResult = eateryRepository.uploadDescriptionImage(uri)
                if (uploadImageResult.isFailure) {
                    throw if (uploadImageResult.exceptionOrNull() == null) uploadImageResult.exceptionOrNull()!!
                    else Exception("Upload image failed")
                }
                product.value?.img = uploadImageResult.getOrNull().toString()
//                user.value?.let {
//                    val updateResult = userRepository.updateCurrentUserInfo(it)
//                    if (updateResult.isFailure)
//                        throw if (uploadAvatarResult.exceptionOrNull() == null) uploadAvatarResult.exceptionOrNull()!!
//                        else Exception("Upload image failed")
//                    else if(updateResult.isSuccess) {
//                        onShowMessage("Upload successful")
//                    }
//                }
            } catch (e: Exception) {
                onShowMessage(e.message)
            }
        }
    }
}