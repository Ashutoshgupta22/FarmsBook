package com.farmsbook.farmsbook.admin.ui.management

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ItemAdminUserManagementBinding

class UserManagementAdapter():
    RecyclerView.Adapter<UserManagementAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(binding: ItemAdminUserManagementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val ivImage = binding.ivItemManagement
        val tvName = binding.tvItemNameManagement
        val tvLocation = binding.tvItemLocationManagement
        val tvCompanyName = binding.tvItemCompanyNameManagement
        val tvCompanyTurnover = binding.tvItemCompanyTurnoverManagement
        val tvCropsDeal = binding.tvItemCropsDealManagement

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
            .load(R.drawable.bajra)
            .centerCrop()
            .into(holder.ivImage)

    }

    override fun getItemCount(): Int {

        return 4
    }
}