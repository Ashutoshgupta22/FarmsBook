package com.farmsbook.farmsbook.buyer.ui.requirements

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.profile.ManageCropsActivity
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.RequirementConfirmationActivity
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.CropData
import com.farmsbook.farmsbook.databinding.ActivityAddRequirementBinding
import com.farmsbook.farmsbook.login.EnterDetailsFragment
import com.farmsbook.farmsbook.seller.ui.listings.fragments.ListingConfirmationActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AddRequirementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRequirementBinding
    private lateinit var cropList: ArrayList<ManageCropData>
    private lateinit var cropName: ArrayList<String>
    private var cropId: Int = 0
    private lateinit var name: AutoCompleteTextView
    private lateinit var variety: EditText
    private lateinit var minPrice: EditText
    private lateinit var maxPrice: EditText
    private lateinit var location: AutoCompleteTextView
    private lateinit var amount: EditText
    private lateinit var metrics: AutoCompleteTextView
    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var rb4: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRequirementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.hide()

        variety = findViewById(R.id.varietyEdt)
        minPrice = findViewById(R.id.minEdt)
        maxPrice = findViewById(R.id.maxEdt)
        amount = findViewById(R.id.amountEdt)
        rb1 = findViewById<RadioButton>(R.id.oc_rb)
        rb2 = findViewById<RadioButton>(R.id.fr_rb)
        rb3 = findViewById<RadioButton>(R.id.req_rb)
        rb4 = findViewById<RadioButton>(R.id.nreq_rb)

        var type_of_buy = false
        rb1.setOnClickListener {
            if (rb2.isChecked) {
                rb2.isChecked = false
                type_of_buy = false
            }
        }
        rb2.setOnClickListener {
            if (rb1.isChecked) {
                rb1.isChecked = false
                type_of_buy = true
            }
        }

        var transportation = false
        rb3.setOnClickListener {
            if (rb4.isChecked) {
                rb4.isChecked = false
                transportation = false
            }
        }
        rb4.setOnClickListener {
            if (rb3.isChecked) {
                rb3.isChecked = false
                transportation = true
            }
        }
       // addData()

       // val arrayAdapter4 = ArrayAdapter(this, R.layout.dropdown_item, cropName)
        name = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView4)
      //  name.setAdapter(arrayAdapter4)

        //Log.d("Crop List","Hello")

        name.setOnClickListener {

            if (cropName.isEmpty()) {

                val intent = Intent(this, ManageCropsActivity::class.java)
                startActivity(intent)
            }
        }


//        Log.d("Crop List","$cropName")

        val states = resources.getStringArray(R.array.States)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, states)
        location = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        location.setAdapter(arrayAdapter)

        val buyerLocation = getSharedPreferences("pref",
            MODE_PRIVATE).getString("buyerLocation", null)

        Log.i("AddRequirementActivity", "buyerLocation $buyerLocation")

        buyerLocation?.let {
            location.setText(it, false)
        }

        val metric = resources.getStringArray(R.array.Metrics)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_item , metric)
        metrics = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        metrics.setAdapter(arrayAdapter2)

        binding.backBtn.setOnClickListener {
            finish()
        }


        binding.confirmBtn.setOnClickListener {
            postDataUsingVolley(
                name.text.toString(),
                variety.text.toString(),
                minPrice.text.toString(),
                maxPrice.text.toString(),
                amount.text.toString(),
                type_of_buy,
                transportation
            )
            startActivity(Intent(this, RequirementConfirmationActivity::class.java))
            finish()
        }

    }

    private fun postDataUsingVolley(
        name: String,
        variety: String,
        minPrice: String,
        maxPrice: String,
        amount: String,
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
        val sharedPreference =  getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId =  sharedPreference.getInt("USER_ID",0)
        val url = "$baseAddressUrl/user/$userId/requirements"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val current = formatter.format(time)

        respObj.put("crop_name", name)
        for(i in cropList.indices)
        {
            if(cropList[i].Name == name.toString())
            {
                //Log.d("Crop List","${cropList}")
                //Log.d("Crop List","$i")
                cropId = cropList[i].crop_id
            }
        }
        Log.d("Crop List","$cropId")
        respObj.put("variety", variety)
        respObj.put("manageCropId", cropId)
        respObj.put("type_of_buy", type_of_buy)
        respObj.put( "min_range", minPrice)
        respObj.put("max_range", maxPrice)
        respObj.put("quantity", amount)
        respObj.put("quantity_unit", metrics.text.toString())
        respObj.put("location", location.text.toString())
        respObj.put("transportation", transportation)
        respObj.put("timestamp", current)
        respObj.put("interested_supplier", 0)
        respObj.put("requirement_status", true)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {

            Toast.makeText(this, "Requirement Added", Toast.LENGTH_SHORT)
                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
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
        cropName = arrayListOf()
        Log.d("Crop List","${cropList}")
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
                    crop.Name = cropObject.getString("cropName")
                    crop.id = cropObject.getInt("id")
                    crop.crop_id = cropObject.getInt("cropId")
                    cropName.add(cropObject.getString("cropName"))

                    cropList.add(crop)
                    Log.d("Crop List","${cropList}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val arrayAdap = ArrayAdapter(this, R.layout.dropdown_item, cropName)
                name.setAdapter(arrayAdap)

            }
           // Log.d("Crop List","${cropList}")
        }, { error -> // method to handle errors.
            Log.d("Crop List","${cropList}")
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()

        })
        queue.add(request)
    }

    override fun onResume() {
        super.onResume()

        addData()
    }
}