package xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class EditProductViewModel(productProperty: Product, app: Application): AndroidViewModel(app) {
    val eateryRepository = EateryRepository()

    private val _selectedProperty = MutableLiveData<Product>()
    val selectedProperty: LiveData<Product>
        get() = _selectedProperty
    var ingredientString: String

    init {
        _selectedProperty.value = productProperty
        ingredientString = when(_selectedProperty.value!!.ingredients?.joinToString(", ")){
            null -> ""
            else -> _selectedProperty.value!!.ingredients?.joinToString(", ").toString()
        }
    }

    fun onDeleteProduct() {
        eateryRepository.deleteProduct(MainApplication.eatery.value?.id.toString(), _selectedProperty.value?.id.toString())
    }
}