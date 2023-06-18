package com.farmsbook.farmsbook.seller.ui.listings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.databinding.ActivityViewListingBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject

class ViewListingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        getDataUsingVolley()
        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val list_id = intent.getStringExtra("LIST_ID")
        val url = "$baseAddressUrl/user/$userId/listings/$list_id"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

            binding.requirementNameTv.text = response.getString("crop_name")
            binding.postedOnTV.text = "Posted on "+response.getString("timestamp")
            binding.varietyTV.text = "Posted on "+response.getString("variety")
            binding.PriceTV.text = response.getString("min_price")+"/kg to "+response.getString("max_price")+"/kg"
            binding.quantityTV.text = response.getString("quantity")+" "+response.getString("quantity_unit")

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Profile Data", "Fail to get response = $error")
        })
        queue.add(request)
    }
}