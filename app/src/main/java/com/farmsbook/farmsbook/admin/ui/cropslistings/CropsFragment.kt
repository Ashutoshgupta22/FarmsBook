package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.CropsAdapter
import com.farmsbook.farmsbook.databinding.FragmentCropsBinding

class CropsFragment: Fragment() {

    private lateinit var binding: FragmentCropsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCropsBinding.inflate(
            LayoutInflater.from(requireContext()),
            container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvGrains.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            isNestedScrollingEnabled = false
            adapter = CropsAdapter({ editPos ->




            }){ deletePos ->


            }
        }

        binding.rvFruits.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            isNestedScrollingEnabled = false
            adapter = CropsAdapter({ editPos ->




            }){ deletePos ->


            }
        }


    }
}