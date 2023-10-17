package com.farmsbook.farmsbook.admin.ui.management.seller

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.FragmentSellersManagementBinding

class SellersManagementFragment: Fragment() {

    private lateinit var binding: FragmentSellersManagementBinding
    private val viewModel: SellerManagementViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSellersManagementBinding.inflate(layoutInflater,
            container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        viewModel.getAllSellers(requireContext())

        viewModel.allSellers.observe(viewLifecycleOwner) {
            it?.let {

                binding.rvSellersManagement.apply {

                    layoutManager = LinearLayoutManager(requireContext(),
                        RecyclerView.VERTICAL, false)

                    adapter = SellerManagementAdapter(it, {
                        phone ->

                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:$phone")
                        startActivity(intent)
                    }){
                        id ->
                        val bundle = bundleOf("id" to id)
                        navController.navigate(R.id.userManagementDetailFragment, bundle)

                    }
                }
            }
        }

    }
}