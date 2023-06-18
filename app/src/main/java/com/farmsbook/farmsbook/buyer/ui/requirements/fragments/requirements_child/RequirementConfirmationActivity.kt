package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.farmsbook.farmsbook.R

class RequirementConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requirement_confirmation)

        supportActionBar?.hide()
        val close = findViewById<TextView>(R.id.closeBtn)

        close.setOnClickListener {
            finish()
        }
    }
}