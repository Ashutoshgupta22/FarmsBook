package com.farmsbook.farmsbook.seller.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropAdapter
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropAdapter2
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropData
import com.farmsbook.farmsbook.databinding.ActivitySellerManageCropsBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class SellerManageCropsActivity : AppCompatActivity() {
    private lateinit var cropList: ArrayList<ManageCropData>
    private lateinit var cropImages: ArrayList<Int>
    private lateinit var binding: ActivitySellerManageCropsBinding
    override fun onResume() {
        super.onResume()
        addData()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerManageCropsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.manageCropBtn.setOnClickListener {
            addData2()
            binding.manageCropBtn.visibility = View.GONE
            binding.doneBtn.visibility = View.VISIBLE

        }

        binding.doneBtn.setOnClickListener {
            addData()
            binding.manageCropBtn.visibility = View.VISIBLE
            binding.doneBtn.visibility = View.GONE
        }
    }

    private fun addData2() {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
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
                    crop.cropName = cropObject.getString("cropName")
                    crop.id = cropObject.getInt("id")
                    crop.cropId = cropObject.getInt("cropId")
                    crop.image = cropImages[crop.cropId - 1]

                    cropList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            binding.cropsRV.layoutManager = GridLayoutManager(this, 4)
            val adapter = ManageCropAdapter2(cropList, this)
            binding.cropsRV.adapter = adapter

            adapter.setOnItemClickListener(object : ManageCropAdapter2.onItemClickListener {
                override fun onItemClick(position: Int) {

                    deleteCrop(cropList[position].id.toString())
                    cropList.removeAt(position)
                    adapter.notifyDataSetChanged()
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

    private fun deleteCrop(id: String) {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/manageCrops/$id"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.DELETE, url, null, { response: JSONObject ->

            //Toast.makeText(this, "Deleted Crop", Toast.LENGTH_SHORT).show()

        }, { error -> // method to handle errors.

            //Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Profile Data", "Fail to get response = $error")
        })
        queue.add(request)
    }

    private fun addData() {
//        for (i in cropNames.indices) {
//            cropList.add(i, ManageCropData(cropNames[i], cropImages[i], cropId[i]))
//        }

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
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
                    crop.cropName = cropObject.getString("cropName")
                    crop.id = cropObject.getInt("id")
                    crop.cropId = cropObject.getInt("cropId")
                    crop.image = cropImages[crop.cropId - 1]

                    cropList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            cropList.add(ManageCropData("Add Crops", R.drawable.add_crop_btn, 0))

            binding.cropsRV.layoutManager = GridLayoutManager(this, 4)
            val adapter = ManageCropAdapter(cropList, this)
            binding.cropsRV.adapter = adapter

            adapter.setOnItemClickListener(object : ManageCropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    if(position == cropList.size-1)
                    {
                        startActivity(Intent(this@SellerManageCropsActivity, SellerAddCropsActivity::class.java))
                    }
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