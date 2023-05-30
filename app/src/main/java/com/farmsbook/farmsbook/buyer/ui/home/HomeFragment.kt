package com.farmsbook.farmsbook.buyer.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.databinding.FragmentHomeBinding
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropAdapter
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchEdt.setOnClickListener {
            startActivity(Intent(context,HomeSearchActivity::class.java))
        }

        binding.cropRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { CropAdapter(plantList, it) }
        binding.cropRecyclerView.adapter = adapter

        adapter?.setOnItemClickListener(object : CropAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                 Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

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


}