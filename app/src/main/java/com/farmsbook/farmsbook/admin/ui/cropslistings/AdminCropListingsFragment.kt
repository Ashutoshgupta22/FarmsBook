package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.ListingPagerAdapter
import com.farmsbook.farmsbook.databinding.FragmentAdminCropListingsBinding
import com.google.android.material.tabs.TabLayoutMediator

class AdminCropListingsFragment: Fragment() {

    private lateinit var binding: FragmentAdminCropListingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminCropListingsBinding.inflate(
            LayoutInflater.from(requireContext()),
            container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pagerCropListing.adapter = ListingPagerAdapter(childFragmentManager, lifecycle)

        val tabNames = arrayListOf(getString(R.string.crops),
            getString(R.string.listed_crops))

        TabLayoutMediator(binding.tabLayoutCropListing, binding.pagerCropListing) {
            tab, position ->

            tab.text = tabNames[position]

        }.attach()
    }
}