package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.CropsAdapter
import com.farmsbook.farmsbook.databinding.FragmentCropsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
            adapter = CropsAdapter({ editPos -> editCropDialog(this.adapter, editPos)

            }) { deletePos -> deleteCropDialog(this.adapter, deletePos) }
        }

        binding.rvFruits.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)

            isNestedScrollingEnabled = false
            adapter = CropsAdapter({ editPos -> editCropDialog(this.adapter, editPos)

            }) { deletePos -> deleteCropDialog(this.adapter, deletePos) }
        }

        binding.fabAddCrop.setOnClickListener{

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add Crop")
                .setView(R.layout.edit_crop_listing)

                .setPositiveButton("Add") {
                        _,_ ->
                }
                .setNegativeButton("Cancel") { _,_ ->
                }.show()

        }
    }

    private fun editCropDialog(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
                               editPos: Int) {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Crop")
            .setIcon(R.drawable.ic_edit_outline)
            .setView(R.layout.edit_crop_listing)

            .setPositiveButton("Save") {
                    _,_ ->  adapter?.notifyItemChanged(editPos)
            }
            .setNegativeButton("Cancel") { _,_ ->
            }.show()

    }

    private fun deleteCropDialog(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
                                 deletePos: Int) {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Crop")
            .setIcon(R.drawable.ic_delete_outline)
            .setMessage("You are going to remove this crop from your record.\n" +
                    "Do you still want to continue?")
            .setPositiveButton("Delete") {
                    _,_ -> adapter?.notifyItemRemoved(deletePos)
            }
            .setNegativeButton("Cancel") { _,_ ->
            }.show()

    }
}