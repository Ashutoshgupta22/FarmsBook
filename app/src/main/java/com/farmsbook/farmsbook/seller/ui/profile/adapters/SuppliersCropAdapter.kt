package com.farmsbook.farmsbook.seller.ui.profile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.R

class SuppliersCropAdapter (private val plantList : ArrayList<ProfileCropData>, val context: Context):RecyclerView.Adapter<SuppliersCropAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.supplier_crop_item,parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


       // val currentItem =plantList[position]
        //Glide.with(context).load(plantList[position].Image).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)

        holder.cropName.text = "Cucumber"
        holder.cropPrice.text= "100-150/10 kg"


//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


       // val cropImage: ImageView = itemView.findViewById(R.id.supplierCropImageTV)
        val cropName: TextView = itemView.findViewById(R.id.supplierCropNameTV)
        val cropPrice: TextView = itemView.findViewById(R.id.supplierCropPriceTv)


        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }

    }
}