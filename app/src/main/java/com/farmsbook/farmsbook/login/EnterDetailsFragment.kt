package com.farmsbook.farmsbook.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import com.farmsbook.farmsbook.MainActivity
import com.farmsbook.farmsbook.R
import com.google.android.material.textfield.TextInputEditText

class EnterDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enter_details, container, false)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)

        val name  = view.findViewById<TextInputEditText>(R.id.editText_Name)
        val email  = view.findViewById<TextInputEditText>(R.id.editText_Email)
        val password = view.findViewById<TextInputEditText>(R.id.editText_Password)
        val confirmPassword  = view.findViewById<TextInputEditText>(R.id.editText_ConfirmPassword)




        view.findViewById<TextView>(R.id.confirmBtn).setOnClickListener {

            if(TextUtils.isEmpty(name.text))
            {
                name.error = "Enter a valid Name"
                name.requestFocus()
            }else if(TextUtils.isEmpty(email.text)){
                name.error = "Enter a valid Email id"
                name.requestFocus()
            }
            else if(TextUtils.isEmpty(password.text)){
                name.error = "Enter a valid Password"
                name.requestFocus()
            }
            else if(confirmPassword.text!!.equals(password.text)){
                name.error = "Passwords don't match"
                name.requestFocus()
            }
            else{
                startActivity(Intent(context,MainActivity::class.java))
                finishAffinity(requireActivity())
            }

        }

        backBtn.setOnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView,EnterNumberFragment())
            fragmentTransaction?.commit()

        }

        return view
    }
}