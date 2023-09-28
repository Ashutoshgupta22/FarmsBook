package com.farmsbook.farmsbook.admin.ui.cropslistings

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
import com.farmsbook.farmsbook.admin.ui.home.Stat
import com.farmsbook.farmsbook.utility.BaseAddressUrl
import org.json.JSONArray
import org.json.JSONObject

class CropsFragmentViewModel: ViewModel() {

    private var _cropAdded = MutableLiveData<Boolean>()
    val cropAdded: LiveData<Boolean> = _cropAdded
    private var _myCrops = MutableLiveData<ArrayList<CropData>>()
    val myCrops: LiveData<ArrayList<CropData>> = _myCrops
    private var _allCrops = MutableLiveData<ArrayList<CropData>>()
    val allCrops: LiveData<ArrayList<CropData>> = _allCrops

    private val baseUrl = BaseAddressUrl().baseAddressUrl

    fun addCrop(context: Context, cropName: String, adminId: Int) {

        val queue = Volley.newRequestQueue(context)
        //val url = "$baseUrl/admin/{adminId}/createCrops"
        val url = "$baseUrl/admin/$adminId/createCrops"

        val cropObj = JSONObject()
        cropObj.put("parentId", adminId)
        cropObj.put("cropName", cropName)

            val request = JsonObjectRequest(
                Request.Method.POST,
                url, cropObj, { response: JSONObject ->

                    _cropAdded.postValue(true)

                } ) {error: VolleyError ->
                Log.e("CropsFragmentViewModel", "addCrop: FAILED",error )
            }
        queue.add(request)
    }

    fun getMyCrops(context: Context, adminId: Int) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/$adminId/adminCropList"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url, null, { response: JSONArray ->

                val cropList = arrayListOf<CropData>()

                for (i in 0 until response.length()) {

                    val cropObj = response.getJSONObject(i)
                    val id = cropObj.getInt("cropId")
                    val parentId = cropObj.getInt("parentId")
                    val name = cropObj.getString("cropName")
                    val status = cropObj.getString("status")
                    val imagePath = cropObj.getString("imagePath")

                    cropList.add( CropData(id, parentId, name, status, imagePath))
                }

                _myCrops.postValue(cropList)

            } ) {error: VolleyError ->
            Log.e("CropsFragmentViewModel", "getMyCrops: FAILED",error )
        }
        queue.add(request)
    }

    fun getAllCrops(context: Context?) {

        val queue = Volley.newRequestQueue(context)
        val url = "$baseUrl/admin/allCrops"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url, null, { response: JSONArray ->

                val cropList = arrayListOf<CropData>()

                for (i in 0 until response.length()) {

                    val cropObj = response.getJSONObject(i)
                    val id = cropObj.getInt("cropId")
                    val parentId = cropObj.getInt("parentId")
                    val name = cropObj.getString("cropName")
                    val status = cropObj.getString("status")
                    val imagePath = cropObj.getString("imagePath")

                    cropList.add( CropData(id, parentId, name, status, imagePath))
                }

                _allCrops.postValue(cropList)

            } ) {error: VolleyError ->
            Log.e("CropsFragmentViewModel", "getAllCrops: FAILED",error )
        }
        queue.add(request)

    }

}
