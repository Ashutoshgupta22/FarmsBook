package com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R

class BuyerOfferAdapter (private val plantList : ArrayList<BuyerOfferData>, val context: Context):RecyclerView.Adapter<BuyerOfferAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
        fun acceptClick(position: Int)
        fun rejectClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.buyer_offer_list_item,parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

        val currentItem =plantList[position]
       Glide.with(context).load(plantList[position].buyer_image).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)
        holder.plantName.text = currentItem.buyer_name
        holder.companyName.text = currentItem.company_name
        holder.plantPrice.text = currentItem.offer_price +"/kg"
        holder.plantQuantity.text = currentItem.offer_quantity + " "+ currentItem.offer_quantity_unit
        holder.purchasedOn.text = currentItem.purchaseOn

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


        val plantImage: ImageView = itemView.findViewById(R.id.profileImage)
        val plantName: TextView = itemView.findViewById(R.id.name_tv)
        val companyName: TextView = itemView.findViewById(R.id.company_name_tv)
        val plantPrice: TextView = itemView.findViewById(R.id.priceTV)
        val plantQuantity: TextView = itemView.findViewById(R.id.quantityTV)
        val purchasedOn : TextView = itemView.findViewById(R.id.type_of_saleTV)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
            itemView.findViewById<TextView>(R.id.acceptBtn).setOnClickListener {
                listener.acceptClick(adapterPosition)
            }
            itemView.findViewById<TextView>(R.id.declineBtn).setOnClickListener {
                listener.rejectClick(adapterPosition)
            }
        }
    }
}