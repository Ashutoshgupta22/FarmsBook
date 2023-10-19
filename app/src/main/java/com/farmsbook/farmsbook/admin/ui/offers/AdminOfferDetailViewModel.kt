package com.farmsbook.farmsbook.admin.ui.offers

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
import com.farmsbook.farmsbook.admin.ui.management.buyer.OfferData
import com.farmsbook.farmsbook.admin.ui.requirements.RequirementData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class AdminOfferDetailViewModel: ViewModel() {

    private val _offer = MutableLiveData<AdminOfferData>()
    val offer: LiveData<AdminOfferData> = _offer

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getOfferDetail(context: Context, id: Int) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/offers/$id"

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->

                val interestedUsersArray = response.optJSONArray("counterStatus")
                val interestedUsersList = ArrayList<Any>()

                if (interestedUsersArray != null) {
                    for (j in 0 until interestedUsersArray.length()) {
                        // Parse interested users objects similarly to OfferData and add them to interestedUsersList
                        // You might need to create a separate data class for the interested users' structure
                    }
                }

                val offerData = AdminOfferData(
                    offerId = response.optInt("offerId"),
                    offerById = response.optInt("offer_by_id"),
                    offerCropName = response.optString("offer_cropName"),
                    offerToCropId = response.optInt("offer_to_crop_id"),
                    offerToFarmerId = response.optInt("offer_to_farmer_id"),
                    purchasedOn = response.optBoolean("purchased_on"),
                    rateOfCommission = response.optInt("rate_of_commission"),
                    offeringPrice = response.optInt("offering_price"),
                    offeringQuantityUnit = response.optString("offering_quantity_unit"),
                    offeringQuantity = response.optInt("offering_quantity"),
                    transportation = response.optBoolean("transportation"),
                    deliveryPlace = response.optString("delivery_place"),
                    imageUrl0 = response.optString("imageUrl0"),
                    buyerImage = response.optString("buyerImage"),
                    farmerImage = response.optString("farmerImage"),
                    offerStatus = response.optBoolean("offer_status"),
                    phone = response.optString("phone"),
                    phone2 = response.optString("phone2"),
                    companyName = response.optString("companyName"),
                    farmerCompanyName = response.optString("farmer_companyName"),
                    timestamp = response.optString("timestamp"),
                    buyerName = response.optString("buyer_name"),
                    farmerName = response.optString("farmer_name"),
                    replied = response.optBoolean("replied"),
                    user = response.opt("user"),
                    listings = response.opt("listings"),
                    counterStatus = interestedUsersList
                )

            _offer.postValue(offerData)
            }
        ) { error: VolleyError ->
            Log.e("ListedCropViewModel", "getAllCrops: FAILED", error)
        }
        queue.add(request)
    }

}