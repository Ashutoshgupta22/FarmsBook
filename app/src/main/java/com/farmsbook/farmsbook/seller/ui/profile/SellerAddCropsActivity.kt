package com.farmsbook.farmsbook.seller.ui.profile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropAdapter
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropData
import com.farmsbook.farmsbook.databinding.ActivitySellerAddCropsBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class SellerAddCropsActivity : AppCompatActivity() {

    private lateinit var cropList: ArrayList<ManageCropData>
    private lateinit var cropNames: ArrayList<String>
    private lateinit var cropImages: ArrayList<Int>
    private lateinit var cropId: ArrayList<String>
    private lateinit var binding: ActivitySellerAddCropsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerAddCropsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

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

        addData()

    }
    private fun addData() {
//        for (i in cropNames.indices) {
//            cropList.add(i, ManageCropData(cropNames[i], cropImages[i], cropId[i]))
//        }

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/manageAllCrops"

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
                    crop.id = cropObject.getInt("cropId")
                    crop.image = cropImages[crop.id - 1]

                    cropList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.cropsRV.layoutManager = GridLayoutManager(this, 4)
            val adapter = ManageCropAdapter(cropList, this)
            binding.cropsRV.adapter = adapter

            adapter.setOnItemClickListener(object : ManageCropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    addCrop(cropList[position].id.toString())
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

    private fun addCrop(cropId: String) {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference.getInt("USER_ID", 0)

        val url = "$baseAddressUrl/user/$userId/manageCrops/${cropId}"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {


            //finish()


            //Toast.makeText(this, "Added Crop", Toast.LENGTH_SHORT).show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
}