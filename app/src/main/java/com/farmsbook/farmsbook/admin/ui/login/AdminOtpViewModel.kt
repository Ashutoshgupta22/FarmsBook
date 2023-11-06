package com.farmsbook.farmsbook.admin.ui.login

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
import com.farmsbook.farmsbook.admin.AdminData.Companion.currentAdmin
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class AdminOtpViewModel: ViewModel() {

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private var _valid = MutableLiveData<Boolean>()
    val valid: LiveData<Boolean> = _valid

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun validate(context: Context, phone: String, session: String, otp: String ) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/auth/validate"

        val loginObj = JSONObject()
        loginObj.put("phoneNumber", phone)
        loginObj.put("sessionId", session)
        loginObj.put("otpCode", otp)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url, loginObj , { response: JSONObject ->

                val isValid = response.getBoolean("valid")

                if (isValid) {
                    _valid.postValue(true)
                }
                else _error.postValue("Invalid OTP")

            } ) {error: VolleyError ->
            _error.postValue("Something went wrong!")
            Log.e("AdminOtpViewModel", "validate: FAILED",error )
        }
        queue.add(request)

    }
}