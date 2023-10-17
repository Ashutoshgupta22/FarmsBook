package com.farmsbook.farmsbook.admin.ui.management.buyer

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray

class BuyerManagementViewModel: ViewModel() {

    private var _allBuyers = MutableLiveData<ArrayList<AdminBuyerData>>()
    val allBuyers: LiveData<ArrayList<AdminBuyerData>> = _allBuyers

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getAllBuyer(context: Context) {

        val queue = Volley.newRequestQueue(context)

        val url = "$baseUrl/admin/getAllAdminMgmtBuyer"

        val request = JsonArrayRequest(Request.Method.GET,
            url, null, { response: JSONArray ->

                val buyerList = arrayListOf<AdminBuyerData>()
                for (i in 0 until  response.length()) {
                    val buyer = response.getJSONObject(i)
                    val id = buyer.getInt("id")
                    val name = buyer.getString("name")
                    val location = buyer.getString("location")
                    val phone = buyer.getString("phone")
                    val companyName = buyer.getString("companyName")
                    val companyTurnover = buyer.getInt("companyTurnover")
                    val crops = buyer.getString("crops")
                    val userImage = buyer.getString("userImage")

                    buyerList.add( AdminBuyerData(id, name, location, phone,
                        companyName, companyTurnover, crops, userImage)
                    )
                }
                _allBuyers.postValue(buyerList)

            } ) {error: VolleyError ->
            Log.e("AdminHomeViewModel", "getAllBuyer: FAILED",error )

        }

        queue.add(request)
    }
}