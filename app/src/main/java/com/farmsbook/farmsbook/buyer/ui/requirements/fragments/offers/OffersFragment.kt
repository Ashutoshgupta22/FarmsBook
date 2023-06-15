package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.offers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.home.ViewHomeCropActivity
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropAdapter
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.ViewRequirementActivity
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsData
import com.farmsbook.farmsbook.databinding.FragmentFavouritesBinding
import com.farmsbook.farmsbook.databinding.FragmentOffersBinding
import com.farmsbook.farmsbook.databinding.FragmentRequirementsChildBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray

class OffersFragment : Fragment() {
    private var _binding: FragmentOffersBinding? = null

    private val binding get() = _binding!!
    private lateinit var plantList: ArrayList<LatestOffersData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        plantList = arrayListOf<LatestOffersData>()
        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        val root: View = binding.root
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
        })

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun getDataUsingVolley() {
//
//        // url to post our data
//        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
//        val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
//        val userId =  sharedPreference?.getInt("USER_ID",0)
//        val url = "$baseAddressUrl/$userId/offer"
//
//        // creating a new variable for our request queue
//        val queue: RequestQueue = Volley.newRequestQueue(context)
//
//
//        // on below line we are calling a string
//        // request method to post the data to our API
//        // in this we are calling a post method.
//        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->
//
//            for(i in 0 until response.length())
//            {
//                try {
//
//                    var cropObject = response.getJSONObject(i)
//                    var crop = CropData()
//                    crop.crop_name = cropObject.getString("crop_name")
//                    crop.crop_image = cropObject.getString("crop_image")
//                    crop.crop_location = cropObject.getString("crop_location")
//                    crop.user = cropObject.getString("user")
//                    crop.offer = cropObject.getBoolean("offer").toString()
//                    crop.quantity = cropObject.getInt("quantity").toString()
//                    crop.id = cropObject.getInt("id").toString()
//                    crop.parent_id = cropObject.getInt("parent_id").toString()
//
//                    plantList.add(crop)
//                }catch(e:Exception)
//                {
//                    e.printStackTrace()
//                }
//            }
//
//            binding.latestRequirementsRv.layoutManager = LinearLayoutManager(context)
//            val adapter = context?.let { LatestOffersAdapter(plantList, it) }
//            binding.latestRequirementsRv.adapter = adapter
//
//            adapter?.setOnItemClickListener(object : LatestOffersAdapter.onItemClickListener {
//                override fun onItemClick(position: Int) {
//
//                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
//
////                    startActivity(Intent(context, ViewHomeCropActivity::class.java).putExtra("LISTED_ID",plantList[position].id).putExtra("PARENT_ID",plantList[position].parent_id))
////                val intent = Intent(this@MainActivity,CropDetailsActivity::class.java)
////                intent.putExtra("Name",plantList[position].Name)
////                intent.putExtra("Location",plantList[position].Location)
////                intent.putExtra("Farmer Name",plantList[position].FarmerName)
////                intent.putExtra("Availability",plantList[position].Availability)
////                intent.putExtra("PricePerKg",plantList[position].PricePerKg)
////                intent.putExtra("Quality",plantList[position].Quality)
////                startActivity(intent)
//                }
//            })
//
////            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
////                .show()
//        }, { error -> // method to handle errors.
//            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
//        })
//        queue.add(request)
//    }

}