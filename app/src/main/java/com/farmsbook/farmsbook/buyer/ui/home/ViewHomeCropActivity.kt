package com.farmsbook.farmsbook.buyer.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
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
        backBtn.setOnClickListener {
            finish()
        }

    }
}