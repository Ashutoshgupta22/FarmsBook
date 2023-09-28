package com.farmsbook.farmsbook.admin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.farmsbook.farmsbook.admin.AdminData.Companion.currentAdmin
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray

class AdminMainViewModel: ViewModel() {

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun getAdminData(context: Context, phone: String) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url, null , { response: JSONArray ->

                for (i in 0 until response.length()) {
                    val admin = response.getJSONObject(i)

                    if (admin.getString("phone") == phone) {
                        currentAdmin.id = admin.getInt("id")
                        currentAdmin.name = admin.getString("name")
                        currentAdmin.email = admin.getString("email")
                        currentAdmin.password = admin.getString("password")
                        currentAdmin.newPassword = admin.getString("newPassword")
                        currentAdmin.phone = admin.getString("phone")
                        currentAdmin.location = admin.getString("location")
                        currentAdmin.imagePath = admin.getString("imagePath")
                        // currentAdmin.crops = admin.get("crops")
                        break

                    }
                }


            } ) {error: VolleyError ->
            Log.e("AdminMainViewModel", "getAdminData: FAILED",error )
        }
        queue.add(request)
    }
}