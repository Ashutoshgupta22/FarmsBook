package com.farmsbook.farmsbook.admin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.AdminMainActivity
import com.farmsbook.farmsbook.databinding.ActivityAdminOtpBinding

class AdminOtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminOtpBinding
    private val viewModel: AdminOtpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = intent.getStringExtra("sessionId")
        val phone = intent.getStringExtra("phone")

        binding.btnVerify.setOnClickListener {

            val otp = binding.etOtp.text.toString()

            if (otp.isBlank()) binding.etOtp.error = "OTP can not be empty"
            else {
                if (phone != null && session!= null) {
                    viewModel.validate(this, phone, session, otp )
                }
            }
        }

        viewModel.valid.observe(this) {

            val edit = getSharedPreferences(packageName, MODE_PRIVATE).edit()
            edit.putString("adminPhone", phone)
            edit.apply()

            val intent = Intent(this, AdminMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        viewModel.error.observe(this) {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}