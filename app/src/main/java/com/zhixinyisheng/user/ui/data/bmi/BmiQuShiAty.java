package com.zhixinyisheng.user.ui.data.bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.listview.ListViewForScrollView;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.BmiListAdapter;
import com.zhixinyisheng.user.domain.BmiZXT;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.view.MyViewBD;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * bmi趋势界面
 * Created by 焕焕 on 2016/11/2.
 */
public class BmiQuShiAty extends BaseAty {
    @Bind(R.id.cjs_view)
    View cjsView;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.fl_xinlv)
    FrameLayout flXinlv;
    @Bind(R.id.iv_rili)
    ImageView ivRili;
    @Bind(R.id.xl_tv1)
    TextView xlTv1;
    @Bind(R.id.xl_rl1)
    RelativeLayout xlRl1;
    @Bind(R.id.xl_mv)
    MyViewBD mv;
    @Bind(R.id.xl_ll1)
    LinearLayout xlLl1;
    @Bind(R.id.xl_purflv)
    ListViewForScrollView xlPurflv;
    List<String> Xlabel = new ArrayList<String>(),
            data = new ArrayList<String>(), data1 = new ArrayList<String>(),
            Ylabel = new ArrayList<String>();
    List<String> Xlabel_ada = new ArrayList<String>(),
            Xlabel_ada1 = new ArrayList<String>(),
            data_ada = new ArrayList<String>(),
            data_ada1 = new ArrayList<String>();
    BmiListAdapter bmiListAdapter;
    List<BmiZXT.BmiListBean> listBeen = new ArrayList<BmiZXT.BmiListBean>();
    @Override
    public int getLayoutId() {
        return R.layout.aty_bmiqushi;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(getResources().getString(R.string.home_bmi));
        ivBack.setVisibility(View.VISIBLE);
        Ylabel.clear();
        csYlabel(0,40);
    }
    private void csYlabel(int start,int end) {
        double d = new BigDecimal((end - start) / 10f).setScale(1,
                BigDecimal.ROUND_HALF_UP).doubleValue();
        for (double i = start; i <= end; i = i + d) {
            Ylabel.add(new BigDecimal(i).setScale(1,
                    BigDecimal.ROUND_HALF_UP).doubleValue()
                    + "");
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkBMIZXT(
                userId, time, phone, secret), 0);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 0:
                Xlabel.clear();
                data.clear();
                data1.clear();
                BmiZXT bmiZXT = JSON.parseObject(result, BmiZXT.class);
                for (int i = 0; i < bmiZXT.getBmiList().size(); i++) {
                    String strt = bmiZXT.getBmiList().get(i).getByTime();
                    double num = bmiZXT.getBmiList().get(i).getNum();
                    String strsj = String.format("%.2f", num);
                    Xlabel.add(strt.substring(5, 7) + "."
                            + strt.substring(8, 10));
                    if (Double.valueOf(strsj) < 0) {
                        data.add("0");
                    } else {
                        data.add(strsj);
                    }
                    data1.add(strsj);
                }
                mv.drawZhexian(Xlabel, Ylabel, data, data1, 1);
                doHttp(RetrofitUtils.createApi(DataUrl.class).checkBMIZXT(
                        userId, Content.DATA, phone, secret), 1);
                break;
            case 1:
                BmiZXT bmiData = JSON.parseObject(result, BmiZXT.class);
                listBeen.clear();
                for (int i = 0; i < bmiData.getBmiList().size(); i++) {
                    bmiData.getBmiList().get(i).setNum(Double.parseDouble(String.format("%.2f", bmiData.getBmiList().get(i).getNum())));
                }
                listBeen.addAll(bmiData.getBmiList());
                bmiListAdapter = new BmiListAdapter(this, listBeen, R.layout.item_xinlv);
                xlPurflv.setAdapter(bmiListAdapter);
                break;
            case 6:
                FindByPidEntity findByPidEntity = JSON.parseObject(result,FindByPidEntity.class);
                new CalenderDialogTest(this,findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
//                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(DataUrl.class).checkBMIZXT(
                                userId, Content.DATA, phone, secret), 0);
                    }
                };
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (what==6){
            new CalenderDialogTest(this,null) {
                @Override
                public void getZXTData() {

                    doHttp(RetrofitUtils.createApi(DataUrl.class).checkBMIZXT(
                            userId, Content.DATA, phone, secret), 0);
                }
            };
        }
    }

    @Override
    public void requestData() {

    }


    @OnClick({R.id.cjs_rlb, R.id.iv_rili})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.iv_rili:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(FindByPid.class).bmiFindByPid(
                        userId, phone, secret), 6);

                break;
        }
    }
}
