package com.farmsbook.farmsbook.admin.ui.management.seller

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

class SellerManagementViewModel: ViewModel() {

    private var _allSellers = MutableLiveData<ArrayList<AdminSellerData>>()
    val allSellers: LiveData<ArrayList<AdminSellerData>> = _allSellers

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getAllSellers(context: Context) {

        val queue = Volley.newRequestQueue(context)

        val url = "$baseUrl/admin/getAllAdminMgmtFarmer"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url, null, { response: JSONArray ->

                val sellerList = arrayListOf<AdminSellerData>()
                for (i in 0 until  response.length()) {
                    val seller = response.getJSONObject(i)
                    val id = seller.getInt("id")
                    val name = seller.getString("name")
                    val location = seller.getString("location")
                    val phone = seller.getString("phone")
                    val companyName = seller.getString("companyName")
                    val companyTurnover = seller.getInt("companyTurnover")
                    val crops = seller.getString("crops")

                    sellerList.add( AdminSellerData(id, name, location, phone,
                        companyName, companyTurnover, crops)
                    )
                }
                _allSellers.postValue(sellerList)

            } ) {error: VolleyError ->
            Log.e("SellerManagementViewModel", "getAllSeller: FAILED",error )

        }

        queue.add(request)
    }

}