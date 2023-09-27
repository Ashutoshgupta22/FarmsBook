package com.farmsbook.farmsbook.admin.ui.management.seller

import android.content.Context
import android.view.LayoutInflater
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
        val textCompanyName = binding.textCompanyName
        val textCompanyTurnover = binding.textCompanyTurnover
        val textCropsDeal = binding.textCropsDeal
        val tvCompanyName = binding.tvCompanyNameManagement
        val tvCompanyTurnover = binding.tvCompanyTurnoverManagement
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

        holder.textCompanyName.text = context.getString(R.string.group_name)
        holder.textCompanyTurnover.text = context.getString(R.string.group_turnover)
        holder.textCropsDeal.text = context.getString(R.string.crops_listed)

        holder.tvName.text = sellers[position].name
        holder.tvLocation.text = sellers[position].location
        holder.tvCompanyName.text = sellers[position].companyName
        holder.tvCompanyTurnover.text = sellers[position].companyTurnover.toString()
        holder.tvCropsDeal.text = sellers[position].crops

        holder.btnCall.setOnClickListener {
            sellers[position].phone?.let { phone -> onCallClick(phone) }
        }
        holder.cvItem.setOnClickListener { callback(position) }

    }

    override fun getItemCount(): Int {
        return sellers.size
    }
}