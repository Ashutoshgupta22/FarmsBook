package com.farmsbook.farmsbook.seller.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.databinding.FragmentSellerHomeBinding
import com.farmsbook.farmsbook.seller.ui.home.adapters.CropAdapter
import com.farmsbook.farmsbook.seller.ui.home.adapters.CropData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray

class HomeFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var binding: FragmentSellerHomeBinding

    private lateinit var plantList: ArrayList<CropData>
    private lateinit var tempArrayList :ArrayList<CropData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        plantList = arrayListOf<CropData>()

        binding = FragmentSellerHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchEdt.setOnClickListener {
            startActivity(Intent(context,HomeSearchActivity::class.java))
        }

        getDataUsingVolley()


        return root
    }

    private fun getDataUsingVolley() {

        Log.d("DataFetch","Function Called")
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl/home_farmer"

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
                    var crop = CropData()
                    crop.crop_name = cropObject.getString("buyer_crop_name")
                    crop.crop_image = cropObject.getString("buyer_crop_image")
                    crop.crop_location = cropObject.getString("buyer_location")
                    crop.timestamp = cropObject.getString("timestamp")
                    crop.user = cropObject.getString("user")
                    crop.offer = cropObject.getBoolean("buyer_interest_status").toString()
                    crop.quantity = cropObject.getInt("buyer_quantity").toString()
                    crop.id = cropObject.getString("buyer_id").toString()
                    crop.parent_id = cropObject.getInt("parent_id").toString()

                    plantList.add(crop)
                }catch(e:Exception)
                {
                    Log.d("Data Fetch",e.toString())
                    e.printStackTrace()
                }
            }
            binding.cropRecyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = context?.let { CropAdapter(plantList, it) }
            binding.cropRecyclerView.adapter = adapter

            adapter?.setOnItemClickListener(object : CropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

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