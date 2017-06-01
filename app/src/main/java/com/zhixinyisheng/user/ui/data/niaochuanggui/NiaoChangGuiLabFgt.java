package com.zhixinyisheng.user.ui.data.niaochuanggui;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.datas.NcgDatas;
import com.zhixinyisheng.user.http.DataDetail;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 尿常规（新）
 * Created by 焕焕 on 2017/2/23.
 */
public class NiaoChangGuiLabFgt extends BaseFgt{
    /**
     * 葡萄糖
     */
    @Bind(R.id.ncgui_rl1_tvn)
    TextView ncgui_rl1_tvn;
    @Bind(R.id.ncgui_rl1_tvd)
    TextView ncgui_rl1_tvd;
    @Bind(R.id.ncgui_rl1_ivd)
    ImageView ncgui_rl1_ivd;
    /**
     * 蛋白质
     */
    @Bind(R.id.ncgui_rl2_tvn)
    TextView ncgui_rl2_tvn;
    @Bind(R.id.ncgui_rl2_tvd)
    TextView ncgui_rl2_tvd;
    @Bind(R.id.ncgui_rl2_ivd)
    ImageView ncgui_rl2_ivd;
    /**
     * 潜血（隐血）
     */
    @Bind(R.id.ncgui_rl3_tvn)
    TextView ncgui_rl3_tvn;
    @Bind(R.id.ncgui_rl3_tvd)
    TextView ncgui_rl3_tvd;
    @Bind(R.id.ncgui_rl3_ivd)
    ImageView ncgui_rl3_ivd;
    /**
     * 尿胆原
     */
    @Bind(R.id.ncgui_rl4_tvn)
    TextView ncgui_rl4_tvn;
    @Bind(R.id.ncgui_rl4_tvd)
    TextView ncgui_rl4_tvd;
    @Bind(R.id.ncgui_rl4_ivd)
    ImageView ncgui_rl4_ivd;
    /**
     * 酮体
     */
    @Bind(R.id.ncgui_rl5_tvn)
    TextView ncgui_rl5_tvn;
    @Bind(R.id.ncgui_rl5_tvd)
    TextView ncgui_rl5_tvd;
    @Bind(R.id.ncgui_rl5_ivd)
    ImageView ncgui_rl5_ivd;
    /**
     * 比重
     */
    @Bind(R.id.tv_bizhong)
    TextView tv_bizhong;
    @Bind(R.id.et_bizhong)
    EditText et_bizhong;
    /**
     * 红细胞
     */
    @Bind(R.id.tv_hongxibao)
    TextView tv_hongxibao;
    @Bind(R.id.et_hongxibao)
    EditText et_hongxibao;
    /**
     * 白细胞
     */
    @Bind(R.id.tv_baixibao)
    TextView tv_baixibao;
    @Bind(R.id.et_baixibao)
    EditText et_baixibao;
    /**
     * 上皮细胞
     */
    @Bind(R.id.tv_shangpixibao)
    TextView tv_shangpixibao;
    @Bind(R.id.et_shangpixibao)
    EditText et_shangpixibao;
    /**
     * 管型
     */
    @Bind(R.id.tv_guangxing)
    TextView tv_guangxing;
    @Bind(R.id.et_guangxing)
    EditText et_guangxing;
    /**
     * 红细胞(高倍视野)
     */
    @Bind(R.id.tv_hongxibao_gaobei)
    TextView tv_hongxibao_gaobei;
    @Bind(R.id.et_hongxibao_gaobei)
    EditText et_hongxibao_gaobei;
    /**
     * 白细胞(高倍视野)
     */
    @Bind(R.id.tv_baixibao_gaobei)
    TextView tv_baixibao_gaobei;
    @Bind(R.id.et_baixibao_gaobei)
    EditText et_baixibao_gaobei;
    /**
     * 病理管型
     */
    @Bind(R.id.tv_bingliguanxing)
    TextView tv_bingliguanxing;
    @Bind(R.id.et_bingliguanxing)
    EditText et_bingliguanxing;
    /**
     * 小圆上皮细胞
     */
    @Bind(R.id.tv_xiaoyuanshangpixibao)
    TextView tv_xiaoyuanshangpixibao;
    @Bind(R.id.et_xiaoyuanshangpixibao)
    EditText et_xiaoyuanshangpixibao;
    @Bind(R.id.ll_ncgui_rl1_ivd)
    RelativeLayout ll_ncgui_rl1_ivd;
    @Bind(R.id.ll_ncgui_rl2_ivd)
    RelativeLayout ll_ncgui_rl2_ivd;
    @Bind(R.id.ll_ncgui_rl3_ivd)
    RelativeLayout ll_ncgui_rl3_ivd;
    @Bind(R.id.ll_ncgui_rl4_ivd)
    RelativeLayout ll_ncgui_rl4_ivd;
    @Bind(R.id.ll_ncgui_rl5_ivd)
    RelativeLayout ll_ncgui_rl5_ivd;
    public PopupWindow pop, pop1;
    private TextView tv11, tv21;//pop的TextView
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    private static String PROTEINFLAG = "1";//蛋白质 1:黑 2：黄3：红   1正常
    private static String BLDFLAG = "1";//潜血（隐血）色值1:黑 2：黄3：红   1正常
    int no = 0;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_niaochanggui_lab;
    }

    @Override
    public void initData() {
        registerReceiver();
        onlick();
        initPop();
        getncgdata();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_NCG_COMMIT);
        getActivity().registerReceiver(myBroadcast, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Constant.LAB_NCG_TIME);
        getActivity().registerReceiver(myBroadcast_time, intentFilter2);
    }
    /**
     * 时间选择器的广播
     */
    BroadcastReceiver myBroadcast_time = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            initView();
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(DataDetail.class).ncgDetail(phone, secret, userId, Content.time_lab), 1);
        }
    };
    /**
     * 提交按钮的广播
     */
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 提交数据
            ncgSubmit();
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            if (myBroadcast != null) {
                getActivity().unregisterReceiver(myBroadcast);
            }
            if (myBroadcast_time != null) {
                getActivity().unregisterReceiver(myBroadcast_time);
            }
        }else{
            registerReceiver();
        }
    }


    /**
     * 获得数据
     */
    private void getncgdata() {
        initView();
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(DataDetail.class).ncgDetail(phone, secret, userId, Content.time_lab), 1);
    }

    private void initView() {
        ncgui_rl1_tvd.setText("");
        ncgui_rl2_tvd.setText("");
        ncgui_rl3_tvd.setText("");
        ncgui_rl4_tvd.setText("");
        ncgui_rl5_tvd.setText("");
        et_bizhong.setText("");
        et_hongxibao.setText("");
        et_baixibao.setText("");
        et_shangpixibao.setText("");
        et_guangxing.setText("");
        et_hongxibao_gaobei.setText("");
        et_baixibao_gaobei.setText("");
        et_bingliguanxing.setText("");
        et_xiaoyuanshangpixibao.setText("");

        ncgui_rl1_tvn.setTextColor(0xff5e5e5e);
        ncgui_rl2_tvn.setTextColor(0xff5e5e5e);
        ncgui_rl3_tvn.setTextColor(0xff5e5e5e);
        ncgui_rl4_tvn.setTextColor(0xff5e5e5e);
        ncgui_rl5_tvn.setTextColor(0xff5e5e5e);
        tv_bizhong.setTextColor(0xff5e5e5e);
        tv_hongxibao.setTextColor(0xff5e5e5e);
        tv_baixibao.setTextColor(0xff5e5e5e);
        tv_shangpixibao.setTextColor(0xff5e5e5e);
        tv_guangxing.setTextColor(0xff5e5e5e);
        tv_hongxibao_gaobei.setTextColor(0xff5e5e5e);
        tv_baixibao_gaobei.setTextColor(0xff5e5e5e);
        tv_bingliguanxing.setTextColor(0xff5e5e5e);
        tv_xiaoyuanshangpixibao.setTextColor(0xff5e5e5e);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 1:
                try {
                    getresult(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                showToast(getString(R.string.tijiaochenggong));
                doHttp(RetrofitUtils.createApi(DataDetail.class).ncgDetail(phone, secret, userId, Content.time_lab), 1);
                break;
        }
    }

    private void getresult(String pathStr) {
        NcgDatas ncgDatas = JSON.parseObject(pathStr, NcgDatas.class);
        if (ncgDatas.getList().getGLUCOSE().contains("+")) {
            ncgui_rl1_tvn.setTextColor(0xffccb400);
        }else{
            ncgui_rl1_tvn.setTextColor(0xff5e5e5e);
        }
        if (ncgDatas.getList().getPROTEIN().contains("+")) {
            ncgui_rl2_tvn.setTextColor(0xffccb400);
        }else{
            ncgui_rl2_tvn.setTextColor(0xff5e5e5e);
        }
        if (ncgDatas.getList().getBLD().contains("+")) {
            ncgui_rl3_tvn.setTextColor(0xffccb400);
        }else{
            ncgui_rl3_tvn.setTextColor(0xff5e5e5e);
        }
        if (ncgDatas.getList().getUBG().contains("+")) {
            ncgui_rl4_tvn.setTextColor(0xffccb400);
        }else{
            ncgui_rl4_tvn.setTextColor(0xff5e5e5e);
        }
        if (ncgDatas.getList().getKET().contains("+")) {
            ncgui_rl5_tvn.setTextColor(0xffccb400);
        }else{
            ncgui_rl5_tvn.setTextColor(0xff5e5e5e);
        }
        if (!ncgDatas.getList().getPROPORTION().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getPROPORTION());
            if (data < 1.003 || data > 1.035) {
                tv_bizhong.setTextColor(0xffccb400);
            }else{
                tv_bizhong.setTextColor(0xff5e5e5e);
            }
        }

        if (!ncgDatas.getList().getREDBLOODCELL().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getREDBLOODCELL());
            if (data > 25) {
                tv_hongxibao.setTextColor(0xffccb400);
            }else{
                tv_hongxibao.setTextColor(0xff5e5e5e);
            }
        }
        if (!ncgDatas.getList().getHEMAMEBA().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getHEMAMEBA());
            if (data > 21.3) {
                tv_baixibao.setTextColor(0xffccb400);
            }else{
                tv_baixibao.setTextColor(0xff5e5e5e);
            }
        }
        if (!ncgDatas.getList().getEPITHELIALCELL().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getEPITHELIALCELL());
            if (data > 21.3) {
                tv_shangpixibao.setTextColor(0xffccb400);
            }else{
                tv_shangpixibao.setTextColor(0xff5e5e5e);
            }
        }
        if (!ncgDatas.getList().getTUBE().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getTUBE());
            if (data > 1.3) {
                tv_guangxing.setTextColor(0xffccb400);
            }else{
                tv_guangxing.setTextColor(0xff5e5e5e);
            }
        }
        if (!ncgDatas.getList().getREDBLOODCELLHIGH().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getREDBLOODCELLHIGH());
            if (data > 4.5) {
                tv_hongxibao_gaobei.setTextColor(0xffccb400);
            }else{
                tv_hongxibao_gaobei.setTextColor(0xff5e5e5e);
            }
        }
        if (!ncgDatas.getList().getHEMAMEBAHIGH().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getHEMAMEBAHIGH());
            if (data > 5.4) {
                tv_baixibao_gaobei.setTextColor(0xffccb400);
            }else{
                tv_baixibao_gaobei.setTextColor(0xff5e5e5e);
            }
        }
        if (!ncgDatas.getList().getPATHCAST().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getPATHCAST());
            if (data > 0) {
                tv_bingliguanxing.setTextColor(0xffccb400);
            }else{
                tv_bingliguanxing.setTextColor(0xff5e5e5e);
            }
        }
        if (!ncgDatas.getList().getSREC().equals("")) {
            Double data = Double.valueOf(ncgDatas.getList().getSREC());
            if (data > 0) {
                tv_xiaoyuanshangpixibao.setTextColor(0xffccb400);
            }else{
                tv_xiaoyuanshangpixibao.setTextColor(0xff5e5e5e);
            }
        }
        ncgui_rl1_tvd.setText(ncgDatas.getList().getGLUCOSE());
        ncgui_rl2_tvd.setText(ncgDatas.getList().getPROTEIN());
        ncgui_rl3_tvd.setText(ncgDatas.getList().getBLD());
        ncgui_rl4_tvd.setText(ncgDatas.getList().getUBG());
        ncgui_rl5_tvd.setText(ncgDatas.getList().getKET());
        et_bizhong.setText(ncgDatas.getList().getPROPORTION());
        et_hongxibao.setText(ncgDatas.getList().getREDBLOODCELL());
        et_baixibao.setText(ncgDatas.getList().getHEMAMEBA());
        et_shangpixibao.setText(ncgDatas.getList().getEPITHELIALCELL());
        et_guangxing.setText(ncgDatas.getList().getTUBE());
        et_hongxibao_gaobei.setText(ncgDatas.getList().getREDBLOODCELLHIGH());
        et_baixibao_gaobei.setText(ncgDatas.getList().getHEMAMEBAHIGH());
        et_bingliguanxing.setText(ncgDatas.getList().getPATHCAST());
        et_xiaoyuanshangpixibao.setText(ncgDatas.getList().getSREC());
    }

    private void onlick() {

        ll_ncgui_rl1_ivd.setOnClickListener(listener);
        ll_ncgui_rl2_ivd.setOnClickListener(listener);
        ll_ncgui_rl3_ivd.setOnClickListener(listener);
        ll_ncgui_rl4_ivd.setOnClickListener(listener);
        ll_ncgui_rl5_ivd.setOnClickListener(listener);
    }
    private void initPop() {
        View view = getActivity().getLayoutInflater().inflate(
                R.layout.ncg_pop1, null);
        View view1 = getActivity().getLayoutInflater().inflate(
                R.layout.niaochanggui_pop, null);
        pop = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        pop1 = new PopupWindow(view1, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        tv1 = (TextView) view1.findViewById(R.id.np_tv1);
        tv2 = (TextView) view1.findViewById(R.id.np_tv2);
        tv3 = (TextView) view1.findViewById(R.id.np_tv3);
        tv4 = (TextView) view1.findViewById(R.id.np_tv4);
        tv5 = (TextView) view1.findViewById(R.id.np_tv5);
        tv6 = (TextView) view1.findViewById(R.id.np_tv6);
        tv1.setOnClickListener(listener);
        tv2.setOnClickListener(listener);
        tv3.setOnClickListener(listener);
        tv4.setOnClickListener(listener);
        tv5.setOnClickListener(listener);
        tv6.setOnClickListener(listener);
        pop.setFocusable(true);
        pop.setOutsideTouchable(false);
        pop1.setFocusable(true);
        pop1.setOutsideTouchable(false);
        tv11 = (TextView) view.findViewById(R.id.np_tv11);
        tv21 = (TextView) view.findViewById(R.id.np_tv21);
        tv11.setOnClickListener(listener);
        tv21.setOnClickListener(listener);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();


                if ((event.getAction() == MotionEvent.ACTION_DOWN)
                        && ((x < 0) || (x >= v.getWidth()) || (y < 0) || (y >= v
                        .getHeight()))) {
                    pop.dismiss();
                    pop1.dismiss();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pop.dismiss();
                    pop1.dismiss();
                    return true;
                } else {
                    return v.onTouchEvent(event);
                }
            }
        });
    }
    /**
     * 提交数据
     */
    private void ncgSubmit() {
        String danbaizhiData = ncgui_rl2_tvd.getText().toString();
        String xianxueData = ncgui_rl3_tvd.getText().toString();
        if (danbaizhiData.equals("")||xianxueData.equals("")){
            showToast(getString(R.string.qingshurudanbaizhiheqianxueshuju));
        }else{
            if (danbaizhiData.equals("-")){
                PROTEINFLAG ="1";
            }else if (danbaizhiData.equals("±")||danbaizhiData.equals("+")||danbaizhiData.equals("++")){
                PROTEINFLAG = "2";
            }else{
                PROTEINFLAG = "3";
            }
            if (xianxueData.equals("-")){
                BLDFLAG ="1";
            }else if (xianxueData.equals("±")||xianxueData.equals("+")||xianxueData.equals("++")){
                BLDFLAG = "2";
            }else{
                BLDFLAG = "3";
            }
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(DataDetail.class).ncgDatas(phone, secret, userId, Content.time_lab, PROTEINFLAG, BLDFLAG,
                    ncgui_rl1_tvd.getText().toString(), ncgui_rl2_tvd.getText().toString(), ncgui_rl3_tvd.getText().toString(),
                    ncgui_rl4_tvd.getText().toString(), ncgui_rl5_tvd.getText().toString(),
                    et_bizhong.getText().toString(), et_hongxibao.getText().toString(), et_baixibao.getText().toString(),
                    et_shangpixibao.getText().toString(), et_guangxing.getText().toString(), et_hongxibao_gaobei.getText().toString(),
                    et_baixibao_gaobei.getText().toString(), et_bingliguanxing.getText().toString(),
                    et_xiaoyuanshangpixibao.getText().toString(), LanguageUtil.judgeLanguage()), 0);
        }


    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == tv1) {
                setData(tv1);
            } else if (v == tv2) {
                setData(tv2);
            } else if (v == tv3) {
                setData(tv3);
            } else if (v == tv4) {
                setData(tv4);
            } else if (v == tv5) {
                setData(tv5);
            } else if (v == tv6) {
                setData(tv6);
            } else if (v == tv11) {
                setData(tv11);
            } else if (v == tv21) {
                setData(tv21);
            } else if (v == ll_ncgui_rl1_ivd) {
                no = 1;
                if (pop1 != null && pop1.isShowing()) {
                    pop1.dismiss();
                    ncgui_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                } else {
                    pop1.showAsDropDown(ncgui_rl1_tvd, 0, 0);
//                    ncgui_rl1_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_ncgui_rl2_ivd) {
                no = 2;
                if (pop1 != null && pop1.isShowing()) {
                    ncgui_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop1.dismiss();
                } else {
                    pop1.showAsDropDown(ncgui_rl2_tvd, 0, 0);
//                    ncgui_rl2_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_ncgui_rl3_ivd) {
                no = 3;
                if (pop1 != null && pop1.isShowing()) {
                    ncgui_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop1.dismiss();
                } else {
                    pop1.showAsDropDown(ncgui_rl3_tvd, 0, 0);
//                    ncgui_rl3_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_ncgui_rl4_ivd) {
                no = 4;
                if (pop != null && pop.isShowing()) {
                    ncgui_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop.dismiss();
                } else {
                    pop.showAsDropDown(ncgui_rl4_tvd, 0, 0);
//                    ncgui_rl4_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            } else if (v == ll_ncgui_rl5_ivd) {
                no = 5;
                if (pop1 != null && pop1.isShowing()) {
                    ncgui_rl5_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                    pop1.dismiss();
                } else {
                    pop1.showAsDropDown(ncgui_rl5_tvd, 0, 0);
//                    ncgui_rl5_ivd.setImageResource(R.drawable.xiangshangjiantou_03);
                }
            }
        }

        // 设置显示数据
        private void setData(TextView tv1) {
            pop1.dismiss();
            pop.dismiss();

            if (no == 1) {
                ncgui_rl1_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ncgui_rl1_tvd.setText(tv1.getText().toString());
            } else if (no == 2) {
                ncgui_rl2_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ncgui_rl2_tvd.setText(tv1.getText().toString());
            } else if (no == 3) {
                ncgui_rl3_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ncgui_rl3_tvd.setText(tv1.getText().toString());
            } else if (no == 4) {
                ncgui_rl4_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ncgui_rl4_tvd.setText(tv1.getText().toString());
            } else if (no == 5) {
                ncgui_rl5_ivd.setImageResource(R.drawable.xiangxiajiantou_03);
                ncgui_rl5_tvd.setText(tv1.getText().toString());
            }
        }
    };




}
