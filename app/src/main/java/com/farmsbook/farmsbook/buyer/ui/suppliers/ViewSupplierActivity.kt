package com.farmsbook.farmsbook.buyer.ui.suppliers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter
import com.farmsbook.farmsbook.databinding.ActivityViewSupplierBinding
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersCropAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersCropData
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class ViewSupplierActivity : AppCompatActivity() {

    private lateinit var plantList: ArrayList<SuppliersCropData>
    private lateinit var tempArrayList: ArrayList<SuppliersCropData>
    private lateinit var binding: ActivityViewSupplierBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {
            finish()
        }

        plantList = arrayListOf<SuppliersCropData>()

        getDataUsingVolley()

    }
    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val userId = intent.getStringExtra("FARMER_ID")

        val url = "$baseAddressUrl/user/$userId/listings"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = SuppliersCropData()
                    crop.Name = cropObject.getString("crop_name")
                    crop.Image = null
                    crop.PricePerKg = "₹ "+cropObject.getString("min_range")+"-"+cropObject.getString("max_range")+"/ kg"
                    plantList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.suppliersCropsRV.layoutManager = GridLayoutManager(this, 2)
            val adapter = SuppliersCropAdapter(plantList, this)
            binding.suppliersCropsRV.adapter = adapter
            binding.suppliersCropsRV.setNestedScrollingEnabled(false);
            adapter.setOnItemClickListener(object : SuppliersCropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    Toast.makeText(
                        this@ViewSupplierActivity,
                        "You Clicked on item no. $position",
                        Toast.LENGTH_SHORT
                    ).show()

//                val intent = Intent(this@MainActivity,CropDetailsActivity::class.java)
//                intent.putExtra("Name",plantList[position].Name)
//                intent.putExtra("Location",plantList[position].Location)
//                intent.putExtra("Farmer Name",plantList[position].FarmerName)
//                intent.putExtra("Availability",plantList[position].Availability)
//                intent.putExtra("PricePerKg",plantList[position].PricePerKg)
//                intent.putExtra("Quality",plantList[position].Quality)
//                startActivity(intent)
                }
            })
//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)


        val url2 = "$baseAddressUrl/user/$userId"
        val request2 = JsonObjectRequest(Request.Method.GET, url2, null, { response: JSONObject ->

            binding.supplierLocationTv.text = response["location"].toString()
            binding.foundedTv.text = response["foundation_date"].toString()
            binding.turnOverTv.text = response["business_turnovers"].toString()
            binding.cropsTv.text = response["crops_count"].toString()
            binding.farmerNameTV.text = response["name"].toString()
            binding.nameTV.text = response["business_name"].toString()
            binding.membersTv.text = response["business_members"].toString()
            binding.phoneTV.text = response["phone"].toString()

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })

        queue.add(request2)

    }

}