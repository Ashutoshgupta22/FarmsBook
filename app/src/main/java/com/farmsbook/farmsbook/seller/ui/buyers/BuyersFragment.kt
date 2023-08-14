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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.ui.suppliers.ViewSupplierActivity
import com.farmsbook.farmsbook.databinding.FragmentBuyersBinding
import com.farmsbook.farmsbook.seller.ui.buyers.adapters.*
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import kotlin.math.log

class BuyersFragment : Fragment() {

    private val plantList = arrayListOf<BuyersData>()
    private val followList =  arrayListOf<BuyersData>()
    private val addedList = arrayListOf<BuyersData>()
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

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference =activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)

        val queue: RequestQueue = Volley.newRequestQueue(context)

        getAddedBuyers(baseAddressUrl, userId)

        val url2 = "$baseAddressUrl/user/$userId/getFarmerFollowRequest"

        val request2 = JsonArrayRequest(Request.Method.GET, url2, null,
            { response: JSONArray ->

                followList.clear()

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
                    acceptRequest(position, adapter3)
                }

                override fun declineClick(position: Int) {

                    declineRequest(position, adapter3)
                }
            })


//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request2)
    }

    private fun getAddedBuyers(baseAddressUrl: String, userId: Int?) {

        Log.i("BuyersFragment", "getAddedBuyers: called ")
        addedList.clear()
        plantList.clear()

        val url = "$baseAddressUrl/user/$userId/getBuyers"

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
                    crop.FarmerID = cropObject.getString("parentId").toString()

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
            val adapter2 = context?.let { BuyersAdapter3(addedList, it) }!!
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
//                intent.putExtra("Quality",plantList[position].Quality)-
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
    }

    private fun declineRequest(position: Int, adapter3: BuyersAdapter4) {

        val requestId = followList[position].id.toString()

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/rejectFollowRequestOfBuyer/${requestId}/reject"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = StringRequest(Request.Method.DELETE, url,  { response: String ->

            Log.i("BuyersFrag", "declineBuyerFollowRequest: SUCCESS")
            followList.removeAt(position)
            adapter3.notifyDataSetChanged()

            if(followList.size == 0)
            {
                binding.textView10.visibility = View.GONE
                binding.requestRV.visibility = View.GONE
            }
            else{
                binding.textView10.visibility = View.VISIBLE
                binding.requestRV.visibility = View.VISIBLE
            }


        }, { error -> // method to handle errors.
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
            Log.e("BuyersFrag", "declineBuyerFollowRequest: FAILED ",error)
        })
        queue.add(request)
    }

    private fun acceptRequest(position: Int, adapter3: BuyersAdapter4) {

        val requestId = followList[position].id.toString()

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/acceptFollowRequestOfBuyer/${requestId}/accept"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = StringRequest(Request.Method.POST, url,  { response: String ->

            Log.i("BuyersFrag", "acceptBuyerFollowRequest: SUCCESS")
            followList.removeAt(position)
            adapter3.notifyDataSetChanged()
            getAddedBuyers(baseAddressUrl, userId)

            if(followList.size == 0)
            {
                binding.textView10.visibility = View.GONE
                binding.requestRV.visibility = View.GONE
            }
            else{
                binding.textView10.visibility = View.VISIBLE
                binding.requestRV.visibility = View.VISIBLE
            }


        }, { error -> // method to handle errors.
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
            Log.e("BuyersFrag", "acceptBuyerFollowRequest: FAILED ",error)
        })
        queue.add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}