package com.zhixinyisheng.user.ui.data.bushu;

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
import com.zhixinyisheng.user.adapter.BuShuListAdapter;
import com.zhixinyisheng.user.domain.BuShuZXT;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.view.MyViewBD;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 步数为了好友详情
 * Created by 焕焕 on 2016/11/11.
 */
public class BuShuNewFgt extends BaseFgt {
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
    List<BuShuZXT.ListBean> listBeen = new ArrayList<BuShuZXT.ListBean>();
    BuShuListAdapter buShuListAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_bushunew;
    }

    @Override
    public void initData() {
        Ylabel.clear();
        for (int i = 0; i <= 30000; i += 3000) {
            Ylabel.add(i + "");
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkBuShuZXT(
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
                BuShuZXT buShuZXT = JSON.parseObject(result, BuShuZXT.class);
                for (int i = 0; i < buShuZXT.getList().size(); i++) {
                    String strt = buShuZXT.getList().get(i).getBYTIME();
                    int strsj = buShuZXT.getList().get(i).getSTEPS();
                    Xlabel.add(strt.substring(5, 7) + "."
                            + strt.substring(8, 10));
                    if (Double.valueOf(strsj) < 0) {
                        data.add("0");
                    } else {
                        data.add(strsj + "");
                    }
                    data1.add(strsj + "");
                }
                mv.drawZhexian(Xlabel, Ylabel, data, data1, 1);
                doHttp(RetrofitUtils.createApi(DataUrl.class).checkBuShuZXT(
                        userId, Content.DATA, phone, secret), 1);
                break;
            case 1:
                BuShuZXT buShuData = JSON.parseObject(result, BuShuZXT.class);
                listBeen.clear();
                listBeen.addAll(buShuData.getList());
                buShuListAdapter = new BuShuListAdapter(getActivity(), listBeen, R.layout.item_xinlv);
                xlPurflv.setAdapter(buShuListAdapter);
                break;
            case 6:
                FindByPidEntity findByPidEntity = JSON.parseObject(result,FindByPidEntity.class);
                new CalenderDialogTest(getActivity(),findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
//                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(DataUrl.class).checkBuShuZXT(
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

                    doHttp(RetrofitUtils.createApi(DataUrl.class).checkBuShuZXT(
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
                doHttp(RetrofitUtils.createApi(FindByPid.class).stepnumberFindByPid(
                        userId, phone, secret), 6);


                break;
        }
    }

}
