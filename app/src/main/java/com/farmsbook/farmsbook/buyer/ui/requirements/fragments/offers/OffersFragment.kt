package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.offers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersData
import com.farmsbook.farmsbook.databinding.FragmentOffersBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class OffersFragment : Fragment() {
    private var _binding: FragmentOffersBinding? = null

    private lateinit var a: ArrayList<String>
    private val binding get() = _binding!!
    private lateinit var plantList: ArrayList<LatestOffersData>
    override fun onResume() {
        super.onResume()
        getDataUsingVolley()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        a = arrayListOf<String>()
        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //getDataUsingVolley()
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDataUsingVolley() {

        // url to post our data
        plantList = arrayListOf<LatestOffersData>()
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId =  sharedPreference?.getInt("USER_ID",0)
        val url = "$baseAddressUrl/user/$userId/offer"

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
                    var crop = LatestOffersData()
                   // crop.crop_name = cropObject.getString("crop_name")
                    crop.offer_to_crop_id = cropObject.getInt("offer_to_crop_id").toString()
                    crop.offer_to_farmer_id = cropObject.getInt("offer_to_farmer_id").toString()
                    crop.offering_price = cropObject.getInt("offering_price").toString()
                    crop.offer_status = cropObject.getBoolean("offer_status").toString()
                    crop.buyer_name = cropObject.getString("buyer_name").toString()
                    crop.phone = cropObject.getString("phone").toString()
                   // crop.max_price = cropObject.getInt("max_range").toString()
                    //crop.min_price = cropObject.getInt("min_range").toString()
                    crop.offering_quantity_unit = cropObject.getString("offering_quantity_unit").toString()
                    crop.offering_quantity = cropObject.getString("offering_quantity").toString()
                    if(cropObject.getBoolean("purchased_on").toString().equals("true"))
                        crop.purchased_on = "On Commission"
                    else
                        crop.purchased_on = "Fixed Rate"

//                   getPlantData(cropObject.getInt("offer_to_farmer_id").toString(),cropObject.getInt("offer_to_crop_id").toString())

//                        crop.name = a[0]
//                        crop.max = a[1]
//                        crop.min = a[2]

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
            val adapter = context?.let { LatestOffersAdapter(plantList, it) }
            binding.latestRequirementsRv.adapter = adapter
            adapter?.setOnItemClickListener(object : LatestOffersAdapter.onItemClickListener {
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

                override fun callBtnClick(position: Int) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${plantList[position].phone}")
                    startActivity(intent)
                }
            })
//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)

    }

    private fun getPlantData(farmerId: String , cropId: String) {

        val queue: RequestQueue = Volley.newRequestQueue(context)

        val url = "${BaseAddressUrl().baseAddressUrl}/user/$farmerId/listings/$cropId"
        val request2 = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

            a.add(response["crop_name"].toString())

            Log.d("Offer",a.toString())
//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request2)

    }
}