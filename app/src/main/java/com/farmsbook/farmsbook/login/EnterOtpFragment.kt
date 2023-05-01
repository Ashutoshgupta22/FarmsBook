package com.farmsbook.farmsbook.login

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.farmsbook.farmsbook.R

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
        verifyBtn.setOnClickListener {

            val typedOTP =
                (editText1.text.toString() + editText2.text.toString() + editText3.text.toString()
                        + editText4.text.toString() + editText5.text.toString() + editText6.text.toString())

            if (typedOTP.length == 6) {
                val progressDialog = ProgressDialog(activity)
                progressDialog.setMessage("Verifying")
                progressDialog.show()

                Handler().postDelayed({
                    progressDialog.dismiss()
                    val fragmentManager = activity?.supportFragmentManager
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainerView, EnterDetailsFragment())
                    fragmentTransaction?.commit()
                },1500)

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
}