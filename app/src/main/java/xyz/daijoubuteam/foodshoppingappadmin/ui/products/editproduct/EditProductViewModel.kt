package xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.daijoubuteam.foodshoppingappadmin.model.Product

class EditProductViewModel(productProperty: Product, app: Application): AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Product>()
    val selectedProperty: LiveData<Product>
        get() = _selectedProperty

    init {
        _selectedProperty.value = productProperty
    }
}