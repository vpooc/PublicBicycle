package com.vpooc.bicycle.Modle.LoginModle.Impl;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SignUpCallback;
import com.vpooc.bicycle.Modle.LoginModle.ILoginModle;

import java.util.logging.LoggingMXBean;

/**
 * Created by Administrator on 2016/5/19.
 */
public class LoginModle implements ILoginModle {


    @Override
    public void getSMSCode(String number, final OnMobileCodeCallback mobileCodeCallback) {
        AVOSCloud.requestSMSCodeInBackground(number, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                // 发送失败可以查看 e 里面提供的信息
                mobileCodeCallback.onGetSMSCode(e);
            }
        });
    }

    @Override
    public void loginOnNubmerAndMobileCode(String number, String code, final OnLogInCallback onLogInCallback) {
        AVUser.signUpOrLoginByMobilePhoneInBackground(number, code, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                // 如果 e 为空就可以表示登录成功了，并且 user 是一个全新的用户
                onLogInCallback.loginDone(avUser, e);
            }
        });
    }

    @Override
    public void SignUp(String name, String password, String email, final OnSignUpCallback OnSignUpCallback) {

        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(name);// 设置用户名
        user.setPassword(password);// 设置密码
        user.setEmail(email);// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    OnSignUpCallback.OnSignUp(null);
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    OnSignUpCallback.OnSignUp(e);
                }
            }
        });
    }

    @Override
    public void loginOnNameAndPassword(String name, String password, final OnLogInCallback OnLogInCallback) {
        AVUser.logInInBackground(name, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                OnLogInCallback.loginDone(avUser, e);
            }
        });
    }

    @Override
    public void loginOnNumberAndPassword(String number, String password, final OnLogInCallback OnLogInCallback) {
        AVUser.loginByMobilePhoneNumberInBackground(number, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                OnLogInCallback.loginDone(avUser, e);
            }
        });
    }

    @Override
    public void requestPasswordResetByEmail(String email) {
        AVUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("LoginModle", "邮件发送成功");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void logOut() {
        AVUser.logOut();// 清除缓存用户对象
        AVUser currentUser = AVUser.getCurrentUser();// 现在的 currentUser 是 null 了
    }
}
