package xyz.daijoubuteam.foodshoppingappadmin.ui.products.newproduct

import android.net.Uri
import android.util.Log
import androidx.core.view.isEmpty
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class NewProductViewModel : ViewModel() {
    val eateryRepository = EateryRepository()
    var product = MutableLiveData<Product>()
    private val eatery = MainApplication.eatery
    private val _ingredients : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val ingredients: LiveData<ArrayList<String>>
        get() = _ingredients
    private val _message = MutableLiveData("")
    val message: LiveData<String>
        get() = _message
    val ingredient = MutableLiveData("")
    val name = MutableLiveData("")
    val price = MutableLiveData("")
    val description = MutableLiveData("")
    val image = MutableLiveData("")
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
                else if(uploadImageResult.isSuccess) {
                        onShowMessage("Upload successful")
                }
                Log.i("image", uploadImageResult.getOrNull().toString())
                image.value = uploadImageResult.getOrNull().toString()
            } catch (e: Exception) {
                onShowMessage(e.message)
            }
        }
    }
    fun onAddProduct() {
            try {
                if(name.value.isNullOrEmpty() || name.value!!.isBlank())
                    throw Exception("Product Name Is Required")
                if(price.value.isNullOrEmpty() || price.value!!.isBlank())
                    throw Exception("Price Is Required")
                if(_ingredients.value.isNullOrEmpty())
                    throw Exception("Ingredients Is Required")
                if(image.value.isNullOrEmpty())
                    throw Exception("Description Image Is Required")
                val product = Product(name = name.value, description = description.value, newPrice = price.value!!.toDoubleOrNull()
                    , img = image.value, ingredients = _ingredients.value)
                eateryRepository.createNewProduct(MainApplication.eatery.value?.id.toString(), product)
                onShowMessage("Add product success")
                name.value = ""
                price.value = ""
                description.value = ""
                _ingredients.value = arrayListOf()
                image.value = ""
            } catch (e: Exception) {
                onShowMessage(e.message)
            }
    }
}