package xyz.daijoubuteam.foodshoppingappadmin.ui.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class ProductsViewModel : ViewModel() {
    private val eateryRepository = EateryRepository()

    private var _productList = MutableLiveData<List<Product>>(null)
    val productList: LiveData<List<Product>>
        get() = _productList

    private var _notification = MutableLiveData(0)
    val notification
        get() = _notification

    private val _errMessage = MutableLiveData("")
    val errMessage: LiveData<String>
        get() = _errMessage

    private val _navigateToSelectedProduct = MutableLiveData<Product>()
    val navigateToSelectedProduct: LiveData<Product>
        get() = _navigateToSelectedProduct

    init {
        viewModelScope.launch {
            val products = eateryRepository
                .getProductList(MainApplication.eatery.value?.id.toString())
            _productList = products.getOrNull()!!
        }
    }

//    private fun onShowError(msg: String?){
//        this._errMessage.value = msg
//    }

//    fun onNavigateToSelectedProductComplete(){
//        _navigateToSelectedProduct.value = null
//    }
//
//    //show detail eatery selected
//    fun displayPropertyProduct(productSelected: Product) {
//        _navigateToSelectedProduct.value = productSelected
//    }
}