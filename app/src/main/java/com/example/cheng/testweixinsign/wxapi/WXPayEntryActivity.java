package com.example.cheng.testweixinsign.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, "wxd58b5e96b2658231");
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

//	0 支付成功
//	-1 发生错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//	-2 用户取消 发生场景：用户不支付了，点击取消，返回APP。
	@Override
	public void onResp(BaseResp resp) {
		if(resp.errCode==0){
			Toast.makeText(this, "支付成功，请稍后确认订单详情", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "支付失败，请重新尝试或联系客服", Toast.LENGTH_SHORT).show();
		}
		finish();
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

}