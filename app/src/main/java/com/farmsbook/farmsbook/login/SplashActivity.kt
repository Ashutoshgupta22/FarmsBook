package com.farmsbook.farmsbook.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.farmsbook.farmsbook.admin.AdminMainActivity
import com.farmsbook.farmsbook.buyer.MainActivity
import com.farmsbook.farmsbook.databinding.ActivitySplashBinding
import com.farmsbook.farmsbook.seller.SellerMainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val storedLocale = AppCompatDelegate.getApplicationLocales()
        if (storedLocale.isEmpty) {
            Log.i("SplashActivity", "onCreate: storedLocale-$storedLocale")
            val appLocale = LocaleListCompat.forLanguageTags("hi")
            AppCompatDelegate.setApplicationLocales(appLocale)
        }


        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference.getInt("USER_ID", 0)
        val userROLE = sharedPreference.getBoolean("USER_ROLE", false)

        if (userId != 0) {
            if (userROLE) {
                Handler().postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 1500)
            } else {
                Handler().postDelayed({
                    startActivity(Intent(this, SellerMainActivity::class.java))
                    finish()
                }, 1500)
            }

        } else {
            val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
            val adminPhone = preferences.getString("adminPhone", "")

            if (adminPhone?.isNotBlank() == true) {
                startActivity(Intent(this, AdminMainActivity::class.java))
                finish()
            } else {
                Handler().postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, 2500)
            }
        }
    }
}