package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.FragmentCropListingDetailBinding

class ListedCropDetailFragment : Fragment() {

    lateinit var binding: FragmentCropListingDetailBinding

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

        Glide
            .with(requireContext())
            .load(R.drawable.groundnut)
            .centerCrop()
            .into(binding.ivCrop)

        binding.btnCall.setOnClickListener {

        }
    }
}