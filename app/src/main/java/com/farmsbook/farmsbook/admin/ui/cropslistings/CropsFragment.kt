package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.admin.AdminData.Companion.currentAdmin
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.CropsAdapter
import com.farmsbook.farmsbook.databinding.FragmentCropsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CropsFragment : Fragment() {

    private lateinit var binding: FragmentCropsBinding
    private val viewModel: CropsFragmentViewModel by viewModels()
    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var ivDialogImage: ImageView
    private var imageUri: Uri? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        launcher = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {

            it?.let {
                imageUri = it
                loadImage(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCropsBinding.inflate(
            LayoutInflater.from(requireContext()),
            container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  viewModel.getMyCrops(requireContext(), currentAdmin.id!!)
        viewModel.getAllCrops(requireContext())

        //viewModel._cropAdded.value = false

        viewModel.allCrops.observe(viewLifecycleOwner) {

            Log.i("C", "onViewCreated: allCrops observer called")

            it?.let {

                binding.rvAllCrops.apply {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        RecyclerView.VERTICAL, false
                    )

                    adapter = CropsAdapter(it, { editPos, cropName ->
                        editCropDialog(this.adapter, editPos, cropName)

                    }) { pos, cropId, parentId ->
                        deleteCropDialog(
                            it,
                            this.adapter,
                            pos,
                            cropId,
                            parentId
                        )
                    }
                }
            }
        }

        viewModel.cropAdded.observe(viewLifecycleOwner) {
            it?.let {

                if (it != -1) {
                    Log.i("CropsFragment", "onViewCreated: crop added id-$it")
                    viewModel.getAllCrops(requireContext())
                    showAddImageDialog(it)
                    viewModel.resetCropAdded()
                }
            }
        }

        binding.fabAddCrop.setOnClickListener {

            val dialogView = LayoutInflater.from(requireContext()).inflate(
                R.layout.dialog_add_crop_name_listing, null
            )

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add Crop")
                .setCancelable(false)
                .setView(dialogView)

                .setPositiveButton("Add") { _, _ ->

                    val etCropName = dialogView.findViewById<EditText>(R.id.et_crop_name)
                    val name = etCropName.text.toString()

                    if (name.isBlank()) {
                        Toast.makeText(
                            requireContext(), "Name can not be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.addCrop(requireContext(), name, currentAdmin.id!!)
                    }

                }
                .setNegativeButton("Cancel") { _, _ ->
                }.show()
        }

        viewModel.cropDeleted.observe(viewLifecycleOwner) {
            it?.let {

                //viewModel.getAllCrops(requireContext() )
            }
        }
    }

    private fun showAddImageDialog(id: Int) {

        val dialogView = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_add_crop_image_listing, null
        )

        val btnCropImage = dialogView.findViewById<Button>(R.id.btn_choose_image)
        ivDialogImage = dialogView.findViewById(R.id.iv_choose_crop_image)

        btnCropImage.setOnClickListener {

            val picker = PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
            launcher.launch(picker)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add crop image")
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->

                if (ivDialogImage.drawable == null) {
                    Toast.makeText(
                        requireContext(), "Select an image",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    imageUri?.let {
                        viewModel.addCropImage(
                            requireContext(),
                            currentAdmin.id!!, it, id
                        )
                    }
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
            }.show()
    }

    private fun loadImage(it: Uri) {

        Glide.with(requireContext())
            .load(it)
            .centerCrop()
            .into(ivDialogImage)
    }


    private fun editCropDialog(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
        editPos: Int, cropName: String
    ) {

        val dialogView = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_edit_crop_listing, null
        )

        val etCropName = dialogView.findViewById<EditText>(R.id.et_crop_name)
        etCropName.text = Editable.Factory.getInstance().newEditable(cropName)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Crop")
            .setIcon(R.drawable.ic_edit_outline)
            .setView(dialogView)

            .setPositiveButton("Save") { _, _ ->

                // val changedName = etCropName.text.toString()
                //  adapter?.notifyItemChanged(editPos)
            }
            .setNegativeButton("Cancel") { _, _ ->
            }.show()

    }

    private fun deleteCropDialog(
        cropList: ArrayList<CropData>,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
        deletePos: Int,
        cropId: Int,
        parentId: Int
    ) {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Crop")
            .setIcon(R.drawable.ic_delete_outline)
            .setMessage(
                "You are going to remove this crop from your record.\n" +
                        "Do you still want to continue?"
            )
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteCrop(requireContext(), cropId, parentId)

                cropList.removeAt(deletePos)
                adapter?.notifyItemRemoved(deletePos)
                Log.i("TAG", "item pos:${deletePos}")
            }
            .setNegativeButton("Cancel") { _, _ ->
            }.show()

    }
}