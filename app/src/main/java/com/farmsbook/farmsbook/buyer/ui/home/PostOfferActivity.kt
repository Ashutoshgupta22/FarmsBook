package com.farmsbook.farmsbook.buyer.ui.home

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityPostOfferBinding
import com.farmsbook.farmsbook.seller.ui.listings.fragments.ListingConfirmationActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PostOfferActivity : AppCompatActivity() {

    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var rb4: RadioButton
    private lateinit var location: AutoCompleteTextView
    private lateinit var metrics: AutoCompleteTextView

    private lateinit var binding : ActivityPostOfferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        rb1 = findViewById<RadioButton>(R.id.oc_rb)
        rb2 = findViewById<RadioButton>(R.id.fr_rb)
        rb3 = findViewById<RadioButton>(R.id.req_rb)
        rb4 = findViewById<RadioButton>(R.id.nreq_rb)

        var type_of_buy = false
        rb1.setOnClickListener {
            if (rb2.isChecked) {
                rb2.isChecked = false
                type_of_buy = false
            }
        }
        rb2.setOnClickListener {
            if (rb1.isChecked) {
                rb1.isChecked = false
                type_of_buy = true

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

        val states = resources.getStringArray(R.array.States)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, states)
        location = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        location.setAdapter(arrayAdapter)

        val metric = resources.getStringArray(R.array.Metrics)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_item , metric)
        metrics = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        metrics.setAdapter(arrayAdapter2)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.deliveryDateEdt.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
                    binding.deliveryDateEdt.setText("" + dayOfMonth + " / " + monthOfYear + 1 + " / " + year)

                },
                year,
                month,
                day
            )

            dpd.show()
        }
        binding.confirmBtn.setOnClickListener {
            postDataUsingVolley(type_of_buy, transportation)
            startActivity(Intent(this, ListingConfirmationActivity::class.java))
            finish()
        }


    }

    private fun postDataUsingVolley(
        type_of_sale: Boolean,
        transportation: Boolean
    ) {

//        {
//            "offerId": 1,
//            "purchased_on": true,
//            "rate_of_commission": 5,
//            "offering_price": 10,
//            "offering_quantity_unit": "kg",
//            "offering_quantity": 100,
//            "transportation": false,
//            "delivery_place": "New York",
//            "offer_status": "active"
//        }
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference.getInt("USER_ID", 0)
        val listed_id = intent.getStringExtra("LISTED_ID")
        val parent_id = intent.getStringExtra("PARENT_ID")
        val url = "$baseAddressUrl/user/$parent_id/listings/$listed_id/enable/$userId"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val current = formatter.format(time)


        respObj.put("offer_id", null)
        respObj.put("purchased_on", type_of_sale)
        respObj.put("rate_of_commission", binding.rateEdt.text.toString().toInt())
        respObj.put("offering_price", binding.minEdt.text.toString().toInt())
        respObj.put("offering_quantity", binding.amountEdt.text.toString().toInt())
        respObj.put("offering_quantity_unit", metrics.text.toString())
        respObj.put("delivery_place", location.text.toString())
        respObj.put("transportation", transportation)
        respObj.put("offer_status", "active")


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