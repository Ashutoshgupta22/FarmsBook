package com.farmsbook.farmsbook.buyer.ui.requirements.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.FragmentFavouritesBinding
import com.farmsbook.farmsbook.databinding.FragmentOffersBinding

class OffersFragment : Fragment() {
    private var _binding: FragmentOffersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}