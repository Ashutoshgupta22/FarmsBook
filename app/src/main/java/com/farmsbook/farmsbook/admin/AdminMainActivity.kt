package com.farmsbook.farmsbook.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.AdminData.Companion.currentAdmin
import com.farmsbook.farmsbook.databinding.ActivityAdminMainBinding
import com.farmsbook.farmsbook.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
            setOf(
                R.id.adminHomeFragment,
                R.id.adminUserManagementFragment,
                R.id.adminCropListingsFragment,
                R.id.adminOffersFragment,
                R.id.adminRequirementsFragment
            ),
            binding.adminDrawerLayout
        )

        binding.adminToolbar.setupWithNavController(navController, appBarConfiguration)

        viewModel.setAdminData.observe(this) {
            it?.let {
                if (it) {

                    val headerView = binding.adminNavView.getHeaderView(0)

                    val tvName = headerView.findViewById<TextView>(R.id.tv_name_nav)
                    tvName.text = currentAdmin.name

                    val tvEmail = headerView.findViewById<TextView>(R.id.tv_email_nav)
                    tvEmail.text = currentAdmin.email

                    val ivImage = headerView.findViewById<ImageView>(R.id.iv_profile_image_nav)
                    val imagePath = currentAdmin.imagePath

                    Glide.with(this)
                        .load(imagePath)
                        .centerCrop()
                        .into(ivImage)
                }
            }
        }
    }


    override fun onBackPressed() {

        if (binding.adminDrawerLayout.isDrawerOpen(GravityCompat.START))
            binding.adminDrawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }
}