package com.farmsbook.farmsbook.buyer.ui.suppliers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersData
import com.farmsbook.farmsbook.databinding.ActivityAddSupplierBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject


class AddSupplierActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSupplierBinding
    private lateinit var plantList: ArrayList<SuppliersData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        plantList = arrayListOf<SuppliersData>()

        getDataUsingVolley()
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

            Toast.makeText(this@AddSupplierActivity, "Added Supplier", Toast.LENGTH_LONG).show()
            //Toast.makeText(context, "USER ID = ${USER_ID}", Toast.LENGTH_SHORT).show()
            finish()

        }, { error -> // method to handle errors.
            Log.d("Suppliers",error.toString())
            Toast.makeText(
                this@AddSupplierActivity,
                "Fail to get response = $error",
                Toast.LENGTH_LONG
            ).show()
        })
        queue.add(request)
    }


    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/home_buyer"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = SuppliersData()
                    crop.GroupName = cropObject.getString("group_name")
                    crop.Image = cropObject.getString("image")
                    crop.Location = cropObject.getString("location")
                    crop.FarmerName = cropObject.getString("name")
                    crop.FarmerID = cropObject.getString("farmer_id").toString()

                    plantList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.suppliersRv.layoutManager = LinearLayoutManager(this)
            val adapter = SuppliersAdapter(plantList, this)
            binding.suppliersRv.adapter = adapter

            adapter.setOnItemClickListener(object : SuppliersAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                    startActivity(
                        Intent(
                            this@AddSupplierActivity,
                            ViewSupplierActivity::class.java
                        ).putExtra("FARMER_ID", plantList[position].FarmerID)
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

                override fun onAddButtonClick(position: Int) {

                    postDataUsingVolley(plantList[position].FarmerID.toString())
                    adapter.notifyDataSetChanged()
                }
            })
//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

}