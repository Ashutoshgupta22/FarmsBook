package com.farmsbook.farmsbook.buyer.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.farmsbook.farmsbook.databinding.ActivityHomeSearchBinding

class HomeSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        binding.searchBtn.setOnClickListener {
            Toast.makeText(this,"Clicked Search Button",Toast.LENGTH_SHORT).show();
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}