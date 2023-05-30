package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.databinding.FragmentRequirementsChildBinding
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsAdapter
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.LatestRequirementsData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters.PreviousRequirementsAdapter

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
        plantList2 = arrayListOf<LatestRequirementsData>()

        _binding = FragmentRequirementsChildBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.latestRequirementsRv.layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { LatestRequirementsAdapter(plantList, it) }
        binding.latestRequirementsRv.adapter = adapter
        adapter?.setOnItemClickListener(object : LatestRequirementsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                startActivity(Intent(context,ViewRequirementActivity::class.java))
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


        binding.previousRequirementsRv.layoutManager = LinearLayoutManager(context)
        val adapter2 = context?.let { PreviousRequirementsAdapter(plantList2, it) }
        binding.previousRequirementsRv.adapter = adapter2
        adapter2?.setOnItemClickListener(object : PreviousRequirementsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                startActivity(Intent(context,ViewRequirementActivity::class.java))
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

        return root
    }

}
