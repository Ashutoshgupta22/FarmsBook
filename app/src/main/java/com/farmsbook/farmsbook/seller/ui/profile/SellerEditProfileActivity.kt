package com.farmsbook.farmsbook.seller.ui.profile

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivitySellerEditProfileBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject
import java.util.*

class SellerEditProfileActivity : AppCompatActivity() {

    private lateinit var image: String
    private lateinit var background_image: String
    private lateinit var name: EditText
    private lateinit var phone: TextView
    private lateinit var email: EditText
    private lateinit var location: TextView
    private lateinit var businessName: EditText
    private lateinit var businessTurnOver: EditText
    private lateinit var businessMembers: EditText
    private lateinit var foundationDate: EditText
    private lateinit var binding: ActivitySellerEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val role = sharedPreference?.getBoolean("USER_ROLE",false)
        supportActionBar?.hide()
        name = findViewById(R.id.nameEdt)
        phone = findViewById(R.id.phoneEdt)
        email = findViewById(R.id.emailEdt)
        businessName = findViewById(R.id.businessNameEdt)
        businessTurnOver = findViewById(R.id.businessTurnoverEdt)
        businessMembers = findViewById(R.id.businessMembersEdt)
        foundationDate = findViewById(R.id.foundationDateEdt)

        binding.backBtn.setOnClickListener {
            finish()
        }

        location = findViewById<TextView>(R.id.locationEdt)
        // set adapter to the autocomplete tv to the arrayAdapter

        val locale = Locale("en", "IN")
        Locale.setDefault(locale)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        foundationDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    // Display Selected date in textbox
                    foundationDate.setText("" + dayOfMonth + " / " + (month + 1) + " / " + year)

                },
                year,
                month,
                day
            )

            dpd.datePicker.maxDate = System.currentTimeMillis();
            dpd.show()
        }

        getDataUsingVolley()


        binding.confirmBtn.setOnClickListener {

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
                    name.text.toString(),
                    role.toString().toBoolean(),
                    phone.text.toString(),
                    email.text.toString(),
                    location.text.toString(),
                    businessName.text.toString(),
                    businessTurnOver.text.toString(),
                    businessMembers.text.toString(),
                    foundationDate.text.toString(),
                )


                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                finish()
//                startActivity(Intent(context, MainActivity::class.java))
//                finishAffinity(requireActivity())
            }

        }
    }
        private fun getDataUsingVolley() {

            // url to post our data
            val baseAddressUrl = BaseAddressUrl().baseAddressUrl
            val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
            val userId = sharedPreference?.getInt("USER_ID", 0)
            val url = "$baseAddressUrl/user/$userId"

            // creating a new variable for our request queue
            val queue: RequestQueue = Volley.newRequestQueue(this)

            // on below line we are calling a string
            // request method to post the data to our API
            // in this we are calling a post method.
            val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

                image = response["imagePath"].toString()
                Glide.with(this).load(image).into(binding.profileImage)
                background_image = response["imagePath"].toString()
                name.setText(response["name"].toString())
                phone.setText(response["phone"].toString())
                email.setText(response["email"].toString())
                location.setText(response["location"].toString())
                businessName.setText(response["business_name"].toString())
                businessTurnOver.setText(response["business_turnovers"].toString())
                businessMembers.setText(response["business_members"].toString())
                foundationDate.setText(response["foundation_date"].toString())


            }, { error -> // method to handle errors.
                Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
                Log.d("Profile Data", "Fail to get response = $error")
            })
            queue.add(request)
        }

    private fun postDataUsingVolley(
        name: String,
        role: Boolean,
        phone: String,
        email: String,
        location: String,
        businessName: String,
        businessTurnovers: String,
        businessMembers: String,
        foundationDate: String
    ) {
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()

        respObj.put("imagePath", image)
        respObj.put("background_imagePath", background_image)
        respObj.put("name", name)
        respObj.put("phone", phone)
        respObj.put("email", email)
        respObj.put("role", role)
        respObj.put("business_members", businessMembers.toInt())
        respObj.put("business_name", businessName)
        respObj.put("business_turnovers", businessTurnovers.toInt())
        respObj.put("crop_count", 50)
        respObj.put("location", location)
        respObj.put("foundation_date", foundationDate)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.PUT, url, respObj, {

            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
            //Toast.makeText(context, "USER ID = ${USER_ID}", Toast.LENGTH_SHORT).show()

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
}