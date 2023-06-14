package com.farmsbook.farmsbook.seller.ui.listings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.buyer.ui.home.adapters.CropData
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.offers.OffersFragment
import com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.RequirementsChildFragment
import com.farmsbook.farmsbook.databinding.FragmentListingsBinding
import com.farmsbook.farmsbook.databinding.FragmentNotificationsBinding
import com.farmsbook.farmsbook.seller.ui.listings.fragments.CropsListingFragment
import com.farmsbook.farmsbook.seller.ui.listings.fragments.InterestedFragment
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.farmsbook.farmsbook.utility.VPAdapter
import org.json.JSONArray

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
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentListingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout = binding.tabLayout
        val viewpager = binding.Viewpager

        tabLayout.setupWithViewPager(viewpager)

        val vpAdapter= VPAdapter(childFragmentManager)
        vpAdapter.addFragment(CropsListingFragment(),"Crops Listings")
        vpAdapter.addFragment(InterestedFragment(),"Interested")
        viewpager.adapter = vpAdapter
        viewpager.setSwipePagingEnabled(true)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}