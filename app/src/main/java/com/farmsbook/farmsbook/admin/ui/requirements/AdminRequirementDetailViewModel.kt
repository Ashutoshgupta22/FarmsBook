package com.farmsbook.farmsbook.admin.ui.requirements

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
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class AdminRequirementDetailViewModel: ViewModel() {

    private val _requirement = MutableLiveData<RequirementData>()
    val requirement: LiveData<RequirementData> = _requirement

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getRequirementDetail(context: Context, id: Int) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/requirements/$id"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null, { response: JSONObject ->

                    val interestedUsersArray = response.optJSONArray("reqInterestedUser")
                    val interestedUsersList = ArrayList<InterestedUser>()
                    if (interestedUsersArray != null) {

                        for (j in 0 until interestedUsersArray.length()) {
                            val interestedUserObj = interestedUsersArray.getJSONObject(j)
                            val interestedUser = InterestedUser(
                                id = interestedUserObj.optInt("id"),
                                imagePath = interestedUserObj.optString("imagePath"),
                                name = interestedUserObj.optString("name"),
                                phone = interestedUserObj.optString("phone"),
                                companyName = interestedUserObj.optString("companyName"),
                                parentId = interestedUserObj.optInt("parent_id"),
                                timestamp = interestedUserObj.optString("timestamp"),
                                requirements = interestedUserObj.opt("requirements")
                            )
                            interestedUsersList.add(interestedUser)
                        }
                    }

                    val requirementData = RequirementData(
                        id = response.optInt("req_id"),
                        cropName = response.optString("crop_name"),
                        variety = response.optString("variety"),
                        typeOfBuy = response.optBoolean("type_of_buy"),
                        minRange = response.optInt("min_range"),
                        maxRange = response.optInt("max_range"),
                        quantity = response.optInt("quantity"),
                        quantityUnit = response.optString("quantity_unit"),
                        location = response.optString("location"),
                        transportation = response.optBoolean("transportation"),
                        interestedSupplier = response.optInt("interested_supplier"),
                        requirementStatus = response.optBoolean("requirement_status"),
                        timestamp = response.optString("timestamp"),
                        manageCropId = response.optInt("manageCropId"),
                        cropBy = response.optString("cropBy"),
                        phone = response.optString("phone"),
                        companyName = response.optString("companyName"),
                        imageCrop = response.optString("imageCrop"),
                        imageUser = response.optString("imageUser"),
                        user = response.opt("user"),
                        reqInterestedUser = interestedUsersList
                    )

                _requirement.postValue(requirementData)

            }
        ) { error: VolleyError ->
            Log.e("ListedCropViewModel", "getAllCrops: FAILED", error)
        }
        queue.add(request)
    }
}