package com.farmsbook.farmsbook.buyer.ui.profile

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropAdapter3
import com.farmsbook.farmsbook.buyer.ui.profile.adapters.ManageCropData
import com.farmsbook.farmsbook.databinding.FragmentProfileBinding
import com.farmsbook.farmsbook.login.LoginActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var cropList: ArrayList<ManageCropData>
    private lateinit var cropImages: ArrayList<Int>
    private lateinit var logoutDialog: AlertDialog
    private lateinit var shareDialog: AlertDialog
    private lateinit var langDialog: AlertDialog
    private lateinit var appLink: String
    private lateinit var template: String

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        getDataUsingVolley()
        getManageCrops()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        appLink = "https://play.google.com/store/apps/details?id="+ requireContext().packageName
        template = "Download and install this amazing application from Google Play and share with your friends!!\n\n";

        val notificationsViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //getDataUsingVolley()
        val builder = AlertDialog.Builder(requireContext())

        val view = layoutInflater.inflate(R.layout.logout_dialog, null)
        val no = view.findViewById<TextView>(R.id.cancel_logoutBtn)
        val yes = view.findViewById<TextView>(R.id.confirm_logoutBtn)
        yes.setOnClickListener {

            logoutDialog.dismiss()
            logoutDialog.dismiss()
            startActivity(Intent(context, LoginActivity::class.java))
            ActivityCompat.finishAffinity(SellerMainActivity())
            val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            var editor = sharedPreference?.edit()
            editor?.remove("USER_ID")
            editor?.remove("USER_ROLE")
            editor?.commit()

        }
        no.setOnClickListener {
            logoutDialog.dismiss()
        }
        builder.setView(view)
        logoutDialog = builder.create()

        val view2 = layoutInflater.inflate(R.layout.share_dialog, null)
        val whatsapp = view2.findViewById<ImageView>(R.id.whatsappBtn)
        val facebook = view2.findViewById<ImageView>(R.id.facebookBtn)
        val twitter = view2.findViewById<ImageView>(R.id.twitterBtn)
        whatsapp.setOnClickListener {

            val packageName ="com.whatsapp"
            if (checkInstallation(requireContext(), packageName)) {
                sendIntent(packageName);
            }else{
                Toast.makeText(requireContext(), "App not installed", Toast.LENGTH_SHORT).show();
            }

            shareDialog.dismiss()
        }
        twitter.setOnClickListener {
            shareDialog.dismiss()

            val packageName ="com.twitter.android"
            if (checkInstallation(requireContext(), packageName)) {
                    sendIntent(packageName);
                }else{
                    Toast.makeText(requireContext(), "App not installed", Toast.LENGTH_SHORT).show();
                }
        }
        facebook.setOnClickListener {

            val packageName ="com.facebook.katana"
            if (checkInstallation(requireContext(), packageName)) {
                sendIntent(packageName);
            }else{
                Toast.makeText(requireContext(), "App not installed", Toast.LENGTH_SHORT).show();
            }

            shareDialog.dismiss()
        }

        builder.setView(view2)
        shareDialog = builder.create()
        cropImages = arrayListOf()
        cropImages.add(R.drawable.bajra)
        cropImages.add(R.drawable.barley)
        cropImages.add(R.drawable.basmati_paddy)
        cropImages.add(R.drawable.black_pepper)
        cropImages.add(R.drawable.cashew)
        cropImages.add(R.drawable.castor_seeds)
        cropImages.add(R.drawable.chana)
        cropImages.add(R.drawable.chilli)
        cropImages.add(R.drawable.coffee)
        cropImages.add(R.drawable.coriander_seeds)
        cropImages.add(R.drawable.cotton)
        cropImages.add(R.drawable.groundnut)
        cropImages.add(R.drawable.guar_gum_refind_splits)
        cropImages.add(R.drawable.guarseed)
        cropImages.add(R.drawable.jaggery)
        cropImages.add(R.drawable.jeera)
        cropImages.add(R.drawable.jowar_white)
        cropImages.add(R.drawable.jowar_yellow)
        cropImages.add(R.drawable.jowar)
        cropImages.add(R.drawable.kapas)
        cropImages.add(R.drawable.maize_kharif)
        cropImages.add(R.drawable.masoor_bold)
        cropImages.add(R.drawable.mazie)
        cropImages.add(R.drawable.moong_dal)
        cropImages.add(R.drawable.mustard_oil)
        cropImages.add(R.drawable.mustard_seed)
        cropImages.add(R.drawable.paddy_basmati_1121)
        cropImages.add(R.drawable.polished_turmeric)
        cropImages.add(R.drawable.refined_soy_oil)
        cropImages.add(R.drawable.rice)
        cropImages.add(R.drawable.sesameseeds)
        cropImages.add(R.drawable.soyabean_meal)
        cropImages.add(R.drawable.soyabean)
        cropImages.add(R.drawable.toor_daal)
        cropImages.add(R.drawable.turmarice_farmer_unpolished)
        cropImages.add(R.drawable.turmeric)
        cropImages.add(R.drawable.wheat)
        cropImages.add(R.drawable.yellow_peas)


        binding.editPictureBtn.setOnClickListener {
            startActivity(Intent(context, BuyerEditPicturesActivity::class.java))
        }

        binding.privacyPolicyBtn.setOnClickListener {
            startActivity(Intent(context, PrivacyPolicyActivity::class.java))
        }
        binding.tncBtn.setOnClickListener {
            startActivity(Intent(context, TnCActivity::class.java))
        }
        binding.manageCropBtn.setOnClickListener {
            startActivity(Intent(context,ManageCropsActivity::class.java))
        }

        binding.viewSupplierBtn.setOnClickListener {
            startActivity(Intent(context, ViewProfileActivity::class.java))
        }
        binding.editProfileBtn.setOnClickListener {
            startActivity(Intent(context,EditProfileActivity::class.java))
        }

        binding.logoutBtn.setOnClickListener {
            logoutDialog.show()
        }
        binding.shareBtn.setOnClickListener {
            shareDialog.show()
        }

        binding.languageBtn.setOnClickListener {
            showLanguageChangeDialog()
        }


        return root
    }

    private fun sendIntent(packageName: String) {

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.setPackage(packageName)
        intent.putExtra(Intent.EXTRA_TEXT, template + appLink)
        startActivity(intent)
    }

    private fun checkInstallation(context: Context, packageName: String): Boolean {

        val packageManager = context.packageManager
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        }
        catch (e: PackageManager.NameNotFoundException ) {
            return false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = getFragmentManager()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.nav_host_fragment_activity_main2, fragment)
        fragmentTransaction?.commit()
    }

    private fun showLanguageChangeDialog() {

        val storedLocale = AppCompatDelegate.getApplicationLocales()

        val checkedItem: Int = if (storedLocale.toLanguageTags() == "hi") 1
        else 0

        val listItems = arrayOf("English", "Hindi")
        val mBuilder = context?.let { AlertDialog.Builder(it) }
        mBuilder?.setTitle(context?.resources?.getString(R.string.choose_language))
        mBuilder?.setSingleChoiceItems(
            listItems, checkedItem
        ) { dialogInterface, i ->
            if (i == 0) {
                setLocale("en")
               // recreate(requireActivity())
            } else if (i == 1) {
                setLocale("hi")
               // recreate(requireActivity())
            }
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder?.create()
        mDialog?.show()
    }


    private fun setLocale(lang: String) {

        val appLocale = LocaleListCompat.forLanguageTags(lang)
        AppCompatDelegate.setApplicationLocales(appLocale)

    //        val locale = Locale(lang)
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.setLocale(locale)
//
//        requireContext().createConfigurationContext(config)

//        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//        val editor = getSharedPreferences("Settings", AppCompatActivity.MODE_PRIVATE).edit()
//        editor.putString("My_lang", lang)
//        editor.apply()
    }
    private fun getDataUsingVolley() {

        // url to post our data
        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId"

        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->


            if(response.getString("imagePath") != "null")
            {
                context?.let { Glide.with(it).load(response.getString("imagePath")).into( binding.profileImage) }
            }
           // context?.let { Glide.with(it).load(response.getString("imagePath")).into( binding.profileImage) }
            if (!response.getBoolean("role"))
                binding.roleTv.text = context?.resources?.getString(R.string.farmer)
            else
                binding.roleTv.text = context?.resources?.getString(R.string.trader)
            binding.nameTv.text = response.getString("name")
            binding.companyTv.text = response.getString("business_name")
            binding.phoneTv.text = response.getString("phone")
            binding.addressTv.text = response.getString("location")
        }, { error -> // method to handle errors.
            Toast.makeText(context, "Fail to get response = $error", Toast.LENGTH_LONG).show()
            Log.d("Profile Data", "Fail to get response = $error")
        })
        queue.add(request)
    }

    private fun getManageCrops() {

        val baseAddressUrl = BaseAddressUrl().baseAddressUrl
        val sharedPreference = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val userId = sharedPreference?.getInt("USER_ID", 0)
        val url = "$baseAddressUrl/user/$userId/manageCrops"

        cropList = arrayListOf<ManageCropData>()
        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(context)


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            for (i in 0 until response.length()) {
                try {
                    val cropObject = response.getJSONObject(i)
                    val crop = ManageCropData()
                    crop.cropName = cropObject.getString("cropName")
                    crop.id = cropObject.getInt("id")
                    crop.cropId = cropObject.getInt("cropId")

                    if( crop.cropId < cropImages.size)
                        crop.image = cropImages[crop.cropId - 1]
                    else
                        crop.imageUrl = cropObject.optString("img", "")

                    cropList.add(crop)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            cropList.add(ManageCropData("Add Crops", R.drawable.add_crop_btn, 0))

            binding.cropsRV.layoutManager = GridLayoutManager(context, 4)
            val adapter = context?.let { ManageCropAdapter3(cropList, it) }
            binding.cropsRV.adapter = adapter

            adapter?.setOnItemClickListener(object : ManageCropAdapter3.onItemClickListener {
                override fun onItemClick(position: Int) {
//
                    if(position == cropList.size-1)
                    {
                        startActivity(Intent(context, AddCropActivity::class.java))
                    }
//                    cropList.removeAt(position)
//                    adapter.notifyDataSetChanged()
                    //Toast.makeText(context, "You Clicked on item no. $position", Toast.LENGTH_SHORT) .show()
//                startActivity(
//                    Intent(
//                        this,
//                        ViewSupplierActivity::class.java
//                    ).putExtra("FARMER_ID", followList[position].FarmerID))

                }
            })

//            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT)
//                .show()
        }, { error -> // method to handle errors.
            //Toast.makeText(this, "Fail to get response = $error", Toast.LENGTH_LONG).show()
        })
        queue.add(request)
    }
}