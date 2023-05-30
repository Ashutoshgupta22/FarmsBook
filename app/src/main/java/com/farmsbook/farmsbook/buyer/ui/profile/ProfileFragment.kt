package com.farmsbook.farmsbook.buyer.ui.profile

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.databinding.FragmentProfileBinding
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var logoutDialog: AlertDialog
    private lateinit var shareDialog: AlertDialog
    private lateinit var langDialog: AlertDialog

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val builder = AlertDialog.Builder(requireContext())

        val view = layoutInflater.inflate(R.layout.logout_dialog, null)
        val no = view.findViewById<TextView>(R.id.cancel_logoutBtn)
        val yes = view.findViewById<TextView>(R.id.confirm_logoutBtn)
        yes.setOnClickListener {

            logoutDialog.dismiss()

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
            shareDialog.dismiss()

        }
        twitter.setOnClickListener {
            shareDialog.dismiss()

        }
        facebook.setOnClickListener {
            shareDialog.dismiss()

        }

        builder.setView(view2)
        shareDialog = builder.create()

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
        val listItems = arrayOf("English", "Hindi")
        val mBuilder = context?.let { AlertDialog.Builder(it) }
        mBuilder?.setTitle("Choose Language...")
        mBuilder?.setSingleChoiceItems(
            listItems, -1
        ) { dialogInterface, i ->
            if (i == 0) {
                setLocale("en")
                recreate(requireActivity())
            } else if (i == 1) {
                setLocale("hi")
                recreate(requireActivity())
            }
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder?.create()
        mDialog?.show()
    }

    private fun setLocale(lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
//        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//        val editor = getSharedPreferences("Settings", AppCompatActivity.MODE_PRIVATE).edit()
//        editor.putString("My_lang", lang)
//        editor.apply()
    }

}