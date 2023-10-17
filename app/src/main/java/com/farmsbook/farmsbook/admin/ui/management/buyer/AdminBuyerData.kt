package com.farmsbook.farmsbook.admin.ui.management.buyer

data class AdminBuyerData(
    var id: Int = 0,
    var name: String? = null,
    var location: String? = null,
    var phone: String? = null,
    var companyName: String? = null,
    var companyTurnover: Int? = null,
    var crops: String? = null,
    var userImage: String? = null,
    var foundationDate: String = "",
    var requirementList: ArrayList<Any> = arrayListOf(),
    var offerPostedList: ArrayList<Any> = arrayListOf()

)