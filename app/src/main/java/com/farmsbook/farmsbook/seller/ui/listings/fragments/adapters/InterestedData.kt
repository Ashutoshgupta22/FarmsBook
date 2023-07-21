package com.farmsbook.farmsbook.seller.ui.listings.fragments.adapters

data class InterestedData(
    var interest_id: String? = null,
    var buyer_name: String? = null,
    var interest_by_id: String? = null,
    var interest_to_buyer_id: String? = null,
    var crop_name: String? = null,
    var phone: String? = null,
    var crop_image:Int = 0 ,
    var purchased_on : String ?= null,
    var rate_of_commission : String? = null,
    var offering_quantity_unit : String? = null,
    var add_interest_status :String?=null,
    var quantity: String? = null,
    var transportation : String? = null,
    var location : String ?= null,
    var user : String? = null,
)


