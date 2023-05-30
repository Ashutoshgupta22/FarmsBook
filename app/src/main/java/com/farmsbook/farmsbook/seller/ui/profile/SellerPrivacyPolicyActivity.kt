package com.farmsbook.farmsbook.seller.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.databinding.ActivityPrivacyPolicyBinding
import com.farmsbook.farmsbook.databinding.ActivitySellerPrivacyPoilicyBinding

class SellerPrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySellerPrivacyPoilicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerPrivacyPoilicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {

            finish()

        }


    }
}