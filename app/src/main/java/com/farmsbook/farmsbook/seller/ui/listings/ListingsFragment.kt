package com.farmsbook.farmsbook.seller.ui.listings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.FragmentListingsBinding
import com.farmsbook.farmsbook.seller.ui.listings.fragments.CropsListingFragment
import com.farmsbook.farmsbook.seller.ui.listings.fragments.InterestedFragment
import com.farmsbook.farmsbook.utility.VPAdapter

class ListingsFragment : Fragment() {

    private var _binding: FragmentListingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.i("ListingsFragment", "onCreateView: called")


        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentListingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout = binding.tabLayout
        val viewpager = binding.Viewpager

        tabLayout.setupWithViewPager(viewpager)

        val vpAdapter= VPAdapter(childFragmentManager)
        vpAdapter.addFragment(CropsListingFragment(),getString(R.string.crop_listings))
        vpAdapter.addFragment(InterestedFragment(),getString(R.string.interested))
        viewpager.adapter = vpAdapter
        viewpager.setSwipePagingEnabled(true)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}