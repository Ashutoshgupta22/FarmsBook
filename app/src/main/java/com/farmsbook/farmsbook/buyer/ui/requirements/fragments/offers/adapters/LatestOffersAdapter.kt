package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.R

class LatestOffersAdapter (private val plantList : ArrayList<LatestOffersData>, val context: Context):RecyclerView.Adapter<LatestOffersAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

        fun callBtnClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.latest_offers_list_item,parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


       val currentItem =plantList[position]
        //Glide.with(context).load(plantList[position].Image).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)
        holder.plantName.text = "Rice"
        holder.plantPrice.text = "Price Offered : ${currentItem.offering_price}/kg"
        holder.plantWeight.text = currentItem.offering_quantity+" "+currentItem.offering_quantity_unit
        holder.plantRateType.text = currentItem.purchased_on
        if(currentItem.offer_status == "true")
        holder.plantStatus.text = "Offer Accepted by ${currentItem.buyer_name}"
        else{
            holder.plantStatus.text = "Offer Rejected by ${currentItem.buyer_name}"
            holder.plantStatus.setTextColor(Color.parseColor("#B80000"))
        }


//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


//        val plantImage: ImageView = itemView.findViewById(R.id.plantImage)
        val plantName: TextView = itemView.findViewById(R.id.crop_name_tv)
        val plantPrice: TextView = itemView.findViewById(R.id.price_offered_tv)
        val plantWeight: TextView = itemView.findViewById(R.id.weightTV)
        val plantStatus: TextView = itemView.findViewById(R.id.postedByTV)
        val plantRateType: TextView = itemView.findViewById(R.id.rate_type_tv)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
            itemView.findViewById<TextView>(R.id.callBtn).setOnClickListener {
                listener.callBtnClick(adapterPosition)
            }
        }

    }
}