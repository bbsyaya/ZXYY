package com.and.yzy.frame.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.R;
import com.and.yzy.frame.util.OkHttpUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 检测更新的工具类
 */
public class AppUpdateUtil {


    private String vistionUrl;
    private Context mContext;
    private FormBody formBody;

    public AppUpdateUtil(Activity context, String vistionUrl, FormBody formBody) {
        this.vistionUrl = vistionUrl;
        this.mContext = context;
        this.formBody = formBody;
    }


    public void checkUpdate(final UpdateCallBack callBack) {


        Request.Builder build = new Request.Builder()
                .url(vistionUrl);

        if (formBody != null) {
            build.post(formBody);
        }

        OkHttpUtils.getInstance().newCall(build.build()).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Log.e("version result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                if (jsonObject.getString("result").equals("2000")){
                    final Version version = JSON.parseObject(result, Version.class);
                    callBack.isUpdate(result);
                    Looper.prepare();
                    MaterialDialog materialDialog = new MaterialDialog(mContext);
                    materialDialog.setMDMessage(version.getRetMessage() + mContext.getString(R.string.banbenhao) + version.getDb().getVersion())
                            .setMDTitle(mContext.getString(R.string.gengxintishi))
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    Toast.makeText(mContext, R.string.houtaixiazaizhong, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mContext, DownLoadService.class);
                                    intent.putExtra("url", version.getDb().getDownloadURL());
                                    mContext.startService(intent);
                                }
                            });

                    materialDialog.show();
                    Looper.loop();
                }else{
                    callBack.isNoUpdate();
                }




                } catch (JSONException e) {
                    callBack.onError();
//                    Toast.makeText(mContext, "json字符串异常", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


    }


}
