package com.farmsbook.farmsbook.buyer.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.cropslistings.CropData
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageAdminCropAdapter
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropAdapter
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropData
import com.farmsbook.farmsbook.databinding.ActivityAddCropBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class AddCropActivity : AppCompatActivity() {
    private lateinit var cropList: ArrayList<ManageCropData>
    private lateinit var cropNames: ArrayList<String>
    private lateinit var cropImages: ArrayList<Int>
    private lateinit var cropId: ArrayList<String>
    private lateinit var binding: ActivityAddCropBinding
    val baseUrl = BaseAddressUrl().baseAddressUrl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
//        cropNames = arrayListOf()
//        cropImages = arrayListOf()
//        cropId = arrayListOf()
//        cropList = arrayListOf()
//        cropNames.add("Bajara")
//        cropNames.add("Barley")
//        cropNames.add("Basmati Paddy")
//        cropNames.add("Black Pepper")
//        cropNames.add("Cashew")
//        cropNames.add("Caster Seeds")
//        cropNames.add("Chana")
//        cropNames.add("Chili")
//        cropNames.add("Coffee")
//        cropNames.add("Coriander Leaves")
//        cropNames.add("Cotton")
//        cropNames.add("Ground Nuts")
//        cropNames.add("Guar Gum Refind Splits")
//        cropNames.add("Guar seeds")
//        cropNames.add("Jaggery")
//        cropNames.add("Jeera")
//        cropNames.add("Jowar White")
//        cropNames.add("Jower Yellow")
//        cropNames.add("Jower")
//        cropNames.add("Kapas")
//        cropNames.add("Maize Kharif")
//        cropNames.add("Masoor Bold")
//        cropNames.add("Maize")
//        cropNames.add("Moong Dal")
//        cropNames.add("Mustard Oil")
//        cropNames.add("Mustard Seed")
//        cropNames.add("Basmati Paddy 1121")
//        cropNames.add("Polished Turmeric")
//        cropNames.add("Refined Soy Oil")
//        cropNames.add("Rice")
//        cropNames.add("Sesameseeds")
//        cropNames.add("Soyabean Meal")
//        cropNames.add("Soyabean")
//        cropNames.add("Toor Daal")
//        cropNames.add("Turmarice Farmer Unpolished")
//        cropNames.add("Turmaric")
//        cropNames.add("Wheat")
//        cropNames.add("Yellow Peas")

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

//        cropId.add("1")
//        cropId.add("2")
//        cropId.add("2")
//        cropId.add("4")
//        cropId.add("5")
//        cropId.add("6")
//        cropId.add("7")
//        cropId.add("8")
//        cropId.add("9")
//        cropId.add("10")
//        cropId.add("11")
//        cropId.add("12")
//        cropId.add("13")
//        cropId.add("14")
//        cropId.add("15")
//        cropId.add("16")
//        cropId.add("17")
//        cropId.add("18")
//        cropId.add("19")
//        cropId.add("20")
//        cropId.add("21")
//        cropId.add("22")
//        cropId.add("23")
//        cropId.add("24")
//        cropId.add("25")
//        cropId.add("26")
//        cropId.add("27")
//        cropId.add("28")
//        cropId.add("29")
//        cropId.add("30")
//        cropId.add("31")
//        cropId.add("32")
//        cropId.add("33")
//        cropId.add("34")
//        cropId.add("35")
//        cropId.add("36")
//        cropId.add("37")
//        cropId.add("38")
        binding.backBtn.setOnClickListener {
            finish()
        }
        getAllCrops()
        getAllAdminCrops()
    }

    private fun getAllCrops() {

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
                    val cropObject = response.getJSONObject(i)
                    val crop = ManageCropData()
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
            binding.cropsRV.isNestedScrollingEnabled = false

            adapter.setOnItemClickListener(object : ManageCropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    addCrop(cropList[position].id.toString())
                    cropList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            })

        }, { error -> // method to handle errors.
            //Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)


    }

    private fun getAllAdminCrops() {

        val queue = Volley.newRequestQueue(this)
        val url = "$baseUrl/admin/allCrops"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url, null, { response: JSONArray ->

                val cropList = arrayListOf<CropData>()

                for (i in 0 until response.length()) {

                    val cropObj = response.getJSONObject(i)
                    val id = cropObj.getInt("cropId")
                    // val parentId = cropObj.getInt("parentId")
                    val name = cropObj.getString("cropName")
                    //  val status = cropObj.getString("status")
                    val imagePath = cropObj.getString("imagePath")

                    cropList.add(CropData(id, null, name, null, imagePath))
                }

                binding.rvAdminCrops.apply {

                    isNestedScrollingEnabled = false
                    layoutManager = GridLayoutManager(this@AddCropActivity, 4)
                    adapter = ManageAdminCropAdapter(cropList) { position ->

                        addAdminCrop(cropList[position].id.toString())
                        //cropList.removeAt(position)
                        adapter?.notifyItemRemoved(position)
                    }
                }

            }) { error: VolleyError ->
            Log.e("AddCropActivity", "getAllAdminCrops: FAILED", error)
        }
        queue.add(request)
    }

    private fun addAdminCrop(cropId: String) {

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference.getInt("USER_ID", 0)

        val url = "$baseAddressUrl/user/${userId}/adminManageCrops/${cropId}"


        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {


            //finish()


            //  Toast.makeText(this, "Added Crop", Toast.LENGTH_SHORT).show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

    private fun addCrop(cropId: String) {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference.getInt("USER_ID", 0)

        val url = "$baseAddressUrl/user/$userId/manageCrops/${cropId}"
        // val url = "$baseAddressUrl/user/${userId}/adminManageCrops/${cropId}"


        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {


            //finish()


            //  Toast.makeText(this, "Added Crop", Toast.LENGTH_SHORT).show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
}


