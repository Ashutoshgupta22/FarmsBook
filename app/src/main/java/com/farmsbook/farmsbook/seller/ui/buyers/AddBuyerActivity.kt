package com.farmsbook.farmsbook.seller.ui.buyers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.suppliers.ViewSupplierActivity
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersData
import com.farmsbook.farmsbook.databinding.ActivityAddBuyerBinding
import com.farmsbook.farmsbook.databinding.ActivityAddSupplierBinding
import com.farmsbook.farmsbook.seller.ui.buyers.adapters.BuyersAdapter
import com.farmsbook.farmsbook.seller.ui.buyers.adapters.BuyersData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray

class AddBuyerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBuyerBinding
    private lateinit var plantList: ArrayList<BuyersData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBuyerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        plantList= arrayListOf<BuyersData>()

        getDataUsingVolley()


    }

    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl/user/home_farmer"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = BuyersData()
                    crop.GroupName = cropObject.getString("group_name")
                    crop.Image = cropObject.getString("image")
                    crop.Location = cropObject.getString("location")
                    crop.FarmerName = cropObject.getString("name")
                    crop.FarmerID = cropObject.getString("buyer_id").toString()

                    plantList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.suppliersRv.layoutManager = LinearLayoutManager(this)
            val adapter = BuyersAdapter(plantList, this)
            binding.suppliersRv.adapter = adapter

            adapter?.setOnItemClickListener(object : BuyersAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                    startActivity(
                        Intent(this@AddBuyerActivity,
                            ViewBuyerActivity::class.java).putExtra("ID",plantList[position].FarmerID))
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
    }
}