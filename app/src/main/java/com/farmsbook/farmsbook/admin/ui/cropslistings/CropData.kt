package com.farmsbook.farmsbook.admin.ui.cropslistings

data class CropData(
    var id:Int? = null,
    var parentId: Int? = null,
    var cropName: String? = null,
    var status: String? = null,
    var imagePath: String? = null
)
