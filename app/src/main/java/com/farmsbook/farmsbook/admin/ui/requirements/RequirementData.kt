package com.farmsbook.farmsbook.admin.ui.requirements

data class RequirementData(
    val id: Int = 0,
    val cropName: String? = null,
    val variety: String? = null,
    val typeOfBuy: Boolean? = null,
    val rate: Int = 0,
    val minRange: Int? = null,
    val maxRange: Int? = null,
    val quantity: Int? = null,
    val quantityUnit: String? = null,
    val location: String? = null,
    val transportation: Boolean? = null,
    val interestedSupplier: Int? = null,
    val requirementStatus: Boolean? = null,
    val timestamp: String? = null,
    val manageCropId: Int? = null,
    val cropBy: String? = null,
    val phone: String? = null,
    val companyName: String? = null,
    val imageCrop: String?= null,
    val imageUser: String? = null,
    val user: Any? = null,
    val reqInterestedUser: ArrayList<InterestedUser> = arrayListOf()
)

data class InterestedUser(
    val id: Int? = null,
    val imagePath: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val companyName: String? = null,
    val parentId: Int? = null,
    val timestamp: String? = null,
    val requirements: Any? = null
)