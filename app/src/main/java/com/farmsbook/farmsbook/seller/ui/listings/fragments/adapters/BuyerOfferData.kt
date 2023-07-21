package com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters

data class BuyerOfferData(
    var listedOfferId: String? = null,
    var farmer_id: String? = null,
    var buyer_name : String? = null,
    var company_name : String? = null,
    var crop_name : String? = null,
    var buyer_image : String? = null,
    var variety : String ?= null,
    var purchaseOn : String? = null,
    var rate : String? = null,
    var offer_price : String? = null,
    var offer_quantity : String? = null,
    var offer_quantity_unit : String? = null,
    var location : String? = null,
    var transportation : String? = null,
    var type_of_farming : String? = null,
    var type_of_sowing : String? = null,
    var timestamp : String? = null,
    var buyer_id : String? = null,
)