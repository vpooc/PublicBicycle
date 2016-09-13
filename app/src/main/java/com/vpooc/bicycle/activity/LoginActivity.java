package com.vpooc.bicycle.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.vpooc.bicycle.CustomUserProvider;
import com.vpooc.bicycle.Modle.LoginModle.ILoginModle;
import com.vpooc.bicycle.Modle.LoginModle.Impl.LoginModle;
import com.vpooc.bicycle.R;
import com.vpooc.bicycle.app.Application;

import cn.leancloud.chatkit.LCChatKit;

public class LoginActivity extends Activity {
    private static EditText etPasswordLogin;
    private static EditText etNameLogin;
    private static Button btLogin;
    private static Button btForget;
    private static Button btRegister;
    private ImageView ivProgress;
    private LinearLayout loginLL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        etPasswordLogin = (EditText)findViewById(R.id.password);
        etNameLogin = (EditText) findViewById(R.id.user_name);
//        btLogin = (Button)findViewById(R.id.activity_login_btn_login);
//        btForget = (Button)findViewById(R.id.forgetPwd);
//        btRegister = (Button)findViewById(R.id.register);
        ivProgress = (ImageView) findViewById(R.id.progress_login);
        loginLL = (LinearLayout) findViewById(R.id.activity_login_ll);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_btn_login:
                loginLL.setVisibility(View.INVISIBLE);
                ivProgress.setVisibility(View.VISIBLE);
                Animation rotation=new RotateAnimation(this,null);
                rotation.setDuration(1000);
                ivProgress.setAnimation(rotation);
                String name = etNameLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();
                login(name,password);
                break;
            // TODO: 2016/8/5
            case R.id.forgetPwd:
                break;
            case R.id.register:
                break;

        }
    }

    private void login(final String userName,String userPWD) {
        new LoginModle().loginOnNameAndPassword(userName, userPWD, new ILoginModle.OnLogInCallback() {
            @Override
            public Void loginDone(AVUser user, AVException e) {
                //返回为null，即为login成功
                if (null == e) {
                    //登录成功后获取LCChatKit实例
                    LCChatKit.getInstance().open(userName, new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVIMException e) {
                            if (null == e) {
                                Log.d("LCChatKit.getInstance", "成功");
                                Application.mClient.setClientID(userName);
                                LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
                                LoginActivity.this.finish();
                            } else {
                                Log.d("LCChatKit.getInstance", "失败" + e.toString());
                            }
                        }
                    });
                } else {
                    ivProgress.setVisibility(View.INVISIBLE);
                    loginLL.setVisibility(View.VISIBLE);
                }
                return null;
            }
        });
    }



/*    //注册
    private static void showRegisterDialog(final Context context) {

        final LoginModle loginModle = new LoginModle();
        final MaterialDialog dialog = new MaterialDialog.Builder(context).title("注册")
                .customView(R.layout.dialog_customview_regi, true).positiveText("确认注册")
                .negativeText(android.R.string.cancel)
                .onPositive(
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //确认按钮
                                final String rname = RnameInput.getText().toString();
                                final String rpassword = RpasswordInput.getText().toString();
                                final String emall = emaillInput.getText().toString();
                                String rPhone = phoneInput.getText().toString();
                                String code = etCode.getText().toString();
                                //验证码判断
                                if (code != null) {
                                    loginModle.loginOnNubmerAndMobileCode(rPhone, code, new ILoginModle.OnLogInCallback() {
                                                @Override
                                                public Void loginDone(AVUser user, AVException e) {

                                                    if (e == null) {
                                                        user.setPassword(rpassword);
                                                        user.setEmail(emall);
                                                        user.saveInBackground();
                                                        Log.d("Dialog ", "登录成功");
                                                        LCChatKit.getInstance().open(rname, new AVIMClientCallback() {
                                                            @Override
                                                            public void done(AVIMClient avimClient, AVIMException e) {
                                                                if (null == e) {
                                                                    Log.d("LCChatKit.getInstance", "成功");
                                                                    LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
                                                                } else {
                                                                    Log.d("LCChatKit.getInstance", "失败" + e.toString());
                                                                }
                                                            }
                                                        });
                                                    } else {

                                                    }
                                                    return null;
                                                }
                                            }

                                    );
                                } else {
                                    Toast.makeText(context, "验证码不能为空", Toast.LENGTH_LONG);
                                }
                            }
                        }

                ).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {     //取消  按钮
                        dialog.cancel();
                    }
                }).build();
        RpasswordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
        RnameInput = (EditText) dialog.getCustomView().findViewById(R.id.user_name);
        emaillInput = (EditText) dialog.getCustomView().findViewById(R.id.emall);
        phoneInput = (EditText) dialog.getCustomView().findViewById(R.id.user_phone);
        etCode = (EditText) dialog.getCustomView().findViewById(R.id.dialog_tv_code);
        btnCode = (Button) dialog.getCustomView().findViewById(R.id.dialog_btn_code);
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("获取验证码", "点击");
                loginModle.getSMSCode(phoneInput.getText().toString(), new ILoginModle.OnMobileCodeCallback() {
                    @Override
                    public void onGetSMSCode(AVException e) {
                        if (e != null) {
                            Log.d("获取验证码失败", e.toString());
                        }
                    }
                });
            }
        });

        dialog.show();*/
}
