package com.farmsbook.farmsbook.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.farmsbook.farmsbook.R
import com.hbb20.CountryCodePicker

class EnterNumberFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_enter_number, container, false)
        val sendCodeBtn = view.findViewById<TextView>(R.id.sendCodeBtn)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        val ccp = view.findViewById<CountryCodePicker>(R.id.ccp)
        val editTextCarrierNumber = view.findViewById<EditText>(R.id.editText_carrierNumber)

        //To attach phone number with country code
        //Using library Country Code Picker
        //https://github.com/hbb20/CountryCodePickerProject/wiki/Full-Number-Support

        ccp.registerCarrierNumberEditText(editTextCarrierNumber)

        ccp.setPhoneNumberValidityChangeListener {
            if(it)
            {

                sendCodeBtn.isEnabled = true
                sendCodeBtn.alpha = 1F

            }
            else{

                sendCodeBtn.isEnabled = false
                sendCodeBtn.alpha = 0.7F

            }
        }

        sendCodeBtn.setOnClickListener {

            val frag = EnterOtpFragment()
            val args = Bundle()
            args.putString("PhoneNumber", ccp.formattedFullNumber)
            frag.arguments = args
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView,frag)
            fragmentTransaction?.commit()

        }

        backBtn.setOnClickListener {

            startActivity(Intent(context,LoginActivity::class.java))
            finishAffinity(requireActivity())

        }
        return view
    }
}