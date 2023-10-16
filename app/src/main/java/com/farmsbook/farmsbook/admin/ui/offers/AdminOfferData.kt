package com.farmsbook.farmsbook.admin.ui.offers

data class AdminOfferData(

    val offerId: Int? = null,
    val offerById: Int? = null,
    val offerCropName: String? = null,
    val offerToCropId: Int? = null,
    val offerToFarmerId: Int? = null,
    val purchasedOn: Boolean? = null,
    val rateOfCommission: Int? = null,
    val minPrice: Int = 0,
    val maxPrice: Int = 0,
    val offeringPrice: Int? = null,
    val offeringQuantityUnit: String? = null,
    val offeringQuantity: Int? = null,
    val transportation: Boolean? = null,
    val deliveryPlace: String? = null,
    val imageUrl0: String? = null,
    val buyerImage: String? = null,
    val farmerImage: String? = null,
    val offerStatus: Boolean? = null,
    val phone: String? = null,
    val phone2: String? = null,
    val companyName: String? = null,
    val farmerCompanyName: String? = null,
    val timestamp: String? = null,
    val buyerName: String? = null,
    val farmerName: String? = null,
    val replied: Boolean? = null,
    val user: Any? = null,
    val listings: Any? = null,
    val counterStatus: List<Any>? = null
)


