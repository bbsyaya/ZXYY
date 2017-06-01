package com.zhixinyisheng.user.ui.data.BLE.control;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.ui.data.BLE.common.Utils;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhoneReceiver extends BroadcastReceiver {
	public static final String ACTION = "android.intent.action.PHONE_STATE";
	Context context;
	String phoneName="";
	String phoneNumber = "";
	/**
	 * 好的
	 */
	@Override
	public void onReceive(final Context context, Intent intent) {
		this.context = context;
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		switch (manager.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING://
				Log.e("receiver", "响铃状态");


				try {
					phoneNumber = intent
							.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
					Log.e("电话号码", phoneNumber);
				} catch (Exception e) {
					phoneNumber = "0";
					Log.e("PhoneReceiver", e.toString());
				}
				try {
					phoneName = getContactNameFromPhoneBook(context, phoneNumber);
					Logger.e("phoneName 60",phoneName);
					sendToDevice(context);
				} catch (Exception e) {
					e.printStackTrace();
				}


//				if (PermissionsUtil.is6()) {
//					PermissionsUtil.checkPermissionBy6(new PermissionListener() {
//						@Override
//						public void onPermissionGranted(PermissionGrantedResponse response) {
//							phoneName = getContactNameFromPhoneBook(context, phoneNumber);
//							Logger.e("phoneName 60",phoneName);
//							sendToDevice(context);
//						}
//
//						@Override
//						public void onPermissionDenied(PermissionDeniedResponse response) {
//							Toast.makeText(context, "未开启读取联系人权限", Toast.LENGTH_SHORT).show();
//						}
//
//						@Override
//						public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//							PermissionsUtil.showPermissDialogBy6(context, token, "开启读取联系人权限");
//						}
//					}, Manifest.permission.READ_CONTACTS);
//
//				} else {
//					if (PermissionsUtil.checkPermission(Manifest.permission.READ_CONTACTS)) {
//						phoneName = getContactNameFromPhoneBook(context, phoneNumber);
//						Logger.e("phoneName 75",phoneName);
//						sendToDevice(context);
//					} else {
//						Toast.makeText(context, "未开启读取联系人权限", Toast.LENGTH_SHORT).show();
//					}
//				}




				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				Log.e("receiver", "OFFHOOK");//
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				Log.e("receiver", "IDLE");
				break;
		}

	}

	private void sendToDevice(Context context) {
		if (phoneName.equals("")) {
            phoneName = "来电";
        }
		Log.e("姓名", phoneName);
		int l = 0;
		phoneName = chuliZFC(phoneName);
		l = phoneName.length()*3;
		phoneName = hanziZhuanER(phoneName);

		//转化为要发送的来电命令
		if (l == 3) {
            phoneName = "ab0000090000000002000100008"+l+phoneName;
        } else {
            phoneName = "ab00000c0000000002000100008"+l+phoneName;
        }

		//发送命令
		try {
            if (TestService.mDevice != null && TestService.is_ld) {
                Utils.sendSHML(context,phoneName);
            }
        } catch (Exception e) {
            Log.e("PhoneReceiver异常 67", e.toString());
        }
	}

	//汉字转二进制
	private String hanziZhuanER(String pn) {
		String pnx = "";
		byte[] b = null;
		try {
			b = pn.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < b.length; i++) {
			pnx += binaryString2hexString(Integer.toBinaryString(b[i] & 0xff));
		}
		return pnx;
	}
	//二进制转十六进制
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}


	Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	//处理字符串
	private String chuliZFC(String pn) {
		String pnx = "";
		//必须保证是中文
		Matcher mz = p.matcher(pn);
		if (mz.matches()) {
			pnx = pn;
		}else {
			for (int i = 0; i < pn.length(); i++) {
				Matcher m = p.matcher(pn.charAt(i)+"");
				if (m.matches()) {
					pnx += pn.charAt(i);
				}
			}
		}
		//截取两个字   abcd
		if (pnx.length()>2) {
			pnx = pnx.subSequence(pnx.length()-2, pnx.length())+"";
		}
		if (pnx.equals("")) {
//			pnx = "未知";
			pnx = "有来电啦";
		}
		return pnx;
	}
	//根据电话号服从通讯录中查询姓名
	public String getContactNameFromPhoneBook(Context context, String phoneNum) {
		String contactName = "";
		ContentResolver cr = context.getContentResolver();
		Cursor pCur = cr.query(
				Phone.CONTENT_URI, null,
				Phone.NUMBER + " = ?",
				new String[] { phoneNum }, null);
		if (pCur.moveToFirst()) {
			contactName = pCur
					.getString(pCur.getColumnIndex(
							ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			pCur.close();
		}

		return contactName;
	}



}
