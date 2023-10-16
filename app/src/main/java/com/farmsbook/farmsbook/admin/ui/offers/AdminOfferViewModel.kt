package com.farmsbook.farmsbook.admin.ui.offers

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
import com.farmsbook.farmsbook.admin.ui.requirements.RequirementData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray

class AdminOfferViewModel : ViewModel() {

    private var _offers = MutableLiveData<ArrayList<AdminOfferData>>()
    val offers: LiveData<ArrayList<AdminOfferData>> = _offers

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getAllOffers(context: Context) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/offers"

        val request = JsonArrayRequest(Request.Method.GET, url, null, { response: JSONArray ->

            val offerList = arrayListOf<AdminOfferData>()

            for (i in 0 until response.length()) {
                val offerObj = response.getJSONObject(i)

                val interestedUsersArray = offerObj.optJSONArray("counterStatus")
                val interestedUsersList = ArrayList<Any>()

                if (interestedUsersArray != null) {
                    for (j in 0 until interestedUsersArray.length()) {
                        // Parse interested users objects similarly to OfferData and add them to interestedUsersList
                        // You might need to create a separate data class for the interested users' structure
                    }
                }

                val offerData = AdminOfferData(
                    offerId = offerObj.optInt("offerId"),
                    offerById = offerObj.optInt("offer_by_id"),
                    offerCropName = offerObj.optString("offer_cropName"),
                    offerToCropId = offerObj.optInt("offer_to_crop_id"),
                    offerToFarmerId = offerObj.optInt("offer_to_farmer_id"),
                    purchasedOn = offerObj.optBoolean("purchased_on"),
                    rateOfCommission = offerObj.optInt("rate_of_commission"),
                    offeringPrice = offerObj.optInt("offering_price"),
                    offeringQuantityUnit = offerObj.optString("offering_quantity_unit"),
                    offeringQuantity = offerObj.optInt("offering_quantity"),
                    transportation = offerObj.optBoolean("transportation"),
                    deliveryPlace = offerObj.optString("delivery_place"),
                    imageUrl0 = offerObj.optString("imageUrl0"),
                    buyerImage = offerObj.optString("buyerImage"),
                    farmerImage = offerObj.optString("farmerImage"),
                    offerStatus = offerObj.optBoolean("offer_status"),
                    phone = offerObj.optString("phone"),
                    phone2 = offerObj.optString("phone2"),
                    companyName = offerObj.optString("companyName"),
                    farmerCompanyName = offerObj.optString("farmer_companyName"),
                    timestamp = offerObj.optString("timestamp"),
                    buyerName = offerObj.optString("buyer_name"),
                    farmerName = offerObj.optString("farmer_name"),
                    replied = offerObj.optBoolean("replied"),
                    user = offerObj.opt("user"),
                    listings = offerObj.opt("listings"),
                    counterStatus = interestedUsersList
                )

                offerList.add(offerData)
            }
            _offers.postValue(offerList)
        }) { error: VolleyError ->
            Log.e("ListedCropViewModel", "getAllCrops: FAILED", error)
        }
        queue.add(request)

    }
}