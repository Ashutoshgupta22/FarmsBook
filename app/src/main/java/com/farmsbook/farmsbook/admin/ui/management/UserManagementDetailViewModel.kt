package com.farmsbook.farmsbook.admin.ui.management

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farmsbook.farmsbook.admin.ui.management.buyer.AdminBuyerData

class UserManagementDetailViewModel: ViewModel() {

    private val _user = MutableLiveData<AdminBuyerData>()
    val user: LiveData<AdminBuyerData> = _user

    fun getUserDetail(context: Context) {



    }
}