package com.farmsbook.farmsbook.admin.ui.management

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.admin.ui.management.adapter.UserOfferAdapter
import com.farmsbook.farmsbook.admin.ui.management.adapter.UserRequirementAdapter
import com.farmsbook.farmsbook.admin.ui.management.buyer.AdminUserData
import com.farmsbook.farmsbook.databinding.FragmentUserManagementDetailBinding

class UserManagementDetailFragment: Fragment() {

    private lateinit var binding: FragmentUserManagementDetailBinding
    private val viewModel: UserManagementDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserManagementDetailBinding.inflate(layoutInflater,
            container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id") ?: 0

        viewModel.getUserDetail(requireContext(), id)

        viewModel.user.observe(viewLifecycleOwner) {

            it?.let {
                setUi(it)
            }
        }
    }

    private fun setUi(it: AdminUserData) {

        Glide
            .with(requireContext())
            .load(it.userImage)
            .into(binding.ivUserManagement)

        binding.tvNameManagement.text = it.name
        binding.tvLocationManagement.text = it.location
        binding.tvCompanyNameManagement.text = it.companyName
        binding.tvCompanyTurnoverManagement.text = it.companyTurnover.toString()
        binding.tvRegistered.text = it.foundationDate
        binding.btnCallManagement.setOnClickListener {_ ->

            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${it.phone}")
            startActivity(intent)
        }

        binding.rvRequirement.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            if(it.requirementList.isEmpty())
                binding.textNoRequirementsPosted.visibility = View.VISIBLE

            adapter = UserRequirementAdapter(it.requirementList)
        }

        binding.rvOffer.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            if(it.offerPostedList.isEmpty())
                binding.textNoOffersPosted.visibility = View.VISIBLE
            adapter = UserOfferAdapter(it.offerPostedList)
        }


    }
}