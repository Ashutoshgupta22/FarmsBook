package com.farmsbook.farmsbook.admin.ui.cropslistings

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.ListedCropData
import com.farmsbook.farmsbook.admin.ui.cropslistings.adapter.ListedOffer
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class ListedCropDetailViewModel : ViewModel() {

    private val _crop = MutableLiveData<ListedCropData>()
    val crop: LiveData<ListedCropData> = _crop

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getCropData(context: Context, id: Int) {

        Log.i("ListedCropDetailViewModel", "getCropData: getting crop id - $id")

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/listedCrop/$id"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null, { response: JSONObject ->

                val listId = response.optInt("list_id", 0)
                val parentId = response.optInt("parent_id")
                val cropName = response.optString("crop_name", null)
                val variety = response.optString("variety", null)
                val typeOfSale = response.optBoolean("type_of_sale", false)
                val rate = response.optInt("rate", 0)
                val minPrice = response.optInt("min_price", 0)
                val maxPrice = response.optInt("max_price", 0)
                val quantity = response.optInt("quantity", 0)
                val quantityUnit = response.optString("quantity_unit", null)
                val location = response.optString("location", null)
                val transportation = response.optBoolean("transportation", false)
                val typeOfFarming = response.optBoolean("type_of_farming", false)
                val timeOfSowing = response.optString("time_of_sowing", null)
                val timestamp = response.optString("timestamp", null)
                val receiveBuyerId = response.optInt("receive_buyer_id")
                val receiveOfferStatus = response.optBoolean("receive_offer_status", false)
                val listedStatus = response.optBoolean("listed_status", false)
                val imageUrl0 = response.optString("imageUrl0", null)
                val userName = response.optString("userName")
                val userImage = response.optString(
                    "userImage",
                    "https://displaypic.s3.ap-south-1.amazonaws.com/noimage.png"
                )
                val phoneNum = response.optString("phoneNum")
                val companyName = response.optString("companyName")
                val imageUrls = response.optJSONArray("imageUrls")
                    ?.let { 0.until(it.length()).map { i -> it.optString(i) } }
                val images = response.optJSONArray("images")
                    ?.let { 0.until(it.length()).map { i -> it.optString(i) } }
                val user =
                    response.opt("user") // The type of "user" is not clear from the provided JSON

                val listedOfferArray = response.optJSONArray("listedOffer")
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
                    userImage, phoneNum, companyName, imageUrls, images, user, listedOfferList
                )
                _crop.postValue(listedCropData)

            }) { error: VolleyError ->
            Log.e("ListedCropDetailViewModel", "getCropData: FAILED", error)
        }
        queue.add(request)
    }
}