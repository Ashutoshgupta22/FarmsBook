package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.offers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.databinding.ActivityViewCounterOfferBinding

class ViewCounterOfferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewCounterOfferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCounterOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}