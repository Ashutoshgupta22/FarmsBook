package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child

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
import com.farmsbook.farmsbook.buyer.ui.requirements.AddRequirementActivity
import com.farmsbook.farmsbook.databinding.FragmentRequirementsChildBinding
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.PreviousRequirementsAdapter
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.ListingsAdapter
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.ListingsData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray

class RequirementsChildFragment : Fragment() {

    private var _binding: FragmentRequirementsChildBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var plantList: ArrayList<LatestRequirementsData>
    private lateinit var plantList2 :ArrayList<LatestRequirementsData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        plantList = arrayListOf<LatestRequirementsData>()


        _binding = FragmentRequirementsChildBinding.inflate(inflater, container, false)
        val root: View = binding.root

       getDataUsingVolley()
        binding.addRequirementBtn.setOnClickListener {
            startActivity(Intent(context , AddRequirementActivity::class.java))
        }



        return root
    }

    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId =  sharedPreference?.getInt("USER_ID",0)
        val url = "$baseAddressUrl/user/$userId/requirements"

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
                    var crop = LatestRequirementsData()
                    crop.crop_name = cropObject.getString("crop_name")
                    crop.req_id = cropObject.getInt("req_id").toString()
                    crop.max_price = cropObject.getInt("max_range").toString()
                    crop.min_price = cropObject.getInt("min_range").toString()
                    crop.timestamp = cropObject.getString("timestamp").toString()
                    crop.interested_suppliers = cropObject.getString("interested_supplier").toString()

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
            val adapter = context?.let { LatestRequirementsAdapter(plantList, it) }
            binding.latestRequirementsRv.adapter = adapter
            adapter?.setOnItemClickListener(object : LatestRequirementsAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    startActivity(Intent(context, ViewRequirementActivity::class.java))
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
