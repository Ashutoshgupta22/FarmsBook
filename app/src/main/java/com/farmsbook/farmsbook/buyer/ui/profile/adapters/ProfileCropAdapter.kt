package com.farmsbook.farmsbook.buyer.ui.profile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R

class ProfileCropAdapter (private val plantList : ArrayList<ProfileCropData>,
                          val context: Context):
    RecyclerView.Adapter<ProfileCropAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.supplier_crop_item,parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


        val currentItem =plantList[position]
        val imageBuyer = plantList[position].imageBuyer
        val imageSeller = plantList[position].imageSeller

        if (imageSeller != null)
            Glide.with(context).load(plantList[position].imageSeller).into(holder.cropImage)
        else if (imageBuyer != 0)
            Glide.with(context).load(plantList[position].imageBuyer).into(holder.cropImage)


        //holder.plantImage.setImageResource(currentItem.Image)

        holder.cropName.text = currentItem.name
        holder.cropPrice.text= currentItem.pricePerKg


//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


        val cropImage: ImageView = itemView.findViewById(R.id.supplierCropImageTV)
        val cropName: TextView = itemView.findViewById(R.id.supplierCropNameTV)
        val cropPrice: TextView = itemView.findViewById(R.id.supplierCropPriceTv)


        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }
    }

}