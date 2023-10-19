package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.ListedCropData
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.ListedOffer
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray

class ListedCropDetailViewModel: ViewModel() {

    private val _crop = MutableLiveData<CropData>()
    val crop : LiveData<CropData> = _crop

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getCropData(context: Context, id: Int) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/{adminId}/currentCrop/{cropsId}"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url, null, { response: JSONArray ->

                val cropList = arrayListOf<ListedCropData>()

                for (i in 0 until response.length()) {
                    val cropObj = response.getJSONObject(i)

                    val listId = cropObj.optInt("list_id", 0)
                    val parentId = cropObj.optInt("parent_id")
                    val cropName = cropObj.optString("crop_name", null)
                    val variety = cropObj.optString("variety", null)
                    val typeOfSale = cropObj.optBoolean("type_of_sale", false)
                    val rate = cropObj.optInt("rate", 0)
                    val minPrice = cropObj.optInt("min_price", 0)
                    val maxPrice = cropObj.optInt("max_price", 0)
                    val quantity = cropObj.optInt("quantity", 0)
                    val quantityUnit = cropObj.optString("quantity_unit", null)
                    val location = cropObj.optString("location", null)
                    val transportation = cropObj.optBoolean("transportation", false)
                    val typeOfFarming = cropObj.optBoolean("type_of_farming", false)
                    val timeOfSowing = cropObj.optString("time_of_sowing", null)
                    val timestamp = cropObj.optString("timestamp", null)
                    val receiveBuyerId = cropObj.optInt("receive_buyer_id")
                    val receiveOfferStatus = cropObj.optBoolean("receive_offer_status", false)
                    val listedStatus = cropObj.optBoolean("listed_status", false)
                    val imageUrl0 = cropObj.optString("imageUrl0", null)
                    val userName = cropObj.optString("userName")
                    val userImage = cropObj.optString("userImage",
                        "https://displaypic.s3.ap-south-1.amazonaws.com/noimage.png")
                    val companyName = cropObj.optString("companyName")
                    val imageUrls = cropObj.optJSONArray("imageUrls")
                        ?.let { 0.until(it.length()).map { i -> it.optString(i) } }
                    val images = cropObj.optJSONArray("images")
                        ?.let { 0.until(it.length()).map { i -> it.optString(i) } }
                    val user = cropObj.opt("user") // The type of "user" is not clear from the provided JSON

                    val listedOfferArray = cropObj.optJSONArray("listedOffer")
                    val listedOfferList = ArrayList<ListedOffer>()
                    if (listedOfferArray != null) {
                        for (j in 0 until listedOfferArray.length()) {
                            val listedOfferObj = listedOfferArray.getJSONObject(j)
                            // Parse ListedOffer objects similarly to ListedCropData and add them to the listedOfferList
                        }
                    }

                    val listedCropData = ListedCropData(
                        listId, parentId, cropName, variety, typeOfSale, rate, minPrice, maxPrice,
                        quantity, quantityUnit, location, transportation, typeOfFarming, timeOfSowing,
                        timestamp, receiveBuyerId, receiveOfferStatus, listedStatus, imageUrl0,
                        userName,
                        userImage,companyName, imageUrls, images, user, listedOfferList
                    )

                    cropList.add(listedCropData)
                }


                //_allListedCrops.postValue(cropList)

            } ) {error: VolleyError ->
            Log.e("ListedCropViewModel", "getAllCrops: FAILED",error )
        }
        queue.add(request)
    }
}