package com.farmsbook.farmsbook.buyer.ui.home

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import com.farmsbook.farmsbook.databinding.FragmentHomeBinding
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropAdapter
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var binding:FragmentHomeBinding

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

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchEdt.setOnClickListener {
            startActivity(Intent(context,HomeSearchActivity::class.java))
        }

        binding.sortBtn.setOnClickListener {

            showDialog()

        }
        getDataUsingVolley()


        return root
    }

    private fun showDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.sort_bottom_sheet_layout)

        val topSeller = dialog.findViewById<TextView>(R.id.top_seller)
        val pricelh = dialog.findViewById<TextView>(R.id.price_low_to_high)
        val pricehl = dialog.findViewById<TextView>(R.id.price_high_to_low)
        val product_atoz = dialog.findViewById<TextView>(R.id.product_atoz)
        val product_ztoa = dialog.findViewById<TextView>(R.id.product_ztoa)

        val button = dialog.findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            dialog.dismiss()
        }
        val closeBtn = dialog.findViewById<ImageView>(R.id.closeBtn)
        closeBtn.setOnClickListener { dialog.dismiss() }

        topSeller.setOnClickListener {
            topSeller.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        }

        pricelh.setOnClickListener {
            pricelh.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        }

        pricehl.setOnClickListener {
            pricehl.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        }

        product_atoz.setOnClickListener {
            product_atoz.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        }
        product_ztoa.setOnClickListener {
            product_ztoa.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        }

        dialog.show()
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimation
        dialog.getWindow()?.setGravity(Gravity.BOTTOM)

    }
    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl/home_buyer"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, {response:JSONArray->

            for(i in 0 until response.length())
            {
               try {

               var cropObject = response.getJSONObject(i)
                var crop = CropData()
                crop.crop_name = cropObject.getString("crop_name")
                crop.crop_image = cropObject.getString("crop_image")
                crop.crop_location = cropObject.getString("crop_location")
                crop.user = cropObject.getString("user")
                crop.offer = cropObject.getBoolean("offer").toString()
                crop.quantity = cropObject.getInt("quantity").toString()
                crop.id = cropObject.getInt("id").toString()
                crop.parent_id = cropObject.getInt("parent_id").toString()

                plantList.add(crop)
               }catch(e:Exception)
               {
                   e.printStackTrace()
               }
            }

            binding.cropRecyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = context?.let { CropAdapter(plantList, it) }
            binding.cropRecyclerView.adapter = adapter

            adapter?.setOnItemClickListener(object : CropAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {

                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                    startActivity(Intent(context, ViewHomeCropActivity::class.java))
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

