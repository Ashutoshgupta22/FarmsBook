package com.farmsbook.farmsbook.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.buyer.MainActivity
import com.farmsbook.farmsbook.seller.SellerMainActivity

class ChooseRoleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_role, container, false)

        val confirmBtn = view.findViewById<TextView>(R.id.confirmBtn)
        val radio1 = view.findViewById<RadioButton>(R.id.radio1)
        val radio2 = view.findViewById<RadioButton>(R.id.radio2)
        confirmBtn.setOnClickListener {

            if (radio1.isChecked) {
                startActivity(Intent(context, MainActivity::class.java))
                finishAffinity(requireActivity())
            }
            if (radio2.isChecked) {
                startActivity(Intent(context, SellerMainActivity::class.java))
                finishAffinity(requireActivity())
            }
        }
        radio1.setOnClickListener {

            if (radio2.isChecked) {
                radio2.isChecked = false
            }

        }

        radio2.setOnClickListener {
            if (radio1.isChecked) {
                radio1.isChecked = false
            }
        }
        return view

    }

}
