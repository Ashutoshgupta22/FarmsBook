package com.farmsbook.farmsbook.admin.ui.management.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.farmsbook.farmsbook.admin.ui.management.BuyersManagementFragment
import com.farmsbook.farmsbook.admin.ui.management.SellersManagementFragment

private const val FRAGMENT_NUM = 2

class ManagementPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int  = FRAGMENT_NUM

    override fun createFragment(position: Int): Fragment {

       return when(position) {
            0 -> BuyersManagementFragment()
           else -> SellersManagementFragment()
       }

    }
}