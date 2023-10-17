package com.farmsbook.farmsbook.admin.ui.management.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.management.buyer.RequirementData
import com.farmsbook.farmsbook.databinding.ItemUserDetailBinding

class UserRequirementAdapter(private val requirementList: ArrayList<RequirementData>)
    : RecyclerView.Adapter<UserRequirementAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(binding: ItemUserDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        val ivCrop = binding.ivItemUserDetail
        val tvCropName = binding.tvItemCropName
        val tvCropQuantity = binding.tvItemCropQuantity
        val tvCropRate = binding.tvItemCropRate
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        context = parent.context
        val binding = ItemUserDetailBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(context)
            .load(requirementList[position].imageCrop)
            .centerCrop()
            .into(holder.ivCrop)

        holder.tvCropName.text = requirementList[position].cropName
        holder.tvCropQuantity.text = "${requirementList[position].quantity} ${requirementList[position].quantityUnit}"
        val rate = "${requirementList[position].minRange}-${requirementList[position].maxRange}/kg"
        holder.tvCropRate.text = rate
    }

    override fun getItemCount(): Int {
        return requirementList.size
    }
}