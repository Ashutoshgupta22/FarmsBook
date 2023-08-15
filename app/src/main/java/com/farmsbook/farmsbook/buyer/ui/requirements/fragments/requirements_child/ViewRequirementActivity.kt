package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.InterestedSuppliersAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.InterestedSuppliersData
import com.farmsbook.farmsbook.buyer.ui.suppliers.ViewSupplierActivity
import com.farmsbook.farmsbook.databinding.ActivityViewRequirementBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.farmsbook.farmsbook.utility.TimeFormatter
import org.json.JSONArray
import org.json.JSONObject

class ViewRequirementActivity : AppCompatActivity() {

    private lateinit var plantList: ArrayList<InterestedSuppliersData>
    private lateinit var binding: ActivityViewRequirementBinding
    private lateinit var cropImages: java.util.ArrayList<Int>
    private lateinit var logoutDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewRequirementBinding.inflate(layoutInflater)
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
        getDataUsingVolley()
        val builder = AlertDialog.Builder(this)

        val view = layoutInflater.inflate(R.layout.delete_requirement_dialog, null)
        val no = view.findViewById<TextView>(R.id.cancel_logoutBtn)
        val yes = view.findViewById<TextView>(R.id.confirm_logoutBtn)
        yes.setOnClickListener {

            logoutDialog.dismiss()
            deleteRequirement()
            finish()

        }
        no.setOnClickListener {
            logoutDialog.dismiss()
        }
        builder.setView(view)
        logoutDialog = builder.create()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun deleteRequirement() {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val req_id = intent.getStringExtra("REQ_ID")
        val url = "$baseAddressUrl/user/$userId/requirements/${req_id}"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.DELETE, url, null, { response: JSONObject ->

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Requirements", "Fail to get response = $error")
        })
        queue.add(request)
    }

    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val req_id = intent.getStringExtra("REQ_ID")
        val url = "$baseAddressUrl/user/$userId/requirements/$req_id"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

            Glide.with(this).load(cropImages[response.getString("manageCropId").toInt()-1]).into( binding.imageView16)
            binding.requirementNameTv.text = response.getString("crop_name")
            val time = response.getString("timestamp")
            binding.postedOnTV.text = "Posted : " + TimeFormatter().getRelativeTime(time)
            binding.varietyTV.text = response.getString("variety")
            binding.PriceTV.text =
                response.getString("min_range") + "/kg to " + response.getString("max_range") + "/kg"
            binding.locationTV.text = response.getString("location")
            binding.quantityTV.text =
                response.getString("quantity") + " " + response.getString("quantity_unit")
            if (response.getString("transportation").equals("true"))
                binding.transportaitonTV.text = "Required"
            else
                binding.transportaitonTV.text = "Not Required"

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Profile Data", "Fail to get response = $error")
        })
        queue.add(request)

        plantList = arrayListOf<InterestedSuppliersData>()

        val url2 = "$baseAddressUrl/user/${userId}/requirements/$req_id/allInterested"

        val request2 = JsonArrayRequest(Request.Method.GET, url2, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {
                    var cropObject = response.getJSONObject(i)
                    var crop = InterestedSuppliersData()
                    crop.GroupName = cropObject.getString("companyName")
                    crop.Image = cropObject.getString("imagePath")
                    crop.phone = cropObject.getString("phone")
                    crop.FarmerName = cropObject.getString("name")
                    crop.FarmerID = cropObject.getString("parent_id").toString()
                    plantList.add(crop)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (plantList.size == 0) {
                binding.textView18.visibility = View.VISIBLE
                binding.textView19.visibility = View.GONE
                binding.recyclerView2.visibility = View.GONE
            } else {
                binding.textView18.visibility = View.GONE
                binding.textView19.visibility = View.VISIBLE
                binding.recyclerView2.visibility = View.VISIBLE
            }

            binding.recyclerView2.layoutManager = LinearLayoutManager(this)
            val adapter2 = InterestedSuppliersAdapter(plantList, this)
            binding.recyclerView2.adapter = adapter2

            adapter2.setOnItemClickListener(object :
                InterestedSuppliersAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
                    startActivity(
                        Intent(
                            this@ViewRequirementActivity,
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

                override fun callClick(position: Int) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${plantList[position].phone}")
                    startActivity(intent)
                }
            })


        }, { error -> // method to handle errors.
            //Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request2)
    }
}