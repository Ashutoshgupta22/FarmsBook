package com.farmsbook.farmsbook.buyer.ui.suppliers

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.ui.home.ViewHomeCropActivity
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropAdapter
import com.farmsbook.farmsbook.databinding.FragmentSuppliersBinding
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter2
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SuppliersFragment : Fragment() {

    private lateinit var plantList: ArrayList<SuppliersData>
    private lateinit var tempArrayList :ArrayList<SuppliersData>
    private lateinit var binding: FragmentSuppliersBinding

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel = ViewModelProvider(this)[SuppliersViewModel::class.java]

        binding = FragmentSuppliersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        plantList= arrayListOf<SuppliersData>()
        getDataUsingVolley()

        binding.floatingActionButton4.setOnClickListener {
            startActivity(Intent(context,AddSupplierActivity::class.java))
        }
        return root
    }

    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl/user/home_buyer"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = SuppliersData()
                    crop.GroupName = cropObject.getString("group_name")
                    crop.Image = cropObject.getString("image")
                    crop.Location = cropObject.getString("location")
                    crop.FarmerName = cropObject.getString("name")
                    crop.FarmerID = cropObject.getString("farmer_id").toString()

                    plantList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.suppliersRv.layoutManager = LinearLayoutManager(context)
            val adapter = context?.let { SuppliersAdapter2(plantList, it) }
            binding.suppliersRv.adapter = adapter

            adapter?.setOnItemClickListener(object : SuppliersAdapter2.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                    startActivity(Intent(context,ViewSupplierActivity::class.java).putExtra("FARMER_ID",plantList[position].FarmerID))
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