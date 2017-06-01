package com.zhixinyisheng.user.ui.data.xuetang;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.BloodSugerAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.datas.BloodSugerDatas;
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
 * 血糖（新）
 * Created by 焕焕 on 2017/1/13.
 */
public class BloodSugerFgt extends BaseFgt implements OnLoadMoreListener {
    @Bind(R.id.trend_listview)
    PullToListView trendListview;
//    @Bind(R.id.srl)
//    SwipyRefreshLayout mSrl;
    List<BloodSugerDatas.ListBean> list_bloodsuger = new ArrayList<>();
    BloodSugerAdapter bloodSugerAdapter;
    private long mPage = 0;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_bloodsuger;
    }

    @Override
    public void initData() {
        Content.bloodsugar++;
        try {
//            showLoadingContentDialog();
            list_bloodsuger.clear();
            mPage = 0;
            for (long i = 30 * mPage; i < 30 + mPage * 30; i++) {
                BloodSugerDatas.ListBean bloodsugerBean = new BloodSugerDatas.ListBean();
                bloodsugerBean.setByTime(DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - 86400000 * i));
                list_bloodsuger.add(bloodsugerBean);
            }
            trendListview.setShowFoot(false);
            bloodSugerAdapter = new BloodSugerAdapter(getActivity(), list_bloodsuger, R.layout.bloodtrend_item);
            View viewFooter = LayoutInflater.from(getActivity()).inflate(R.layout.foot_main, null, false);
            trendListview.addFooterView(viewFooter);
            trendListview.setAdapter(bloodSugerAdapter);
            trendListview.setOnLoadMoreListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).bloodSugerTimeBeforeList(
                userId, Content.DATA, phone, secret), HttpIdentifier.BLOODSUGER_TIME_BEFORELIST);
    }


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.BLOODSUGER_TIME_BEFORELIST:
//                mSrl.setRefreshing(false);
                BloodSugerDatas bloodSugerDatas = JSON.parseObject(result, BloodSugerDatas.class);
                for (int i = 0; i < bloodSugerDatas.getList().size(); i++) {
                    for (int j = (int) (30 * mPage); j < list_bloodsuger.size(); j++) {
                        if (bloodSugerDatas.getList().get(i).getByTime().equals(list_bloodsuger.get(j).getByTime())) {
                            list_bloodsuger.get(j).setBREAKFASTBEFORE(bloodSugerDatas.getList().get(i).getBREAKFASTBEFORE());
                            list_bloodsuger.get(j).setBREAKFASTBEFOREC(bloodSugerDatas.getList().get(i).getBREAKFASTBEFOREC());

                            list_bloodsuger.get(j).setBREAKFASTAFTER(bloodSugerDatas.getList().get(i).getBREAKFASTAFTER());
                            list_bloodsuger.get(j).setBREAKFASTAFTERC(bloodSugerDatas.getList().get(i).getBREAKFASTAFTERC());

                            list_bloodsuger.get(j).setLUNCHBEFORE(bloodSugerDatas.getList().get(i).getLUNCHBEFORE());
                            list_bloodsuger.get(j).setLUNCHBEFOREC(bloodSugerDatas.getList().get(i).getLUNCHBEFOREC());

                            list_bloodsuger.get(j).setLUNCHAFTER(bloodSugerDatas.getList().get(i).getLUNCHAFTER());
                            list_bloodsuger.get(j).setLUNCHAFTERC(bloodSugerDatas.getList().get(i).getLUNCHAFTERC());

                            list_bloodsuger.get(j).setDINNERBEFORE(bloodSugerDatas.getList().get(i).getDINNERBEFORE());
                            list_bloodsuger.get(j).setDINNERBEFOREC(bloodSugerDatas.getList().get(i).getDINNERBEFOREC());

                            list_bloodsuger.get(j).setDINNERAFTER(bloodSugerDatas.getList().get(i).getDINNERAFTER());
                            list_bloodsuger.get(j).setDINNERAFTERC(bloodSugerDatas.getList().get(i).getDINNERAFTERC());

                            list_bloodsuger.get(j).setSLEEPBEFORE(bloodSugerDatas.getList().get(i).getSLEEPBEFORE());
                            list_bloodsuger.get(j).setSLEEPBEFOREC(bloodSugerDatas.getList().get(i).getSLEEPBEFOREC());

                        }
                    }
                }
                bloodSugerAdapter.notifyDataSetChanged();
                break;
            case HttpIdentifier.BLOODSUGER_HAVE_DATA:
                FindByPidEntity findByPidEntity = JSON.parseObject(result, FindByPidEntity.class);
                new CalenderDialogTest(getActivity(), findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
                        list_bloodsuger.clear();
                        mPage = 0;
                        for (long i = 30 * mPage; i < 30 + mPage * 30; i++) {
                            BloodSugerDatas.ListBean bloodsugerBean = new BloodSugerDatas.ListBean();
                            bloodsugerBean.setByTime(DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - 86400000 * i));
                            list_bloodsuger.add(bloodsugerBean);
                        }
                        doHttp(RetrofitUtils.createApi(DataUrl.class).bloodSugerTimeBeforeList(
                                userId, Content.DATA, phone, secret), HttpIdentifier.BLOODSUGER_TIME_BEFORELIST);
                    }
                };
                break;
        }
    }
//
//    @Override
//    public void onRefresh(SwipyRefreshLayoutDirection direction) {
//        mPage = mPage + 1;
//
//        for (long i = 30 * mPage; i < 30 + mPage * 30; i++) {
//            BloodSugerDatas.ListBean bloodSugerBean = new BloodSugerDatas.ListBean();
//            bloodSugerBean.setByTime(DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - 86400000 * i));
//            list_bloodsuger.add(bloodSugerBean);
//        }
////        Logger.e("beforetime", DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - (long) 86400000 * 30 * mPage));
//        doHttp(RetrofitUtils.createApi(DataUrl.class).bloodSugerTimeBeforeList(
//                userId, DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - (long) 86400000 * 30 * mPage), phone, secret), HttpIdentifier.BLOODSUGER_TIME_BEFORELIST);
//    }

//    @Override
//    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
//        super.onFailure(result, call, response, what);
//        mSrl.setRefreshing(false);
//    }
//
//    @Override
//    public void onError(Call<ResponseBody> call, Throwable t, int what) {
//        super.onError(call, t, what);
//        mSrl.setRefreshing(false);
//    }

    @OnClick(R.id.iv_qushi)
    public void onClick() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(FindByPid.class).bloodsugarFindByPid(
                userId, phone, secret), HttpIdentifier.BLOODSUGER_HAVE_DATA);
    }

    @Override
    public void onLoadMore() {
        mPage = mPage + 1;

        for (long i = 30 * mPage; i < 30 + mPage * 30; i++) {
            BloodSugerDatas.ListBean bloodSugerBean = new BloodSugerDatas.ListBean();
            bloodSugerBean.setByTime(DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - 86400000 * i));
            list_bloodsuger.add(bloodSugerBean);
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).bloodSugerTimeBeforeList(
                userId, DateTool.getFormatDate(DateTool.strTimeToTimestamp(Content.DATA) - (long) 86400000 * 30 * mPage), phone, secret), HttpIdentifier.BLOODSUGER_TIME_BEFORELIST);

    }
}
