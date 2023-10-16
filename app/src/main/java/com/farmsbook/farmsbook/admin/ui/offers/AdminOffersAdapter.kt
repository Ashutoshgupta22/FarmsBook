package com.farmsbook.farmsbook.admin.ui.offers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.ItemAdminOffersBinding
import com.farmsbook.farmsbook.databinding.ItemAdminRequirementsBinding
import com.farmsbook.farmsbook.utility.TimeFormatter

class AdminOffersAdapter(
    private val offerList: ArrayList<AdminOfferData>
): RecyclerView.Adapter<AdminOffersAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(binding: ItemAdminOffersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val cvItem = binding.cvItem
        val ivCrop = binding.ivCrop
        val tvCropName = binding.tvCropName
        val tvCropRate = binding.tvCropRate
        val tvCropQuantity = binding.tvCropQuantity
        val tvDate = binding.tvDate
        val tvSellerName = binding.tvSellerName
        val tvSellerCompanyName = binding.tvSellerCompanyName
        val tvBuyerName = binding.tvBuyerName
        val tvBuyerCompanyName = binding.tvCompanyName

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = ItemAdminOffersBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = offerList[position]

        Glide
            .with(context)
            .load(currentItem.imageUrl0)
            .fitCenter()
            .into(holder.ivCrop)

        holder.tvCropName.text = currentItem.offerCropName
       // val rate = "${currentItem.minPrice}-${currentItem.maxPrice}/kg"
        holder.tvCropRate.text = "Rs. ${currentItem.offeringPrice.toString()}"
        holder.tvCropQuantity.text =
            "${currentItem.offeringQuantity.toString()} ${currentItem.offeringQuantityUnit}"


        holder.tvBuyerName.text = currentItem.buyerName
        holder.tvBuyerCompanyName.text = currentItem.companyName
        holder.tvSellerName.text = currentItem.farmerName
        holder.tvSellerCompanyName.text = currentItem.farmerCompanyName

        holder.tvDate.text = currentItem.timestamp?.let { TimeFormatter().getFullDate(it) }

        // holder.cvItem.setOnClickListener { onItemClick(position) }

    }

    override fun getItemCount(): Int {
        return offerList.size
    }
}