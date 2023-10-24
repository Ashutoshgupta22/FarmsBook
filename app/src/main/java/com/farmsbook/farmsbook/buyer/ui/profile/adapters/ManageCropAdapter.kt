package com.farmsbook.farmsbook.buyer.ui.profile.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.google.android.material.imageview.ShapeableImageView

class ManageCropAdapter (
    private val plantList : ArrayList<ManageCropData>,
    val context: Context
):RecyclerView.Adapter<ManageCropAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.manage_crop_list_item,
            parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


        val currentItem =plantList[position]

        Log.i("ManageCropAdapter", "onBindViewHolder: image-${currentItem.imageUrl}")
        if (currentItem.imageUrl != null)
            Glide.with(context).load(currentItem.imageUrl).into(holder.cropImage)
        else
            Glide.with(context).load(currentItem.image).into(holder.cropImage)

        holder.cropName.text = currentItem.cropName
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


       // val cropImage: ImageView = itemView.findViewById(R.id.supplierCropImageTV)
        val cropName: TextView = itemView.findViewById(R.id.crop_name)
        val cropImage: ShapeableImageView = itemView.findViewById(R.id.crop_image)


        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }
    }

}