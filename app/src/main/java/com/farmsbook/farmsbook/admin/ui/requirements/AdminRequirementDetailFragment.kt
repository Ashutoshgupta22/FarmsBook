package com.farmsbook.farmsbook.admin.ui.requirements

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.databinding.FragmentAdminRequirementDetailBinding
import com.farmsbook.farmsbook.databinding.FragmentAdminRequirementsBinding

class AdminRequirementDetailFragment: Fragment() {

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

        val id = arguments?.getInt("id", ) ?: 0

        viewModel.getRequirementDetail(requireContext(), id)

        viewModel.requirement.observe(viewLifecycleOwner){
            it?.let {
                setUi(it)
            }
        }
    }

    private fun setUi(it: RequirementData) {

        Glide.with(requireContext()).load(it.imageCrop).fitCenter().into(binding.ivCrop)
        Glide.with(requireContext()).load(it.imageUser).fitCenter().into(binding.ivBuyer)
        binding.tvBuyerName.text = it.cropBy
        binding.tvBuyerLocation.text = it.location
        binding.tvCropName.text = it.cropName
        binding.tvVariety.text = it.variety
        binding.tvTypeOfSale.text = it.typeOfBuy.toString()
        //binding.tvRate.text = it.
        binding.tvPriceRange.text = "${it.minRange}-${it.maxRange}/kg"
        binding.tvQuantity.text = "${it.quantity} ${it.quantityUnit}"
        binding.tvLocation.text = it.location
        binding.tvTransportation.text = if (it.transportation == true) "Available"
        else "Not Available"

        binding.btnCall.setOnClickListener {
            _ ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${it.phone}")
            startActivity(intent)
        }

        binding.rvInterestedSellers.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,
                false)

            adapter = InterestedUserAdapter(it.reqInterestedUser) {
                phone ->

                if (phone != "null") {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phone")
                    startActivity(intent)
                }
            }
        }

    }
}