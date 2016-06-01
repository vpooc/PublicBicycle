package com.vpooc.bicycle.Modle.LoginModle;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface ILoginModle {
    /**
     * 获取手机验证码
     *
     * @param number             手机号码
     * @param mobileCodeCallback 回调
     */
    void getSMSCode(String number, OnMobileCodeCallback mobileCodeCallback);

    /**
     * 接收验证时的回调接口
     */
    interface OnMobileCodeCallback {
        void onGetSMSCode(AVException e);
    }

    /**
     * 以手机验证随机方式登录
     *
     * @param number          手机号码
     * @param code            验证码
     * @param OnLogInCallback 回调
     */
    void loginOnNubmerAndMobileCode(String number, String code, OnLogInCallback OnLogInCallback);

    /**
     * 登录时的回调接口
     */
    interface OnLogInCallback {
        Void loginDone(AVUser user, AVException e);
    }

    /**
     * 注册
     *
     * @param name             姓名
     * @param password         密码
     * @param email            邮箱
     * @param OnSignUpCallback 回调
     */
    void SignUp(String name, String password, String email, OnSignUpCallback OnSignUpCallback);

    /**
     * 发起注册后的回调
     */
    interface OnSignUpCallback {

        void OnSignUp(AVException e);

    }

    /**
     * 用户，密码方式登录
     *
     * @param name            姓名
     * @param password        密码
     * @param OnLogInCallback 回调
     */

    void loginOnNameAndPassword(String name, String password, OnLogInCallback OnLogInCallback);


    /**
     * @param number          手机号码
     * @param password        密码
     * @param OnLogInCallback 回调
     */
    void loginOnNumberAndPassword(String number, String password, OnLogInCallback OnLogInCallback);

    /**
     * 邮箱重置密码
     *
     * @param email 邮箱地址
     */
    void requestPasswordResetByEmail(String email);

    /**
     * 用户登出系统，SDK 会自动的清理缓存信息。
     */
    void logOut();


}
