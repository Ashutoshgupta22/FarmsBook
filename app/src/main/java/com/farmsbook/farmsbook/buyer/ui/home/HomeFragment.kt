package com.farmsbook.farmsbook.buyer.ui.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropAdapter
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData
import com.farmsbook.farmsbook.databinding.FragmentHomeBinding
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var adapter : CropAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreference: SharedPreferences
    private var userId: Int = 0
    private lateinit var plantList: ArrayList<CropData>
    private lateinit var tempArrayList: ArrayList<CropData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)!!
        userId = sharedPreference.getInt("USER_ID", 0)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getDataUsingVolley("/user/$userId/home_buyer_main")

        binding.searchEdt.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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

        var extension = "/user/$userId/home_buyer_main"
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
            extension = "/user/$userId/home_buyer01"
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        pricehl.setOnClickListener {
            extension = "/user/$userId/home_buyer10"
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        product_atoz.setOnClickListener {
            extension = "/user/$userId/home_buyerAZ"
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }
        product_ztoa.setOnClickListener {
            extension = "/user/$userId/home_buyerZA"
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


        var extension = "/user/$userId/home_buyer_main"
        val button = dialog.findViewById<Button>(R.id.button3)
        button.setOnClickListener {

            if(state.text.toString().equals("Select your state") && crops.text.toString().equals("Select your crops"))
            {
                getDataUsingVolley(extension)
                dialog.dismiss()
            }else if(state.text.toString().equals("Select your state") && !TextUtils.isEmpty(crops.text.toString()))
            {
                getDataUsingVolley("/user/$userId/home_buyer_state/${crops.text.toString()}")
                dialog.dismiss()
            } else if(crops.text.toString().equals("Select your crops")&& !TextUtils.isEmpty(state.text.toString()))
            {
                getDataUsingVolley("/user/$userId/home_buyer_state/${state.text.toString()}")
                dialog.dismiss()
            }else {
                getDataUsingVolley("/user/${userId}/home_buyer_states_crops/state/${state.text}/crop/${crops.text}")
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
    private fun getDataUsingVolley(extension: String) {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl$extension"

        plantList = arrayListOf<CropData>()
        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {
                    var cropObject = response.getJSONObject(i)
                    var crop = CropData()
                    crop.crop_name = cropObject.getString("cropName")
                    crop.crop_image = cropObject.getString("imageUrl0")
                    crop.crop_location = cropObject.getString("crop_location")
                    crop.user = cropObject.getString("user")
                    crop.offer = cropObject.getBoolean("offer").toString()
                    crop.quantity = cropObject.getInt("quantity").toString()
                    crop.min_price = cropObject.getInt("crop_min_price").toString()
                    crop.max_price = cropObject.getInt("crop_max_price").toString()
                    crop.id = cropObject.getInt("id").toString()
                    crop.parent_id = cropObject.getInt("parent_id").toString()

                    plantList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.cropRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter = context?.let { CropAdapter(plantList, it) }!!
            binding.cropRecyclerView.adapter = adapter


            adapter?.setOnItemClickListener(object : CropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                    startActivity(
                        Intent(
                            context,
                            ViewHomeCropActivity::class.java
                        ).putExtra("LISTED_ID", plantList[position].id)
                            .putExtra("PARENT_ID", plantList[position].parent_id)
                    )
//                val intent = Intent(this@MainActivity,CropDetailsActivity::class.java)
//                intent.putExtra("Name",plantList[position].Name)
//                intent.putExtra("Location",plantList[position].Location)
//                intent.putExtra("Farmer Name",plantList[position].FarmerName)
//                intent.putExtra("Availability",plantList[position].Availability)
//                intent.putExtra("PricePerKg",plantList[position].PricePerKg)
//                intent.putExtra("Quality",plantList[position].Quality)
//                startActivity(intent)
                }

                override fun offerPrice(position: Int) {
                    startActivity(Intent(context,PostOfferActivity::class.java).putExtra("LISTED_ID", plantList[position].id)
                        .putExtra("PARENT_ID", plantList[position].parent_id))
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

