package com.vpooc.bicycle.app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.avos.avoscloud.AVOSCloud;

import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.vpooc.bicycle.CustomUserProvider;
import com.vpooc.bicycle.activity.MainActivity;
import com.vpooc.bicycle.entity.ClientUser;
import com.vpooc.bicycle.utils.LoginDialogUtil;

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
    private final String clientId = "keke";
    private static ClientUser mClient;


    @Override
    public void onCreate() {
        super.onCreate();
        mClient = new ClientUser();
        mClient.setClientID(clientId);
        //初始化百度地图
        SDKInitializer.initialize(getApplicationContext());

        //初始化cleanCloud
        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理

        AVOSCloud.setDebugLogEnabled(true);
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
