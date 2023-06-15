package com.farmsbook.farmsbook.seller.ui.listings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityAddListingBinding
import com.farmsbook.farmsbook.seller.ui.listings.fragments.ListingConfirmationActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AddListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddListingBinding

    private lateinit var location: AutoCompleteTextView
    private lateinit var metrics: AutoCompleteTextView
    private lateinit var months: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        supportActionBar?.hide()

        binding.backBtn.setOnClickListener {
            finish()
        }


        val rb1 = binding.ocRb
        val rb2 = binding.frRb
        val rb3 = binding.reqRb
        val rb4 = binding.nreqRb
        val rb5 = binding.uoRb
        val rb6 = binding.orRb

        var type_of_sale = false
        rb1.setOnClickListener {
            if (rb2.isChecked) {
                rb2.isChecked = false
                type_of_sale = false
            }
        }
        rb2.setOnClickListener {
            if (rb1.isChecked) {
                rb1.isChecked = false
                type_of_sale = true

            }
        }

        var transportation = false
        rb3.setOnClickListener {
            if (rb4.isChecked) {
                rb4.isChecked = false
                transportation = false
            }
        }
        rb4.setOnClickListener {
            if (rb3.isChecked) {
                rb3.isChecked = false
                transportation = true

            }
        }

        var type_of_farming = false
        rb5.setOnClickListener {
            if (rb6.isChecked) {
                rb6.isChecked = false
                type_of_farming = false
            }
        }
        rb6.setOnClickListener {
            if (rb5.isChecked) {
                rb5.isChecked = false
                type_of_farming = true

            }
        }

        val states = resources.getStringArray(R.array.States)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, states)
        location = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        location.setAdapter(arrayAdapter)

        val metric = resources.getStringArray(R.array.Metrics)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_item, metric)
        metrics = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        metrics.setAdapter(arrayAdapter2)

        val month = resources.getStringArray(R.array.Months)
        val arrayAdapter3 = ArrayAdapter(this, R.layout.dropdown_item, month)
        months = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView3)
        months.setAdapter(arrayAdapter3)


        binding.confirmBtn.setOnClickListener {
            postDataUsingVolley(type_of_sale, transportation, type_of_farming)
            startActivity(Intent(this@AddListingActivity,ListingConfirmationActivity::class.java))
            finish()
        }

    }

    private fun postDataUsingVolley(
        type_of_sale: Boolean,
        transportation: Boolean,
        type_of_farming: Boolean
    ) {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/listings"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val current = formatter.format(time)

        respObj.put("crop_name", binding.nameEdt.text.toString())
        respObj.put("variety", binding.varietyEdt.text.toString())
        respObj.put("type_of_sale", type_of_sale)
        respObj.put("rate", binding.rateEdt.text.toString().toInt())
        respObj.put("min_price", binding.minEdt.text.toString().toInt())
        respObj.put("max_price", binding.maxEdt.text.toString().toInt())
        respObj.put("quantity", binding.amountEdt.text.toString().toInt())
        respObj.put("quantity_unit", null)
        respObj.put("location", null)
        respObj.put("transportation", transportation)
        respObj.put("type_of_farming", type_of_farming)
        respObj.put("time_of_sowing", months.text.toString())
        respObj.put("timestamp", current)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {

//            Toast.makeText(this, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

}