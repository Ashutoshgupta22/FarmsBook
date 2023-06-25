package com.farmsbook.farmsbook.seller.ui.listings.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersData
import com.farmsbook.farmsbook.databinding.FragmentInterestedBinding
import com.farmsbook.farmsbook.databinding.FragmentOffersBinding
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.InterestedAdapter
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.InterestedData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject


class InterestedFragment : Fragment() {
    private var _binding: FragmentInterestedBinding? = null

    private val binding get() = _binding!!
    private lateinit var cropId : String
    private lateinit var plantList: ArrayList<InterestedData>


    override fun onResume() {
        super.onResume()
        getDataUsingVolley()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentInterestedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getDataUsingVolley()

        return root
    }
    private fun getDataUsingVolley() {

        // url to post our data
        plantList = arrayListOf<InterestedData>()
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId =  sharedPreference?.getInt("USER_ID",0)
        val url = "$baseAddressUrl/user/$userId/interest"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for(i in 0 until response.length())
            {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = InterestedData()
                    // crop.crop_name = cropObject.getString("crop_name")
                    crop.crop_name = cropObject.getString("name").toString()
                    crop.interest_id = cropObject.getString("interest_id").toString()
                    crop.phone = cropObject.getString("phone").toString()
                    crop.buyer_name = cropObject.getString("interest_farmer").toString()
                    //crop.min_price = cropObject.getInt("min_range").toString()
                    //crop.offering_quantity_unit = cropObject.getString("offering_quantity_unit").toString()
                   // crop.quantity = cropObject.getString("offering_quantity").toString()
                    if(cropObject.getBoolean("type_of_buy").toString().equals("true"))
                        crop.purchased_on = "On Commission"
                    else
                        crop.purchased_on = "Fixed Rate"

                    plantList.add(crop)


                }catch(e:Exception)
                {
                    e.printStackTrace()
                }
            }
            if(plantList.size == 0)
            {
                binding.textView5.visibility = View.VISIBLE
                binding.latestRequirementsRv.visibility = View.GONE
                binding.textView51.visibility = View.GONE
            }
            else{
                binding.textView5.visibility = View.GONE
                binding.latestRequirementsRv.visibility = View.VISIBLE
                binding.textView51.visibility = View.VISIBLE
            }
            binding.latestRequirementsRv.layoutManager = LinearLayoutManager(context)
            val adapter = context?.let { InterestedAdapter(plantList, it) }
            binding.latestRequirementsRv.adapter = adapter
            adapter?.setOnItemClickListener(object : InterestedAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //startActivity(Intent(context, ViewRequirementActivity::class.java))
                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
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

                override fun deleteClick(position: Int) {
                    cropId = plantList[position].interest_id.toString()
                        deleteInterest()
                }
            })
//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)

    }

    private fun deleteInterest() {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/interest/${cropId}"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.DELETE, url, null, { response: JSONObject ->

        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Requirements", "Fail to get response = $error")
        })
        queue.add(request)
    }
}