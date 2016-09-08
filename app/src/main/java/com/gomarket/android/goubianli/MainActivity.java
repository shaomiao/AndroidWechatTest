package com.gomarket.android.goubianli;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
    private IWXAPI api;
    private final OkHttpClient client = new OkHttpClient();
    private void regToWx(){
        api= WXAPIFactory.createWXAPI(this,ConfigUtil.Wechat_Appid,true);
        api.registerApp(ConfigUtil.Wechat_Appid);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册微信
        regToWx();
        findViewById(R.id.wechat_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
            }
        });
    }


}
