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
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.requirements.AdminRequirementAdapter
import com.farmsbook.farmsbook.admin.ui.requirements.AdminRequirementDetailViewModel
import com.farmsbook.farmsbook.admin.ui.requirements.InterestedUserAdapter
import com.farmsbook.farmsbook.admin.ui.requirements.RequirementData
import com.farmsbook.farmsbook.databinding.FragmentAdminOfferDetailBinding
import com.farmsbook.farmsbook.databinding.FragmentAdminRequirementDetailBinding
import com.farmsbook.farmsbook.utility.TimeFormatter

class AdminOfferDetailFragment: Fragment() {

    private lateinit var binding: FragmentAdminOfferDetailBinding
    private val viewModel: AdminOfferDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAdminOfferDetailBinding.inflate(
            LayoutInflater.from(requireContext()),
            container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id") ?: 0
        viewModel.getOfferDetail(requireContext(), id)

        viewModel.offer.observe(viewLifecycleOwner){
            it?.let {
                setUi(it)
            }
        }
    }

    private fun setUi(it: AdminOfferData) {

        Glide.with(requireContext()).load(it.imageUrl0).fitCenter().into(binding.ivCrop)
        Glide.with(requireContext()).load(it.buyerImage).fitCenter().into(binding.ivBuyer)
        Glide.with(requireContext()).load(it.farmerImage).fitCenter().into(binding.ivSeller)
        binding.tvBuyerName.text = it.buyerName
        binding.tvBuyerLocation.text = ""
        binding.tvBuyerPhone.text = it.phone
        binding.tvSellerName.text = it.farmerName
        binding.tvSellerLocation.text = ""
        binding.tvSellerPhone.text = it.phone2
        binding.tvCropName.text = it.offerCropName
        binding.tvVariety.text = ""
        binding.tvTypeOfSale.text = ""
        binding.tvRate.text = it.rateOfCommission.toString()
        binding.tvPriceRange.text = "${it.minPrice}-${it.maxPrice}/kg"
        binding.tvQuantity.text = "${it.offeringQuantity} ${it.offeringQuantityUnit}"
        binding.tvDeliveryLocation.text = it.deliveryPlace
        binding.tvDate.text = it.timestamp?.let { it1 -> TimeFormatter().getFullDate(it1) }
        binding.tvTransportation.text = if (it.transportation == true) "Available"
        else "Not Available"

//        binding.rvInterestedSellers.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,
//                false)
//
//            adapter = InterestedUserAdapter(it.reqInterestedUser) {
//                    phone ->
//
//                if (phone != "null") {
//                    val intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:$phone")
//                    startActivity(intent)
//                }
//            }
//        }

    }
}