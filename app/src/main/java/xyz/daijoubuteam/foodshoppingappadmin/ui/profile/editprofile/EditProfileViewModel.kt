package xyz.daijoubuteam.foodshoppingappadmin.ui.profile.editprofile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery
import xyz.daijoubuteam.foodshoppingappadmin.model.EateryAddress
import xyz.daijoubuteam.foodshoppingappadmin.model.Product
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository

class EditProfileViewModel(eateryAddress: EateryAddress?) : ViewModel() {
    private val eateryRepository = EateryRepository()
    val eatery = MutableLiveData<Eatery>()
    var originalEatery: Eatery?
    private val _message = MutableLiveData("")
    val message: LiveData<String>
        get() = _message

    init {
        eatery.value = MainApplication.eatery.value?.copy()
        originalEatery = MainApplication.eatery.value?.copy()
        originalEatery?.addressEatery = MainApplication.eatery.value?.addressEatery?.copy()
        eatery.value?.addressEatery = eateryAddress?.copy()
    }

    fun onShowMessage(msg: String?) {
        _message.value = msg
    }

    fun onShowMessageComplete() {
        _message.value = ""
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
                eatery.value?.photoUrl = uploadImageResult.getOrNull().toString()
            } catch (e: Exception) {
                onShowMessage(e.message)
            }
        }
    }

    fun updateProfileInfo(): Boolean {
        try {
            if (eatery.value?.name.isNullOrEmpty() || eatery.value?.name?.isBlank() == true)
                throw Exception("Eatery Name Is Required")
            if (eatery.value?.work_time.isNullOrEmpty() || eatery.value?.work_time?.isBlank() == true)
                throw Exception("Work time Is Required")
            if (eatery.value?.addressEatery?.address.isNullOrEmpty() || eatery.value?.addressEatery?.address?.isBlank() == true)
                throw Exception("Eatery Address Is Required")
            if (eatery.value?.photoUrl.isNullOrEmpty())
                throw Exception("Eatery Image Is Required")
            val newEatery = Eatery(
                name = eatery.value?.name.toString(),
                description = eatery.value?.description.toString(),
                work_time = eatery.value?.work_time,
                average_rating_count = eatery.value?.average_rating_count,
                addressEatery = eatery.value?.addressEatery,
                products = eatery.value?.products,
                photoUrl = eatery.value?.photoUrl,
                username = eatery.value?.username,
                password = eatery.value?.password,
                eateryPath = eatery.value?.eateryPath
            )
            eateryRepository.updateEateryInfo(
                MainApplication.eatery.value?.id.toString(),
                newEatery
            )
            val id = MainApplication.eatery.value?.id
            MainApplication.eatery.value = newEatery
            MainApplication.eatery.value!!.id = id
            originalEatery = eatery.value
            onShowMessage("Update success")
            return true
        } catch (e: Exception) {
            onShowMessage(e.message)
            return false
        }
    }
}