package com.farmsbook.farmsbook.admin.ui.requirements

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.R
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

        val navController = findNavController()

        viewModel.getAllRequirements(requireContext())

        viewModel.requirements.observe(viewLifecycleOwner) {

            it?.let {
                binding.rvAdminRequirements.apply {
                    layoutManager = LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false)

                    adapter = AdminRequirementAdapter(it, {
                        phone ->

                        if (phone != "null") {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:$phone")
                            startActivity(intent)
                        }

                    }){
                        id ->

                        val bundle = bundleOf("id" to id)
                        navController.navigate(R.id.adminRequirementDetailFragment, bundle)
                    }
                }
            }
        }
    }
}