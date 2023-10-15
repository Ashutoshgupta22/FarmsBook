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
        "ashutoshgupta1422@gmail.com" to "7800303348",
        "adminankit@gmail.com" to "7740869648",
        "pankaj.farmsbook@gmail.com" to "7740869648"
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

            if (validate(username, password) ) {

                if (adminPhone.containsKey(username))
                    viewModel.authenticate(username, password,
                        adminPhone[username]!!, this)
                else
                    Toast.makeText(this, "Invalid username",
                        Toast.LENGTH_SHORT).show()
            }


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
            binding.etLoginUsername.error = "Username can not be empty"
        }
        if (password.isBlank()){
            check = false
            binding.etPassword.error = "Password can not be empty"
        }
        return check
    }
}