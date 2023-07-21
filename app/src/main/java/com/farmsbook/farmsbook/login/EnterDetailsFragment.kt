package com.farmsbook.farmsbook.login

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.MainActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import com.google.android.material.imageview.ShapeableImageView
import org.json.JSONObject
import java.io.File
import java.util.*

open class EnterDetailsFragment : Fragment() {

    private lateinit var name: EditText
    private lateinit var phone: TextView
    private lateinit var email: EditText
    private lateinit var location: AutoCompleteTextView
    private lateinit var businessName: EditText
    private lateinit var businessTurnOver: EditText
    private lateinit var foundationDate: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enter_details, container, false)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)


        val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val role = sharedPreference?.getBoolean("USER_ROLE",false)
        //Toast.makeText(context,"Role = $role",Toast.LENGTH_SHORT).show()

        val states = resources.getStringArray(R.array.States)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = context?.let { ArrayAdapter(it, R.layout.dropdown_item, states) }
        // get reference to the autocomplete text view
        location = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        // set adapter to the autocomplete tv to the arrayAdapter
        location.setAdapter(arrayAdapter)

        name = view.findViewById(R.id.nameEdt)
        phone = view.findViewById(R.id.phoneEdt)
        email = view.findViewById(R.id.emailEdt)
        businessName = view.findViewById(R.id.businessNameEdt)
        businessTurnOver = view.findViewById(R.id.businessTurnoverEdt)
        foundationDate = view.findViewById(R.id.foundationDateEdt)

        val value = requireArguments().getString("PhoneNumber")
phone.setText(value)

//        val locale = Locale("en", "IN")
//        Locale.setDefault(locale)
//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)

//
//
//        }

        foundationDate.setOnClickListener {
            val c = Calendar.getInstance(Locale.getDefault())
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = context?.let {
                DatePickerDialog(
                    it, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        // Display Selected date in textbox
                        foundationDate.setText("" + dayOfMonth + " / " + (month+1) + " / " + year)
                    },
                    year,
                    month,
                    day
                )
            }
            dpd?.datePicker?.maxDate = System.currentTimeMillis();
            dpd?.show()
        }
        view.findViewById<TextView>(R.id.confirmBtn).setOnClickListener {
            if (TextUtils.isEmpty(name.text)) {
                name.error = "Enter a valid Name"
                name.requestFocus()
            } else if (TextUtils.isEmpty(location.text)) {
                location.error = "Select a valid State"
                location.requestFocus()
            } else if (TextUtils.isEmpty(phone.text)) {
                phone.error = "Enter a valid Mobile Number"
                phone.requestFocus()
            } else if (TextUtils.isEmpty(businessName.text)) {
                businessName.error = "Enter a valid Business Name"
                businessName.requestFocus()
            } else if (TextUtils.isEmpty(businessTurnOver.text)) {
                businessTurnOver.error = "Enter a valid Business TurnOver"
                businessTurnOver.requestFocus()
            } else if (TextUtils.isEmpty(foundationDate.text)) {
                foundationDate.error = "Enter a valid Foundation Date"
                foundationDate.requestFocus()
            } else {

                postDataUsingVolley(
                    name.text.trim().toString(),
                    role.toString().toBoolean(),
                    phone.text.trim().toString(),
                    email.text.trim().toString(),
                    location.text.trim().toString(),
                    businessName.text.trim().toString(),
                    businessTurnOver.text.trim().toString(),
                    foundationDate.text.trim().toString(),
                )
//                startActivity(Intent(context, MainActivity::class.java))
//                finishAffinity(requireActivity())
            }
        }

        backBtn.setOnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, ChooseRoleFragment())
            fragmentTransaction?.commit()

        }

        return view
    }


//    private fun pickImageGallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent,IMAGE_REQUEST_CODE)
//    }
//    private  fun requestPermissions(permission: String) {
//        ActivityCompat.requestPermissions(
//            requireActivity(), arrayOf<String>(permission),
//            REQUEST_PERMISSION
//        )
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_PERMISSION) {
//            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(permissions.toString())
//            } else {
//                Toast.makeText(context, "Storage permission required", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK)
//        {
////            profileImage.setImageURI(data?.data)
//            val imageUri = data?.data
//            uritext = imageUri
////            val inputStream: InputStream? = imageUri?.let {
////                activity?.getContentResolver()?.openInputStream(
////                    it
////                )
////            }
////            val outputStream = ByteArrayOutputStream()
////
////            val buffer = ByteArray(1024)
////            var bytesRead: Int = 0
////            while (inputStream?.read(buffer).also {
////                    if (it != null) {
////                        bytesRead = it
////                    }
////                } != -1) {
////                outputStream.write(buffer, 0, bytesRead)
////            }
////
////            val imageBytes: ByteArray = outputStream.toByteArray()
////            Log.d("Image","$imageBytes")
//        }
//    }
//
//
//    private fun getImageFilePath(uri: Uri): String? {
//        var imagePath: String? = null
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor: Cursor? = activity?.getContentResolver()?.query(uri, projection, null, null, null)
//        if (cursor != null) {
//            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            if (cursor.moveToFirst()) {
//                imagePath = cursor.getString(columnIndex)
//            }
//            cursor.close()
//        }
//        return imagePath
//    }

    private fun postDataUsingVolley(
        name: String,
        role: Boolean,
        phone: String,
        email: String,
        location: String,
        businessName: String,
        businessTurnovers: String,
        foundationDate: String
    ) {
        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val url = "$baseAddressUrl/user"


        // Replace with the actual image file path on the device
//        File imageFile = new File("/path/to/image.jpg");
//        val imagePath = uritext?.let { getImageFilePath(it) }
//
//        Log.w("IMAGE Path", imagePath!!)
//
//        val imageFile = File(imagePath)
//        uploadManager?.uploadFormData(url, name, phone, email, location, role,"500",businessName,businessTurnovers,foundationDate,imageFile)
//
//        print("Exit")
        //getDataUsingVolley(phone)
        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val respObj = JSONObject()

        respObj.put("imagePath", null)
        respObj.put("name", name)
        respObj.put("phone", phone)
        respObj.put("email", email)
        respObj.put("role", role)
        respObj.put("business_members", 500)
        respObj.put("business_name", businessName)
        respObj.put("business_turnovers", businessTurnovers.toInt())
        respObj.put("crop_count", 50)
        respObj.put("location", location)
        respObj.put("active", true)
        respObj.put("foundation_date", foundationDate)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.POST, url, respObj, {


            val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            var editor = sharedPreference?.edit()
            editor?.putInt("USER_ID",it["id"].toString().toInt())
           // editor?.putBoolean("USER_ROLE",it["role"].toString().toBoolean())
            editor?.commit()
            val frag = UploadImageFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, frag)
            fragmentTransaction?.commit()
            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT).show()
            //Toast.makeText(context, "USER ID = ${USER_ID}", Toast.LENGTH_SHORT).show()

        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }

    private fun getDataUsingVolley(phone: String) {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
//        val userId = intent.getStringExtra("FARMER_ID")


        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method

        val url2 = "$baseAddressUrl/check_exist/$phone"
        val request2 = JsonObjectRequest(Request.Method.GET, url2, null, { response: JSONObject ->

            if(response["active"]== true)
            {
                Toast.makeText(context,"An Error occurred",Toast.LENGTH_SHORT).show()
            }
            else{
                val sharedPreference =  activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
                var editor = sharedPreference?.edit()
                editor?.putInt("USER_ID",response["id"].toString().toInt())
                editor?.putBoolean("USER_ROLE",response["role"].toString().toBoolean())
                editor?.commit()

                if(response["role"] == true)
                {
                    startActivity(Intent(context, MainActivity::class.java))
                    ActivityCompat.finishAffinity(LoginActivity())
                }
                else
                {
                    startActivity(Intent(context, SellerMainActivity::class.java))
                    ActivityCompat.finishAffinity(LoginActivity())
                }
            }

        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request2)
    }
}