package com.zhixinyisheng.user.util;

import android.content.Context;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传
 * @author 剧京
 * 
 * */
public class UpLodeUtilofHeadImage {

	private static final int TIME_OUT = 30 * 1000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	private static String ATTPURL;
	private static String DOCTOR_ID;
	private static String ATTP;
	/**************************************************************/
	public static String post(final Context context, final String posturi,
							  final String id, final String attp) {
		String resultq = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(posturi);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("DOCTOR_ID", id));// 医生id
		params.add(new BasicNameValuePair("ATTP", attp));// 医师执照�?
		
		params.add(new BasicNameValuePair("ATTPURL", ATTPURL));// 化验�?
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);// 提交表单
			HttpEntity httpEntity = response.getEntity();// 得到服务器响�?
			String result = EntityUtils.toString(httpEntity, "UTF-8");// 得到服务器返回的json数据
			JSONObject object = new JSONObject(result);// 解析返回�?
			/* 判断返回值参�?*/
			if (object.getString("result").equals("0000")) {
				Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
				resultq = "01";
				return resultq;
			} else if (object.getString("result").equals("02")) {
				Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
				resultq = "02";
				return resultq;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultq;
	}

	public static String uploadFile_SheZhen(File file1, String RequestURL) {
		int res = 0;
		String result = null;
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入�?
			conn.setDoOutput(true); // 允许输出�?
			conn.setUseCaches(false); // 不允许使用缓�?
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			if (file1 != null) {
				/**
				 * 当文件不为空时执行上�?
				 */
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意�?name里面的�?为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后�?��
				 */

				sb.append("Content-Disposition: form-data; name=\"files\"; filename=\""
						+ file1.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file1);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
//				sb.append("Content-Disposition: form-data; name=\"files\"; filename=\""
//						+ file2.getName() + "\"" + LINE_END);
//				sb.append("Content-Type: application/octet-stream; charset="
////						+ CHARSET + LINE_END);
//				sb.append(LINE_END);
//				dos.write(sb.toString().getBytes());
//				InputStream is2 = new FileInputStream(file2);
//				byte[] bytes2 = new byte[1024];
//				int len2 = 0;
//				while ((len2 = is2.read(bytes)) != -1) {
//					dos.write(bytes, 0, len2);
//				}
//				is2.close();
//				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应�?200=成功 当响应成功，获取响应的流
				 */
				res = conn.getResponseCode();
				// ssssss(TAG, "response code:" + res);
				if (res == 200) {
					// ssssss(TAG, "request success");
					InputStream input = conn.getInputStream();
					StringBuffer sb1 = new StringBuffer();
					int ss;
					while ((ss = input.read()) != -1) {
						sb1.append((char) ss);
					}
					result = sb1.toString();
					Logger.e("result 159", result+"");
//					JSONObject jo;
//					try {
//						jo = new JSONObject(result);
//						ATTPURL = jo.getString("result");
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}

				} else {
					// ssssss(TAG, "request error");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	

	/***********************************************************************/
	public static String uploadFile(File file1, String RequestURL) {
		int res = 0;
		String result = null;
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入�?
			conn.setDoOutput(true); // 允许输出�?
			conn.setUseCaches(false); // 不允许使用缓�?
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			if (file1 != null) {
				/**
				 * 当文件不为空时执行上�?
				 */
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意�?name里面的�?为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后�?��
				 */

				sb.append("Content-Disposition: form-data; name=\"files\"; filename=\""
						+ file1.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file1);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
//				sb.append("Content-Disposition: form-data; name=\"files\"; filename=\""
//						+ file2.getName() + "\"" + LINE_END);
//				sb.append("Content-Type: application/octet-stream; charset="
////						+ CHARSET + LINE_END);
//				sb.append(LINE_END);
//				dos.write(sb.toString().getBytes());
//				InputStream is2 = new FileInputStream(file2);
//				byte[] bytes2 = new byte[1024];
//				int len2 = 0;
//				while ((len2 = is2.read(bytes)) != -1) {
//					dos.write(bytes, 0, len2);
//				}
//				is2.close();
//				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应�?200=成功 当响应成功，获取响应的流
				 */
				res = conn.getResponseCode();
				// ssssss(TAG, "response code:" + res);
				if (res == 200) {
					// ssssss(TAG, "request success");
					InputStream input = conn.getInputStream();
					StringBuffer sb1 = new StringBuffer();
					int ss;
					while ((ss = input.read()) != -1) {
						sb1.append((char) ss);
					}
					result = sb1.toString();
					Logger.e("result 159", result+"");
					JSONObject jo;
					try {
						jo = new JSONObject(result);
						ATTPURL = jo.getString("result");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					// ssssss(TAG, "request error");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ATTPURL;
	}

}
