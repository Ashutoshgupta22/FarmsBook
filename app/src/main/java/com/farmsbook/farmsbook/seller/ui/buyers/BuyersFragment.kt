package com.farmsbook.farmsbook.seller.ui.buyers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.buyer.ui.suppliers.ViewSupplierActivity
import com.farmsbook.farmsbook.databinding.FragmentBuyersBinding
import com.farmsbook.farmsbook.seller.ui.buyers.adapters.BuyersAdapter
import com.farmsbook.farmsbook.seller.ui.buyers.adapters.BuyersData

class BuyersFragment : Fragment() {

    private lateinit var plantList: ArrayList<BuyersData>
    private lateinit var tempArrayList :ArrayList<BuyersData>
    private var _binding: FragmentBuyersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentBuyersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.suppliersRv.layoutManager = LinearLayoutManager(context)
        plantList = arrayListOf<BuyersData>()
        val adapter = context?.let { BuyersAdapter(plantList, it) }
        binding.suppliersRv.adapter = adapter

        adapter?.setOnItemClickListener(object : BuyersAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()

                startActivity(Intent(context, ViewSupplierActivity::class.java))
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