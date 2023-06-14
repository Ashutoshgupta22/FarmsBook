package com.farmsbook.farmsbook.seller.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.farmsbook.farmsbook.databinding.ActivityEditProfileBinding
import com.farmsbook.farmsbook.databinding.ActivitySellerEditProfileBinding

class SellerEditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellerEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        supportActionBar?.hide()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}