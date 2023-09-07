package com.farmsbook.farmsbook.admin.ui.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.databinding.FragmentBuyersManagementBinding
import com.farmsbook.farmsbook.databinding.FragmentSellersManagementBinding

class BuyersManagementFragment: Fragment() {

    private lateinit var binding: FragmentBuyersManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBuyersManagementBinding.inflate(layoutInflater,
            container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBuyersManagement.apply {

            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            adapter = UserManagementAdapter()
        }

    }
}