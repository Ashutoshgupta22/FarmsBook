package com.farmsbook.farmsbook.admin.ui.offers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farmsbook.farmsbook.admin.ui.requirements.RequirementData

class AdminOfferDetailViewModel: ViewModel() {

    private val _offer = MutableLiveData<RequirementData>()
    val offer: LiveData<RequirementData> = _offer

    fun getOfferDetail(context: Context) {

    }

}