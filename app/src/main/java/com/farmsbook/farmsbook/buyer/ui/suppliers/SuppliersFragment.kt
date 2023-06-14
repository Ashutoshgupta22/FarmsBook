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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.databinding.FragmentSuppliersBinding
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SuppliersFragment : Fragment() {

    private lateinit var plantList: ArrayList<SuppliersData>
    private lateinit var tempArrayList :ArrayList<SuppliersData>
    private var _binding: FragmentSuppliersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel = ViewModelProvider(this)[SuppliersViewModel::class.java]

        _binding = FragmentSuppliersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.suppliersRv.layoutManager = LinearLayoutManager(context)
        plantList = arrayListOf<SuppliersData>()
        val adapter = context?.let { SuppliersAdapter(plantList, it) }
        binding.suppliersRv.adapter = adapter

        adapter?.setOnItemClickListener(object : SuppliersAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                startActivity(Intent(context,ViewSupplierActivity::class.java))
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

    private fun postDataUsingVolley(
        name: String,
        variety: String,
        minPrice: String,
        maxPrice: String,
        location: String,
        amount: String,
        metric:String,
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
        val url = "$baseAddressUrl/user/4/requirements"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val respObj = JSONObject()
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val current = formatter.format(time)

        respObj.put("crop_name", name)
        respObj.put("variety", variety)
        respObj.put("type_of_buy", type_of_buy)
        respObj.put( "min_range", minPrice)
        respObj.put("max_range", maxPrice)
        respObj.put("quantity", amount)
        respObj.put("quantity_unit", metric)
        respObj.put("location", location)
        respObj.put("transportation", transportation)
        respObj.put("timestamp", current)
        respObj.put("interested_supplier", 0)
        respObj.put("requirement_status", true)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, respObj, {

            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

}