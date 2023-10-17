package com.farmsbook.farmsbook.admin.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONObject

data class Stat(
    var todaySize: Int = 0,
    var todaySizeBuyer: Int = 0,
    var todaySizeFarmer: Int = 0,
    var totalSize: Int = 0,
    var totalSizeBuyer: Int = 0,
    var totalSizeFarmer: Int = 0,
    var cropListedToday: Int = 0,
    var cropListedTotal: Int = 0,
    var requirementsToday: Int = 0,
    var requirementsTotal: Int = 0,
    var offerToday: Int = 0,
    var offerTotal: Int = 0
)

class AdminHomeViewModel: ViewModel() {

    private var _stats = MutableLiveData<Stat>()
    val stats: LiveData<Stat> = _stats

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getHomeStats(context: Context) {

            val queue = Volley.newRequestQueue(context)

            val url = "$baseUrl/admin/home"

            val request = JsonObjectRequest(
                Request.Method.GET,
                url, null, { response: JSONObject ->

                    val todaySize = response.getInt("todaySize")
                    val todaySizeBuyer = response.getInt("todaySizeBuyer")
                    val todaySizeFarmer = response.getInt("todaySizeFarmer")
                    val totalSize = response.getInt("totalSize")
                    val totalSizeBuyer = response.getInt("totalSizeBuyer")
                    val totalSizeFarmer = response.getInt("totalSizeFarmer")
                    val cropListedToday = response.getInt("cropListedToday")
                    val cropListedTotal = response.getInt("cropListedTotal")
                    val requirementsToday = response.getInt("requirementsToday")
                    val requirementsTotal = response.getInt("requirementsTotal")
                    val offerToday = response.getInt("offerToday")
                    val offerTotal = response.getInt("offerTotal")

                    _stats.postValue( Stat( todaySize, todaySizeBuyer, todaySizeFarmer,
                        totalSize, totalSizeBuyer, totalSizeFarmer, cropListedToday,
                        cropListedTotal, requirementsToday, requirementsTotal,
                        offerToday, offerTotal))

                } ) {error: VolleyError ->
                Log.e("AdminHomeViewModel", "getHomeStats: FAILED",error )
            }
        queue.add(request)
    }
}