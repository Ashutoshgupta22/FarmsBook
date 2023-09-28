package com.farmsbook.farmsbook.admin.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.farmsbook.farmsbook.R
import com.farmsbook.farmsbook.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AdminLogoutDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialogView = layoutInflater.inflate(R.layout.logout_dialog, null)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView).create()
       // dialog.show()

        val btnLogout = dialogView.findViewById<TextView>(R.id.confirm_logoutBtn)
        val btnCancel = dialogView.findViewById<TextView>(R.id.cancel_logoutBtn)

        btnLogout.setOnClickListener {
            dialog.dismiss()
            val edit = requireContext().getSharedPreferences(
                requireContext().packageName, AppCompatActivity.MODE_PRIVATE).edit()
            edit.putString("adminPhone", "")
            edit.apply()

            startActivity( Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        btnCancel.setOnClickListener { dialog.dismiss() }

        return dialog
    }
}