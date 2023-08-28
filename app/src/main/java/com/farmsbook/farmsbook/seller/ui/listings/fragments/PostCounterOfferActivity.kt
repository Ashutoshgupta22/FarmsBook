package com.farmsbook.farmsbook.seller.ui.listings.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.home.OfferConfirmationActivity
import com.farmsbook.farmsbook.databinding.ActivityPostCounterOfferBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PostCounterOfferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostCounterOfferBinding
    private lateinit var metrics: AutoCompleteTextView
    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostCounterOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        rb1 = findViewById<RadioButton>(R.id.oc_rb)
        rb2 = findViewById<RadioButton>(R.id.fr_rb)
        var type_of_buy = true
        rb1.isChecked = true
        rb1.setOnClickListener {
            binding.rateEdt.isEnabled = true
            type_of_buy = true
            if (rb2.isChecked) {
                rb2.isChecked = false
            }
        }

        rb2.setOnClickListener {

            binding.rateEdt.isEnabled = false
            type_of_buy = false
            //binding.rateEdt.isActivated = false
            if (rb1.isChecked) {
                rb1.isChecked = false
            }
        }

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
                    binding.deliveryDateEdt.setText("" + dayOfMonth + " / " +
                            (monthOfYear + 1) + " / " + year)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        val metric = resources.getStringArray(R.array.Metrics)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_item, metric)
        metrics = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        metrics.setAdapter(arrayAdapter2)


        binding.confirmBtn.setOnClickListener {

            if (TextUtils.isEmpty(binding.deliveryDateEdt.text)) {
                binding.deliveryDateEdt.error = "Enter a valid Delivery Date"
                binding.deliveryDateEdt.requestFocus()
            } else if (type_of_buy == true) {

                if (TextUtils.isEmpty(binding.rateEdt.text)) {
                    binding.rateEdt.error = "Enter a value for rate"
                    binding.rateEdt.requestFocus()
                }
            } else if (TextUtils.isEmpty(binding.minEdt.text)) {
                binding.minEdt.error = "Select a valid price"
                binding.minEdt.requestFocus()
            } else if (TextUtils.isEmpty(binding.amountEdt.text)) {
                binding.amountEdt.error = "Enter a valid quantity"
                binding.amountEdt.requestFocus()
            } else if (TextUtils.isEmpty(binding.autoCompleteTextView.text)) {
                binding.autoCompleteTextView.error = "Enter a valid unit"
                binding.autoCompleteTextView.requestFocus()
            } else {

                postDataUsingVolley(type_of_buy)
               // startActivity(Intent(this, OfferConfirmationActivity::class.java))
                finish()
            }
        }
        }

    private fun postDataUsingVolley( type_of_sale: Boolean ) {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference.getInt("USER_ID", 0)
        val list_id = intent.getStringExtra("LIST_ID")
        val listedOfferId = intent.getStringExtra("Offer_ID")
        val url = "$baseAddressUrl/user/$userId/listings/$list_id/offer/$listedOfferId/counter/12"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val current = formatter.format(time)

//        "counter_offer_by_id": 2,
//        "counter_offer_to_crop_id": 3,
//        "counter_offer_to_buyer_id": 4,
//        "counter_date": "2023-07-16",
//        "counter_price": 100,
//        "counter_rate": 5,
//        "counter_transportation_cost": 10,

        //respObj.put("purchased_on", type_of_sale)

        if (TextUtils.isEmpty(binding.rateEdt.text)) {
            respObj.put("counter_rate", 0)
        } else {
            respObj.put("counter_rate", binding.rateEdt.text.toString().toInt())
        }

        respObj.put("counter_price", binding.minEdt.text.toString().toInt())
        respObj.put("counter_date", binding.deliveryDateEdt.text.toString())
        respObj.put("counter_transportation_cost", binding.amountEdt.text.toString().toInt())
        //respObj.put("offering_quantity_unit", metrics.text.toString())


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {

            Toast.makeText(this, getString(R.string.posted_counter_offer),
                Toast.LENGTH_SHORT).show()
            finish()

        }, { error -> // method to handle errors.
            Toast.makeText(this, getString(R.string.something_went_wrong),
                Toast.LENGTH_LONG).show()

            Log.e("PostCounterOfferActivity", "postDataUsingVolley: ", error)
        })
        queue.add(request)
    }
}