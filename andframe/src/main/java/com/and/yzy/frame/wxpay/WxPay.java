package com.and.yzy.frame.wxpay;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.and.yzy.frame.util.Toolkit;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 微信支付相关类
 */
public class WxPay {

	private static final String TAG = "MicroMsg.SDKSample.PayActivity";

	PayReq req;
	final IWXAPI msgApi;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;

	/** 购买商品名 */
	private String body;

	/** 商品价格 */
	private String total_fee;

	/** 回调url */
	private String notify_url;

	private String APP_ID;
	private String MCH_ID;
	private String API_KEY;
	private String out_trade_no;
	public WxPay(Context context
			) {
		msgApi = WXAPIFactory.createWXAPI(context, null);
		req = new PayReq();
		sb = new StringBuffer();

		this.APP_ID = Toolkit.getConfigProperties("APP_ID");
		this.MCH_ID = Toolkit.getConfigProperties("MCH_ID");
		this.API_KEY =Toolkit.getConfigProperties("API_KEY");
		Log.i("result","API_KEY=="+API_KEY);
		msgApi.registerApp(APP_ID);
	}

	/**
	 * 支付方法
	 * 
	 * @param body
	 *            商品名称 不可为空
	 * @param total_fee
	 *            商品价格，不可为空
	 * @param notify_url
	 *            回调url 可以为空
	 */
	public void pay(String body, String total_fee,String order_sn, String notify_url) {
		if (body!=null&&!body.equals("")) {
			this.body = body;
		} else {
			this.body = "充值";
		}

		if (order_sn==null||order_sn.equals("")){
			out_trade_no = genOutTradNo();
		}else {
			this.out_trade_no = order_sn;
		}


		if (notify_url==null||notify_url.equals("")) {
			this.notify_url = WXConfig.DEFAULT_NOTIFY_URL;

		} else {
			this.notify_url = notify_url;
		}

		this.total_fee = changeY2F(total_fee);

		GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
		getPrepayId.execute();
	}

	/** 元转换为分 */
	private String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(
					".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(
					".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(
					".", "") + "00");
		}
		return amLong.toString();
	}

	private class GetPrepayIdTask extends
			AsyncTask<Void, Void, Map<String, String>> {

		@Override
		protected void onPreExecute() {
			// dialog = ProgressDialog.show(PayActivity.this,
			// getString(R.string.app_tip),
			// getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
			resultunifiedorder = result;
			genPayReq();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(Void... params) {

			String url = String
					.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			Log.e("orion", entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String, String> xml = decodeXml(content);

			return xml;
		}
	}

	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", APP_ID));

			packageParams.add(new BasicNameValuePair("body", body));
			packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url", notify_url));
			packageParams.add(new BasicNameValuePair("out_trade_no",
					out_trade_no));
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					Toolkit.getLocalIpAddress()));
			packageParams.add(new BasicNameValuePair("total_fee", total_fee));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);
			return new String(xmlstring.toString().getBytes(), "ISO8859-1");

//			 return xmlstring;

		} catch (Exception e) {

			return null;
		}
	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	/**
	 * 生成签名
	 */

	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", packageSign);
		return packageSign;
	}

	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:

					if ("xml".equals(nodeName) == false) {
						// 实例化student对象
						xml.put(nodeName, parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;
	}

	// 获得时间戳
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	// 生成签名参数
	public void genPayReq() {

		req.appId = APP_ID;
		req.partnerId = MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n" + req.sign + "\n\n");


		Log.e("orion", signParams.toString());

		msgApi.registerApp(APP_ID);
		msgApi.sendReq(req);

	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		Log.e("orion", sb.toString());
		return sb.toString();
	}

}
