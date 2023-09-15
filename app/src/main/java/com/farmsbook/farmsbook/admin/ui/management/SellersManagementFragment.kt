package com.farmsbook.farmsbook.admin.ui.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.management.adapter.UserManagementAdapter
import com.farmsbook.farmsbook.databinding.FragmentSellersManagementBinding

class SellersManagementFragment: Fragment() {

    private lateinit var binding: FragmentSellersManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSellersManagementBinding.inflate(layoutInflater,
            container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.rvSellersManagement.apply {

            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            adapter = UserManagementAdapter{
                navController.navigate(R.id.userManagementDetailFragment)
            }
        }

    }
}