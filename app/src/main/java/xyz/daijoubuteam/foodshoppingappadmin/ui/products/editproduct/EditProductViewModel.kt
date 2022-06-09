package xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditProductViewModel(productProperty: Product, app: Application) : AndroidViewModel(app) {
    private val eateryRepository = EateryRepository()
    val ingredient = MutableLiveData("")
    private val eatery = MainApplication.eatery
    val selectedProperty = MutableLiveData<Product>()

    private val _message = MutableLiveData("")
    val message: LiveData<String>
        get() = _message

    private val _notify = MutableLiveData(-1)
    val notify: LiveData<Int>
        get() = _notify

    val price = MutableLiveData("")

    var originalProduct: Product

    init {
        selectedProperty.value = productProperty
        originalProduct = productProperty.copy()
        originalProduct.ingredients = ArrayList(selectedProperty.value!!.ingredients)
        price.value = selectedProperty.value?.price.toString()
    }

    fun onShowMessage(msg: String?) {
        _message.value = msg
    }

    fun onShowMessageComplete() {
        _message.value = ""
    }

    fun onNotify(index: Int) {
        _notify.value = index
    }

    fun onNotifySuccess() {
        _notify.value = -1
    }

    fun onAddIngredient() {
        if (eatery.value == null) {
            throw Exception("Eatery not found")
        }
        if (!ingredient.value.isNullOrEmpty() && ingredient.value!!.isNotBlank()
            && !selectedProperty.value?.ingredients?.contains(ingredient.value.toString())!!
        ) {
            selectedProperty.value?.ingredients!!.add(ingredient.value.toString())
            ingredient.value = ""
            onNotify(selectedProperty.value?.ingredients!!.size - 1)
        }
    }

    fun onResetIngredientList() {
        selectedProperty.value!!.ingredients = ArrayList(originalProduct.ingredients)
    }

    fun uploadProductImage(uri: Uri) {
        viewModelScope.launch {
            try {
                val uploadImageResult = eateryRepository.uploadDescriptionImage(uri)
                if (uploadImageResult.isFailure) {
                    throw if (uploadImageResult.exceptionOrNull() == null) uploadImageResult.exceptionOrNull()!!
                    else Exception("Upload image failed")
                } else if (uploadImageResult.isSuccess) {
                    onShowMessage("Upload successful")
                }
                selectedProperty.value?.img = uploadImageResult.getOrNull().toString()
            } catch (e: Exception) {
                onShowMessage(e.message)
            }
        }
    }

    fun onDeleteProduct() {
        eateryRepository.deleteProduct(
            MainApplication.eatery.value?.id.toString(),
            selectedProperty.value?.id.toString()
        )
    }

    fun updateProductInfo() {
        try {
            if (selectedProperty.value?.name.isNullOrEmpty() || selectedProperty.value?.name?.isBlank() == true)
                throw Exception("Product Name Is Required")
            if (selectedProperty.value?.price.toString()
                    .isNullOrEmpty() || selectedProperty.value?.price?.toString()
                    ?.isBlank() == true
            )
                throw Exception("Price Is Required")
            if (selectedProperty.value?.ingredients.isNullOrEmpty())
                throw Exception("Ingredients Is Required")
            if (selectedProperty.value?.img.toString().isNullOrEmpty())
                throw Exception("Description Image Is Required")
            val productName = selectedProperty.value?.name.toString()
            val description = selectedProperty.value?.description.toString()
            val newPrice = price.value.toString().toDoubleOrNull()
            val ingredients = selectedProperty.value?.ingredients
            val image = selectedProperty.value?.img.toString()
            val product = Product(
                name = productName,
                description = description,
                price = newPrice,
                img = image,
                ingredients = ingredients
            )
            eateryRepository.updateProductInfo(
                MainApplication.eatery.value?.id.toString(),
                selectedProperty.value?.id.toString(),
                product
            )
            originalProduct = product
            onShowMessage("Update success")
        } catch (e: Exception) {
            onShowMessage(e.message)
        }
    }
}