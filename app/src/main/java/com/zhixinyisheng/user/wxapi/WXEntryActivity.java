package com.zhixinyisheng.user.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	IWXAPI api;

	private void handleIntent(Intent intent) {
		api.handleIntent(intent, this);
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, "wx54a16495034d3dd2");
		handleIntent(getIntent());
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub

		Bundle bundle = new Bundle();
		Log.e("errCode 42",resp.errCode+"");
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			// 分享收藏的回调方法
			if (resp instanceof SendMessageToWX.Resp) {
				
//				Intent intent = new Intent(this, MainActivity.class);
//				startActivity(intent);
				finish();

			} else {
				// 微信第三方登录的回调方法
				// 获取code,方便以后获取临时票据access_token

				resp.toBundle(bundle);
				Resp sp = new Resp(bundle);
				String code = sp.code;
				Log.e("code", code);

//				 getResult(code);

			}
			break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				finish();
				break;

		default:
			break;
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}

}
