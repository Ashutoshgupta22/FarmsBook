package com.farmsbook.farmsbook.buyer.ui.profile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.admin.ui.cropslistings.CropData
import com.farmsbook.farmsbook.databinding.ItemCropsBinding
import com.farmsbook.farmsbook.databinding.ManageCropListItemBinding

class ManageAdminCropAdapter (
    val cropList: ArrayList<CropData>,
    val onClick: (Int) -> Unit
): RecyclerView.Adapter<ManageAdminCropAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(binding: ManageCropListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val ivCrop = binding.cropImage
        val tvCropName = binding.cropName
       // val btnEdit = binding.btnEdit
        //val btnDelete = binding.btnDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = ManageCropListItemBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(context)
            .load(cropList[position].imagePath)
            .centerCrop()
            .into(holder.ivCrop)

        holder.tvCropName.text = cropList[position].cropName

        holder.itemView.setOnClickListener {
            onClick(position)
        }

//        holder.btnEdit.visibility = View.INVISIBLE
//        holder.btnDelete.visibility = View.INVISIBLE

//        holder.btnEdit.setOnClickListener { onEditClick(position,
//            cropList[position].cropName!!) }
//        holder.btnDelete.setOnClickListener { onDeleteClick(position) }

    }

    override fun getItemCount(): Int {
        return cropList.size
    }
}