package com.farmsbook.farmsbook.admin.ui.cropslistings

data class CropData(
    var id:Int = 0,
    var parentId: Int = 0,
    var cropName: String? = null,
    var status: String? = null,
    var imagePath: String? = null
)
