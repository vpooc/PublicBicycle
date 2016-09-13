package com.vpooc.bicycle.app;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.avos.avoscloud.AVOSCloud;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.vpooc.bicycle.entity.ClientUser;

import cn.leancloud.chatkit.LCChatKit;


/**
 * Created by Administrator on 2016/5/26.
 */
public class Application extends android.app.Application {
    private static Context context;
    public static RequestQueue requestQueue;
    private static BaiduMap map;
    private final String APP_ID = "rJqDhpg8TkD65RtmSo0gMdow-gzGzoHsz";
    private final String APP_KEY = "VtlXXWrgs0L1NdgK7Lw0fnju";
    public static ClientUser mClient;


    @Override
    public void onCreate() {
        super.onCreate();
        mClient = new ClientUser();
        //初始化百度地图
        SDKInitializer.initialize(getApplicationContext());

        //初始化cleanCloud
        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理

        AVOSCloud.setDebugLogEnabled(false);
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static BaiduMap getInstanceMap() {
        return map;
    }

    public void setInstanceMap(BaiduMap mMap) {
        map = mMap;
    }

    public static Context getContext() {

        return context;
    }

    public static ClientUser getClientUser() {
        return mClient;
    }

    public RequestQueue getRrequestQueue() {
        return requestQueue;
    }


}
