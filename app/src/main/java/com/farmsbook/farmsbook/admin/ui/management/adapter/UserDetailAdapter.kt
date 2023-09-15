package com.farmsbook.farmsbook.admin.ui.management.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ItemUserDetailBinding

class UserDetailAdapter: RecyclerView.Adapter<UserDetailAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(binding: ItemUserDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        val ivCrop = binding.ivItemUserDetail
        val tvCropName = binding.tvItemCropName
        val tvCropAmount = binding.tvItemCropAmount
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
            .load(R.drawable.barley)
            .centerCrop()
            .into(holder.ivCrop)

    }

    override fun getItemCount(): Int {
        return 2
    }
}