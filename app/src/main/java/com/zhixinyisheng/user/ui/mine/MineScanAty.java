package com.zhixinyisheng.user.ui.mine;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.Friend;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.ui.AddFriendDetialAty;

import butterknife.Bind;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * 扫描二维码
 * Created by gjj on 2016/3/22.
 */
public class MineScanAty extends BaseAty implements QRCodeView.Delegate {


 /*   @Bind(R.id.zxing_order)
    ZXingScannerView mZxingOrder;*/

    @Bind(R.id.zxingview)
    QRCodeView mQRCodeView;
    @Bind(R.id.iv_finish)
    ImageView mIvFinish;

    @Override
    public int getLayoutId() {
        return R.layout.scan_code_layout;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mQRCodeView.setResultHandler(this);
        mIvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onScanQRCodeSuccess(String result) {

        Log.i("result", "result==" + result);
        mQRCodeView.stopSpotAndHiddenRect();
        //绑定
        showLoadingDialog(null);
//        showToast("添加好友成功");

        Logger.e("扫一扫参数",phone+"---"+secret+"-------"+userId+"------"+result);
        doHttp(RetrofitUtils.createApi(Friend.class).sweep(phone,secret,userId,result), 0);

        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        Logger.e("扫一扫加好友，返回值",result);


        Bundle bundle = new Bundle();
        bundle.putString("a",result);

        startActivity(AddFriendDetialAty.class,bundle);
        finish();

        super.onSuccess(result, call, response, what);
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        mQRCodeView.startSpotAndShowRect();
        Logger.e("扫一扫失败返回的值",result);
        showToast(getResources().getString(R.string.saomiaoshibai));

    }
}
