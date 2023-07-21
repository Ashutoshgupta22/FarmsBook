package com.farmsbook.farmsbook.buyer.ui.requirements.fragments.requirements_child.adapters

data class LatestRequirementsData(
    var req_id: String? = null,
    var crop_name : String? = null,
    var crop_id : String? = null,
    var variety : String ?= null,
    var Image : Int  = 0,
    var type_of_buy : String? = null,
    var min_price : String? = null,
    var max_price : String? = null,
    var quantity : String? = null,
    var quantity_unit : String? = null,
    var location : String? = null,
    var transportation : String? = null,
    var interested_suppliers : String? = null,
    var timestamp : String? = null,
    var requirement_status : String? = null,
    var user : String? = null,
)