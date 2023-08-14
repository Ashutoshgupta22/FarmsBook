package com.farmsbook.farmsbook.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.MainActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity

class ChooseRoleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_role, container, false)

        val confirmBtn = view.findViewById<TextView>(R.id.confirmBtn)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        val radio1 = view.findViewById<RadioButton>(R.id.radio1)
        val radio2 = view.findViewById<RadioButton>(R.id.radio2)

        val linear1 = view.findViewById<LinearLayout>(R.id.linearLayout)
        val linear2 = view.findViewById<LinearLayout>(R.id.linearLayout2)

//        val value = requireArguments().getString("PhoneNumber")
//        Log.i("ChooseRoleFragment", "onCreateView: phoneNo-$value")

        linear1.setOnClickListener {
            radio1.isChecked = true
           //Toast.makeText(context,"Role = $role",Toast.LENGTH_SHORT).show()
            if (radio2.isChecked) {
                radio2.isChecked = false
            }
        }

        linear2.setOnClickListener {
            radio2.isChecked = true
           // Toast.makeText(context,"Role = $role",Toast.LENGTH_SHORT).show()
            if (radio1.isChecked) {
                radio1.isChecked = false
            }
        }

        radio1.setOnClickListener {
            if (radio2.isChecked) {
                radio2.isChecked = false
            }
        }

        radio2.setOnClickListener {
            if (radio1.isChecked) {
                radio1.isChecked = false
            }
        }

        backBtn.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, EnterNumberFragment())
            fragmentTransaction?.commit()
        }

        confirmBtn.setOnClickListener {

            val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            var editor = sharedPreference?.edit()
            if(radio1.isChecked)
            {
                editor?.putBoolean("USER_ROLE",false)
            }
            else if(radio2.isChecked){

                editor?.putBoolean("USER_ROLE",true)
            }

            editor?.commit()

            val value = requireArguments().getString("PhoneNumber")
            val frag = EnterDetailsFragment()
            val args = Bundle()
            args.putString("PhoneNumber", value)
            frag.arguments = args
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, frag)
            fragmentTransaction?.commit()

        }
//
        return view
    }
}
