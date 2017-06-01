package com.zhixinyisheng.user.ui.data.shuimian;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.listview.ListViewForScrollView;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.ShuiMianListAdapter;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.ShuiMianZXT;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.ui.BaseFgt;
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
 * 睡眠趋势为了好友详情
 * Created by 焕焕 on 2016/11/11.
 */
public class ShuiMianNewFgt extends BaseFgt {
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
            data_ada1 = new ArrayList<String>(),
            item_id = new ArrayList<String>();
    ShuiMianListAdapter shuiMianListAdapter;
    List<ShuiMianZXT.ListBean> listBeen = new ArrayList<ShuiMianZXT.ListBean>();
    @Override
    public int getLayoutId() {
        return R.layout.fgt_shuimiannew;
    }

    @Override
    public void initData() {
        Ylabel.clear();
        csYlabel(0, 24);
    }
    private void csYlabel(int start, int end) {
        double d = new BigDecimal((end - start) / 10f).setScale(1,
                BigDecimal.ROUND_HALF_UP).doubleValue();
        for (double i = start; i <= end; i = i + d) {
            Ylabel.add(new BigDecimal(i).setScale(1,
                    BigDecimal.ROUND_HALF_UP).doubleValue()
                    + "");
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkShuiMianZXT(
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
                ShuiMianZXT shuiMianZXT = JSON.parseObject(result, ShuiMianZXT.class);
                for (int i = 0; i < shuiMianZXT.getList().size(); i++) {
                    String strt = shuiMianZXT.getList().get(i).getBYTIME();
                    String strsj = shuiMianZXT.getList().get(i).getHOUR();
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
                doHttp(RetrofitUtils.createApi(DataUrl.class).checkShuiMianZXT(
                        userId, Content.DATA, phone, secret), 1);
                break;
            case 1:
                ShuiMianZXT shuiMianData = JSON.parseObject(result, ShuiMianZXT.class);
                listBeen.clear();
                listBeen.addAll(shuiMianData.getList());
                shuiMianListAdapter = new ShuiMianListAdapter(getActivity(), listBeen, R.layout.item_xinlv);
                xlPurflv.setAdapter(shuiMianListAdapter);
                break;
            case 6:

                FindByPidEntity findByPidEntity = JSON.parseObject(result,FindByPidEntity.class);
                new CalenderDialogTest(getActivity(),findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
//                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(DataUrl.class).checkShuiMianZXT(
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
            new CalenderDialogTest(getActivity(),null) {
                @Override
                public void getZXTData() {

                    doHttp(RetrofitUtils.createApi(DataUrl.class).checkShuiMianZXT(
                            userId, Content.DATA, phone, secret), 0);
                }
            };
        }
    }

    @Override
    public void requestData() {

    }
    @OnClick( R.id.iv_rili)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_rili:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(FindByPid.class).sleepFindByPid(
                        userId, phone, secret), 6);


                break;
        }
    }
}
