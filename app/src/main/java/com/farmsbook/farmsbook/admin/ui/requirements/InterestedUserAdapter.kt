package com.farmsbook.farmsbook.admin.ui.requirements

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.ItemInterestedSellersRequirementsBinding

class InterestedUserAdapter(private val list: ArrayList<InterestedUser>,
    val onCallClick: (String) -> Unit
): RecyclerView.Adapter<InterestedUserAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(binding: ItemInterestedSellersRequirementsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        val ivUser = binding.ivUser
        val name = binding.tvUserName
        val location = binding.tvBuyerLocation
        val btnCall = binding.btnCall
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        context = parent.context
        val binding = ItemInterestedSellersRequirementsBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(list[position].imagePath).fitCenter().into(holder.ivUser)
        holder.name.text = list[position].name
        holder.location.text = list[position].companyName
        holder.btnCall.setOnClickListener {
            list[position].phone?.let { it1 -> onCallClick(it1) }
        }

    }

    override fun getItemCount(): Int {

        return list.size
    }
}