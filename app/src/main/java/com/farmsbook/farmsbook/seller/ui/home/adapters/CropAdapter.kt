package com.farmsbook.farmsbook.seller.ui.home.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.R

class CropAdapter (private val plantList : ArrayList<CropData>, val context: Context):RecyclerView.Adapter<CropAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_crop_item,parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


       // val currentItem =plantList[position]
        //Glide.with(context).load(plantList[position].Image).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)
        holder.plantName.text = "Rice"
        holder.plantPrice.text = "Min 2/ton - Max 4/ton"
        holder.plantLocation.text = "Nalanda, Bihar"
        holder.plantWeight.text = "64 Ton"

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


//        val plantImage: ImageView = itemView.findViewById(R.id.plantImage)
        val plantName: TextView = itemView.findViewById(R.id.cropNameTv)
        val plantPrice: TextView = itemView.findViewById(R.id.costTv)
        val plantLocation: TextView = itemView.findViewById(R.id.locationTv)
        val plantWeight : TextView = itemView.findViewById(R.id.weightTv)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }

    }
}