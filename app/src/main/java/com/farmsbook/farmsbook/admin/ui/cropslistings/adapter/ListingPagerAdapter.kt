package com.farmsbook.farmsbook.admin.ui.cropslistings.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.farmsbook.farmsbook.admin.ui.cropslistings.CropsFragment
import com.farmsbook.farmsbook.admin.ui.cropslistings.ListedCropsFragment

private const val FRAGMENT_NUM = 2

class ListingPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int  = FRAGMENT_NUM

    override fun createFragment(position: Int): Fragment {

        return when(position) {

            0 -> CropsFragment()
            else -> ListedCropsFragment()
        }

    }
}