package com.farmsbook.farmsbook.admin.ui.requirements

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

class AdminRequirementsViewModel : ViewModel() {

    private var _requirements = MutableLiveData<ArrayList<RequirementData>>()
    val requirements: LiveData<ArrayList<RequirementData>> = _requirements

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getAllRequirements(context: Context) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/requirements"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url, null, { response: JSONArray ->
                val cropList = arrayListOf<RequirementData>()

                for (i in 0 until response.length()) {
                    val cropObj = response.getJSONObject(i)

                    val interestedUsersArray = cropObj.optJSONArray("reqInterestedUser")
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
                        id = cropObj.optInt("req_id"),
                        cropName = cropObj.optString("crop_name"),
                        variety = cropObj.optString("variety"),
                        typeOfBuy = cropObj.optBoolean("type_of_buy"),
                        minRange = cropObj.optInt("min_range"),
                        maxRange = cropObj.optInt("max_range"),
                        quantity = cropObj.optInt("quantity"),
                        quantityUnit = cropObj.optString("quantity_unit"),
                        location = cropObj.optString("location"),
                        transportation = cropObj.optBoolean("transportation"),
                        interestedSupplier = cropObj.optInt("interested_supplier"),
                        requirementStatus = cropObj.optBoolean("requirement_status"),
                        timestamp = cropObj.optString("timestamp"),
                        manageCropId = cropObj.optInt("manageCropId"),
                        cropBy = cropObj.optString("cropBy"),
                        phone = cropObj.optString("phone"),
                        companyName = cropObj.optString("companyName"),
                        imageCrop = cropObj.optString("imageCrop"),
                        imageUser = cropObj.optString("imageUser"),
                        user = cropObj.opt("user"),
                        reqInterestedUser = interestedUsersList
                    )

                    cropList.add(requirementData)
                }

                _requirements.postValue(cropList)

            }) { error: VolleyError ->
            Log.e("ListedCropViewModel", "getAllCrops: FAILED", error)
        }
        queue.add(request)

    }
}