package com.farmsbook.farmsbook.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.MainActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.hbb20.CountryCodePicker
import org.json.JSONObject

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
            sendOtp(ccp.fullNumber)

            Handler().postDelayed(
            {
                args.putString("PhoneNumber", ccp.formattedFullNumber)
                frag.arguments = args
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.fragmentContainerView, frag)
                fragmentTransaction?.commit()
            },400)
        }

        backBtn.setOnClickListener {

            startActivity(Intent(context, LoginActivity::class.java))
            finishAffinity(requireActivity())

        }
        return view
    }

    private fun sendOtp(phone:String) {
            // url to post our data
            val baseAddressUrl = BaseAddressUrl().baseAddressUrl
            val url = "$baseAddressUrl/otp"

            // creating a new variable for our request queue
            val queue: RequestQueue = Volley.newRequestQueue(context)
            val respObj = JSONObject()

            respObj.put("phoneNumber", phone)

            // on below line we are calling a string
            // request method to post the data to our API
            // in this we are calling a post method.
            val request = JsonObjectRequest(Request.Method.POST, url, respObj, {

                Toast.makeText(context, "OTP Sent", Toast.LENGTH_SHORT).show()
                //Toast.makeText(context, "USER ID = ${USER_ID}", Toast.LENGTH_SHORT).show()

            }, { error -> // method to handle errors.
                Log.d("OTP Validation",error.toString())
                //Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            })
            queue.add(request)
    }
}