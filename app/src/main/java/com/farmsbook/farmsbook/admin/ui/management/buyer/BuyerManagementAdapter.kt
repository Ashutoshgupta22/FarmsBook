package com.farmsbook.farmsbook.admin.ui.management.buyer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.ItemAdminUserManagementBinding

class BuyerManagementAdapter(private val buyers: ArrayList<AdminUserData>,
                             private val onCallClick: (String) -> Unit,
                             private val callback: (pos: Int) -> Unit):
    RecyclerView.Adapter<BuyerManagementAdapter.ViewHolder>() {

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
            .load(buyers[position].userImage)
            .centerCrop()
            .into(holder.ivImage)

        holder.tvName.text = buyers[position].name
        holder.tvLocation.text = buyers[position].location
        holder.tvCompanyName.text = buyers[position].companyName
        holder.tvCompanyTurnover.text = buyers[position].companyTurnover.toString()
        holder.tvCropsDeal.visibility = View.GONE
        holder.textCropsDeal.visibility =  View.GONE

        holder.btnCall.setOnClickListener {
            buyers[position].phone?.let { phone -> onCallClick(phone) }
        }

        holder.cvItem.setOnClickListener { callback(buyers[position].id) }

    }

    override fun getItemCount(): Int {
        return buyers.size
    }
}