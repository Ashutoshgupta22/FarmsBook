package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.ListedCropData
import com.farmsbook.farmsbook.databinding.FragmentCropListingDetailBinding

class ListedCropDetailFragment : Fragment() {

    lateinit var binding: FragmentCropListingDetailBinding
    private val viewModel: ListedCropDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCropListingDetailBinding.inflate(
            layoutInflater,
            container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id") ?: -1
        viewModel.getCropData(requireContext(), id)

        viewModel.crop.observe(viewLifecycleOwner) {

            it?.let {
                setUi(it)
            }
        }
    }

    private fun setUi(it: ListedCropData) {
        Glide
            .with(requireContext())
            .load(it.imageUrl0)
            .centerCrop()
            .into(binding.ivCrop)

        Glide
            .with(requireContext())
            .load(it.userImage)
            .centerCrop()
            .into(binding.ivSeller)

        binding.tvSellerName.text = it.userName
        binding.tvSellerLocation.text = it.location
        binding.tvCropName.text = it.cropName
        binding.tvVariety.text = it.variety
        binding.tvTypeOfSale.text = if(it.typeOfSale == true) "Commission" else "Fixed"
        binding.tvRate.text = it.rate.toString()
        binding.tvPriceRange.text = "${it.minPrice}-${it.maxPrice}/kg"
        binding.tvQuantity.text = "${it.quantity} ${it.quantityUnit}"
        binding.tvLocation.text = it.location
        binding.tvTransportation.text = if (it.transportation == true) "Available"
        else "Not Available"
        binding.tvFarmingType.text = if(it.typeOfFarming == true) "Organic" else "Non organic"
        binding.tvSowingMonth.text = it.timeOfSowing

        binding.btnCall.setOnClickListener {_ ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${it.phoneNum}")
            startActivity(intent)
        }

    }
}