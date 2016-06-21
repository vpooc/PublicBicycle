package com.vpooc.bicycle.Modle.bicycleModle.Impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vpooc.bicycle.Modle.bicycleModle.AbsBicycleRequst;
import com.vpooc.bicycle.Modle.bicycleModle.IBicycleModle;
import com.vpooc.bicycle.R;
import com.vpooc.bicycle.app.Application;
import com.vpooc.bicycle.entity.BicycleInfomation;
import com.vpooc.bicycle.utils.Const;
import com.vpooc.bicycle.utils.ParserJsonDataUtil;

//import org.apache.http.Header;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2016/5/19.
 */

public class BicycleModle implements IBicycleModle {
    private Context context;
    private Handler handler;
    private RequestQueue requestQueue;
    private IBicycleModle.StateListCallback stateListCallback;
    protected List<BicycleInfomation> bicycleInfomation;
    protected Bitmap bm;

    public BicycleModle(Context context, Handler handler, RequestQueue requestQueue) {
        super();
        this.context = context;
        this.handler = handler;
        this.requestQueue = requestQueue;
    }

    private void getStateList_(String district, String state) {

        AbsBicycleRequst abs = new AbsBicycleRequst(requestQueue) {

            @Override
            public void SuccessResponse(String response) {
                // TODO Auto-generated method stub
                log("返回结果 ", response);
                bicycleInfomation = ParserJsonDataUtil.Parser(response);
                log("解析完成后的列表数据", bicycleInfomation.size());
                stateListCallback.OnGetStateList(bicycleInfomation);
            }

            @Override
            public void ErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                log("HttpDataModule", "请求失败");
            }
        };

        abs.setRequestQueue(requestQueue);
        abs.getBicycleInfomation(district, state);

    }

    @Override
    public void getStateList(String district, String state, StateListCallback stateListCallback) {
        // TODO Auto-generated method stub
        this.stateListCallback = stateListCallback;
        getStateList_(district, state);

    }

    @Override
    public void getStateDetail(final BicycleInfomation state, final GifImageView ivGet, final GifImageView ivStop) {

        Request requestGet = new ImageRequest(state.getGet(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                ivGet.setImageBitmap(bitmap);
            }
        }, 60, 60, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("请求图片数据出错", "");
            }
        });
        requestGet.setTag(Const.REQUEST_TAG);
        Application.requestQueue.add(requestGet);

        Request requestStop = new ImageRequest(state.getGet(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                ivGet.setImageBitmap(bitmap);
            }
        }, 60, 60, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("请求图片数据出错", "");
            }
        });
        requestStop.setTag(Const.REQUEST_TAG);
        Application.requestQueue.add(requestStop);

    }

    //图片缓存（volley)
    private class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 2 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {

                @Override
                protected int sizeOf(String key, Bitmap value) {
                    // TODO Auto-generated method stub
                    return value.getHeight() * value.getWidth();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            // TODO Auto-generated method stub
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            // TODO Auto-generated method stub

            mCache.put(url, bitmap);
        }

    }

    private void log(String msg, Object param) {

        Log.d("HttpDataModule", msg + param.toString());

    }

}
