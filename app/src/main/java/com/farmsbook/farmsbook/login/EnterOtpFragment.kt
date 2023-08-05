package com.farmsbook.farmsbook.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.MainActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONException
import org.json.JSONObject


class EnterOtpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enter_otp, container, false)

        val verifyBtn = view.findViewById<TextView>(R.id.verifyOtp_btn)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        val number = view.findViewById<TextView>(R.id.number_tv)
        val editText1 = view.findViewById<EditText>(R.id.otpEditText1)
        val editText2 = view.findViewById<EditText>(R.id.otpEditText2)
        val editText3 = view.findViewById<EditText>(R.id.otpEditText3)
        val editText4 = view.findViewById<EditText>(R.id.otpEditText4)
        val editText5 = view.findViewById<EditText>(R.id.otpEditText5)
        val editText6 = view.findViewById<EditText>(R.id.otpEditText6)

        editText1.requestFocus()

        //GenericTextWatcher here works only for moving to next EditText when a number is entered
//first parameter is the current EditText and second parameter is next EditText
        editText1.addTextChangedListener(GenericTextWatcher(editText1, editText2))
        editText2.addTextChangedListener(GenericTextWatcher(editText2, editText3))
        editText3.addTextChangedListener(GenericTextWatcher(editText3, editText4))
        editText4.addTextChangedListener(GenericTextWatcher(editText4, editText5))
        editText5.addTextChangedListener(GenericTextWatcher(editText5, editText6))
        editText6.addTextChangedListener(GenericTextWatcher(editText6, null))


//GenericKeyEvent here works for deleting the element and to switch back to previous EditText
//first parameter is the current EditText and second parameter is previous EditText
        editText1.setOnKeyListener(GenericKeyEvent(editText1, null))
        editText2.setOnKeyListener(GenericKeyEvent(editText2, editText1))
        editText3.setOnKeyListener(GenericKeyEvent(editText3, editText2))
        editText4.setOnKeyListener(GenericKeyEvent(editText4, editText3))
        editText5.setOnKeyListener(GenericKeyEvent(editText5, editText4))
        editText6.setOnKeyListener(GenericKeyEvent(editText6, editText5))
        val value = requireArguments().getString("PhoneNumber")
        number.text = value

        val timerText = view.findViewById<TextView>(R.id.textView9)
        timerText.isEnabled = false
        val timer = object: CountDownTimer(25000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                timerText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                timerText.isEnabled = false
                timerText.setText("Resend the Code (in ${(millisUntilFinished)/1000} seconds)")

            }
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                timerText.setText("Resend the Code")
                timerText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                timerText.isEnabled = true
            }
        }
        timer.start()

        timerText.setOnClickListener {
            timer.start()
            sendOtp(value.toString())
        }

        verifyBtn.setOnClickListener {

            val typedOTP =
                (editText1.text.toString() + editText2.text.toString() + editText3.text.toString()
                        + editText4.text.toString() + editText5.text.toString() + editText6.text.toString())

            if (typedOTP.length == 6) {
                val progressDialog = ProgressDialog(activity)
                progressDialog.setMessage("Verifying")
                progressDialog.show()

               // timer.cancel()

                Handler().postDelayed({
                    progressDialog.dismiss()
                    validateOtp(typedOTP, timer)
//                    val frag = ChooseRoleFragment()
//                    val args = Bundle()
//                    args.putString("PhoneNumber", value)
//                    frag.arguments = args
//                    val fragmentManager = activity?.supportFragmentManager
//                    val fragmentTransaction = fragmentManager?.beginTransaction()
//                    fragmentTransaction?.replace(R.id.fragmentContainerView, frag)
//                    fragmentTransaction?.commit()
                },1000)

            }
            else {
                Toast.makeText(context, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
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

    class GenericKeyEvent internal constructor(
        private val currentView: EditText,
        private val previousView: EditText?
    ) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.otpEditText1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.requestFocus()
                return true

            }
            return false
        }


    }

    class GenericTextWatcher internal constructor(
        private val currentView: View,
        private val nextView: View?
    ) :
        TextWatcher {
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
            val text = editable.toString()
            when (currentView.id) {
                R.id.otpEditText1 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otpEditText2 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otpEditText3 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otpEditText4 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.otpEditText5 -> if (text.length == 1) nextView!!.requestFocus()
                //You can use EditText4 same as above to hide the keyboard
            }
        }




        override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }

        override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }

    }

    private fun validateOtp(typedOtp: String, timer: CountDownTimer) {
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl/otp/validate"

        val jsonBody = JSONObject()
        jsonBody.put("otpCode", typedOtp)
        // creating a new variable for our request queue
        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Method.POST, url, Response.Listener { response ->
            // Handle the response here

            Log.i("EnterOtpFrag","validateOtp response: $response")

            if (response.isNotEmpty()) {

                try {
                    val jsonResponse = JSONObject(response)
                    val valid = jsonResponse.getBoolean("valid")

                    if (valid) {

                        timer.cancel()
                        val value = requireArguments().getString("PhoneNumber")
                        getDataUsingVolley(value.toString())
                    }
                    else Toast.makeText(requireContext(), "Invalid OTP",
                        Toast.LENGTH_SHORT).show()

                }
                catch (e: JSONException) {
                    Log.e("EnterOtpFrag", "validateOtp: Json exception",e )
                }
            }
            else Toast.makeText(requireContext(), "Invalid OTP", Toast.LENGTH_SHORT).show()

         }, Response.ErrorListener { error ->
            Toast.makeText(context,"Invalid OTP",Toast.LENGTH_SHORT).show()
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return jsonBody.toString().toByteArray()
            }
        }

        queue.add(request)
    }

    private fun getDataUsingVolley(phone: String) {

        // url to post our data
       val baseAddressUrl = BaseAddressUrl().baseAddressUrl
//        val userId = intent.getStringExtra("FARMER_ID")


        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method

        val url2 = "$baseAddressUrl/check_exist/$phone"
        val request2 = JsonObjectRequest(Request.Method.GET, url2, null, { response: JSONObject ->

            if(response["active"]== true)
            {
                val value = requireArguments().getString("PhoneNumber")
                val frag = ChooseRoleFragment()
                val args = Bundle()
                args.putString("PhoneNumber", value)
                frag.arguments = args
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.fragmentContainerView, frag)
                fragmentTransaction?.commit()
            }
            else{
                val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
                val editor = sharedPreference?.edit()
                editor?.putInt("USER_ID",response["id"].toString().toInt())
                editor?.putBoolean("USER_ROLE",response["role"].toString().toBoolean())
                editor?.apply()

                if(response["role"] == true)
                {
                    Log.i("EnterOtpFrag", "getDataUsingVolley: MainActivity called")
                    startActivity(Intent(context,MainActivity::class.java))
                    finishAffinity(LoginActivity())
                }
                else
                {
                    Log.i("EnterFrag", "getDataUsingVolley: sellerMainActivity called ")
                    startActivity(Intent(context, SellerMainActivity::class.java))
                    finishAffinity(LoginActivity())
                }
            }

        }, { error -> // method to handle errors.
            Log.e("EnterOtpFrag", "getDataUsingVolley: Failed",error )
            Toast.makeText(context, "Something went wrong! Try again", Toast.LENGTH_LONG).show()
        })
        queue.add(request2)
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

            //Toast.makeText(context, "OTP Sent", Toast.LENGTH_SHORT).show()
            //Toast.makeText(context, "USER ID = ${USER_ID}", Toast.LENGTH_SHORT).show()

        }, { error -> // method to handle errors.
            Log.d("OTP Validation",error.toString())
            //Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
}