package com.farmsbook.farmsbook.seller.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivitySellerEditPicturesBinding
import com.farmsbook.farmsbook.login.EnterNumberFragment
import com.farmsbook.farmsbook.login.UploadImageFragment
import com.farmsbook.farmsbook.seller.ui.profile.adapters.UploadManager
import com.farmsbook.farmsbook.seller.ui.profile.adapters.UploadManager2
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.google.android.material.imageview.ShapeableImageView
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class SellerEditPicturesActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySellerEditPicturesBinding
    private lateinit var profileImage: ShapeableImageView
    private lateinit var backgroundImage: ImageView
    private var uploadManager: UploadManager? = null
    private var uploadManager2: UploadManager2? = null

    companion object {
        val IMAGE_REQUEST_CODE = 100
        val BACKGROUND_IMAGE_REQUEST_CODE = 200
    }

    private val REQUEST_PERMISSION = 1
    private val REQUEST_IMAGE_PICK = 5

    private var uritext: Uri? = null
    private var uritext2: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerEditPicturesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        uploadManager = UploadManager(this)
        uploadManager2 = UploadManager2(this)
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        profileImage =findViewById<ShapeableImageView>(R.id.profileImage)
        backgroundImage =findViewById<ImageView>(R.id.backgroundImage)
//        val uploadBtn =

        getDataUsingVolley()
        backBtn.setOnClickListener {

            finish()
        }

        findViewById<Button>(R.id.uploadBtn).setOnClickListener {
            postDataUsingVolley()
            //Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show()
        }


        findViewById<ImageView>(R.id.editDPbtn).setOnClickListener {

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

        findViewById<ImageView>(R.id.editBPbtn).setOnClickListener {

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                println("Your Android version is 13.")
                checkPermission2(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    REQUEST_PERMISSION
                )
            } else {
                println("Your Android version is not 13.")
                checkPermission2(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    REQUEST_PERMISSION
                )
            }
        }
    }

    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(
                this,
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

    fun checkPermission2(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(permission),
                requestCode
            )
        } else {
//            Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT)
//                .show()
            pickImageGallery2()
            //uploadFormData()
        }
    }
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    private fun pickImageGallery2() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, BACKGROUND_IMAGE_REQUEST_CODE)

    }

    private fun requestPermissions(permission: String) {
        ActivityCompat.requestPermissions(
            this, arrayOf<String>(permission),
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
                Toast.makeText(this, "Storage permission required", Toast.LENGTH_SHORT).show()
                requestPermissions(permissions.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val imageUri = data?.data
            profileImage.setImageURI(imageUri)
            uritext = imageUri
//
        }else if (requestCode == BACKGROUND_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val imageUri = data?.data
            backgroundImage.setImageURI(imageUri)
            uritext2 = imageUri
//
        }
    }

    private fun getImageFilePath(uri: Uri): String? {
        var imagePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver?.query(uri, projection, null, null, null)
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
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/image"
        val url2 = "$baseAddressUrl/user/$userId/bgimage"

//         Replace with the actual image file path on the device
//        File imageFile = new File("/path/to/image.jpg");



        val imagePath = uritext?.let { getImageFilePath(it) }
        val imagePath2 = uritext2?.let { getImageFilePath(it) }

        Log.i("Display IMAGE Path", imagePath.toString())
        Log.i("Background IMAGE Path", imagePath.toString())

        if (imagePath != null && imagePath2 != null) {


            Log.i("Display IMAGE Path", imagePath.toString())
            Log.i("Background IMAGE Path", imagePath2.toString())

            val imageFile = File(imagePath)
            val imageFile2 = File(imagePath2)
            val maxFileSize = 4 * 1024 * 1024 // 5MB (example: set your desired maximum file size limit)
            val selectedImageSize1 = File(imagePath).length()
            val selectedImageSize2 = File(imagePath2).length()

            if (selectedImageSize1 > maxFileSize && selectedImageSize2 > maxFileSize) {
                showErrorDialog(this, "Image Size Limit", "The selected image size exceeds 4 MB")
                return
            }else{
                var comImageFile = compressImage(imagePath,60)
                comImageFile = resizeImage(comImageFile,600,600)!!
                uploadManager?.uploadFormData(url, comImageFile)
                var comImageFile2 = compressImage(imagePath2,60)
                comImageFile2 = resizeImage(comImageFile2,600,600)!!
                uploadManager2?.uploadFormData(url2, comImageFile2)
                Log.w("IMAGE Path", "Uploaded")
                finish()
            }


        }else if (imagePath != null && imagePath2 == null) {

            Log.i("Display IMAGE Path", imagePath.toString())
            Log.i("Background IMAGE Path", imagePath2.toString())

            val imageFile = File(imagePath)
            val maxFileSize = 4 * 1024 * 1024 // 5MB (example: set your desired maximum file size limit)
            val selectedImageSize1 = File(imagePath).length()
            //val selectedImageSize2 = File(imagePath2).length()

            if (selectedImageSize1 > maxFileSize ) {
                showErrorDialog(this, "Image Size Limit", "The selected image size exceeds 4 MB")
                return
            }else{
                var comImageFile = compressImage(imagePath,60)
               val comImageFile3 = resizeImage(comImageFile,200,200)!!
                val comImageFile4 = setRotation(comImageFile3,90)
                uploadManager?.uploadFormData(url, comImageFile4)
                //uploadManager2?.uploadFormData(url2, imageFile2)
                Log.w("IMAGE Path", "Uploaded")
                finish()

            }

        }else if (imagePath == null && imagePath2 != null) {


            Log.w("Display IMAGE Path", imagePath.toString())
            Log.w("Background IMAGE Path", imagePath2.toString())

            //val imageFile = File(imagePath)
            val imageFile2 = File(imagePath2)
            val maxFileSize = 4 * 1024 * 1024 // 5MB (example: set your desired maximum file size limit)
            // val selectedImageSize1 = File(imagePath).length()
            val selectedImageSize2 = File(imagePath2).length()

            if (selectedImageSize2 > maxFileSize) {
                showErrorDialog(this, "Image Size Limit", "The selected image size exceeds 4 MB")
                return
            }else{
                //uploadManager?.uploadFormData(url, imageFile)
                var comImageFile = compressImage(imagePath2,60)
               val  comImageFile2 = resizeImage(comImageFile,600,600)!!
                uploadManager2?.uploadFormData(url2, comImageFile2)
                Log.w("IMAGE Path", "Uploaded")
                finish()
            }

        } else {
            //finish()
            Log.w("Display IMAGE Path", "Select An Image")
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

    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

            if(response.getString("imagePath") != "null")
            {
                Glide.with(this).load(response.getString("imagePath")).into( binding.profileImage)
            }
          if(response.getString("background_imagePath") != "null")
          {
              Glide.with(this).load(response.getString("background_imagePath")).into( binding.backgroundImage)
          }

        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Profile Data", "Fail to get response = $error")
        })
        queue.add(request)
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

    fun setRotation(imageFile: File, rotationDegrees: Int): File? {
        val rotatedImageFile: File
        try {
            val exifInterface = ExifInterface(imageFile.path)
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, rotationDegrees.toString())
            exifInterface.saveAttributes()

            // Create a temporary file to save the rotated image
            rotatedImageFile = File.createTempFile("rotated_image", ".jpg")

            // Copy the original image file to the rotated image file
            val inputStream = imageFile.inputStream()
            val outputStream = FileOutputStream(rotatedImageFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return rotatedImageFile
    }
}