package com.farmsbook.farmsbook.login

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import com.google.android.material.imageview.ShapeableImageView
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

open class EnterDetailsFragment : Fragment() {

    private lateinit var name: EditText
    private lateinit var profileImage: ShapeableImageView
    private lateinit var phone: TextView
    private lateinit var email: EditText
    private lateinit var location: AutoCompleteTextView
    private lateinit var businessName: EditText
    private lateinit var businessTurnOver: EditText
    private lateinit var foundationDate: EditText
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enter_details, container, false)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)

        val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val role = sharedPreference?.getBoolean("USER_ROLE",false)
        //Toast.makeText(context,"Role = $role",Toast.LENGTH_SHORT).show()

        val states = resources.getStringArray(R.array.States)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = context?.let { ArrayAdapter(it, R.layout.dropdown_item, states) }
        // get reference to the autocomplete text view
        location = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        // set adapter to the autocomplete tv to the arrayAdapter
        location.setAdapter(arrayAdapter)

        profileImage = view.findViewById(R.id.profileImage)
        name = view.findViewById(R.id.nameEdt)
        phone = view.findViewById(R.id.phoneEdt)
        email = view.findViewById(R.id.emailEdt)
        businessName = view.findViewById(R.id.businessNameEdt)
        businessTurnOver = view.findViewById(R.id.businessTurnoverEdt)
        foundationDate = view.findViewById(R.id.foundationDateEdt)

        val value = requireArguments().getString("PhoneNumber")
phone.setText(value)

        val locale = Locale("en", "IN")
        Locale.setDefault(locale)
        val c = Calendar.getInstance(locale)
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        profileImage.setOnClickListener {

            pickImageGallery()

        }


        foundationDate.setOnClickListener {
            val dpd = context?.let {
                DatePickerDialog(
                    it, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        // Display Selected date in textbox
                        foundationDate.setText("" + dayOfMonth + " / " + (month+1) + " / " + year)

                    },
                    year,
                    month,
                    day
                )

            }
            dpd?.datePicker?.maxDate = System.currentTimeMillis();
            dpd?.show()
        }
        view.findViewById<TextView>(R.id.confirmBtn).setOnClickListener {

            if (TextUtils.isEmpty(name.text)) {
                name.error = "Enter a valid Name"
                name.requestFocus()
            } else if (TextUtils.isEmpty(location.text)) {
                location.error = "Select a valid State"
                location.requestFocus()
            } else if (TextUtils.isEmpty(phone.text)) {
                phone.error = "Enter a valid Mobile Number"
                phone.requestFocus()
            } else if (TextUtils.isEmpty(businessName.text)) {
                businessName.error = "Enter a valid Business Name"
                businessName.requestFocus()
            } else if (TextUtils.isEmpty(businessTurnOver.text)) {
                businessTurnOver.error = "Enter a valid Business TurnOver"
                businessTurnOver.requestFocus()
            } else if (TextUtils.isEmpty(foundationDate.text)) {
                foundationDate.error = "Enter a valid Foundation Date"
                foundationDate.requestFocus()
            } else {

                postDataUsingVolley(
                    name.text.trim().toString(),
                    role.toString().toBoolean(),
                    phone.text.trim().toString(),
                    email.text.trim().toString(),
                    location.text.trim().toString(),
                    businessName.text.trim().toString(),
                    businessTurnOver.text.trim().toString(),
                    foundationDate.text.trim().toString(),
                )

//                startActivity(Intent(context, MainActivity::class.java))
//                finishAffinity(requireActivity())
            }

        }

        backBtn.setOnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, ChooseRoleFragment())
            fragmentTransaction?.commit()

        }

        return view
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            profileImage.setImageURI(data?.data)
            val imageUri = data?.data
            val inputStream: InputStream? = imageUri?.let {
                activity?.getContentResolver()?.openInputStream(
                    it
                )
            }
            val outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var bytesRead: Int = 0
            while (inputStream?.read(buffer).also {
                    if (it != null) {
                        bytesRead = it
                    }
                } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            val imageBytes: ByteArray = outputStream.toByteArray()
            Log.d("Image","$imageBytes")
        }
    }

    private fun postDataUsingVolley(
        name: String,
        role: Boolean,
        phone: String,
        email: String,
        location: String,
        businessName: String,
        businessTurnovers: String,
        foundationDate: String
    ) {
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl/user"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val respObj = JSONObject()

        respObj.put("display_image", "https://example.com/display_image.jpg")
        respObj.put("background_image", "https://example.com/background_image.jpg")
        respObj.put("name", name)
        respObj.put("phone", phone)
        respObj.put("email", email)
        respObj.put("role", role)
        respObj.put("business_members", 500)
        respObj.put("business_name", businessName)
        respObj.put("business_turnovers", businessTurnovers.toInt())
        respObj.put("crop_count", 50)
        respObj.put("location", location)
        respObj.put("foundation_date", foundationDate)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {


            val sharedPreference2 =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val userROLE = sharedPreference2?.getBoolean("USER_ROLE",false)
            if (!userROLE!!) {
                startActivity(Intent(context, SellerMainActivity::class.java))
                finishAffinity(requireActivity())
            }
            else {
                startActivity(Intent(context, MainActivity::class.java))
                finishAffinity(requireActivity())
            }

            val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            var editor = sharedPreference?.edit()
            editor?.putInt("USER_ID",it["id"].toString().toInt())
           // editor?.putBoolean("USER_ROLE",it["role"].toString().toBoolean())
            editor?.commit()
            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT).show()
            //Toast.makeText(context, "USER ID = ${USER_ID}", Toast.LENGTH_SHORT).show()

        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
}