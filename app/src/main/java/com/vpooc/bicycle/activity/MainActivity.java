package com.vpooc.bicycle.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.vpooc.bicycle.Modle.LoginModle.ILoginModle;
import com.vpooc.bicycle.Modle.LoginModle.Impl.LoginModle;
import com.vpooc.bicycle.R;
import com.vpooc.bicycle.app.Application;
import com.vpooc.bicycle.presenter.Presenter;
import com.vpooc.bicycle.utils.CircleImageView;

public class MainActivity extends Activity {
    private CircleImageView cirPerson, cirMessage;
    private Button btnStart;
    private LinearLayout mHint;// 隐藏布局
    private BaiduMap mMap;
    private MapView mMapView;
    private Button btnSOUSHUO;
    private EditText etSOUSHUO;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();
//
//        AVOSCloud.initialize(this, "rJqDhpg8TkD65RtmSo0gMdow-gzGzoHsz", "VtlXXWrgs0L1NdgK7Lw0fnju");
//        LoginModle login = new LoginModle();
//        login.getSMSCode("18328073673", new ILoginModle.OnMobileCodeCallback() {
//            @Override
//            public void onGetSMSCode(AVException e) {
//                if (e != null) {
//                    Log.d("", e.toString());
//                }
//            }
//        });

}


    private void initData() {
        presenter = new Presenter(this, new com.vpooc.bicycle.View.MapView(getBaseContext(), mMap));

    }


    private void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] items = {"当前位置", "查询位置"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(MainActivity.this, "出发地点", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "查询地点", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void initView() {
        cirPerson = (CircleImageView) findViewById(R.id.cir_didi_person);
        cirMessage = (CircleImageView) findViewById(R.id.cir_didi_message);
        mHint = (LinearLayout) findViewById(R.id.ll_didi_menu);
        btnStart = (Button) findViewById(R.id.bt_didi_start);
        // 获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mMap = mMapView.getMap();
        Application app= (Application) getApplication();
        app.setInstanceMap(mMap);
        btnSOUSHUO = (Button) findViewById(R.id.ccx_soushuo_btn);
        etSOUSHUO = (EditText) findViewById(R.id.ccx_soushuo_et);

    }

    private void setListener() {
        // 搜索测试区
        btnSOUSHUO.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String state = etSOUSHUO.getText().toString();
                presenter.getStateList("sz", "广场");
            }
        });

        // 搜索测试区结束


        cirPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHint.setVisibility(View.VISIBLE);
                TranslateAnimation anim = new TranslateAnimation(-mHint.getWidth(), 0, 0, 0);
                anim.setDuration(500);
                mHint.setAnimation(anim);
            }
        });

        cirMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_didi_start:
                        setDialog();
                        break;
                }
            }
        });
    }

    public void doClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.rb_didi_history:
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_didi_message:
                intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_didi_setting:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    // //处理就是点击返回键的业务
    @Override
    public void onBackPressed() {
        if (mHint.getVisibility() == View.VISIBLE) {
            System.out.println("sasda");
            TranslateAnimation anim = new TranslateAnimation(0, -mHint.getWidth(), 0, 0);
            anim.setDuration(500);
            mHint.setAnimation(anim);
            mHint.setVisibility(View.INVISIBLE);
        } else {
            System.out.println("hjjjj");
            // 隐藏布局是不可见的状态 则开辟一个新的任务栈回到主页
            Intent in = new Intent(Intent.ACTION_MAIN);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            in.addCategory(Intent.CATEGORY_HOME);
            startActivity(in);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub


        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mMapView.onStartTemporaryDetach();
    }


}
