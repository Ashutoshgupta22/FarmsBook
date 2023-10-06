package com.farmsbook.farmsbook.admin.ui.requirements

import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.ListedOffer

data class RequirementData(
var listId: Int?,
var parentId: Int?,
var cropName: String?,
var variety: String?,
var typeOfSale: Boolean?,
var rate: Int?,
var minPrice: Int?,
var maxPrice: Int?,
var quantity: Int?,
var quantityUnit: String?,
var location: String?,
var transportation: Boolean?,
var timestamp: String?,
var receiveBuyerId: Int?,
var receiveOfferStatus: Boolean?,
var listedStatus: Boolean?,
var imageUrl0: String?,
var imageUrls: List<String>?,
var images: List<String>?,
var user: Any?,
var listedOffer: List<ListedOffer>?
)
