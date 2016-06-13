package com.vpooc.bicycle.app;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.vpooc.bicycle.Other.AVImClientManager;
import com.vpooc.bicycle.Other.MessageHandler;

/**
 * Created by Administrator on 2016/5/26.
 */
public class Application extends android.app.Application {
    private static Context context;
    public static RequestQueue requestQueue;
    private static BaiduMap map;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化百度地图
        SDKInitializer.initialize(getApplicationContext());

        //初始化cleanCloud
        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVOSCloud.initialize(this, "rJqDhpg8TkD65RtmSo0gMdow-gzGzoHsz",
                "VtlXXWrgs0L1NdgK7Lw0fnju");
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));

        AVImClientManager.getInstance().open("Tom", new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                Log.d("application", "创建客户端 Tom");
            }
        });
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    public static BaiduMap getInstanceMap(){
        return map;
    }

    public  void setInstanceMap(BaiduMap mMap) {
        map = mMap;
    }
    public static Context getContext() {

        return context;
    }

    public RequestQueue getRrequestQueue() {
        return requestQueue;
    }
}
