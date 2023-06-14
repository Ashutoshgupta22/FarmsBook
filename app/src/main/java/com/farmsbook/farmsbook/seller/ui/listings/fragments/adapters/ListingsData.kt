package com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters

data class ListingsData(
    var list_id: String? = null,
    var parent_id: String? = null,
    var crop_name : String? = null,
    var variety : String ?= null,
    var type_of_sale : String? = null,
    var rate : String? = null,
    var min_price : String? = null,
    var max_price : String? = null,
    var quantity : String? = null,
    var quantity_unit : String? = null,
    var location : String? = null,
    var transportation : String? = null,
    var type_of_farming : String? = null,
    var type_of_sowing : String? = null,
    var timestamp : String? = null,
    var receive_buyer_id : String? = null,
    var receive_offer_status : String? = null,
    var listed_status : String? = null,
    var user : String? = null,
    var offer : String? = null
)