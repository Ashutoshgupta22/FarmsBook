package com.farmsbook.farmsbook.seller.ui.listings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.ActivityViewListingBinding
import com.farmsbook.farmsbook.seller.ui.listings.fragments.ViewBuyerOfferActivity
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.BuyerOfferAdapter
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.BuyerOfferData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class ViewListingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewListingBinding
    private lateinit var plantList: ArrayList<BuyerOfferData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        getDataUsingVolley()
        getAllOfferOfBuyers()
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
            Glide.with(this).load(response.getString("imageUrl0")).into(binding.imageView16)
            binding.requirementNameTv.text = response.getString("crop_name")
            binding.postedOnTV.text = "Posted on " + response.getString("timestamp")
            binding.varietyTV.text = response.getString("variety")
            binding.PriceTV.text =
                response.getString("min_price") + "/kg to " + response.getString("max_price") + "/kg"
            binding.quantityTV.text =
                response.getString("quantity") + " " + response.getString("quantity_unit")

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Profile Data", "Fail to get response = $error")
        })
        queue.add(request)
    }

    private fun getAllOfferOfBuyers() {

        plantList = arrayListOf<BuyerOfferData>()

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val list_id = intent.getStringExtra("LIST_ID")

        Log.i("ViewListingActivity", "getAllOfferOfBuyers: " +
                "listId- $list_id userId -$userId")
        val url = "$baseAddressUrl/user/$userId/listings/$list_id/getAllOfferOfBuyers"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = BuyerOfferData()
                    crop.company_name = cropObject.getString("farmer_company")
                    crop.buyer_image = cropObject.getString("buyerImage")
                    crop.listedOfferId = cropObject.getString("listed_offerId")
                    crop.offer_price = cropObject.getString("listed_offering_price")
                    crop.location = cropObject.getString("delivery_place")
                    crop.phone = cropObject.getString("phone")
                    crop.buyer_name = cropObject.getString("buyer_name")
                    crop.offer_quantity = cropObject.getString("listed_offering_quantity")
                    crop.offer_quantity_unit = cropObject.getString("listed_offering_quantity_unit")
                    crop.buyer_id = cropObject.getString("listed_offer_by_id").toString()

                    plantList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (plantList.size == 0) {
                binding.textView18.visibility = View.GONE
                binding.sentRequestRV.visibility = View.GONE
                binding.textView63.visibility = View.VISIBLE
            } else {
                binding.textView18.visibility = View.VISIBLE
                binding.sentRequestRV.visibility = View.VISIBLE
                binding.textView63.visibility = View.GONE
            }


            binding.sentRequestRV.layoutManager = LinearLayoutManager(this)
            val adapter = BuyerOfferAdapter(plantList, this)
            binding.sentRequestRV.adapter = adapter

            adapter?.setOnItemClickListener(object : BuyerOfferAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
                    startActivity(
                        Intent(
                            this@ViewListingActivity,
                            ViewBuyerOfferActivity::class.java
                        ).putExtra("Offer_ID", plantList[position].listedOfferId).putExtra("LIST_ID",list_id).putExtra("BUYER_ID",plantList[position].buyer_id)
                    )
//                val intent = Intent(this@MainActivity,CropDetailsActivity::class.java)
//                intent.putExtra("Name",plantList[position].Name)
//                intent.putExtra("Location",plantList[position].Location)
//                intent.putExtra("Farmer Name",plantList[position].FarmerName)
//                intent.putExtra("Availability",plantList[position].Availability)
//                intent.putExtra("PricePerKg",plantList[position].PricePerKg)
//                intent.putExtra("Quality",plantList[position].Quality)
//                startActivity(intent)
                }

                override fun callClick(position: Int) {

                    Log.i("ViewListingActivity", "callClick: clicked")
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${plantList[position].phone}")
                    startActivity(intent)
                }
            })
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

//    private fun rejectOffer(listedOfferId: String?) {
//        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
//        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
//        val userId = sharedPreference?.getInt("USER_ID", 0)
//        val list_id = intent.getStringExtra("LIST_ID")
//        val url = "$baseAddressUrl/user/$userId/listings/$list_id/offer/$listedOfferId/value/0"
//
//        // creating a new variable for our request queue
//        val queue: RequestQueue = Volley.newRequestQueue(this)
//
//
//        // on below line we are calling a string
//        // request method to post the data to our API
//        // in this we are calling a post method.
//        val request = JsonObjectRequest(Request.Method.POST, url, null, { response: JSONObject ->
//
//
//        }, { error -> // method to handle errors.
//            //Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
//            Log.d("Profile Data", "Fail to get response = $error")
//        })
//        queue.add(request)
//    }
//
//    private fun acceptOffer(listedOfferId: String?) {
//        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
//        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
//        val userId = sharedPreference?.getInt("USER_ID", 0)
//        val list_id = intent.getStringExtra("LIST_ID")
//        val url = "$baseAddressUrl/user/$userId/listings/$list_id/offer/$listedOfferId/value/1"
//
//        // creating a new variable for our request queue
//        val queue: RequestQueue = Volley.newRequestQueue(this)
//
//
//        // on below line we are calling a string
//        // request method to post the data to our API
//        // in this we are calling a post method.
//        val request = JsonObjectRequest(Request.Method.POST, url, null, { response: JSONObject ->
//
//
//        }, { error -> // method to handle errors.
//            //Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
//            Log.d("Profile Data", "Fail to get response = $error")
//        })
//        queue.add(request)
//
//    }
}