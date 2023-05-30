package com.farmsbook.farmsbook.seller.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.databinding.ActivitySellerTncBinding
import com.farmsbook.farmsbook.databinding.ActivityTnCactivityBinding

class SellerTnCActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellerTncBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerTncBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {

            finish()

        }

    }
}