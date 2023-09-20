package com.farmsbook.farmsbook.admin.ui.cropslistings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.FragmentListedCropsBinding
import com.farmsbook.farmsbook.databinding.ItemCropsBinding

class CropsAdapter(val onEditClick: (Int)-> Unit,
                   val onDeleteClick: (Int) -> Unit
): RecyclerView.Adapter<CropsAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(binding: ItemCropsBinding) : RecyclerView.ViewHolder(binding.root) {

        val ivCrop = binding.ivItemCrops
        val tvCropName = binding.tvCropName
        val btnEdit = binding.btnEdit
        val btnDelete = binding.btnDelete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = ItemCropsBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(context)
            .load(R.drawable.coffee)
            .centerCrop()
            .into(holder.ivCrop)

        holder.btnEdit.setOnClickListener { onEditClick(position) }
        holder.btnDelete.setOnClickListener { onDeleteClick(position) }

    }

    override fun getItemCount(): Int {
        return 13
    }
}