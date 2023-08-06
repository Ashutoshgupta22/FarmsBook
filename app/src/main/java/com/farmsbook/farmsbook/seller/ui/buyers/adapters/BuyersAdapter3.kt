package com.farmsbook.farmsbook.seller.ui.buyers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R

class BuyersAdapter3 (private val plantList : ArrayList<BuyersData>, val context: Context):RecyclerView.Adapter<BuyersAdapter3.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
        fun callClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.supplier_main_screen_item,parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


       val currentItem =plantList[position]
        Glide.with(context).load(plantList[position].Image).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)

        holder.groupName.text = currentItem.GroupName
        holder.suppliersName.text= currentItem.FarmerName

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


        val plantImage: ImageView = itemView.findViewById(R.id.imageView8)
        val groupName: TextView = itemView.findViewById(R.id.companyNameTV)
        val suppliersName: TextView = itemView.findViewById(R.id.name_tv)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)

            }
            itemView.findViewById<TextView>(R.id.callBtn).setOnClickListener {
                listener.callClick(adapterPosition)
            }
        }
    }
}