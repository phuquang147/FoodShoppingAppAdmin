package xyz.daijoubuteam.foodshoppingappadmin.authentication


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import xyz.daijoubuteam.foodshoppingappadmin.MainActivity
import xyz.daijoubuteam.foodshoppingappadmin.MainApplication
import xyz.daijoubuteam.foodshoppingappadmin.R
import xyz.daijoubuteam.foodshoppingappadmin.databinding.ActivityAuthBinding
import xyz.daijoubuteam.foodshoppingappadmin.utils.Constants.USERNAME
import xyz.daijoubuteam.foodshoppingappadmin.utils.Constants.USER_PREF

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        val sharedPref = this.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)

        MainApplication.eatery.observe(this) {
            if (it != null) {
                with(sharedPref?.edit()) {
                    this?.putString(USERNAME, it.username)
                    this?.apply()
                }
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}