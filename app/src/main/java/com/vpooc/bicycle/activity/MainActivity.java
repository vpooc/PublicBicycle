package com.vpooc.bicycle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vpooc.bicycle.R;
import com.vpooc.bicycle.utils.LoginDialogUtil;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MainActivity extends android.app.TabActivity{

    private TabHost mTabHost;
    private Intent mMainIntent;
    private Intent mContactIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareIntent();
        setupIntent();
        LoginDialogUtil.showLoginDialog(this);

    }

    private void prepareIntent()
    {
        this.mMainIntent = new Intent(this, MapActivity.class);
        this.mContactIntent = new Intent(this, ContactActivity.class);
    }

    private void setupIntent()
    {
        this.mTabHost = getTabHost();
        TabHost localTabHost = this.mTabHost;
        localTabHost.addTab(buildTabSpec("tab_tag_main",null, R.drawable.icon_mark_pt , this.mMainIntent));
        localTabHost.addTab(buildTabSpec("tab_tag_contact", null,R.drawable.btn_square_members, this.mContactIntent));

        this.mTabHost.setCurrentTabByTag("tab_tag_main");
    }

    private TabHost.TabSpec buildTabSpec(String paramString, String paramInt1, int paramInt2, Intent paramIntent)
    {
        return this.mTabHost.newTabSpec(paramString).setIndicator(paramInt1, getResources().getDrawable(paramInt2)).setContent(paramIntent);
    }
}
