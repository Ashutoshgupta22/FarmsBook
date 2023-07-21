package com.farmsbook.farmsbook.buyer.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityViewHomeCropBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject

class ViewHomeCropActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewHomeCropBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewHomeCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()

        getDataUsingVolley()
        val backBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val postOffer = findViewById<TextView>(R.id.postOfferBtn)

        postOffer.setOnClickListener {
            startActivity(Intent(this,PostOfferActivity::class.java).putExtra("LISTED_ID",intent.getStringExtra("LISTED_ID")).putExtra("PARENT_ID",intent.getStringExtra("PARENT_ID")))
        }

        binding.callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${binding.farmerPhoneTV.text}")
            startActivity(intent)
        }

        binding.AddSupplierBtn.setOnClickListener {
            postDataUsingVolley(intent.getStringExtra("PARENT_ID").toString())
        }

        backBtn.setOnClickListener {
            finish()
        }

    }

    private fun postDataUsingVolley(receiverId: String) {
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/sendToFarmer/$receiverId"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {

            Toast.makeText(this@ViewHomeCropActivity, "Added Supplier", Toast.LENGTH_LONG).show()
            //Toast.makeText(context, "USER ID = ${USER_ID}", Toast.LENGTH_SHORT).show()
            finish()

        }, { error -> // method to handle errors.
            Log.d("Suppliers",error.toString())
            Toast.makeText(
                this@ViewHomeCropActivity,
                "You have already added the supplier",
                Toast.LENGTH_LONG
            ).show()
        })
        queue.add(request)
    }

    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val id = intent.getStringExtra("LISTED_ID")
        val parent_id   = intent.getStringExtra("PARENT_ID")
        val url = "$baseAddressUrl/user/$parent_id/listings/$id"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

            Glide.with(this).load(response.getString("imageUrl0")).into( binding.imageView26)
            binding.cropNameTV.text = response["crop_name"].toString()
            binding.postedOnTV.text = "Posted on "+response["timestamp"].toString()
            binding.locationTV.text = response["location"].toString()
            binding.varietyTV.text = response["variety"].toString()
            binding.varietyTV2.text = response["variety"].toString()
            binding.priceRangeTV.text = "Price Range : ₹"+response["min_price"].toString()+"/kg to ₹"+response["max_price"].toString()+"/kg"
            binding.timeOfSowingTV.text = response["time_of_sowing"].toString()
            binding.quantityTV.text = response["quantity"].toString()+" "+response["quantity_unit"].toString()

            if(response["type_of_farming"].toString().equals("true"))
                binding.timeOfFarmingTV.text = "Organic"
            else
                binding.timeOfFarmingTV.text = "Unorganic"

            if(response["type_of_sale"].toString().equals("true"))
                binding.typeOfSaleTV.text = "On Commission"
            else
                binding.typeOfSaleTV.text = "Fixed Rate"

            if(response["transportation"].toString().equals("true"))
                binding.transportaionTV.text = "Transportation \nAvailable"
            else
                binding.transportaionTV.text = "Transportation \nNot Available"
//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)

        val url2 = "$baseAddressUrl/user/$parent_id"
        val request2 = JsonObjectRequest(Request.Method.GET, url2, null, { response: JSONObject ->
            Glide.with(this).load(response.getString("imagePath")).into( binding.imageView28)
            binding.farmerLocationTV.text = response["location"].toString()
            binding.farmerNameTV.text = response["name"].toString()
            binding.NameTV.text = response["name"].toString()
            binding.farmerPhoneTV.text = response["phone"].toString()

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request2)
    }
}