package com.farmsbook.farmsbook.admin.ui.requirements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.buyer.ui.requirements.RequirementsViewModel
import com.farmsbook.farmsbook.databinding.FragmentAdminRequirementsBinding

class AdminRequirementsFragment: Fragment() {

    private lateinit var binding: FragmentAdminRequirementsBinding
    private val viewModel: AdminRequirementsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminRequirementsBinding.inflate(
            LayoutInflater.from(requireContext()),
            container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAdminRequirements.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)

            adapter = AdminRequirementAdapter(arrayListOf())
        }

        viewModel.requirements.observe(viewLifecycleOwner) {

            binding.rvAdminRequirements.apply {
                layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL, false)

                adapter = AdminRequirementAdapter(arrayListOf())
            }


        }
    }
}