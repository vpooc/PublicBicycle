package com.vpooc.bicycle.Modle.bicycleModle;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.vpooc.bicycle.utils.Const;
import com.vpooc.bicycle.utils.URLUtil;

public abstract class AbsBicycleRequst {

    private RequestQueue requestQueue;

    public AbsBicycleRequst(RequestQueue requestQueue) {
        // TODO Auto-generated constructor stub
        this.requestQueue = requestQueue;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public abstract void SuccessResponse(String response);

    public abstract void ErrorResponse(VolleyError error);

    public void getBicycleInfomation(String district, final String state)

    {

        String url = URLUtil.getStateInfomation(district);

        Listener<String> listener = new Listener<String>() {

            @Override
            public void onResponse(String response) {
                log("����ɹ���", response);
                SuccessResponse(response);

            }
        };
        ErrorListener errorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                log("����ʧ��", "");
                ErrorResponse(error);
            }
        };
        StringRequest request = new StringRequest(Method.POST, url, listener,
                errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // TODO Auto-generated method stub
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", URLUtil.key);
                map.put("state", state);
                return map;

            }
        };

        requestQueue.cancelAll(Const.REQUEST_TAG);
        requestQueue.add(request);

    }

    private void log(String msg, String param) {

        Log.d("AbsBicycleRequst", msg + param);

    }
}