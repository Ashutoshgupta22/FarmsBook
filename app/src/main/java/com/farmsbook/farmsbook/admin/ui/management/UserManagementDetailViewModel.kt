package com.farmsbook.farmsbook.admin.ui.management

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.admin.ui.management.buyer.AdminUserData
import com.farmsbook.farmsbook.admin.ui.management.buyer.OfferData
import com.farmsbook.farmsbook.admin.ui.management.buyer.RequirementData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject

class UserManagementDetailViewModel : ViewModel() {

    private val _user = MutableLiveData<AdminUserData>()
    val user: LiveData<AdminUserData> = _user

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getUserDetail(context: Context, id: Int) {

        val queue = Volley.newRequestQueue(context)

        val url = "$baseUrl/user/$id"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null, { response: JSONObject ->

                val userId = response.getInt("id")
                val name = response.getString("name")
                val location = response.getString("location")
                val phone = response.getString("phone")
                val companyName = response.getString("business_name")
                val companyTurnover = response.getInt("business_turnovers")
               // val crops = response.getString("crops")
                val userImage = response.getString("imagePath")
                val foundationData = response.getString("foundation_date")

                val requirementsArray = response.getJSONArray("requirements")
                val offerArray = response.getJSONArray("offer")

                val requirementsList = arrayListOf<RequirementData>()
                val offersList = arrayListOf<OfferData>()

                    for (i in 0 until requirementsArray.length()) {
                        val requirementObj = requirementsArray.getJSONObject(i)
                        val requirement = RequirementData(
                            reqId = requirementObj.optInt("req_id", 0),
                            cropName = requirementObj.optString("crop_name", ""),
                            variety = requirementObj.optString("variety", ""),
                            typeOfBuy = requirementObj.optBoolean("type_of_buy", false),
                            minRange = requirementObj.optInt("min_range", 0),
                            maxRange = requirementObj.optInt("max_range", 0),
                            quantity = requirementObj.optInt("quantity", 0),
                            quantityUnit = requirementObj.optString("quantity_unit", ""),
                            location = requirementObj.optString("location", ""),
                            transportation = requirementObj.optBoolean("transportation", false),
                            interestedSupplier = requirementObj.optInt("interested_supplier", 0),
                            requirementStatus = requirementObj.optBoolean("requirement_status", false),
                            timestamp = requirementObj.optString("timestamp", ""),
                            manageCropId = requirementObj.optInt("manageCropId", 0),
                            cropBy = requirementObj.optString("cropBy", ""),
                            phone = requirementObj.optString("phone", ""),
                            companyName = requirementObj.optString("companyName", ""),
                            imageCrop = requirementObj.optString("imageCrop", ""),
                            imageUser = requirementObj.optString("imageUser", ""),
                            user = requirementObj.opt("user"), // The type of "user" is not clear from the provided JSON
                            reqInterestedUser = emptyList() // To be filled if there are interested users
                        )
                        requirementsList.add(requirement)
                    }

                    for (i in 0 until offerArray.length()) {
                        val offerObj = offerArray.getJSONObject(i)
                        val offer = OfferData(
                            offerId = offerObj.optInt("offerId", 0),
                            offerById = offerObj.optInt("offer_by_id", 0),
                            offerCropName = offerObj.optString("offer_cropName", ""),
                            offerToCropId = offerObj.optInt("offer_to_crop_id", 0),
                            offerToFarmerId = offerObj.optInt("offer_to_farmer_id", 0),
                            purchasedOn = offerObj.optBoolean("purchased_on", false),
                            rateOfCommission = offerObj.optInt("rate_of_commission", 0),
                            offeringPrice = offerObj.optInt("offering_price", 0),
                            offeringQuantityUnit = offerObj.optString("offering_quantity_unit", ""),
                            offeringQuantity = offerObj.optInt("offering_quantity", 0),
                            transportation = offerObj.optBoolean("transportation", false),
                            deliveryPlace = offerObj.optString("delivery_place", ""),
                            imageUrl0 = offerObj.optString("imageUrl0", ""),
                            buyerImage = offerObj.optString("buyerImage", ""),
                            farmerImage = offerObj.optString("farmerImage", ""),
                            offerStatus = offerObj.optBoolean("offer_status", false),
                            phone = offerObj.optString("phone", ""),
                            phone2 = offerObj.optString("phone2", ""),
                            companyName = offerObj.optString("companyName", ""),
                            farmerCompanyName = offerObj.optString("farmer_companyName", ""),
                            timestamp = offerObj.optString("timestamp", ""),
                            buyerName = offerObj.optString("buyer_name", ""),
                            farmerName = offerObj.optString("farmer_name", ""),
                            replied = offerObj.optBoolean("replied", false),
                            user = offerObj.opt("user"), // The type of "user" is not clear from the provided JSON
                            listings = offerObj.opt("listings"), // The type of "listings" is not clear from the provided JSON
                            counterStatus = emptyList() // To be filled if there are counter statuses
                        )
                        offersList.add(offer)
                    }


                _user.postValue(
                    AdminUserData(
                        userId, name, location,
                        phone, companyName, companyTurnover, "", userImage,
                        foundationData, requirementsList, offersList
                    )
                )
            }
        ) { error: VolleyError ->
            Log.e("RequirementDetailViewModel", "getBuyerDetail: FAILED", error)

        }

        queue.add(request)
    }
}