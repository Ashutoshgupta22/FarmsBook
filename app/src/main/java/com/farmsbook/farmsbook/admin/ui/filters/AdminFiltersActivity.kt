package com.farmsbook.farmsbook.admin.ui.filters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityAdminFiltersBinding

class AdminFiltersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminFiltersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminFiltersBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin_filters)

    }
}