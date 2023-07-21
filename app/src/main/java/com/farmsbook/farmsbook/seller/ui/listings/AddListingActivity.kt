package com.farmsbook.farmsbook.seller.ui.listings

import android.Manifest
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
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.ActivityAddListingBinding
import com.farmsbook.farmsbook.seller.ui.listings.fragments.ListingConfirmationActivity
import com.farmsbook.farmsbook.seller.ui.listings.fragments.UploadManager
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.ImageAdapter
import com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters.ListingsData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
//import id.zelory.compressor.Compressor
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Matrix as Matrix1

class AddListingActivity : AppCompatActivity(), ImageAdapter.CountOfImagesWhenRemoved {

    private lateinit var binding: ActivityAddListingBinding
    private lateinit var adapter: ImageAdapter
    private lateinit var cropList: ArrayList<String>
    private lateinit var imageList: ArrayList<Uri>
    private lateinit var plantList: ArrayList<ListingsData>
    private lateinit var name: AutoCompleteTextView
    private var listId: Int = 0
    private lateinit var location: AutoCompleteTextView
    private lateinit var metrics: AutoCompleteTextView
    private lateinit var months: AutoCompleteTextView
    private var uploadManager: UploadManager? = null

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    private val REQUEST_PERMISSION = 1
    private val REQUEST_IMAGE_PICK = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        supportActionBar?.hide()

        imageList = arrayListOf()

        binding.backBtn.setOnClickListener {
            finish()
        }

        uploadManager = UploadManager(this)

        val rb1 = binding.ocRb
        val rb2 = binding.frRb
        val rb3 = binding.reqRb
        val rb4 = binding.nreqRb
        val rb5 = binding.uoRb
        val rb6 = binding.orRb

        var type_of_sale = true
        rb1.isChecked = true
        rb1.setOnClickListener {
            binding.rateEdt.isEnabled = true
            binding.rateEdt.visibility = VISIBLE
            binding.textView227.visibility = VISIBLE


            if (rb2.isChecked) {
                rb2.isChecked = false
                type_of_sale = true
            }
        }
        rb2.setOnClickListener {
            binding.rateEdt.isEnabled = false
            binding.rateEdt.visibility = GONE
            binding.textView227.visibility = GONE
            if (rb1.isChecked) {
                rb1.isChecked = false
                type_of_sale = false
            }
        }

        var transportation = false
        rb3.isChecked = true
        rb3.setOnClickListener {
            if (rb4.isChecked) {
                rb4.isChecked = false
                transportation = false
            }
        }
        rb4.setOnClickListener {
            if (rb3.isChecked) {
                rb3.isChecked = false
                transportation = true
            }
        }

        var type_of_farming = false
        rb5.isChecked = true
        rb5.setOnClickListener {
            if (rb6.isChecked) {
                rb6.isChecked = false
                type_of_farming = false
            }
        }
        rb6.setOnClickListener {
            if (rb5.isChecked) {
                rb5.isChecked = false
                type_of_farming = true
            }
        }


        addData()

        val arrayAdapter4 = ArrayAdapter(this, R.layout.dropdown_item, cropList)
        name = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView4)
        name.setAdapter(arrayAdapter4)

        val states = resources.getStringArray(R.array.States)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, states)
        location = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        location.setAdapter(arrayAdapter)

        val metric = resources.getStringArray(R.array.Metrics)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_item, metric)
        metrics = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        metrics.setAdapter(arrayAdapter2)

        val month = resources.getStringArray(R.array.Months)
        val arrayAdapter3 = ArrayAdapter(this, R.layout.dropdown_item, month)
        months = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView3)
        months.setAdapter(arrayAdapter3)

        binding.imageRv.layoutManager = GridLayoutManager(this, 4)

        binding.selectImageBtn.setOnClickListener {

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

        binding.confirmBtn.setOnClickListener {
//            if (type_of_sale == true) {
//
//                if (TextUtils.isEmpty(binding.rateEdt.text)) {
//                    binding.rateEdt.error = "Enter a value for rate"
//                    binding.rateEdt.requestFocus()
//                }
//            } else if (TextUtils.isEmpty(binding.minEdt.text)) {
//                binding.minEdt.error = "Select a valid price"
//                binding.minEdt.requestFocus()
//            }else if (TextUtils.isEmpty(binding.maxEdt.text)) {
//                binding.maxEdt.error = "Select a valid price"
//                binding.maxEdt.requestFocus()
//            } else if (TextUtils.isEmpty(binding.amountEdt.text)) {
//                binding.amountEdt.error = "Enter a valid quantity"
//                binding.amountEdt.requestFocus()
//            } else if (TextUtils.isEmpty(binding.autoCompleteTextView.text)) {
//                binding.autoCompleteTextView.error = "Enter a valid unit"
//                binding.autoCompleteTextView.requestFocus()
//            } else if (TextUtils.isEmpty(binding.autoCompleteTextView2.text)) {
//                binding.autoCompleteTextView2.error = "Select a state"
//                binding.autoCompleteTextView2.requestFocus()
//            }else if (TextUtils.isEmpty(binding.autoCompleteTextView3.text)) {
//                binding.minEdt.error = "Select a valid time of sowing"
//                binding.minEdt.requestFocus()
//            }
//            else {

            postDataUsingVolley(type_of_sale, transportation, type_of_farming)
            Handler().postDelayed({
                getDataUsingVolley()
            }, 100)

            Handler().postDelayed({
                postDataUsingVolley()
            }, 900)


//
        }
    }

    open fun checkPermission(permission: String, requestCode: Int) {
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

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Images"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val x = data.clipData?.itemCount
                val maxFileSize = 4 * 1024 * 1024

                for (i in 0 until x!!) {
                    if (imageList.size < 1) {
                        if (getImageFilePath(data.clipData?.getItemAt(i)!!.uri)?.let { File(it).length() }!! < maxFileSize) {
                            imageList.add(data.clipData?.getItemAt(i)!!.uri)
                        } else {
                            showErrorDialog(
                                this@AddListingActivity,
                                "Image Selection Error",
                                "The size of selected image exceeded 4 MB"
                            )
                            break
                        }
                    } else {
                        showErrorDialog(
                            this@AddListingActivity,
                            "Image Selection Error",
                            "The maximum number of images is 1"
                        )
                        break
                    }
                }

                adapter = ImageAdapter(imageList, this, this)
                binding.imageRv.adapter = adapter
                adapter.notifyDataSetChanged()

            } else if (data?.data != null) {
                if (imageList.size < 4) {
                    val imageUrl = data?.data
                    imageList.add(imageUrl!!)
                } else {
                    showErrorDialog(
                        this@AddListingActivity,
                        "Image Selection Error",
                        "The maximum number of images is 1"
                    )
                }
            }
        }
    }

    private fun postDataUsingVolley(
        type_of_sale: Boolean,
        transportation: Boolean,
        type_of_farming: Boolean
    ) {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/listings"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val respObj = JSONObject()
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val current = formatter.format(time)

        respObj.put("crop_name", binding.autoCompleteTextView4.text.toString())
        respObj.put("variety", binding.varietyEdt.text.toString())
        respObj.put("type_of_sale", type_of_sale)

        if (type_of_sale == false) {
            respObj.put("rate", 0)
        } else {
            respObj.put("rate", binding.rateEdt.text.toString().toInt())
        }
        //respObj.put("rate", binding.rateEdt.text.toString().toInt())
        respObj.put("min_price", binding.minEdt.text.toString().toInt())
        respObj.put("max_price", binding.maxEdt.text.toString().toInt())
        respObj.put("quantity", binding.amountEdt.text.toString().toInt())
        respObj.put("quantity_unit", binding.autoCompleteTextView.text.toString())
        respObj.put("location", binding.autoCompleteTextView2.text.toString())
        respObj.put("transportation", transportation)
        respObj.put("type_of_farming", type_of_farming)
        respObj.put("time_of_sowing", months.text.toString())
        respObj.put("timestamp", current)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {
//            Toast.makeText(this, "Profile Created", Toast.LENGTH_SHORT)
//                .show()


        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        if (imageList.size != 0) {
            queue.add(request)
        } else {
            showErrorDialog(this, "Image Selection Error", "Select atleast 1 image to upload")
        }


    }

    private fun addData() {

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/manageCrops"

        cropList = arrayListOf()
        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {
                    var cropObject = response.getJSONObject(i)
//
                    cropList.add(cropObject.getString("cropName"))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, { error -> // method to handle errors.
            //Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)

    }

    override fun clicked(getSize: Int) {
        Log.d("Images", imageList.size.toString())
    }

    private fun getDataUsingVolley() {

        // url to post our data
        plantList = arrayListOf()
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/listings"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {

                    var cropObject = response.getJSONObject(i)
                    var crop = ListingsData()
                    crop.list_id = cropObject.getInt("list_id").toString()
                    crop.receive_offer_status =
                        cropObject.getString("receive_offer_status").toString()

                    plantList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            listId = plantList[plantList.size - 1].list_id!!.toInt()
            Log.d("List Id", listId.toString())
        }, { error -> // method to handle errors.
            Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

    private fun getImageFilePath(uri: Uri): String? {
        var imagePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            getContentResolver()?.query(uri, projection, null, null, null)
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
        val url = "$baseAddressUrl/user/$userId/listings/$listId/upload-images"

        val images: ArrayList<File> = arrayListOf()
//         Replace with the actual image file path on the device
//        File imageFile = new File("/path/to/image.jpg");
        if (imageList.size > 0) {
            for (i in 0 until imageList.size) {

                val imagePath = getImageFilePath(imageList[i])
                //val actualImageFile = File(imagePath.toString())
//               val imageFile =compressImage( File(imagePath.toString()),600,600,60)
               // Replace with the actual path to your image
//                val maxWidth = 800  // Maximum desired width
//                val maxHeight = 600  // Maximum desired height
//                val compressFormat = Bitmap.CompressFormat.JPEG  // Desired output format (JPEG, PNG, etc.)
//                val quality = 80  // Desired compression quality (0-100)
//                val destinationPath = "/path/to/compressed_image.jpg"  // Replace with the desired output path


                   val imageFile = imagePath?.let { compressImage(it, 60) }
                val comImageFile = imageFile?.let { resizeImage(it,600,600) }
                Log.d("ImageSize1", File(imagePath.toString()).length().toString())

               // val compressedImageFile = Compressor().compress(this, actualImageFile)
                Log.d("ImageSize2", imageFile?.length().toString())

                if (comImageFile != null) {
                    images.add(comImageFile)
                }
            }
            Log.w("IMAGE Path", images.toString())

            uploadManager?.uploadFormData(url, images)

            Log.w("IMAGE Path", "Uploaded")
            startActivity(
                Intent(
                    this@AddListingActivity, ListingConfirmationActivity::class.java
                )
            )
            finish()
//            }

        } else {
            Toast.makeText(this, "Select a Image", Toast.LENGTH_SHORT).show()
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