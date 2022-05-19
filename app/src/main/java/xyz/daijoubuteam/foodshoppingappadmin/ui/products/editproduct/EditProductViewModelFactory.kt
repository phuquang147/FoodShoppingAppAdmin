package xyz.daijoubuteam.foodshoppingappadmin.ui.products.editproduct

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.daijoubuteam.foodshoppingappadmin.model.Product

class EditProductViewModelFactory(
    private val productProperty: Product,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProductViewModel::class.java)) {
            return EditProductViewModel(productProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
