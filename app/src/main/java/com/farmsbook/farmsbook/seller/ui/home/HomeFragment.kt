package com.farmsbook.farmsbook.seller.ui.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.FragmentSellerHomeBinding
import com.farmsbook.farmsbook.seller.ui.home.adapters.CropAdapter
import com.farmsbook.farmsbook.seller.ui.home.adapters.CropData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.farmsbook.farmsbook.utility.TimeFormatter
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var binding: FragmentSellerHomeBinding
    private lateinit var plantList: ArrayList<CropData>
    private lateinit var cropImages: java.util.ArrayList<Int>
    private lateinit var tempArrayList :ArrayList<CropData>
    private lateinit var adapter:CropAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        binding = FragmentSellerHomeBinding.inflate(inflater, container, false)
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

        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        getDataUsingVolley("/user/$userId/home_farmer_main")

        binding.searchEdt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i("HomeFrag Seller", "onTextChanged: filter called")
                if (plantList.isNotEmpty())
                    filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.sortBtn.setOnClickListener {

            showDialog()

        }

        binding.filterBtn.setOnClickListener {
            showFilterDialog()
        }
        //getDataUsingVolley("/user/$userId/home_farmer_main")


        return root
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredNames = ArrayList<CropData>()
        //looping through existing elements and adding the element to filtered list
        plantList.filterTo(filteredNames) {
            //if the existing elements contains the search input
            it.crop_name?.toLowerCase(Locale.ROOT)!!.contains(text.toLowerCase(Locale.ROOT))
        }
        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filteredNames)
    }
    private fun getDataUsingVolley(extension:String) {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl$extension"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        plantList = arrayListOf<CropData>()
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
                   // crop.crop_image = cropObject.getString("buyer_crop_image")
                    crop.crop_image = cropImages[cropObject.getString("cropId").toInt()-1]
                    crop.crop_location = cropObject.getString("buyer_location")
                    val time = cropObject.getString("timestamp")
                    crop.timestamp = TimeFormatter().getRelativeTime(time)
                    crop.user = cropObject.getString("user")
                    crop.offer = cropObject.getBoolean("buyer_interest_status").toString()
                    crop.min_price = cropObject.getInt("buyer_min_price").toString()
                    crop.max_price = cropObject.getInt("buyer_max_price").toString()
                    crop.quantity = cropObject.getInt("buyer_quantity").toString() +" "+cropObject.getString("quantity_unit").toString()
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
            adapter = context?.let { CropAdapter(plantList, it) }!!
            binding.cropRecyclerView.adapter = adapter

            adapter?.setOnItemClickListener(object : CropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                   // Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
                    startActivity(Intent(context,ViewSellerHomeCropActivity::class.java).putExtra("req_id",plantList[position].id).putExtra("PARENT_ID",plantList[position].parent_id))
//                intent.putExtra("Name",plantList[position].Name)
//                intent.putExtra("Location",plantList[position].Location)
//                intent.putExtra("Farmer Name",plantList[position].FarmerName)
//                intent.putExtra("Availability",plantList[position].Availability)
//                intent.putExtra("PricePerKg",plantList[position].PricePerKg)
//                intent.putExtra("Quality",plantList[position].Quality)
//
                }

                override fun interestedClick(position: Int) {
                    postDataUsingVolley(position,adapter)
//                    val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
//                    val userId = sharedPreference?.getInt("USER_ID", 0)
//                    getDataUsingVolley("/user/$userId/home_farmer_main")
                }
            })


        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
    private fun postDataUsingVolley(position: Int, adapter: CropAdapter) {

        val listedId = plantList[position].id.toString()
        val parentId = plantList[position].parent_id.toString()


//        {
//            "offerId": 1,
//            "purchased_on": true,
//            "rate_of_commission": 5,
//            "offering_price": 10,
//            "offering_quantity_unit": "kg",
//            "offering_quantity": 100,
//            "transportation": false,
//            "delivery_place": "New York",
//            "offer_status": "active"
//        }
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$parentId/requirements/$listedId/value/1/$userId"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val respObj = JSONObject()



        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {

            plantList.removeAt(position)
            adapter.notifyItemRemoved(position)

            Toast.makeText(context, "Posted Interest", Toast.LENGTH_SHORT)
                .show()


        }, { error -> // method to handle errors.
            Toast.makeText(getContext(), "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
    private fun showDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.sort_bottom_sheet_layout)

        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)

        val topSeller = dialog.findViewById<TextView>(R.id.top_seller)
        val pricelh = dialog.findViewById<TextView>(R.id.price_low_to_high)
        val pricehl = dialog.findViewById<TextView>(R.id.price_high_to_low)
        val product_atoz = dialog.findViewById<TextView>(R.id.product_atoz)
        val product_ztoa = dialog.findViewById<TextView>(R.id.product_ztoa)

        var extension ="/home_farmer"
        val button = dialog.findViewById<Button>(R.id.button2)
        button.setOnClickListener {

            getDataUsingVolley(extension)

            dialog.dismiss()
        }
        val closeBtn = dialog.findViewById<ImageView>(R.id.closeBtn)
        closeBtn.setOnClickListener { dialog.dismiss() }

        topSeller.setOnClickListener {
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        pricelh.setOnClickListener {
            extension = "/user/$userId/home_farmer01"
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        pricehl.setOnClickListener {
            extension = "/user/$userId/home_farmer10"
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        product_atoz.setOnClickListener {
            extension = "/user/$userId/home_farmerAZ"
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }
        product_ztoa.setOnClickListener {
            extension = "/user/$userId/home_farmerZA"
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        }

        dialog.show()
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimation
        dialog.getWindow()?.setGravity(Gravity.BOTTOM)

    }

    private fun showFilterDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.filter_bottom_sheet_layout)

        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)

        val states = resources.getStringArray(R.array.States)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, states)
        val state = dialog.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        state.setAdapter(arrayAdapter)

        val Crops = resources.getStringArray(R.array.Crops)
        val arrayAdapter2 = ArrayAdapter(requireContext(), R.layout.dropdown_item, Crops)
        val crops = dialog.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        crops.setAdapter(arrayAdapter2)


        var extension = "/user/$userId/home_farmer_main"
        val button = dialog.findViewById<Button>(R.id.button3)
        button.setOnClickListener {

            if(state.text.toString().equals("Select Your State") && crops.text.toString().equals("Select your crops"))
            {
                getDataUsingVolley(extension)
                dialog.dismiss()
            }else if(state.text.toString().equals("Select Your State") && !TextUtils.isEmpty(crops.text.toString()))
            {
                getDataUsingVolley("/user/$userId/home_farmer_state/${crops.text.toString()}")
                dialog.dismiss()
            } else if(crops.text.toString().equals("Select your crops")&& !TextUtils.isEmpty(state.text.toString()))
            {
                getDataUsingVolley("/user/$userId/home_farmer_state/${state.text.toString()}")
                dialog.dismiss()
            }else {
                getDataUsingVolley("/user/${userId}/home_farmer_states_crops/state/${state.text}/crop/${crops.text}")
                dialog.dismiss()
            }
        }
        val closeBtn = dialog.findViewById<ImageView>(R.id.closeBtn)
        closeBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimation
        dialog.getWindow()?.setGravity(Gravity.BOTTOM)
    }
}