package com.farmsbook.farmsbook.buyer.ui.profile

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.databinding.ActivityViewProfileBinding
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersCropAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersCropData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ViewProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewProfileBinding
    private lateinit var plantList: ArrayList<SuppliersCropData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityViewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {
            finish()
        }
        plantList = arrayListOf<SuppliersCropData>()

        binding.suppliersCropsRV.layoutManager = GridLayoutManager(this, 2)
        val adapter = SuppliersCropAdapter(plantList, this)
        binding.suppliersCropsRV.adapter = adapter
        binding.suppliersCropsRV.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(object : SuppliersCropAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                Toast.makeText(
                    this@ViewProfileActivity,
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

    }
    private fun postDataUsingVolley(
        name: String,
        variety: String,
        minPrice: String,
        maxPrice: String,
        location: String,
        amount: String,
        metric:String,
        type_of_buy: Boolean,
        transportation: Boolean
    ) {

        {
//            "crop_name": "rice0002",
//            "variety": "Average0002",
//            "type_of_buy": true,
//            "min_range": 15,
//            "max_range": 15,
//            "quantity": 15,
//            "quantity_unit": "KG",
//            "location": "India",
//            "transportation": true,
//            "timestamp": "10/05/2023",
//            "interested_supplier":5,
//            "requirement_status":true
        }
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl/user/4/requirements"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val current = formatter.format(time)

        respObj.put("crop_name", name)
        respObj.put("variety", variety)
        respObj.put("type_of_buy", type_of_buy)
        respObj.put( "min_range", minPrice)
        respObj.put("max_range", maxPrice)
        respObj.put("quantity", amount)
        respObj.put("quantity_unit", metric)
        respObj.put("location", location)
        respObj.put("transportation", transportation)
        respObj.put("timestamp", current)
        respObj.put("interested_supplier", 0)
        respObj.put("requirement_status", true)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, respObj, {

            Toast.makeText(this, "Profile Created", Toast.LENGTH_SHORT)
                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
    private fun setLocale(lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
//        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//        val editor = getSharedPreferences("Settings", AppCompatActivity.MODE_PRIVATE).edit()
//        editor.putString("My_lang", lang)
//        editor.apply()
    }
}