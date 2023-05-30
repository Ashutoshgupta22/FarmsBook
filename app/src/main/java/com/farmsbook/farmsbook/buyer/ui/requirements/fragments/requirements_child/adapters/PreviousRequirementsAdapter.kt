package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.R

class PreviousRequirementsAdapter (private val plantList : ArrayList<LatestRequirementsData>, val context: Context):RecyclerView.Adapter<PreviousRequirementsAdapter.Myviewholder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    //var onItemClickListener :((PlantData)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.previous_requirement_list_item,parent,false)

        return Myviewholder(itemView,mListener)//,mListener
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


        // val currentItem =plantList[position]
        //Glide.with(context).load(plantList[position].Image).into(holder.plantImage)
        //holder.plantImage.setImageResource(currentItem.Image)
        holder.plantName.text = "Rice"
        holder.plantPrice.text = "Min 2/ton - Max 4/ton"
        holder.plantDate.text = "Posted on : 5 min ago"
        holder.plantStatus.text = "Completed Order"

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    class Myviewholder(itemView : View,listener:onItemClickListener) : RecyclerView.ViewHolder(itemView){ //,listener:onItemClickListener


        //        val plantImage: ImageView = itemView.findViewById(R.id.plantImage)
        val plantName: TextView = itemView.findViewById(R.id.requirementName_tv)
        val plantPrice: TextView = itemView.findViewById(R.id.requirement_price_tv)
        val plantDate: TextView = itemView.findViewById(R.id.postedOn_tv)
        val plantStatus : TextView = itemView.findViewById(R.id.requirement_status_tv)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }

    }
}