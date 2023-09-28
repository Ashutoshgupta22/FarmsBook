package com.farmsbook.farmsbook.admin.ui.login

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

class AdminLoginViewModel: ViewModel() {

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private var _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String> = _sessionId

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun authenticate(email: String, password: String,
                     phone: String ,context: Context) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/auth"

        val loginObj = JSONObject()
        loginObj.put("email", email)
        loginObj.put("password", password)

        val request = JsonObjectRequest(
                Request.Method.POST,
                url, loginObj , { response: JSONObject ->

                if (response.getBoolean("emailStatus")) {
                    _sessionId.postValue(response.getString("session"))
                    sendOtp(context, phone)
                }
                else _error.postValue("Incorrect username or password")

                } ) {error: VolleyError ->

            _error.postValue("Something went wrong!")
            Log.e("AdminLoginViewModel", "authenticate: FAILED",error )
        }
        queue.add(request)
    }

    private fun sendOtp(context: Context, phone: String) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/otp"

        val otpObj = JSONObject()
        otpObj.put("phoneNumber", phone)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url, otpObj , { response: JSONObject ->


            } ) {error: VolleyError ->

            _error.postValue("Something went wrong!")
            Log.e("AdminLoginViewModel", "sendOtp: FAILED",error )
        }
        queue.add(request)
    }
}