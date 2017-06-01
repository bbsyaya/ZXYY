package com.zhixinyisheng.user.ui.pay;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayout;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.pay.IncomeDetailAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.domain.pay.IncomeDetail;
import com.zhixinyisheng.user.http.Pay;
import com.zhixinyisheng.user.ui.BaseFgt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 支出明细
 * Created by 焕焕 on 2017/1/8.
 */
public class ExpenditureDetailFgt extends BaseFgt implements SwipyRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener{
    @Bind(R.id.lv_incomedetail)
    ListView lvIncomedetail;
    @Bind(R.id.srl)
    SwipyRefreshLayout mSrl;
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    List<IncomeDetail.ListBean> list_incomedetail = new ArrayList<IncomeDetail.ListBean>();
    IncomeDetailAdapter incomeDetailAdapter;

    private int mPage = 1;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_incomedetail;
    }

    @Override
    public void initData() {
        try {
            mSrl.setOnRefreshListener(this);
            lvIncomedetail.setOnItemClickListener(this);
            mSrl.setColorSchemeColors(Color.parseColor("#25CDBC"));
            list_incomedetail.clear();
            incomeDetailAdapter = new IncomeDetailAdapter(getActivity(),list_incomedetail,R.layout.item_incomedetail);
            lvIncomedetail.setAdapter(incomeDetailAdapter);
            doHttp(RetrofitUtils.createApi(Pay.class).searchPay(phone, secret, userId, 10, mPage), HttpIdentifier.SEARCH_PAY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case HttpIdentifier.SEARCH_PAY:
                mSrl.setRefreshing(false);
                IncomeDetail incomeDetail = JSON.parseObject(result,IncomeDetail.class);
                list_incomedetail.addAll(incomeDetail.getList());
                if (list_incomedetail.size() == 0) {
                    llEmpty.setVisibility(View.VISIBLE);
                    mSrl.setVisibility(View.GONE);
                }else{
                    llEmpty.setVisibility(View.GONE);
                    mSrl.setVisibility(View.VISIBLE);
                }
                incomeDetailAdapter.notifyDataSetChanged();
                break;
            case 0:
                mSrl.setRefreshing(false);
                IncomeDetail incomeDetail0 = JSON.parseObject(result,IncomeDetail.class);
                list_incomedetail.clear();
                list_incomedetail.addAll(incomeDetail0.getList());
                incomeDetailAdapter.notifyDataSetChanged();
                break;
        }
    }
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            mPage = 1;
            doHttp(RetrofitUtils.createApi(Pay.class).searchPay(phone, secret, userId, 10, mPage), 0);
        } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            mPage++;
            doHttp(RetrofitUtils.createApi(Pay.class).searchPay(phone, secret, userId, 10, mPage), HttpIdentifier.SEARCH_PAY);

        }
    }
    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        mSrl.setRefreshing(false);
        llEmpty.setVisibility(View.VISIBLE);
        mSrl.setVisibility(View.GONE);
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        mSrl.setRefreshing(false);
        llEmpty.setVisibility(View.VISIBLE);
        mSrl.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("incomePayRecordId",list_incomedetail.get(position).getIncomePayRecordId());
        bundle.putString("ex_or_in","ex");
        startActivity(OrderDetailAty.class,bundle);
    }
}
