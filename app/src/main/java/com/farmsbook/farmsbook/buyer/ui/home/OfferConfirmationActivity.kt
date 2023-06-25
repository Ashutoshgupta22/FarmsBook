package com.farmsbook.farmsbook.buyer.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.MainActivity

class OfferConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_confirmation)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        val close = findViewById<TextView>(R.id.closeBtn)

        close.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this,MainActivity::class.java))

        }
    }
}