package com.farmsbook.farmsbook.admin

import com.farmsbook.farmsbook.admin.ui.cropslistings.CropData

class AdminData private constructor(){

    var id: Int? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var newPassword: String? = null
    var phone: String? = null
    var location: String? = null
    var imagePath: String? = null
    val crops = arrayListOf<CropData>()

    companion object {
        val currentAdmin = AdminData()
    }
}