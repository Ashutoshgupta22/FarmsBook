package com.farmsbook.farmsbook.buyer.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.farmsbook.farmsbook.R

class OfferConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_confirmation)

        val close = findViewById<TextView>(R.id.closeBtn)

        close.setOnClickListener {
            finish()
        }
    }
}