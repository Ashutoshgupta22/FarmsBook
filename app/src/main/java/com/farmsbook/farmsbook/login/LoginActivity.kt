package com.farmsbook.farmsbook.login

import android.content.res.Configuration
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityLoginBinding
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        binding.changeLangBtn.setOnClickListener {
            showLanguageChangeDialog()
        }

        binding.signinBtn.setOnClickListener {
            binding.loginLayout.visibility = GONE
            replaceFragment(EnterNumberFragment())

        }


    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
       val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }

    private fun showLanguageChangeDialog() {
        val listItems = arrayOf("English","Hindi")
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose Language...")
        mBuilder.setSingleChoiceItems(
            listItems, -1
        ) { dialogInterface, i ->
            if (i == 0) {
                setLocale("en")
                recreate()
            }  else if (i == 1) {
                setLocale("hi")
                recreate()
            }
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }
    private fun setLocale(lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_lang", lang)
        editor.apply()
    }

    fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val lang = sharedPreferences.getString("My_lang", "")
        setLocale(lang)
    }

}