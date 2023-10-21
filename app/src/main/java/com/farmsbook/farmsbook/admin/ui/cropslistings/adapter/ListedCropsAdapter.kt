package com.farmsbook.farmsbook.admin.ui.cropslistings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.ItemListedCropsBinding
import com.farmsbook.farmsbook.utility.TimeFormatter

class ListedCropsAdapter(
    private val listedCropsList: ArrayList<ListedCropData>,
    val onItemClick: (Int) -> Unit
): RecyclerView.Adapter<ListedCropsAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(binding: ItemListedCropsBinding) : RecyclerView.ViewHolder(binding.root) {

        val cvItem = binding.cvItemListedCrops
        val ivCrop = binding.ivCrop
        val tvCropName = binding.tvCropName
        val tvCropRate = binding.tvCropRate
        val tvCropQuantity = binding.tvCropQuantity
        val tvDate = binding.tvDate
        val tvStatus = binding.tvStatus
        val ivSeller = binding.ivSeller
        val tvSellerName = binding.tvSellerName
        val tvGroupName = binding.tvGroupName

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = ItemListedCropsBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = listedCropsList[position]
        Glide
            .with(context)
            .load(listedCropsList[position].imageUrl0)
            .fitCenter()
            .into(holder.ivCrop)

        Glide.with(context)
            .load(listedCropsList[position].userImage)
            .fitCenter()
            .into(holder.ivSeller)

        holder.tvCropName.text = listedCropsList[position].cropName
        val rate = "${currentItem.minPrice}-${currentItem.maxPrice}/kg"
        holder.tvCropRate.text = rate
        holder.tvCropQuantity.text =
            "${listedCropsList[position].quantity.toString()} ${listedCropsList[position].quantityUnit}"
        holder.tvDate.text = listedCropsList[position].timestamp?.let {
            TimeFormatter().getFullDate(it)
        }

        holder.tvStatus.visibility = View.GONE

//        if(listedCropsList[position].listedStatus == true) {
//            holder.tvStatus.text = context.getString(R.string.active)
//            holder.tvStatus.setTextColor(Color.GREEN)
//        }
//        else {
//            holder.tvStatus.text = context.getString(R.string.inactive)
//            holder.tvStatus.setTextColor(Color.RED)
//        }

        holder.tvSellerName.text = listedCropsList[position].userName
        holder.tvGroupName.text = listedCropsList[position].companyName

        holder.cvItem.setOnClickListener { onItemClick(position) }

    }

    override fun getItemCount(): Int {
        return listedCropsList.size
    }
}