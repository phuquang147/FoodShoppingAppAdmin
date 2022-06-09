package xyz.daijoubuteam.foodshoppingappadmin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.authentication.AuthActivity
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository
import xyz.daijoubuteam.foodshoppingappadmin.utils.Constants
import androidx.lifecycle.lifecycleScope
import com.google.firebase.ktx.Firebase

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private val eateryRepository = EateryRepository()
    private val routingComplete = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        val splashScreen = installSplashScreen()
        val keepOnScreenCondition = SplashScreen.KeepOnScreenCondition {
            return@KeepOnScreenCondition (routingComplete.value != true)
        }
        splashScreen.setKeepOnScreenCondition(keepOnScreenCondition)
        setContentView(R.layout.activity_splash_screen)
        startRouting()
    }

    private fun startRouting() {
        val sharedPref =
            this.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE) ?: return
        val username = sharedPref.getString(Constants.USERNAME, null)

        if (username != null) {
            lifecycleScope.launch {
                val eatery = eateryRepository.getEateryByUsername(username)
                MainApplication.eatery.value = eatery.getOrNull()
                if (eatery.getOrNull() == null) {
                    val intent = Intent(baseContext, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val intent = Intent(baseContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        } else {
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}