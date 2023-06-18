package com.farmsbook.farmsbook.seller.ui.listings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersData
import com.farmsbook.farmsbook.databinding.FragmentInterestedBinding
import com.farmsbook.farmsbook.databinding.FragmentOffersBinding
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.InterestedAdapter
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.InterestedData


class InterestedFragment : Fragment() {
    private var _binding: FragmentInterestedBinding? = null

    private val binding get() = _binding!!
    private lateinit var plantList: ArrayList<InterestedData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        plantList = arrayListOf<InterestedData>()
        _binding = FragmentInterestedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.latestRequirementsRv.layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { InterestedAdapter(plantList, it) }
        binding.latestRequirementsRv.adapter = adapter
        adapter?.setOnItemClickListener(object : InterestedAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                //startActivity(Intent(context, ViewRequirementActivity::class.java))
                //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
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

}