package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.AdminData.Companion.currentAdmin
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.CropsAdapter
import com.farmsbook.farmsbook.databinding.FragmentCropsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CropsFragment: Fragment() {

    private lateinit var binding: FragmentCropsBinding
    private val viewModel: CropsFragmentViewModel by viewModels()

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

        viewModel.getMyCrops(requireContext(), currentAdmin.id!!)
        viewModel.getAllCrops(requireContext())

        viewModel.myCrops.observe(viewLifecycleOwner) {
            binding.rvMyCrops.apply {
                layoutManager = LinearLayoutManager(requireContext(),
                    RecyclerView.VERTICAL, false)

                isNestedScrollingEnabled = false
                adapter = CropsAdapter(it!!, { editPos, cropName ->
                    editCropDialog(this.adapter, editPos, cropName)

                }) { deletePos -> deleteCropDialog(this.adapter, deletePos) }
            }
        }

        viewModel.allCrops.observe(viewLifecycleOwner) {

            binding.rvAllCrops.apply {
                layoutManager = LinearLayoutManager(requireContext(),
                    RecyclerView.VERTICAL, false)

                isNestedScrollingEnabled = false
                adapter = CropsAdapter(it!!, { editPos, cropName ->
                    editCropDialog(this.adapter, editPos, cropName)

                }) { deletePos -> deleteCropDialog(this.adapter, deletePos) }
            }

        }

        viewModel.cropAdded.observe(viewLifecycleOwner) {
            it?.let { if (it) {
                viewModel.getMyCrops(requireContext(), currentAdmin.id!!)
                viewModel.getAllCrops(requireContext())
            } }
        }

        binding.fabAddCrop.setOnClickListener{

            val dialogView = LayoutInflater.from(requireContext()).inflate(
                R.layout.dialog_add_edit_crop_listing, null )

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add Crop")
                .setView(dialogView)

                .setPositiveButton("Add") {
                        _,_ ->

                    val etCropName = dialogView.findViewById<EditText>(R.id.et_crop_name)
                    val name = etCropName.text.toString()

                    if (name.isBlank()) etCropName.error = "Name can not be empty"
                    else {
                        viewModel.addCrop(requireContext(), name, currentAdmin.id!!)
                    }

                }
                .setNegativeButton("Cancel") { _,_ ->
                }.show()

        }
    }

    private fun editCropDialog(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
                               editPos: Int, cropName: String) {

        val dialogView = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_add_edit_crop_listing, null )

        val etCropName = dialogView.findViewById<EditText>(R.id.et_crop_name)
        etCropName.text = Editable.Factory.getInstance().newEditable(cropName)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Crop")
            .setIcon(R.drawable.ic_edit_outline)
            .setView(dialogView)

            .setPositiveButton("Save") {
                    _,_ ->

               // val changedName = etCropName.text.toString()
              //  adapter?.notifyItemChanged(editPos)
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
                    _,_ ->
                adapter?.notifyItemRemoved(deletePos)
            }
            .setNegativeButton("Cancel") { _,_ ->
            }.show()

    }
}