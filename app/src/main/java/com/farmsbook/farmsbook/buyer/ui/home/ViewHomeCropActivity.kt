package com.farmsbook.farmsbook.buyer.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.farmsbook.farmsbook.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewHomeCropActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_home_crop)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        val backBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val postOffer = findViewById<TextView>(R.id.postOfferBtn)

        postOffer.setOnClickListener {
            startActivity(Intent(this,PostOfferActivity::class.java).putExtra("LISTED_ID",intent.getStringExtra("LISTED_ID")).putExtra("PARENT_ID",intent.getStringExtra("PARENT_ID")))
        }


        backBtn.setOnClickListener {
            finish()
        }

    }
}