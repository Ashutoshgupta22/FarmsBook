package com.farmsbook.farmsbook.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class UploadManager {

    private static final String TAG = UploadManager.class.getSimpleName();

    private Context context;
    private RequestQueue requestQueue;

    public UploadManager(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }


    public void uploadFormData(String url,File imageFile) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        FileBody fileBody = new FileBody(imageFile, ContentType.IMAGE_JPEG);
        builder.addPart("imageFile", fileBody);

        HttpEntity entity = builder.build();

        CustomMultipartRequest customRequest = new CustomMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String responseBody = new String(response.data);
                        System.out.println(responseBody);
                        Log.d("Profile", "Upload success. Response: " + response.toString());
                        // Handle the response
//                        Context context = new LoginActivity();
//                        SharedPreferences sharedPref =  context.getSharedPreferences("pref", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
            //editor.putInt("USER_ID",response["id"].toString().toInt());
           // editor?.putBoolean("USER_ROLE",it["role"].toString().toBoolean())
//         editor.apply();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Upload error: " + error.toString());
                        // Handle the error
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return entity.getContentType().getValue();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    entity.writeTo(bos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bos.toByteArray();
            }

            @Override
            protected void deliverResponse(NetworkResponse response) {

            }
        };

        customRequest.setRetryPolicy(new DefaultRetryPolicy(
                0, // No retries
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(context, new HurlStack()).add(customRequest);
    }

    static abstract class CustomMultipartRequest extends Request<NetworkResponse> {
        public CustomMultipartRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
        }

        @Override
        protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
            return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
        }
    }
}
