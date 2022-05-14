package xyz.daijoubuteam.foodshoppingappadmin.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class ProductsViewModel : ViewModel() {
    private val eateryRepository = EateryRepository()
    lateinit var productList: LiveData<List<Product>>

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
//        productList = MainApplication.eatery.value?.products as LiveData<List<Product>>
    }

    private fun onShowError(msg: String?){
        this._errMessage.value = msg
    }

//    fun onNavigateToSelectedProductComplete(){
//        _navigateToSelectedProduct.value = null
//    }
//
//    //show detail eatery selected
//    fun displayPropertyProduct(productSelected: Product) {
//        _navigateToSelectedProduct.value = productSelected
//    }
}