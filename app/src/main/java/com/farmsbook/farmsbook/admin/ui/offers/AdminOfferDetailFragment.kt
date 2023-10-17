package com.farmsbook.farmsbook.admin.ui.offers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.requirements.AdminRequirementAdapter
import com.farmsbook.farmsbook.admin.ui.requirements.AdminRequirementDetailViewModel
import com.farmsbook.farmsbook.admin.ui.requirements.RequirementData
import com.farmsbook.farmsbook.databinding.FragmentAdminRequirementDetailBinding

class AdminOfferDetailFragment: Fragment() {

    private lateinit var binding: FragmentAdminRequirementDetailBinding
    private val viewModel: AdminRequirementDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAdminRequirementDetailBinding.inflate(
            LayoutInflater.from(requireContext()),
            container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

//        viewModel.get(requireContext())
//
//        viewModel.requirements.observe(viewLifecycleOwner) {
//
//            it?.let {
//                binding.rvAdminRequirements.apply {
//                    layoutManager = LinearLayoutManager(requireContext(),
//                        LinearLayoutManager.VERTICAL, false)
//
//                    adapter = AdminRequirementAdapter(it, {
//                            phone ->
//
//                        if (phone != "null") {
//                            val intent = Intent(Intent.ACTION_DIAL)
//                            intent.data = Uri.parse("tel:$phone")
//                            startActivity(intent)
//                        }
//
//                    }){
//                            itemPos ->
//
//                        navController.navigate(R.id.adminRequirementDetailFragment)
//                    }
//                }
//            }
//        }
    }


    private fun setUi(it: RequirementData) {


    }
}