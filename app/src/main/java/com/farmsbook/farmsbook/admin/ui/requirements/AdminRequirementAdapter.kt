package com.farmsbook.farmsbook.admin.ui.requirements

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.ItemAdminRequirementsBinding
import com.farmsbook.farmsbook.databinding.ItemListedCropsBinding

class AdminRequirementAdapter(
    private val requirementList: ArrayList<RequirementData>
): RecyclerView.Adapter<AdminRequirementAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(binding: ItemAdminRequirementsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val cvItem = binding.cvItem
        val ivCrop = binding.ivCrop
        val tvCropName = binding.tvCropName
        val tvCropRate = binding.tvCropRate
        val tvCropQuantity = binding.tvCropQuantity
        val tvDate = binding.tvDate
        val ivBuyer = binding.ivBuyer
        val tvBuyerName = binding.tvBuyerName
        val tvCompanyName = binding.tvCompanyName

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = ItemAdminRequirementsBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        Glide
//            .with(context)
//            .load(requirementList[position].imageUrl0)
//            .fitCenter()
//            .into(holder.ivCrop)
//
//        Glide.with(context)
//            .load(requirementList[position].imageUrls?.get(0))
//            .fitCenter()
//            .into(holder.ivBuyer)
//
//        holder.tvCropName.text = requirementList[position].cropName
//        holder.tvCropRate.text = requirementList[position].rate.toString()
//        holder.tvCropQuantity.text =
//            "${requirementList[position].quantity.toString()} ${requirementList[position].quantityUnit}"
//
//
//        holder.tvBuyerName.text = requirementList[position].user.toString()
        //holder.tvGroupName.text = requirementList[position].listedOffer!![0].farmerCompany

    // holder.cvItem.setOnClickListener { onItemClick(position) }

    }

    override fun getItemCount(): Int {
        return requirementList.size.coerceAtLeast(10)
    }
}