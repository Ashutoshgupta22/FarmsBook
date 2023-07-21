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
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter
import com.farmsbook.farmsbook.databinding.ActivityViewSupplierBinding
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersCropAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersCropData
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersData
import com.farmsbook.farmsbook.seller.ui.profile.adapters.ManageCropAdapter
import com.farmsbook.farmsbook.seller.ui.profile.adapters.ManageCropData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class ViewSupplierActivity : AppCompatActivity() {

    private lateinit var plantList: ArrayList<SuppliersCropData>
    private lateinit var cropList: java.util.ArrayList<ManageCropData>
    private lateinit var cropImages: java.util.ArrayList<Int>
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

        cropImages = arrayListOf()
        cropImages.add(R.drawable.bajra)
        cropImages.add(R.drawable.barley)
        cropImages.add(R.drawable.basmati_paddy)
        cropImages.add(R.drawable.black_pepper)
        cropImages.add(R.drawable.cashew)
        cropImages.add(R.drawable.castor_seeds)
        cropImages.add(R.drawable.chana)
        cropImages.add(R.drawable.chilli)
        cropImages.add(R.drawable.coffee)
        cropImages.add(R.drawable.coriander_seeds)
        cropImages.add(R.drawable.cotton)
        cropImages.add(R.drawable.groundnut)
        cropImages.add(R.drawable.guar_gum_refind_splits)
        cropImages.add(R.drawable.guarseed)
        cropImages.add(R.drawable.jaggery)
        cropImages.add(R.drawable.jeera)
        cropImages.add(R.drawable.jowar_white)
        cropImages.add(R.drawable.jowar_yellow)
        cropImages.add(R.drawable.jowar)
        cropImages.add(R.drawable.kapas)
        cropImages.add(R.drawable.maize_kharif)
        cropImages.add(R.drawable.masoor_bold)
        cropImages.add(R.drawable.mazie)
        cropImages.add(R.drawable.moong_dal)
        cropImages.add(R.drawable.mustard_oil)
        cropImages.add(R.drawable.mustard_seed)
        cropImages.add(R.drawable.paddy_basmati_1121)
        cropImages.add(R.drawable.polished_turmeric)
        cropImages.add(R.drawable.refined_soy_oil)
        cropImages.add(R.drawable.rice)
        cropImages.add(R.drawable.sesameseeds)
        cropImages.add(R.drawable.soyabean_meal)
        cropImages.add(R.drawable.soyabean)
        cropImages.add(R.drawable.toor_daal)
        cropImages.add(R.drawable.turmarice_farmer_unpolished)
        cropImages.add(R.drawable.turmeric)
        cropImages.add(R.drawable.wheat)
        cropImages.add(R.drawable.yellow_peas)

        plantList = arrayListOf<SuppliersCropData>()

        getDataUsingVolley()
        addData()

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
                    crop.Image = cropObject.getString("imageUrl0")
                    crop.PricePerKg = "₹ "+cropObject.getString("min_price")+"-"+cropObject.getString("max_price")+"/ kg"
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
//
//                    Toast.makeText(
//                        this@ViewSupplierActivity,
//                        "You Clicked on item no. $position",
//                        Toast.LENGTH_SHORT
//                    ).show()

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

            if(response.getString("imagePath") != "null")
            {
                Glide.with(this).load(response.getString("imagePath")).into( binding.imageView12)
                Glide.with(this).load(response.getString("imagePath")).into( binding.imageView15)
            }
            if(response.getString("background_imagePath") != "null")
            {
                Glide.with(this).load(response.getString("background_imagePath")).into( binding.imageView11)
            }
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

    private fun addData() {
//        for (i in cropNames.indices) {
//            cropList.add(i, ManageCropData(cropNames[i], cropImages[i], cropId[i]))
//        }

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        // val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = intent.getStringExtra("FARMER_ID")
        val url = "$baseAddressUrl/user/$userId/manageCrops"

        cropList = arrayListOf<ManageCropData>()
        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {
                    var cropObject = response.getJSONObject(i)
                    var crop = ManageCropData()
                    crop.Name = cropObject.getString("cropName")
                    crop.id = cropObject.getInt("id")
                    crop.crop_id = cropObject.getInt("cropId")
                    crop.Image = cropImages[crop.crop_id - 1]

                    cropList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.cropsRv.layoutManager = GridLayoutManager(this, 4)
            val adapter = ManageCropAdapter(cropList, this)
            binding.cropsRv.adapter = adapter

            adapter.setOnItemClickListener(object : ManageCropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

//                    if(position == cropList.size-1)
//                    {
//                        startActivity(Intent(context, SellerAddCropsActivity::class.java))
//                    }
//                    cropList.removeAt(position)
//                    adapter.notifyDataSetChanged()
                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
//                startActivity(
//                    Intent(
//                        this,
//                        ViewSupplierActivity::class.java
//                    ).putExtra("FARMER_ID", followList[position].FarmerID))

                }
            })

//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            //Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)

    }

}