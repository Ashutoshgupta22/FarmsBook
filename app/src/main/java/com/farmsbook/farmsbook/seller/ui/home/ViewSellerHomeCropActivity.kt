package com.farmsbook.farmsbook.seller.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.MainActivity
import com.farmsbook.farmsbook.buyer.ui.home.OfferConfirmationActivity
import com.farmsbook.farmsbook.databinding.ActivityViewSellerHomeCropBinding
import com.farmsbook.farmsbook.seller.SellerMainActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ViewSellerHomeCropActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewSellerHomeCropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSellerHomeCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        getDataUsingVolley()

        binding.callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${binding.farmerPhoneTV.text}")
            startActivity(intent)
        }

        binding.addBtn.setOnClickListener {
            addBuyer()
        }
        binding.floatingActionButton.setOnClickListener {
            finish()
        }
        binding.interestedBtn.setOnClickListener {
            postDataUsingVolley()
        }
    }

    private fun addBuyer() {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference =getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val parent_id   = intent.getStringExtra("PARENT_ID")
        val url = "$baseAddressUrl/user/$userId/sendToBuyer/$parent_id"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, null, {

            Toast.makeText(this@ViewSellerHomeCropActivity,"Added Buyer",Toast.LENGTH_LONG).show()
            //Toast.makeText(context, "USER ID = ${USER_ID}", Toast.LENGTH_SHORT).show()
            finish()

        }, { error -> // method to handle errors.
            Log.d("Buyers",error.toString())
            Toast.makeText(this@ViewSellerHomeCropActivity, "Buyer already Added", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val id = intent.getStringExtra("req_id")
        val parent_id   = intent.getStringExtra("PARENT_ID")
        val url = "$baseAddressUrl/user/$parent_id/requirements/$id"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

            binding.cropNameTV.text = response["crop_name"].toString()
            binding.postedOnTV.text = "Posted on "+response["timestamp"].toString()
            binding.locationTV.text = response["location"].toString()
            binding.varietyTV.text = response["variety"].toString()
            binding.priceTV.text = response["min_range"].toString()+"/kg to ₹"+response["max_range"].toString()+"/kg"
            binding.quantityTV.text = response["quantity"].toString()+" "+response["quantity_unit"].toString()


            if(response["transportation"].toString().equals("true"))
                binding.transportationTV.text = "Required"
            else
                binding.transportationTV.text = "Not Required"
//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)

        val url2 = "$baseAddressUrl/user/$parent_id"
        val request2 = JsonObjectRequest(Request.Method.GET, url2, null, { response: JSONObject ->

            binding.farmerLocationTV.text = response["location"].toString()
            binding.farmerNameTV.text = response["name"].toString()
            binding.farmerPhoneTV.text = response["phone"].toString()

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })

        queue.add(request2)
    }

    private fun postDataUsingVolley() {

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
        val listed_id = intent.getStringExtra("req_id")
        val parent_id = intent.getStringExtra("PARENT_ID")
        val url = "$baseAddressUrl/user/$parent_id/requirements/$listed_id/value/1/$userId"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()



        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {

        Toast.makeText(this, "Posted Interest", Toast.LENGTH_SHORT)
                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }




}