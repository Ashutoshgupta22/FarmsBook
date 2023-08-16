package com.farmsbook.farmsbook.buyer.ui.home.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R

class CropAdapter (private var plantList : ArrayList<CropData>, val context: Context):RecyclerView.Adapter<CropAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

        fun offerPrice(position: Int)
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


        val currentItem = plantList[position]
        Glide.with(context).load(plantList[position].crop_image).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)
        holder.plantName.text = currentItem.crop_name
        holder.plantPrice.text = "${currentItem.min_price}/kg - ${currentItem.max_price}/kg"
        holder.plantLocation.text = currentItem.crop_location
        holder.plantWeight.text = currentItem.quantity.toString()+" ton"
        holder.tvTimestamp.text = "Posted : ${currentItem.timestamp}"

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


       val plantImage: ImageView = itemView.findViewById(R.id.imageView7)
        val plantName: TextView = itemView.findViewById(R.id.cropNameTv)
        val plantPrice: TextView = itemView.findViewById(R.id.costTv)
        val plantLocation: TextView = itemView.findViewById(R.id.locationTv)
        val plantWeight : TextView = itemView.findViewById(R.id.weightTv)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tv_posted_home_crop_item)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)


            }
            itemView.findViewById<TextView>(R.id.offerPriceBtn).setOnClickListener {
                listener.offerPrice(adapterPosition)
            }
        }
    }
    fun filterList(filteredNames: ArrayList<CropData>) {
        this.plantList = filteredNames
        notifyDataSetChanged()
    }
}