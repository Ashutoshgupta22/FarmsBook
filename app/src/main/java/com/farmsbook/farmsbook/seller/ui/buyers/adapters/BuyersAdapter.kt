package com.farmsbook.farmsbook.seller.ui.buyers.adapters

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.suppliers.ViewSupplierActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity
import com.farmsbook.farmsbook.seller.ui.buyers.BuyersFragment

class BuyersAdapter (private val plantList : ArrayList<BuyersData>, val context: Context):RecyclerView.Adapter<BuyersAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

        fun onAddButtonClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.supplier_list_item,parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


        val currentItem =plantList[position]
        Glide.with(context).load(plantList[position].Image).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)

        holder.groupName.text = currentItem.GroupName
        holder.suppliersName.text= currentItem.FarmerName
        holder.suppliersLocation.text= currentItem.Location
        holder.crop1.text = "Rice"
        holder.crop2.text = "Wheat"
        holder. crop3.text = "Apple"

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


        val plantImage: ImageView = itemView.findViewById(R.id.suppliers_iv)
        val groupName: TextView = itemView.findViewById(R.id.group_name_tv)
        val suppliersName: TextView = itemView.findViewById(R.id.supplier_name_tv)
        val suppliersLocation: TextView = itemView.findViewById(R.id.supplier_location_tv)
        val crop1 : TextView = itemView.findViewById(R.id.crop1_tv)
        val crop2 : TextView = itemView.findViewById(R.id.crop2_tv)
        val crop3 : TextView = itemView.findViewById(R.id.crop3_tv)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
            itemView.findViewById<TextView>(R.id.addSupplierBtn).setOnClickListener {
                listener.onAddButtonClick(adapterPosition)
            }
        }

    }
}