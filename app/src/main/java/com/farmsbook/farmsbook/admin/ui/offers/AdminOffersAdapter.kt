package com.farmsbook.farmsbook.admin.ui.offers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.ItemAdminOffersBinding
import com.farmsbook.farmsbook.databinding.ItemAdminRequirementsBinding

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

//        Glide
//            .with(context)
//            .load(offerList[position].imageUrl0)
//            .fitCenter()
//            .into(holder.ivCrop)
//
//        holder.tvCropName.text = offerList[position].cropName
//        holder.tvCropRate.text = offerList[position].rate.toString()
//        holder.tvCropQuantity.text =
//            "${offerList[position].quantity.toString()} ${offerList[position].quantityUnit}"
//
//
//        holder.tvBuyerName.text = offerList[position].user.toString()
        //holder.tvGroupName.text = offerList[position].listedOffer!![0].farmerCompany

        // holder.cvItem.setOnClickListener { onItemClick(position) }

    }

    override fun getItemCount(): Int {
        return offerList.size.coerceAtLeast(10)
    }
}