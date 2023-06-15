package com.farmsbook.farmsbook.seller.ui.listings.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.farmsbook.farmsbook.R

class ListingConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_confirmation)

        val close = findViewById<TextView>(R.id.closeBtn)

        close.setOnClickListener {
            finish()
        }
    }
}