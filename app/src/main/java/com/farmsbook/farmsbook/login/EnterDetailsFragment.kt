package com.farmsbook.farmsbook.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        val city = view.findViewById<TextInputEditText>(R.id.editText_City)
        val state  = view.findViewById<TextInputEditText>(R.id.editText_State)




        view.findViewById<TextView>(R.id.confirmBtn).setOnClickListener {

            if(TextUtils.isEmpty(name.text))
            {
                name.error = "Enter a valid Name"
                name.requestFocus()
            }else if(TextUtils.isEmpty(email.text)){
                name.error = "Enter a valid Email id"
                name.requestFocus()
            }
            else if(TextUtils.isEmpty(city.text)){
                name.error = "Enter a valid Password"
                name.requestFocus()
            }
            else if(TextUtils.isEmpty(state.text)){
                name.error = "Passwords don't match"
                name.requestFocus()
            }
            else{
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.fragmentContainerView,
                    ChooseRoleFragment()
                )
                fragmentTransaction?.commit()
//                startActivity(Intent(context, MainActivity::class.java))
//                finishAffinity(requireActivity())
            }

        }

        backBtn.setOnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, EnterNumberFragment())
            fragmentTransaction?.commit()

        }

        return view
    }
}