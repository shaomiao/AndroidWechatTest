package com.gomarket.android.goubianli.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gomarket.android.goubianli.ConfigUtil;
import com.gomarket.android.goubianli.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shaomiao on 2016-9-7.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private final OkHttpClient client = new OkHttpClient();
    private final String TAG = "aaa";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getApplicationContext(), "恭喜你onCreate", Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        IWXAPI api = WXAPIFactory.createWXAPI(this, ConfigUtil.Wechat_Appid, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Toast.makeText(getApplicationContext(), "恭喜你onReq", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Bundle bundle = new Bundle();
        Toast.makeText(getApplicationContext(), "恭喜你onResp", Toast.LENGTH_LONG).show();
        switch (baseResp.errCode) {
            //用户同意：
            case BaseResp.ErrCode.ERR_OK:
                //      可用以下两种方法获得code
                //      resp.toBundle(bundle);
                //      Resp sp = new Resp(bundle);
                //      String code = sp.code;
                //      或者
                String code = ((SendAuth.Resp) baseResp).code;
                String state = ((SendAuth.Resp) baseResp).state;
                Toast.makeText(getApplicationContext(), "恭喜这是你的code" + code, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onResp: " + code);
                try {
                    getAccess_token(code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //上面的code就是接入指南里要拿到的code
                break;
            //用户拒绝授权：
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            //用户取消：
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            default:
                break;
        }

    }

    /**
     * 获取accesstoken
     *
     * @param code
     */
    private void getAccess_token(String code) throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + ConfigUtil.Wechat_Appid +
                "&secret=" + ConfigUtil.Wechat_AppSecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        run(url);
    }

    public void run(String url) throws Exception {
        final Request request = new Request.Builder()
                //.url("http://publicobject.com/helloworld.txt")
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                try {
                    String responseStr=response.body().string();
                    JSONObject jsonObject = new JSONObject(responseStr);
                    System.out.println("aaaaa" + jsonObject.getString("access_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }



}
