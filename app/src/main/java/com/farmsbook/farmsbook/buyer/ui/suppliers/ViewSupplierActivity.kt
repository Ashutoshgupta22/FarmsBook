package com.farmsbook.farmsbook.buyer.ui.suppliers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.farmsbook.farmsbook.databinding.ActivityViewSupplierBinding
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersCropAdapter
import com.farmsbook.farmsbook.buyer.ui.suppliers.adapters.SuppliersCropData

class ViewSupplierActivity : AppCompatActivity() {

    private lateinit var plantList: ArrayList<SuppliersCropData>
    private lateinit var tempArrayList: ArrayList<SuppliersCropData>
    private lateinit var binding: ActivityViewSupplierBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {
            finish()
        }

        plantList = arrayListOf<SuppliersCropData>()

        binding.suppliersCropsRV.layoutManager = GridLayoutManager(this, 2)
        val adapter = SuppliersCropAdapter(plantList, this)
        binding.suppliersCropsRV.adapter = adapter
        binding.suppliersCropsRV.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(object : SuppliersCropAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                Toast.makeText(
                    this@ViewSupplierActivity,
                    "You Clicked on item no. $position",
                    Toast.LENGTH_SHORT
                ).show()

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
    }
}