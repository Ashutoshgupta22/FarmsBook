package com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R

class ImageAdapter(private val plantList: ArrayList<Uri>, val context: Context, val countofimagesWhenRemoved : CountOfImagesWhenRemoved) :
    RecyclerView.Adapter<ImageAdapter.Myviewholder>() {


//    private lateinit var mListener: onItemClickListener
//
//    interface onItemClickListener{
//
//        fun onItemClick(position: Int)
//        fun callClick(position: Int)
//        fun deleteClick(position: Int)
//    }
//
//    fun setOnItemClickListener(listener: onItemClickListener){
//        mListener = listener
//    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)

        return Myviewholder(itemView,countofimagesWhenRemoved)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


       // val currentItem = plantList[position]
        Glide.with(context).load(plantList[position]).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)
//        holder.plantName.text = currentItem.crop_name
//        holder.plantRateType.text = currentItem.purchased_on
//        holder.postedBy.text = "Requirement Posted by ${currentItem.buyer_name}"
holder.deleteImage.setOnClickListener {
    plantList.remove(plantList.get(position))
    notifyItemRemoved(position)
    notifyItemRangeChanged(position,itemCount)
    countofimagesWhenRemoved.clicked(plantList.size)
}

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class Myviewholder(itemView: View, val countofimagesWhenRemoved: CountOfImagesWhenRemoved) :
        RecyclerView.ViewHolder(itemView) { //,listener:onItemClickListener


        val plantImage: ImageView = itemView.findViewById(R.id.imageView19)
        val deleteImage: ImageView = itemView.findViewById(R.id.deleteBtn)
//        val plantName: TextView = itemView.findViewById(R.id.crop_name_tv)
//        val plantRateType: TextView = itemView.findViewById(R.id.rate_type_tv)
//        val postedBy: TextView = itemView.findViewById(R.id.postedByTV)

//        init {
//            itemView.setOnClickListener{
//
//                listener.onItemClick(adapterPosition)
//            }
//            itemView.findViewById<TextView>(R.id.deleteBtn).setOnClickListener{
//
//                listener.deleteClick(adapterPosition)
//            }
//            itemView.findViewById<TextView>(R.id.callBtn).setOnClickListener{
//
//                listener.callClick(adapterPosition)
//            }
    }

    public interface  CountOfImagesWhenRemoved{
        fun clicked(getSize:Int)
    }

}