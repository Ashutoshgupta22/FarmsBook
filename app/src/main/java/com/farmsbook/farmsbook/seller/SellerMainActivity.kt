package com.farmsbook.farmsbook.seller

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityMain2Binding


class SellerMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home2, R.id.navigation_listings, R.id.navigation_buyers,R.id.navigation_profile2
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener {
            controller, destination, args ->

            when(destination.id) {

//                R.id.navigation_home2 -> navView.selectedItemId = R.id.navigation_home2
//                R.id.navigation_listings -> navView.selectedItemId = R.id.navigation_listings
//                R.id.navigation_buyers -> navView.selectedItemId = R.id.navigation_buyers
//                R.id.navigation_profile2 -> controller.navigate(R.id.navigation_profile2)
            }
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
       // finishAffinity()
    }
}