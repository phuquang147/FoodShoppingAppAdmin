package xyz.daijoubuteam.foodshoppingappadmin.authentication.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainActivity
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository
import kotlin.math.log

class LoginViewModel: ViewModel() {
    private val eateryRepository = EateryRepository()
    private val _loginResult = MutableLiveData<Result<Eatery?>?>(null)
    private val _navigateToForgetPassword = MutableLiveData(false)
    private val _message = MutableLiveData("")

    val loginResult: LiveData<Result<Eatery?>?>
        get() = _loginResult
    val navigateToForgetPassword: LiveData<Boolean>
        get() = _navigateToForgetPassword
    val message: LiveData<String>
        get() = _message

    val username =  MutableLiveData("")
    val password = MutableLiveData("")

    fun onNavigateToForgetPassword(){
        _navigateToForgetPassword.value = true
    }

    fun onNavigateToForgetPasswordComplete(){
        _navigateToForgetPassword.value = false
    }

    fun onLoginWithUsernameAndPassword(){
        if(username.value.isNullOrEmpty() || password.value.isNullOrEmpty())
        {
            val exception = Exception("Illegal username or password")
            _loginResult.value = Result.failure(exception)
            return
        }

        viewModelScope.launch {
            _loginResult.value = eateryRepository.getEateryByUsername(username.toString())
        }
    }

    fun onLoginComplete(){
//        MainApplication.eatery.value = _loginResult.value?.getOrNull()
        MainApplication.eatery.value = Eatery()
        _loginResult.value = null
    }

    fun onShowMessage(msg: String){
        _message.value = msg
    }

    fun onShowMessageComplete(){
        _message.value = ""
    }
}