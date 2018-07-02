package com.example.cheng.testweixinsign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.rongyu.enterprisehouse100.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button pay;
    private TextView value;
    private IWXAPI api;
    private PayReq req = new PayReq();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = WXAPIFactory.createWXAPI(this, "wxd58b5e96b2658231");
        pay = findViewById(R.id.pay);
        value = findViewById(R.id.value);
        pay.setOnClickListener(this);

// "appid": "wxd58b5e96b2658231"
// "partnerid": "1488787972"
// "prepayid": "wx201710251029435442477c220119774578"
// "noncestr": "541db3c9de90cb178316"
// "timestamp": "1508898583"
// "package": "Sign=WXPay"
// "sign": "E4385CD99B2F341145BF3B0C38A157F3"

        req.appId = "wxd58b5e96b2658231";
        req.partnerId = "1488787972";
        req.prepayId = "wx2017102510333899f5448f1d0409212373";
        req.packageValue = "Sign=WXPay";
        req.nonceStr = "ca13b40147769955daac";
        req.timeStamp = "1508898818";
        req.packageValue = "Sign=WXPay";
        req.sign = "78A1F3D746608044BB2721A6DD9CE0FE";
        //微信签名测试
        List<NameValuePair> signParams = new LinkedList<>();
        signParams.add(new NameValuePair("appid", req.appId));
        signParams.add(new NameValuePair("noncestr", req.nonceStr));
        signParams.add(new NameValuePair("package", req.packageValue));
        signParams.add(new NameValuePair("partnerid", req.partnerId));
        signParams.add(new NameValuePair("prepayid", req.prepayId));
        signParams.add(new NameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);

        StringBuffer sb = new StringBuffer();
        sb.append("appId：" + req.appId + "\n");
        sb.append("partnerId：" + req.partnerId + "\n");
        sb.append("prepayId：" + req.prepayId + "\n");
        sb.append("packageValue：" + req.packageValue + "\n");
        sb.append("nonceStr：" + req.nonceStr + "\n");
        sb.append("timeStamp：" + req.timeStamp + "\n");
        sb.append("sign：" + req.sign + "\n");

        value.setText(sb.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pay:
                Log.i("111", "微信支付");
                startWinXinPay();
                break;
        }
    }

    private void startWinXinPay(){
        boolean b = api.sendReq(req);
        if(!b){
            Log.i("111", "调用微信支付失败");
        }else{
            Log.i("111", "调用微信支付成功");
        }
    }

    public class NameValuePair{
        public String name;
        public String value;
        public NameValuePair(String name, String value){
            this.name = name;
            this.value = value;
        }
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).name);
            sb.append('=');
            sb.append(params.get(i).value);
            sb.append('&');
        }
        sb.append("key=");
        sb.append("094c369ceacfcbadcec188736f0185e3");
        String appSign = md5(sb.toString()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
