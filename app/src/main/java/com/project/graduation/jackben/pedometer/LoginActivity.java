package com.project.graduation.jackben.pedometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.graduation.jackben.pedometer.beans.UserBean;
import com.project.graduation.jackben.pedometer.db.PedometerDbUtil;
import com.project.graduation.jackben.pedometer.utils.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private LinearLayout login_qq;
    private LinearLayout login_sina;
    private Tencent mTencent;
    private IUiListener loginListener;
    private AuthInfo mAuthInfo;
    private PedometerDbUtil dbUtil;
    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;
    private UserBean user;
    private  String openid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbUtil = PedometerDbUtil.getInstance(this);


        login_qq = (LinearLayout) findViewById(R.id.login_qq);
        login_sina = (LinearLayout) findViewById(R.id.login_sina);
        login_qq.setOnClickListener(this);
        login_sina.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_qq:
                qqLogin();
                break;
            case R.id.login_sina:
//                sinaLogin();
                break;
        }

    }

    private void sinaLogin() {
        mAuthInfo = new AuthInfo(this, Constants.SINA_APP_KEY, Constants.SINA_REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
        mSsoHandler.authorize(new SinaAuthListener());
    }

    private void sinaLoginOut() {
        mAccessToken = new Oauth2AccessToken();
    }

    class SinaAuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle bundle) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
            String uid = mAccessToken.getUid();
            Log.i(TAG, "uid:" + uid);
            if (mAccessToken.isSessionValid()) {
                //保存用户信息
                if (uid != null) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = bundle.getString("code");
                Log.i(TAG, "code:" + code);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }

        @Override
        public void onCancel() {

        }
    }

    public void qqLogin() {
        mTencent = Tencent.createInstance("1105282111", this.getApplicationContext());
        loginListener = new QQBaseListener();
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }

    public void qqLogout() {
        mTencent.logout(this);
    }

    public void qqGetUserInfo() {
        UserInfo info = new UserInfo(this, mTencent.getQQToken());
        info.getUserInfo(new userDataListener());
    }

    private class userDataListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            JSONObject userdata = ((JSONObject) o);
            Log.i(TAG, "MAG:" + userdata.toString());
            String nickName=userdata.optString("nickname");
            user.setUserName(nickName==null?"用户"+openid:nickName);
            user.setUserSex(userdata.optString("gender"));//性别
            user.setUserPic(userdata.optString("figureurl"));//头像
            user.setUserStepLength((int) 0.8);
            user.setUserHeight(170);
            user.setUserWeight(50);
            dbUtil.insertUserInfo(user);
            if (dbUtil.queryUserInfo().getUserId() != null) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "获取资料出错", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {

        }
    }

    private class QQBaseListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            JSONObject userData = ((JSONObject) o);
             openid = userData.optString("openid");
            Log.i(TAG, "MAG:" + userData.toString());
            user = new UserBean();
            user.setUserId(openid);
            qqGetUserInfo();

        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "登录出错", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "登录取消", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
