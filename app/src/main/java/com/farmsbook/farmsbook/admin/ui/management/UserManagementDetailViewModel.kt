package com.farmsbook.farmsbook.admin.ui.management

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
import com.farmsbook.farmsbook.admin.ui.management.buyer.AdminBuyerData
import com.farmsbook.farmsbook.seller.ui.buyers.adapters.BuyersData
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class UserManagementDetailViewModel : ViewModel() {

    private val _user = MutableLiveData<AdminBuyerData>()
    val user: LiveData<AdminBuyerData> = _user

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

                val requirementList = arrayListOf<Any>()
                val offerList = arrayListOf<Any>()

                for ( i in 0 until requirementsArray.length()) {

                    //add requirement
                }

                for ( i in 0 until offerArray.length()) {
                    //add offer
                }

                _user.postValue(
                    AdminBuyerData(
                        userId, name, location,
                        phone, companyName, companyTurnover, "", userImage,
                        foundationData, requirementList, offerList
                    )
                )
            }
        ) { error: VolleyError ->
            Log.e("RequirementDetailViewModel", "getBuyerDetail: FAILED", error)

        }

        queue.add(request)
    }
}