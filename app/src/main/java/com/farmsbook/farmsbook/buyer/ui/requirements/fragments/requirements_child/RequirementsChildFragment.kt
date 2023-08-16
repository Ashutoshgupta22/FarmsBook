package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.requirements.AddRequirementActivity
import com.farmsbook.farmsbook.databinding.FragmentRequirementsChildBinding
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.PreviousRequirementsAdapter
import com.farmsbook.farmsbook.login.LoginActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.ListingsAdapter
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.ListingsData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.farmsbook.farmsbook.utility.TimeFormatter
import org.json.JSONArray
import org.json.JSONObject

class RequirementsChildFragment : Fragment() {

    private var _binding: FragmentRequirementsChildBinding? = null
    private lateinit var logoutDialog: AlertDialog
    private lateinit var cropImages: java.util.ArrayList<Int>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter:LatestRequirementsAdapter
    private var clickedPosition = -1
    private lateinit var plantList: ArrayList<LatestRequirementsData>

    override fun onResume() {
        super.onResume()
        getDataUsingVolley()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment




        _binding = FragmentRequirementsChildBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cropImages = arrayListOf()
        cropImages.add(R.drawable.bajra)
        cropImages.add(R.drawable.barley)
        cropImages.add(R.drawable.basmati_paddy)
        cropImages.add(R.drawable.black_pepper)
        cropImages.add(R.drawable.cashew)
        cropImages.add(R.drawable.castor_seeds)
        cropImages.add(R.drawable.chana)
        cropImages.add(R.drawable.chilli)
        cropImages.add(R.drawable.coffee)
        cropImages.add(R.drawable.coriander_seeds)
        cropImages.add(R.drawable.cotton)
        cropImages.add(R.drawable.groundnut)
        cropImages.add(R.drawable.guar_gum_refind_splits)
        cropImages.add(R.drawable.guarseed)
        cropImages.add(R.drawable.jaggery)
        cropImages.add(R.drawable.jeera)
        cropImages.add(R.drawable.jowar_white)
        cropImages.add(R.drawable.jowar_yellow)
        cropImages.add(R.drawable.jowar)
        cropImages.add(R.drawable.kapas)
        cropImages.add(R.drawable.maize_kharif)
        cropImages.add(R.drawable.masoor_bold)
        cropImages.add(R.drawable.mazie)
        cropImages.add(R.drawable.moong_dal)
        cropImages.add(R.drawable.mustard_oil)
        cropImages.add(R.drawable.mustard_seed)
        cropImages.add(R.drawable.paddy_basmati_1121)
        cropImages.add(R.drawable.polished_turmeric)
        cropImages.add(R.drawable.refined_soy_oil)
        cropImages.add(R.drawable.rice)
        cropImages.add(R.drawable.sesameseeds)
        cropImages.add(R.drawable.soyabean_meal)
        cropImages.add(R.drawable.soyabean)
        cropImages.add(R.drawable.toor_daal)
        cropImages.add(R.drawable.turmarice_farmer_unpolished)
        cropImages.add(R.drawable.turmeric)
        cropImages.add(R.drawable.wheat)
        cropImages.add(R.drawable.yellow_peas)

        val builder = AlertDialog.Builder(requireContext())



        val view = layoutInflater.inflate(R.layout.delete_requirement_dialog, null)
        val no = view.findViewById<TextView>(R.id.cancel_logoutBtn)
        val yes = view.findViewById<TextView>(R.id.confirm_logoutBtn)
        yes.setOnClickListener {

            logoutDialog.dismiss()
            deleteRequirement()
            getDataUsingVolley()

        }
        no.setOnClickListener {
            logoutDialog.dismiss()
        }
        builder.setView(view)
        logoutDialog = builder.create()

       //getDataUsingVolley()
        binding.addRequirementBtn.setOnClickListener {
            startActivity(Intent(context , AddRequirementActivity::class.java))
        }

        return root
    }

    private fun deleteRequirement() {

        val cropId = plantList[clickedPosition].req_id.toString()
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/requirements/${cropId}"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = StringRequest(Request.Method.DELETE, url,
            { response: String ->

                plantList.removeAt(clickedPosition)
                adapter.notifyItemRemoved(clickedPosition)

        }, { error -> // method to handle errors.

                Toast.makeText(requireContext(), "Something went wrong!",
                    Toast.LENGTH_SHORT).show()
                Log.e("RequirementsChildFragment", "Failed" ,error)
        })
        queue.add(request)
    }

    private fun getDataUsingVolley() {

        // url to post our data
        plantList = arrayListOf<LatestRequirementsData>()
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
                    crop.crop_id = cropObject.getString("manageCropId")
                    crop.Image = cropImages[cropObject.getString("manageCropId").toInt()-1]
                    crop.req_id = cropObject.getInt("req_id").toString()
                    crop.max_price = cropObject.getInt("max_range").toString()
                    crop.min_price = cropObject.getInt("min_range").toString()
                    val time = cropObject.getString("timestamp")
                    crop.timestamp = TimeFormatter().getRelativeTime(time)
                    crop.quantity_unit = cropObject.getString("quantity_unit").toString()
                    crop.interested_suppliers = cropObject.getJSONArray("reqInterestedUser").length().toString()
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
            adapter = context?.let { LatestRequirementsAdapter(plantList, it) }!!
            binding.latestRequirementsRv.adapter = adapter
            adapter?.setOnItemClickListener(object : LatestRequirementsAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    startActivity(Intent(context, ViewRequirementActivity::class.java).putExtra("REQ_ID",plantList[position].req_id))
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

                override fun deleteClick(position: Int) {

                    clickedPosition = position
                    if(clickedPosition != -1 ) logoutDialog.show()
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
