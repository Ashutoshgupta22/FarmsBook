package com.farmsbook.farmsbook.admin.ui.management

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val FRAGMENT_NUM = 2

class PagerAdapter(
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