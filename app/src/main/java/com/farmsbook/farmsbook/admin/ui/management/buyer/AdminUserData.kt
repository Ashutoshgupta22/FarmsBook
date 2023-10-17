package com.farmsbook.farmsbook.admin.ui.management.buyer

data class AdminUserData(
    var id: Int = 0,
    var name: String? = null,
    var location: String? = null,
    var phone: String? = null,
    var companyName: String? = null,
    var companyTurnover: Int? = null,
    var crops: String? = null,
    var userImage: String? = null,
    var foundationDate: String = "",
    var requirementList: ArrayList<RequirementData> = arrayListOf(),
    var offerPostedList: ArrayList<OfferData> = arrayListOf()

)

data class RequirementData(
    var reqId: Int = 0,
    var cropName: String = "",
    var variety: String = "",
    var typeOfBuy: Boolean = false,
    var minRange: Int = 0,
    var maxRange: Int = 0,
    var quantity: Int = 0,
    var quantityUnit: String = "",
    var location: String = "",
    var transportation: Boolean = false,
    var interestedSupplier: Int = 0,
    var requirementStatus: Boolean = false,
    var timestamp: String = "",
    var manageCropId: Int = 0,
    var cropBy: String = "",
    var phone: String = "",
    var companyName: String = "",
    var imageCrop: String = "",
    var imageUser: String = "",
    var user: Any? = null,
    var reqInterestedUser: List<Any> = emptyList()
)

data class OfferData(
    var offerId: Int = 0,
    var offerById: Int = 0,
    var offerCropName: String = "",
    var offerToCropId: Int = 0,
    var offerToFarmerId: Int = 0,
    var purchasedOn: Boolean = false,
    var rateOfCommission: Int = 0,
    var offeringPrice: Int = 0,
    var offeringQuantityUnit: String = "",
    var offeringQuantity: Int = 0,
    var transportation: Boolean = false,
    var deliveryPlace: String = "",
    var imageUrl0: String = "",
    var buyerImage: String = "",
    var farmerImage: String = "",
    var offerStatus: Boolean = false,
    var phone: String = "",
    var phone2: String = "",
    var companyName: String = "",
    var farmerCompanyName: String = "",
    var timestamp: String = "",
    var buyerName: String = "",
    var farmerName: String = "",
    var replied: Boolean = false,
    var user: Any? = null,
    var listings: Any? = null,
    var counterStatus: List<Any> = emptyList()
)
