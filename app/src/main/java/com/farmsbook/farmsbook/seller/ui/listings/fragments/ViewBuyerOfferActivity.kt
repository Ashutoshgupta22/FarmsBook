package com.farmsbook.farmsbook.seller.ui.listings.fragments

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ViewUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityViewBuyerOfferBinding
import com.farmsbook.farmsbook.databinding.ActivityViewOfferBinding
import com.farmsbook.farmsbook.seller.ui.buyers.ViewBuyerActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject

class ViewBuyerOfferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBuyerOfferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBuyerOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val list_id = intent.getStringExtra("LIST_ID")
        val listedOfferId = intent.getStringExtra("Offer_ID")
        val buyerId = intent.getStringExtra("BUYER_ID")
        getData()
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.viewBtn.setOnClickListener {
            startActivity(Intent(this,ViewBuyerActivity::class.java).putExtra("ID",buyerId))
        }
        binding.acceptBtn.setOnClickListener {
            acceptOffer()
        }
        binding.rejectBtn.setOnClickListener {
            Log.i("ViewBuyerOfferActivity", "onCreate: reject clicked")
            rejectOffer()
        }
        binding.counterBtn.setOnClickListener {
         startActivity(Intent(this,PostCounterOfferActivity::class.java).putExtra("Offer_ID", listedOfferId).putExtra("LIST_ID",list_id))
        }

    }

    private fun getData() {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val list_id = intent.getStringExtra("LIST_ID")
        val listedOfferId = intent.getStringExtra("Offer_ID")

        Log.i("ViewBuyerOfferActivity", "getData: userid-$userId listId-$list_id " +
                "listOfferId-$listedOfferId")

        val url = "$baseAddressUrl/user/$userId/listings/$list_id/listedOffer/$listedOfferId"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)



        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

            if(response.getString("buyerImage") != "null")
            {
                Glide.with(this).load(response.getString("buyerImage")).into( binding.profileImage)
            }

            if (!response.getBoolean("purchased_on"))
                binding.purchasedOnTV.text = resources.getString(R.string.on_commission)
            else
                binding.purchasedOnTV.text = resources.getString(R.string.fixed_rate)

            if (!response.getBoolean("transportation"))
                binding.purchasedOnTV.text = resources.getString(R.string.required)
            else
                binding.purchasedOnTV.text = resources.getString(R.string.not_required)

            binding.farmerNameTV.text = response.getString("buyer_name")
            binding.companyNameTV.text = response.getString("farmer_company")
            binding.dateTV.text = response.getString("timestamp")
            binding.priceTV.text = response.getString("listed_offering_price")+"/kg"
            binding.rateTV.text = response.getString("rate_of_commission")
            binding.quantityTV.text = response.getString("listed_offering_quantity")+" "+response.getString("listed_offering_quantity_unit")
            binding.locationTV.text = response.getString("delivery_place")
            val reply = response.getBoolean("reply")

            checkReply(reply)

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            Log.e("ViewBuyerOfferActivity", "getData: ",error )
        })
        queue.add(request)
    }

    private fun checkReply(reply: Boolean) {

        Log.i("ViewBuyerOfferActivity", "checkReply: $reply")
        if (reply) {
            binding.acceptBtn.visibility = View.INVISIBLE
            binding.rejectBtn.visibility = View.INVISIBLE
        }
    }

    private fun rejectOffer() {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val list_id = intent.getStringExtra("LIST_ID")
        val listedOfferId = intent.getStringExtra("Offer_ID")
        val url = "$baseAddressUrl/user/$userId/listings/$list_id/offer/$listedOfferId/value/0"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, null, {
                response: JSONObject ->

            Log.i("ViewBuyerActivity", "rejectOffer: SUCCESS")
            Toast.makeText(this, "Offer Rejected", Toast.LENGTH_SHORT).show()
            binding.rejectBtn.visibility = View.INVISIBLE
            binding.acceptBtn.visibility = View.INVISIBLE

        }, { error -> // method to handle errors.

            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            Log.e("ViewBuyerActivity", "rejectOffer: FAILED",error )
        })
        queue.add(request)
    }

    private fun acceptOffer() {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val list_id = intent.getStringExtra("LIST_ID")
        val listedOfferId = intent.getStringExtra("Offer_ID")
        val url = "$baseAddressUrl/user/$userId/listings/$list_id/offer/$listedOfferId/value/1"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, null,
            { response: JSONObject ->

                Log.i("ViewBuyerOfferActivity", "acceptOffer: SUCCESS")
                Toast.makeText(this, "Offer Accepted", Toast.LENGTH_SHORT).show()
                binding.acceptBtn.visibility = View.INVISIBLE
                binding.rejectBtn.visibility = View.INVISIBLE

        }, { error -> // method to handle errors.
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                Log.e("ViewBuyerOfferActivity", "acceptOffer: FAILED",error )

        })
        queue.add(request)
    }
}