package xyz.daijoubuteam.foodshoppingappadmin.authentication


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
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
                with (sharedPref?.edit()) {
                    this?.putString(USERNAME, it.username)
                    this?.apply()
                }
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
//        setupActionBar()
    }

//    private fun setupActionBar(){
//        binding.authToolbar.setTitleTextColor(Color.TRANSPARENT)
//        setSupportActionBar(binding.authToolbar)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.login_fragment_container_view) as NavHostFragment
//        val navController = navHostFragment.findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        NavigationUI.setupWithNavController(binding.authToolbar, navController, appBarConfiguration)
//        navController.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination, arguments: Bundle? ->
//            setupActionBarUI()
//            binding.authToolbar.setNavigationIcon(R.drawable.img_chevronleft)
//        }
//    }

//    private fun setupActionBarUI(){
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.img_chevronleft)
//    }
}