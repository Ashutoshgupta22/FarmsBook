package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.offers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.ViewRequirementActivity
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestOffersData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsData
import com.farmsbook.farmsbook.databinding.FragmentFavouritesBinding
import com.farmsbook.farmsbook.databinding.FragmentOffersBinding
import com.farmsbook.farmsbook.databinding.FragmentRequirementsChildBinding

class OffersFragment : Fragment() {
    private var _binding: FragmentOffersBinding? = null

    private val binding get() = _binding!!
    private lateinit var plantList: ArrayList<LatestOffersData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        plantList = arrayListOf<LatestOffersData>()
        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.latestRequirementsRv.layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { LatestOffersAdapter(plantList, it) }
        binding.latestRequirementsRv.adapter = adapter
        adapter?.setOnItemClickListener(object : LatestOffersAdapter.onItemClickListener {
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}