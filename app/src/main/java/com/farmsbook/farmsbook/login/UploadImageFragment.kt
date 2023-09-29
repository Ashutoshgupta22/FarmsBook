package com.farmsbook.farmsbook.login

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.MainActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class UploadImageFragment : Fragment() {

    private lateinit var profileImage: ShapeableImageView
    private var uploadManager: UploadManager? = null

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    private val REQUEST_PERMISSION = 1
    private val REQUEST_IMAGE_PICK = 5

    private var uritext: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload_image, container, false)
        uploadManager = UploadManager(context)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        profileImage = view.findViewById<ShapeableImageView>(R.id.profileImage)
//        val uploadBtn =

        backBtn.setOnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, EnterNumberFragment())
            fragmentTransaction?.commit()

        }

        view.findViewById<Button>(R.id.uploadBtn).setOnClickListener {
            postDataUsingVolley()
            //Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show()
        }


        view.findViewById<FloatingActionButton>(R.id.chooseBtn).setOnClickListener {

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                println("Your Android version is 13.")
                checkPermission(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    REQUEST_PERMISSION
                )
            } else {
                println("Your Android version is not 13.")
                checkPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    REQUEST_PERMISSION
                )
            }
        }






        return view
    }

    open fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf<String>(permission),
                requestCode
            )
        } else {
//            Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT)
//                .show()
            pickImageGallery()
            //uploadFormData()
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    private fun requestPermissions(permission: String) {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf<String>(permission),
            REQUEST_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions.toString())
            } else {
                Toast.makeText(context, "Storage permission required", Toast.LENGTH_SHORT).show()
                requestPermissions(permissions.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
           profileImage.setImageURI(data?.data)
            val imageUri = data?.data
            uritext = imageUri
        }
    }

    private fun getImageFilePath(uri: Uri): String? {
        var imagePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            activity?.getContentResolver()?.query(uri, projection,
                null, null, null)
        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (cursor.moveToFirst()) {
                imagePath = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        return imagePath
    }

    private fun postDataUsingVolley() {
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/image"


//         Replace with the actual image file path on the device
//        File imageFile = new File("/path/to/image.jpg");
        val imagePath = uritext?.let { getImageFilePath(it) }

        if (imagePath != null) {


            Log.w("IMAGE Path", imagePath)

            val imageFile = File(imagePath)
            val maxFileSize = 4 * 1024 * 1024 // 1MB (example: set your desired maximum file size limit)
            val selectedImageSize = File(imagePath).length()

            if (selectedImageSize > maxFileSize) {
                context?.let { showErrorDialog(it, "Image Size Limit", "The selected image size exceeds 4 MB") }
                //Toast.makeText(context,"Image size should not exceed 1 MB",Toast.LENGTH_SHORT).show()
                return
            }
            else{
                val compImageFile = compressImage(imagePath,60)
                val comImageFile3 = resizeImage(compImageFile,300,300)!!
                uploadManager?.uploadFormData(url, comImageFile3)
                Log.w("IMAGE Path", "Uploaded")
            }


            val sharedPreference2 = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val userROLE = sharedPreference2?.getBoolean("USER_ROLE", false)
            if (!userROLE!!) {
                startActivity(Intent(context, SellerMainActivity::class.java))
                ActivityCompat.finishAffinity(requireActivity())
            } else {
                startActivity(Intent(context, MainActivity::class.java))
                ActivityCompat.finishAffinity(requireActivity())
            }

        } else {
            Toast.makeText(context, "Select a Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showErrorDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
    fun compressImage(imagePath: String, compressionQuality: Int): File {
        val bitmap = BitmapFactory.decodeFile(imagePath) // Decode the image file into a Bitmap

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream)
        // Compress the Bitmap to JPEG format with the specified compression quality

        val compressedImageBytes = outputStream.toByteArray()
        outputStream.close()

        val compressedImageFile = File.createTempFile("compressed_image", ".jpg")
        val fileOutputStream = FileOutputStream(compressedImageFile)
        fileOutputStream.write(compressedImageBytes)
        fileOutputStream.flush()
        fileOutputStream.close()

        return compressedImageFile
    }

    fun resizeImage(imagePath: File, targetWidth: Int, targetHeight: Int): File? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imagePath.path, options)

        // Calculate the desired scale factor
        val scaleFactorX = options.outWidth.toFloat() / targetWidth
        val scaleFactorY = options.outHeight.toFloat() / targetHeight
        val scaleFactor = Math.max(scaleFactorX, scaleFactorY)

        options.inJustDecodeBounds = false
        options.inSampleSize = scaleFactor.toInt()

        // Decode the image file into a Bitmap
        val bitmap = BitmapFactory.decodeFile(imagePath.path, options)
        if (bitmap == null) {
            Log.e("ImageResize", "Failed to decode bitmap")
            return null
        }

        // Create a resized bitmap
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)

        // Recycle the original bitmap if necessary
        if (resizedBitmap != bitmap) {
            bitmap.recycle()
        }

        // Create a temporary file to save the resized bitmap
        val resizedImageFile: File
        try {
            resizedImageFile = File.createTempFile("resized_image", ".jpg")
        } catch (e: IOException) {
            Log.e("ImageResize", "Failed to create resized image file")
            return null
        }

        // Save the resized bitmap to the file
        val outputStream = FileOutputStream(resizedImageFile)
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.close()

        return resizedImageFile
    }
}