package com.farmsbook.farmsbook.admin.ui.requirements

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.ItemAdminRequirementsBinding
import com.farmsbook.farmsbook.databinding.ItemListedCropsBinding
import com.farmsbook.farmsbook.utility.TimeFormatter

class AdminRequirementAdapter(
    private val requirementList: ArrayList<RequirementData>,
    val onCallClick: (String) -> Unit,
    val onItemClick: (Int) -> Unit
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
        val btnCall = binding.btnCall

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = ItemAdminRequirementsBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = requirementList[position]

        Glide
            .with(context)
            .load(currentItem.imageCrop)
            .fitCenter()
            .into(holder.ivCrop)

        Glide.with(context)
            .load(currentItem.imageUser)
            .fitCenter()
            .into(holder.ivBuyer)

        holder.tvCropName.text = currentItem.cropName
        val rate = "${currentItem.minRange}-${currentItem.maxRange}/kg"
        holder.tvCropRate.text = rate
        holder.tvCropQuantity.text =
            "${currentItem.quantity.toString()} ${currentItem.quantityUnit}"

        holder.tvBuyerName.text = currentItem.cropBy
        holder.tvCompanyName.text = currentItem.companyName
        holder.tvDate.text = currentItem.timestamp?.let { TimeFormatter().getFullDate(it) }

            holder.btnCall.setOnClickListener { currentItem.phone?.let { it1 -> onCallClick(it1) } }

    // holder.cvItem.setOnClickListener { onItemClick() }

    }

    override fun getItemCount(): Int {
        return requirementList.size
    }
}