package com.vpooc.bicycle.app;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2016/5/26.
 */
public class Application extends android.app.Application {
    private static Context context;
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static Context getContext() {

        return context;
    }

    public RequestQueue getRrequestQueue() {
        return requestQueue;
    }
}
