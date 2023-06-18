package com.farmsbook.farmsbook.seller.ui.listings.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.ViewRequirementActivity
import com.farmsbook.farmsbook.databinding.FragmentCropsListingBinding
import com.farmsbook.farmsbook.seller.ui.listings.AddListingActivity
import com.farmsbook.farmsbook.seller.ui.listings.ViewListingActivity
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.ListingsAdapter
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.ListingsData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray


class CropsListingFragment : Fragment() {


    private lateinit var binding : FragmentCropsListingBinding

    private lateinit var plantList: ArrayList<ListingsData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        plantList = arrayListOf()

        binding = FragmentCropsListingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getDataUsingVolley()


        binding.addRequirementBtn.setOnClickListener {
            startActivity(Intent(context , AddListingActivity::class.java))
        }

        return root
    }
    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId =  sharedPreference?.getInt("USER_ID",0)
        val url = "$baseAddressUrl/user/$userId/listings"

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
                    var crop = ListingsData()
                    crop.crop_name = cropObject.getString("crop_name")
                    crop.max_price = cropObject.getInt("max_price").toString()
                    crop.min_price = cropObject.getInt("min_price").toString()
                    crop.timestamp = cropObject.getString("timestamp").toString()
                    crop.quantity_unit = cropObject.getString("quantity_unit")
                    crop.list_id= cropObject.getInt("list_id").toString()
                    crop.receive_offer_status = cropObject.getString("receive_offer_status").toString()

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
            val adapter = context?.let { ListingsAdapter(plantList, it) }
            binding.latestRequirementsRv.adapter = adapter
            adapter?.setOnItemClickListener(object : ListingsAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    startActivity(Intent(context, ViewListingActivity::class.java).putExtra("LIST_ID",plantList[position].list_id))
//                Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
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
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
}