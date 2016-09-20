package com.gomarket.android.goubianli;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MainActivity extends Activity implements View.OnClickListener {
    private IWXAPI api;
    private Button btnWechatLogin;
    private Button btnWechatPay;
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
        initView();
    }
    private void initView(){
        btnWechatLogin= (Button) findViewById(R.id.wechat_login);
        btnWechatPay= (Button) findViewById(R.id.wechat_pay);
        btnWechatLogin.setOnClickListener(this);
        btnWechatPay.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wechat_login:
                //登录
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
                break;
            case R.id.wechat_pay:
                PayReq request = new PayReq();
                request.appId = ConfigUtil.Wechat_Appid;
                request.partnerId = "1305176001";
                request.prepayId= "wx201609201413082e4dd0949a0514279401";
                request.packageValue = "Sign=WXPay";
                request.nonceStr= "eea571b0d5df427954b3e03508e13878";
                request.timeStamp= "1474351988";
                request.sign= "001324C12292C47F1CA2EA5125C7A39F";
                api.sendReq(request);
                break;
        }
    }
}
