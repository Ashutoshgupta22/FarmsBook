package com.farmsbook.farmsbook.admin.ui.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.management.adapter.PagerAdapter
import com.farmsbook.farmsbook.databinding.FragmentAdminUserManagementBinding
import com.google.android.material.tabs.TabLayoutMediator

class AdminUserManagementFragment: Fragment() {

    private lateinit var binding: FragmentAdminUserManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminUserManagementBinding.inflate(
            LayoutInflater.from(requireContext()), container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpUserManagement.adapter = PagerAdapter(childFragmentManager, lifecycle)

        val tabNames = arrayListOf( getString(R.string.buyers), getString(R.string.sellers))

        TabLayoutMediator(binding.tabLayoutUserManagement, binding.vpUserManagement) {
            tab, position ->

            tab.text = tabNames[position]

        }.attach()
    }
}