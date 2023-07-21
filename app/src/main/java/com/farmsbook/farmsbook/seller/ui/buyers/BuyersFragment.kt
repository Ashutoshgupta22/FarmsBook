package com.farmsbook.farmsbook.seller.ui.buyers

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.ui.profile.ViewProfileActivity
import com.farmsbook.farmsbook.buyer.ui.suppliers.AddSupplierActivity
import com.farmsbook.farmsbook.buyer.ui.suppliers.ViewSupplierActivity
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter2
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter3
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter4
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersData
import com.farmsbook.farmsbook.databinding.FragmentBuyersBinding
import com.farmsbook.farmsbook.seller.ui.buyers.adapters.*
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class BuyersFragment : Fragment() {

    private lateinit var plantList: ArrayList<BuyersData>
    private lateinit var followList: ArrayList<BuyersData>
    private lateinit var addedList: ArrayList<BuyersData>
    private var _binding: FragmentBuyersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        getDataUsingVolley()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentBuyersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //getDataUsingVolley()

        binding.imageView30.setOnClickListener {
            startActivity(Intent(context, AddBuyerActivity::class.java))
        }

        return root
    }

    private fun getDataUsingVolley() {

        // url to post our data
        plantList= arrayListOf<BuyersData>()
        addedList= arrayListOf<BuyersData>()
        followList= arrayListOf<BuyersData>()
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference =activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/getBuyers"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = BuyersData()
                    crop.GroupName = cropObject.getString("group_name")
                    crop.Image = cropObject.getString("imagePath")
                    crop.phone = cropObject.getString("phone")
                    crop.Location = cropObject.getString("location")
                    crop.FarmerName = cropObject.getString("name")
                    crop.FarmerID = cropObject.getString("parent_id").toString()

                    if(cropObject.getBoolean("add_response_status") == false)
                        plantList.add(crop)
                    else{
                        addedList.add(crop)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if(plantList.size == 0)
            {
                binding.textView18.visibility = View.GONE
                binding.sentRequestRV.visibility = View.GONE
            }
            else{
                binding.textView18.visibility = View.VISIBLE
                binding.sentRequestRV.visibility = View.VISIBLE
            }

            if(addedList.size == 0)
            {
                binding.textView15.visibility = View.GONE
                binding.suppliersRv.visibility = View.GONE
            }
            else{
                binding.textView15.visibility = View.VISIBLE
                binding.suppliersRv.visibility = View.VISIBLE
            }

            binding.sentRequestRV.layoutManager = LinearLayoutManager(context)
            val adapter = context?.let { BuyersAdapter2(plantList, it) }
            binding.sentRequestRV.adapter = adapter

            adapter?.setOnItemClickListener(object : BuyersAdapter2.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
                    startActivity(Intent(context,ViewBuyerActivity::class.java).putExtra("ID",plantList[position].FarmerID))
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

            binding.suppliersRv.layoutManager = LinearLayoutManager(context)
            val adapter2 = context?.let { BuyersAdapter3(addedList, it) }
            binding.suppliersRv.adapter = adapter2

            adapter2?.setOnItemClickListener(object : BuyersAdapter3.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
                    startActivity(Intent(context,ViewBuyerActivity::class.java).putExtra("ID",addedList[position].FarmerID))
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
                    intent.data = Uri.parse("tel:${addedList[position].phone}")
                    startActivity(intent)
                }
            })
//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)


        val url2 = "$baseAddressUrl/user/$userId/getFarmerFollowRequest"

        val request2 = JsonArrayRequest(Request.Method.GET, url2, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = BuyersData()
                    crop.GroupName = cropObject.getString("company_name")
                    crop.Image = cropObject.getString("imagePath")
                    crop.FarmerName = cropObject.getString("name")
                    crop.id = cropObject.getInt("id").toString()
                    crop.FarmerID = cropObject.getString("sender_id").toString()

                    followList.add(crop)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if(followList.size == 0)
            {
                binding.textView10.visibility = View.GONE
                binding.requestRV.visibility = View.GONE
            }
            else{
                binding.textView10.visibility = View.VISIBLE
                binding.requestRV.visibility = View.VISIBLE
            }

            binding.requestRV.layoutManager = LinearLayoutManager(context)
            val adapter3 = context?.let { BuyersAdapter4(followList, it) }
            binding.requestRV.adapter = adapter3

            adapter3?.setOnItemClickListener(object : BuyersAdapter4.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
                    startActivity(Intent(context,ViewSupplierActivity::class.java).putExtra("FARMER_ID",followList[position].FarmerID))
//                val intent = Intent(this@MainActivity,CropDetailsActivity::class.java)
//                intent.putExtra("Name",plantList[position].Name)
//                intent.putExtra("Location",plantList[position].Location)
//                intent.putExtra("Farmer Name",plantList[position].FarmerName)
//                intent.putExtra("Availability",plantList[position].Availability)
//                intent.putExtra("PricePerKg",plantList[position].PricePerKg)
//                intent.putExtra("Quality",plantList[position].Quality)
//                startActivity(intent)
                }

                override fun acceptClick(position: Int) {
                    acceptRequest(followList[position].id.toString())
                    adapter3.notifyDataSetChanged()
                }

                override fun declineClick(position: Int) {
                    declineRequest(followList[position].id.toString())
                    adapter3.notifyDataSetChanged()
                }
            })


//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request2)


    }

    private fun declineRequest(requestId : String) {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/rejectFollowRequestOfBuyer/${requestId}/reject"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.DELETE, url, null, { response: JSONObject ->


        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Profile Data", "Fail to get response = $error")
        })
        queue.add(request)
    }

    private fun acceptRequest(requestId : String) {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/acceptFollowRequestOfBuyer/${requestId}/accept"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, null, { response: JSONObject ->


        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Profile Data", "Fail to get response = $error")
        })
        queue.add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}