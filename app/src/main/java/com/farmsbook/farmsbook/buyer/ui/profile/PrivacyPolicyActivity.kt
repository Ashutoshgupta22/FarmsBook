package com.farmsbook.farmsbook.buyer.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {

            finish()

        }


    }
}