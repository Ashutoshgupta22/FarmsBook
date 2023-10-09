package com.farmsbook.farmsbook.admin.ui.management.seller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ItemAdminUserManagementBinding

class SellerManagementAdapter(private val sellers: ArrayList<AdminSellerData>,
                            private val onCallClick: (String) -> Unit,
                            private val callback: (pos: Int) -> Unit):
    RecyclerView.Adapter<SellerManagementAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(binding: ItemAdminUserManagementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val ivImage = binding.ivUserManagement
        val tvName = binding.tvNameManagement
        val tvLocation = binding.tvLocationManagement
        val btnCall = binding.btnCallManagement
        val textGroupName = binding.textCompanyName
        val textGroupTurnover = binding.textCompanyTurnover
        val textCropsDeal = binding.textCropsDeal
        val tvGroupName = binding.tvCompanyNameManagement
        val tvGroupTurnover = binding.tvCompanyTurnoverManagement
        val tvCropsDeal = binding.tvCropsDealManagement
        val cvItem = binding.cvItemManagement

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        context = parent.context
        val binding = ItemAdminUserManagementBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(context)
            .load(R.drawable.coffee)
            .centerCrop()
            .into(holder.ivImage)

        holder.textGroupName.text = context.getString(R.string.group_name)
        holder.textGroupTurnover.text = context.getString(R.string.group_turnover)
       // holder.textCropsDeal.text = context.getString(R.string.crops_listed)
        holder.textCropsDeal.visibility = View.GONE

        holder.tvName.text = sellers[position].name
        holder.tvLocation.text = sellers[position].location
        holder.tvGroupName.text = sellers[position].groupName
        holder.tvGroupTurnover.text = sellers[position].groupTurnover.toString()
        //holder.tvCropsDeal.text = sellers[position].crops
        holder.tvCropsDeal.visibility = View.GONE

        holder.btnCall.setOnClickListener {
            sellers[position].phone?.let { phone -> onCallClick(phone) }
        }
        holder.cvItem.setOnClickListener { callback(position) }

    }

    override fun getItemCount(): Int {
        return sellers.size
    }
}