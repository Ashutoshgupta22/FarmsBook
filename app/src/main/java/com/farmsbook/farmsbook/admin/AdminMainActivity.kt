package com.farmsbook.farmsbook.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityAdminMainBinding

class AdminMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminMainBinding
    private val viewModel: AdminMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val adminPhone = preferences.getString("adminPhone", "")

        if (adminPhone?.isNotBlank() == true)
            viewModel.getAdminData(this, adminPhone)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.admin_nav_host_container) as NavHostFragment

        val navController = navHostFragment.navController
        binding.adminNavView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.adminHomeFragment,
                R.id.adminUserManagementFragment,
                R.id.adminCropListingsFragment,
                R.id.adminOffersFragment,
                R.id.adminRequirementsFragment),
            binding.adminDrawerLayout)

        binding.adminToolbar.setupWithNavController(navController, appBarConfiguration)


    }

    override fun onBackPressed() {

        if (binding.adminDrawerLayout.isDrawerOpen(GravityCompat.START))
            binding.adminDrawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }
}