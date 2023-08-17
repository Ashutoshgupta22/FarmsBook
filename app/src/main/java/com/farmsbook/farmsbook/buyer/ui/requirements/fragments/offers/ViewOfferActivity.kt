package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.offers

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityViewOfferBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject

class ViewOfferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewOfferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        getOfferData()
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${binding.phoneTV.text}")
            startActivity(intent)
        }

    }

    private fun getOfferData() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val req_id = intent.getStringExtra("OFFER_ID")
        val url = "$baseAddressUrl/user/$userId/offer/$req_id"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

            Log.i("ViewOfferActivity", "getOfferData: $response")

            Glide
                .with(this)
                .load(response.getString("imageUrl0"))
                .into(binding.profileImage)
            binding.cropNameTv.text = response.getString("offer_cropName")
            binding.postedOnTV.text = "${resources.getString(R.string.posted)} :"+ response.getString("timestamp")
            binding.farmerNameTV.text = response.getString("farmer_name")
            binding.phoneTV.text = response.getString("phone2")
            binding.rateTV.text = response.getString("rate_of_commission")
            val farmerName = response.getString("farmer_name")
            binding.textView21.text = resources.getString(R.string.contact_to_place_order,farmerName)
            if (response.getString("replied") == "false") {
                binding.textView20.text = "${resources.getString(R.string.response_pending_by)} ${response.getString("farmer_name")}"
                binding.textView20.setTextColor(Color.parseColor("#FF8A00"))
            } else {
                if (response.getBoolean("offer_status") == false) {
                    binding.textView20.text =
                        "Offer Rejected by ${response.getString("farmer_name")}"
                    binding.textView20.setTextColor(Color.parseColor("#B80000"))
                } else {
                    binding.textView20.text =
                        "Offer Accepted by ${response.getString("farmer_name")}"
                    binding.textView20.setTextColor(Color.parseColor("#00853C"))
                }
            }
            val kg = resources.getStringArray(R.array.Metrics)[1]
            binding.priceTV.text = response.getString("offering_price") + "/ $kg"
            binding.costTv.text = response.getString("offering_price") + "/ $kg"
            binding.locationTV.text = response.getString("delivery_place")
            binding.locationTV2.text = response.getString("delivery_place")
            binding.quantityTV.text =
                response.getString("offering_quantity") + " " + response.getString("offering_quantity_unit")
            if (response.getString("transportation").equals("true"))
                binding.transportaitonTV.text = resources.getString(R.string.required)
            else
                binding.transportaitonTV.text = resources.getString(R.string.not_required)

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            Log.e("ViewOfferActivity", "FAILED " ,error)
        })
        queue.add(request)

    }
}