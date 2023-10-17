package com.farmsbook.farmsbook.admin.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.FragmentAdminOffersBinding

class AdminOffersFragment: Fragment() {

    private lateinit var binding: FragmentAdminOffersBinding
    private val viewModel: AdminOfferViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminOffersBinding.inflate(
            LayoutInflater.from(requireContext()),
            container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        viewModel.getAllOffers(requireContext())

        viewModel.offers.observe(viewLifecycleOwner) {

            it?.let {
                binding.rvAdminOffers.apply {
                    layoutManager = LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false)

                    adapter = AdminOffersAdapter(it) {
                        id->
                        navController.navigate(R.id.adminOfferDetailFragment)


                    }
                }
            }
        }
    }
}