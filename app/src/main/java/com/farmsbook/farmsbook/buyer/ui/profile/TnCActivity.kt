package com.farmsbook.farmsbook.buyer.ui.profile

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.databinding.ActivityTnCactivityBinding

class TnCActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTnCactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTnCactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {

            finish()

        }

    }
}