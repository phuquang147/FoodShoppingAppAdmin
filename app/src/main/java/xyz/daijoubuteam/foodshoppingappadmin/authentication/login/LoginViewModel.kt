package xyz.daijoubuteam.foodshoppingappadmin.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class LoginViewModel : ViewModel() {
    private val eateryRepository = EateryRepository()
    private val _loginResult = MutableLiveData<Result<Eatery?>?>(null)
    private val _message = MutableLiveData("")

    val loginResult: LiveData<Result<Eatery?>?>
        get() = _loginResult
    val message: LiveData<String>
        get() = _message

    val username = MutableLiveData("")
    val password = MutableLiveData("")

    fun onLoginWithUsernameAndPassword() {
        if (username.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            val exception = Exception("Illegal username or password")
            _loginResult.value = Result.failure(exception)
            return
        }

        viewModelScope.launch {
            _loginResult.value = eateryRepository.getEateryByUsername(username.value.toString())
        }
    }

    fun onLoginComplete() {
        MainApplication.eatery.value = _loginResult.value?.getOrNull()
        _loginResult.value = null
    }

    fun onShowMessage(msg: String) {
        _message.value = msg
    }

    fun onShowMessageComplete() {
        _message.value = ""
    }
}