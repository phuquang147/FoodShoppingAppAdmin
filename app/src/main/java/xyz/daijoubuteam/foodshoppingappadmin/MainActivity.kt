package xyz.daijoubuteam.foodshoppingappadmin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import xyz.daijoubuteam.foodshoppingappadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupHomeActionBar()
    }

    override fun onStart() {
        super.onStart()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        NavigationUI.setupWithNavController(binding.navView, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (binding.navView.menu.findItem(destination.id) == null) {
                binding.navView.visibility = View.GONE
                binding.appBarLayout.visibility = View.VISIBLE
            }else {
                binding.navView.visibility = View.VISIBLE
                binding.appBarLayout.visibility = View.GONE
            }

        }
    }

    private fun setupHomeActionBar() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_events,
                R.id.navigation_products,
                R.id.navigation_orders,
                R.id.navigation_report,
                R.id.navigation_profile
            )
        )
        NavigationUI.setupWithNavController(binding.homeToolbar, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination, arguments: Bundle? ->
            binding.homeToolbar.setNavigationIcon(R.drawable.img_chevronleft)
            binding.homeToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setAppBarTitle(title: String){
        binding.homeToolbar.title = title
    }
}