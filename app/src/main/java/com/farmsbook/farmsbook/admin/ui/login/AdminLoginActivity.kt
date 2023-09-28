package com.farmsbook.farmsbook.admin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityAdminLoginBinding

class AdminLoginActivity : AppCompatActivity() {

    private val adminPhone = hashMapOf(
        "ashutoshgupta1422@gmail.com" to "9369771101",
        "ankitsharma97194@gmail.com" to "7740869648"
    )

    private lateinit var binding: ActivityAdminLoginBinding
    private val viewModel: AdminLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            val username = binding.etLoginUsername.text.toString()
            val password = binding.etPassword.text.toString()

//            val preferences = getSharedPreferences(packageName, MODE_PRIVATE)
//            val editor = preferences.edit()

            if (validate(username, password))
                viewModel.authenticate(username, password,
                    adminPhone[username]!!, this)

            viewModel.sessionId.observe(this) {

                it?.let {
//                    editor.putString("adminSessionId", it)
//                    editor.apply()
                    val intent = Intent(this, AdminOtpActivity::class.java)
                    intent.putExtra("sessionId", it)
                    intent.putExtra("phone", adminPhone[username])
                    startActivity(intent)
                }
            }

            viewModel.error.observe(this) {
                it?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validate(username: String, password: String): Boolean {
        var check = true

        if (username.isBlank()) {
            check = false
            binding.etLoginUsername.error = "Enter valid username"
        }
        if (password.isBlank()){
            check = false
            binding.etLoginUsername.error = "Password can not be empty"
        }
        return check
    }
}