package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.databinding.ActivityViewRequirementBinding

class ViewRequirementActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewRequirementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewRequirementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}