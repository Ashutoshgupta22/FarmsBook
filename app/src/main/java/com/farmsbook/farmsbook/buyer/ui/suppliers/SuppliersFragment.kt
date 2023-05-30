package com.farmsbook.farmsbook.buyer.ui.suppliers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.databinding.FragmentSuppliersBinding
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersData

class SuppliersFragment : Fragment() {

    private lateinit var plantList: ArrayList<SuppliersData>
    private lateinit var tempArrayList :ArrayList<SuppliersData>
    private var _binding: FragmentSuppliersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel = ViewModelProvider(this)[SuppliersViewModel::class.java]

        _binding = FragmentSuppliersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.suppliersRv.layoutManager = LinearLayoutManager(context)
        plantList = arrayListOf<SuppliersData>()
        val adapter = context?.let { SuppliersAdapter(plantList, it) }
        binding.suppliersRv.adapter = adapter

        adapter?.setOnItemClickListener(object : SuppliersAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                startActivity(Intent(context,ViewSupplierActivity::class.java))
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