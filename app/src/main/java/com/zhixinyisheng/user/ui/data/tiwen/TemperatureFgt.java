package com.zhixinyisheng.user.ui.data.tiwen;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.TemperatureAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.datas.TemperatureDatas;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.interfaces.OnLoadMoreListener;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.view.PullToListView;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 体温(新)
 * Created by 焕焕 on 2017/1/7.
 */
public class TemperatureFgt extends BaseFgt implements OnLoadMoreListener {
    @Bind(R.id.trend_listview)
    PullToListView trendListview;
//    @Bind(R.id.srl)
//    SwipyRefreshLayout mSrl;
    List<TemperatureDatas.ListBean> list_temperature = new ArrayList<>();
    TemperatureAdapter temperatureAdapter;
    private long mPage = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_temperature;
    }

    @Override
    public void initData() {
        Content.animalHeat++;
        try {
//            showLoadingContentDialog();
            list_temperature.clear();
            mPage = 0;
            for (long i = 30 * mPage; i < 30 + mPage * 30; i++) {
                TemperatureDatas.ListBean temperatureBean = new TemperatureDatas.ListBean();
                temperatureBean.setBYTIME(DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - 86400000 * i));
                list_temperature.add(temperatureBean);
            }
            trendListview.setShowFoot(false);
            temperatureAdapter = new TemperatureAdapter(getActivity(), list_temperature, R.layout.trend_list_item);
            View viewFooter = LayoutInflater.from(getActivity()).inflate(R.layout.foot_main, null, false);
            trendListview.addFooterView(viewFooter);
            trendListview.setAdapter(temperatureAdapter);
            trendListview.setOnLoadMoreListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).temperatureTimeBeforeList(
                userId, Content.DATA, phone, secret), HttpIdentifier.TEMPERATURE_TIME_BEFORELIST);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.TEMPERATURE_TIME_BEFORELIST:
//                mSrl.setRefreshing(false);
                TemperatureDatas temperatureDatas = JSON.parseObject(result, TemperatureDatas.class);
                for (int i = 0; i < temperatureDatas.getList().size(); i++) {
                    for (int j = (int) (30 * mPage); j < list_temperature.size(); j++) {
                        if (temperatureDatas.getList().get(i).getBYTIME().equals(list_temperature.get(j).getBYTIME())) {
                            list_temperature.get(j).setMORNINGVALUE(temperatureDatas.getList().get(i).getMORNINGVALUE());
                            list_temperature.get(j).setAFTERNOONVALUE(temperatureDatas.getList().get(i).getAFTERNOONVALUE());
                            list_temperature.get(j).setEVENINGVALUE(temperatureDatas.getList().get(i).getEVENINGVALUE());

                        }
                    }
                }
                temperatureAdapter.notifyDataSetChanged();
                break;
            case HttpIdentifier.TEMPERATURE_HAVE_DATA:
                FindByPidEntity findByPidEntity = JSON.parseObject(result,FindByPidEntity.class);
                new CalenderDialogTest(getActivity(),findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
                        list_temperature.clear();
                        mPage = 0;
                        for (long i = 30 * mPage; i < 30 + mPage * 30; i++) {
                            TemperatureDatas.ListBean temperatureBean = new TemperatureDatas.ListBean();
                            temperatureBean.setBYTIME(DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - 86400000 * i));
                            list_temperature.add(temperatureBean);
                        }
                        doHttp(RetrofitUtils.createApi(DataUrl.class).temperatureTimeBeforeList(
                                userId, Content.DATA, phone, secret), HttpIdentifier.TEMPERATURE_TIME_BEFORELIST);
                    }
                };
                break;
        }
    }

//    @Override
//    public void onRefresh(SwipyRefreshLayoutDirection direction) {
//        mPage = mPage + 1;
//
//        for (long i = 30 * mPage; i < 30 + mPage * 30; i++) {
//            TemperatureDatas.ListBean temperatureBean = new TemperatureDatas.ListBean();
//            temperatureBean.setBYTIME(DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - 86400000 * i));
//            list_temperature.add(temperatureBean);
//        }
////        Logger.e("beforetime", DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - (long) 86400000 * 30 * mPage));
//        doHttp(RetrofitUtils.createApi(DataUrl.class).temperatureTimeBeforeList(
//                userId, DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - (long) 86400000 * 30 * mPage), phone, secret), HttpIdentifier.TEMPERATURE_TIME_BEFORELIST);
//    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
//        mSrl.setRefreshing(false);
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
//        mSrl.setRefreshing(false);
    }


    @OnClick(R.id.iv_qushi)
    public void onClick() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(FindByPid.class).animalheatFindByPid(
                userId, phone, secret), HttpIdentifier.TEMPERATURE_HAVE_DATA);
    }

    @Override
    public void onLoadMore() {
        mPage = mPage + 1;
        for (long i = 30 * mPage; i < 30 + mPage * 30; i++) {
            TemperatureDatas.ListBean temperatureBean = new TemperatureDatas.ListBean();
            temperatureBean.setBYTIME(DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - 86400000 * i));
            list_temperature.add(temperatureBean);
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).temperatureTimeBeforeList(
                userId, DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - (long) 86400000 * 30 * mPage), phone, secret), HttpIdentifier.TEMPERATURE_TIME_BEFORELIST);
    }
}
