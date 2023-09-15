package com.farmsbook.farmsbook.admin.ui.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.management.adapter.UserDetailAdapter
import com.farmsbook.farmsbook.databinding.FragmentUserManagementDetailBinding

class UserManagementDetailFragment: Fragment() {

    private lateinit var binding: FragmentUserManagementDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserManagementDetailBinding.inflate(layoutInflater,
            container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(requireContext())
            .load(R.drawable.jowar)
            .into(binding.ivUserManagement)

        binding.rvFirst.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            adapter = UserDetailAdapter()
        }

        binding.rvSecond.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            adapter = UserDetailAdapter()
        }


    }
}