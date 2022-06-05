package xyz.daijoubuteam.foodshoppingappadmin

import android.app.Application
import androidx.lifecycle.MutableLiveData
import xyz.daijoubuteam.foodshoppingappadmin.model.Eatery

class MainApplication : Application() {
    companion object {
        var eatery: MutableLiveData<Eatery> = MutableLiveData(null)
    }
}