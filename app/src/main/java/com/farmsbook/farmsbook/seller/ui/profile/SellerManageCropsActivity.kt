package com.farmsbook.farmsbook.seller.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.databinding.ActivityManageCropsBinding

class SellerManageCropsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageCropsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageCropsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}