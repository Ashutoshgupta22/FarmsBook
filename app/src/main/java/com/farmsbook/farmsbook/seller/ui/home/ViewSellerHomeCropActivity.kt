package com.farmsbook.farmsbook.seller.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.databinding.ActivityViewSellerHomeCropBinding

class ViewSellerHomeCropActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewSellerHomeCropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSellerHomeCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}