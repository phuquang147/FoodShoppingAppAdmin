package xyz.daijoubuteam.foodshoppingappadmin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.authentication.AuthActivity
import xyz.daijoubuteam.foodshoppingappadmin.repositories.EateryRepository
import xyz.daijoubuteam.foodshoppingappadmin.utils.Constants
import androidx.lifecycle.lifecycleScope

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private val eateryRepository = EateryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val sharedPref =
            this.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE) ?: return
        val username = sharedPref.getString(Constants.USERNAME, null)

        if (username != null) {
            lifecycleScope.launch {
                val eatery = eateryRepository.getEateryByUsername(username)
                MainApplication.eatery.value = eatery.getOrNull()
            }
        }

        Handler().postDelayed({
            MainApplication.eatery.observe(this) {
                if (it == null) {
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }, 2000)
    }
}