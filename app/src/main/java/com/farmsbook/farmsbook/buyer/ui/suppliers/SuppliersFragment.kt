package com.farmsbook.farmsbook.buyer.ui.suppliers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.farmsbook.farmsbook.databinding.FragmentSuppliersBinding
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.*
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import kotlin.collections.ArrayList

class SuppliersFragment : Fragment() {

    private lateinit var plantList: ArrayList<SuppliersData>
    private lateinit var followList: ArrayList<SuppliersData>
    private lateinit var addedList: ArrayList<SuppliersData>
    private lateinit var binding: FragmentSuppliersBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    override fun onResume() {
        super.onResume()
        getDataUsingVolley()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel = ViewModelProvider(this)[SuppliersViewModel::class.java]

        binding = FragmentSuppliersBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //getDataUsingVolley()

        binding.floatingActionButton4.setOnClickListener {
            startActivity(Intent(context,AddSupplierActivity::class.java))
        }
        return root
    }

    private fun getDataUsingVolley() {

        // url to post our data
        plantList= arrayListOf<SuppliersData>()
        addedList= arrayListOf<SuppliersData>()
        followList= arrayListOf<SuppliersData>()
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference =activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        Log.i("SuppliersFrag", "getDataUsingVolley: userId: $userId")

        val queue: RequestQueue = Volley.newRequestQueue(context)

        getAddedSuppliers(baseAddressUrl, userId)

        val url2 = "$baseAddressUrl/user/$userId/getBuyerFollowRequest"

        // creating a new variable for our request queue


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request2 = JsonArrayRequest(Request.Method.GET, url2, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = SuppliersData()
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
                binding.textView10.visibility = GONE
                binding.requestRV.visibility = GONE
            }
            else{
                binding.textView10.visibility = VISIBLE
                binding.requestRV.visibility = VISIBLE
            }

            binding.requestRV.layoutManager = LinearLayoutManager(context)
            val adapter3 = context?.let { SuppliersAdapter4(followList, it) }
            binding.requestRV.adapter = adapter3

            adapter3?.setOnItemClickListener(object : SuppliersAdapter4.onItemClickListener {
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
            Log.e("SuppliersFrag", "getSellerFollowRequest: Failed ", error)
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
        })
        queue.add(request2)

    }

    private fun getAddedSuppliers(baseAddressUrl: String, userId: Int?) {

        Log.i("SuppliersFragment", "getAddedSuppliers: called")

        val url = "$baseAddressUrl/user/$userId/getSuppliers"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            addedList.clear()
            plantList.clear()

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = SuppliersData()
                    crop.GroupName = cropObject.getString("group_name")
                    crop.Image = cropObject.getString("image")
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
            Log.d("Suppliers" , plantList.size.toString())
            Log.d("Suppliers" , addedList.size.toString())
            if(plantList.size == 0)
            {
                binding.textView18.visibility = GONE
                binding.sentRequestRV.visibility = GONE
            }
            else{
                binding.textView18.visibility = VISIBLE
                binding.sentRequestRV.visibility = VISIBLE
            }

            if(addedList.size == 0)
            {
                binding.textView15.visibility = GONE
                binding.suppliersRv.visibility = GONE
            }
            else{
                binding.textView15.visibility = VISIBLE
                binding.suppliersRv.visibility = VISIBLE
            }

            binding.sentRequestRV.layoutManager = LinearLayoutManager(context)
            val adapter = context?.let { SuppliersAdapter2(plantList, it) }
            binding.sentRequestRV.adapter = adapter

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

            binding.suppliersRv.layoutManager = LinearLayoutManager(context)
            val adapter2 = context?.let { SuppliersAdapter3(addedList, it) }
            binding.suppliersRv.adapter = adapter2

            adapter2?.setOnItemClickListener(object : SuppliersAdapter3.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
                    startActivity(Intent(context,ViewSupplierActivity::class.java).putExtra("FARMER_ID",addedList[position].FarmerID))
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

            Log.e("SuppliersFrag", "getAddedSuppliers: FAILED",error )
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            // Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)

    }

    private fun declineRequest(position: Int, adapter3: SuppliersAdapter4) {

        val requestId = followList[position].id.toString()
        
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)

        Log.i("SuppliersFrag", "declineRequest: userId: $userId   requestId: $requestId")
        val url = "$baseAddressUrl/user/$userId/rejectFollowRequestOfFarmer/${requestId}/reject"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = StringRequest(Request.Method.DELETE, url, {
                response: String ->

            Log.i("SuppliersFrag", "declineSellerFollowRequest: SUCCESS")
            followList.removeAt(position)
            adapter3.notifyDataSetChanged()

            if(followList.size == 0)
            {
                binding.textView10.visibility = GONE
                binding.requestRV.visibility = GONE
            }
            else{
                binding.textView10.visibility = VISIBLE
                binding.requestRV.visibility = VISIBLE
            }


        }, { error -> // method to handle errors.
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
            Log.e("SuppliersFrag", "declineSellerFollowRequest: FAILED ",error)
        })
        queue.add(request)

    }

    private fun acceptRequest(position: Int, adapter3: SuppliersAdapter4) {

        val requestId = followList[position].id.toString()

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/acceptFollowRequestOfFarmer/${requestId}/accept"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = StringRequest(Request.Method.POST, url,
            { response: String ->

            Log.i("SuppliersFrag", "acceptSellerFollowRequest: SUCCESS")

                followList.removeAt(position)
                adapter3.notifyDataSetChanged()
                getAddedSuppliers(baseAddressUrl, userId)

                if(followList.size == 0)
                {
                    binding.textView10.visibility = GONE
                    binding.requestRV.visibility = GONE
                }
                else{
                    binding.textView10.visibility = VISIBLE
                    binding.requestRV.visibility = VISIBLE
                }

            }, { error -> // method to handle errors.
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
            Log.e("SuppliersFrag", "acceptSellerFollowRequest: FAILED ",error)
        })
        queue.add(request)
    }

}