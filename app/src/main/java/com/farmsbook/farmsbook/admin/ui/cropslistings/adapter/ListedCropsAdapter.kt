package com.farmsbook.farmsbook.admin.ui.cropslistings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ItemCropsBinding
import com.farmsbook.farmsbook.databinding.ItemListedCropsBinding

class ListedCropsAdapter(
   val onItemClick: (Int) -> Unit
): RecyclerView.Adapter<ListedCropsAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(binding: ItemListedCropsBinding) : RecyclerView.ViewHolder(binding.root) {

        val cvItem = binding.cvItemListedCrops
        val ivCrop = binding.ivCrop
        val tvCropName = binding.tvCropName
        val tvCropRate = binding.tvCropRate
        val tvCropAmount = binding.tvCropAmount
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

        Glide
            .with(context)
            .load(R.drawable.jowar)
            .fitCenter()
            .into(holder.ivCrop)

        Glide.with(context)
            .load(R.drawable.black_pepper)
            .fitCenter()
            .into(holder.ivSeller)

        holder.cvItem.setOnClickListener { onItemClick(position) }

    }

    override fun getItemCount(): Int {
        return 6
    }
}