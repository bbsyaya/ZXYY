package com.zhixinyisheng.user.ui.data.ranking;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayout;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.ranking.StepSortAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.datas.StepSort;
import com.zhixinyisheng.user.http.Ranking;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Colors;
import com.zhixinyisheng.user.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 计步排行榜
 * Created by 焕焕 on 2017/4/19.
 */

public class StepSortFgt extends BaseFgt implements SwipyRefreshLayout.OnRefreshListener{
    private final int showCount = 10;
    @Bind(R.id.lv_step_sort)
    ListView lvStepSort;
    @Bind(R.id.tv_sort)
    TextView tvSort;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_nick)
    TextView tvNick;
    @Bind(R.id.tv_steps)
    TextView tvSteps;
//    @Bind(R.id.tv_me)
//    TextView tvMe;
    @Bind(R.id.srl_step)
    SwipyRefreshLayout srlStep;
    private int currentPage = 1;
    private List<StepSort.ListBean> stepList = new ArrayList<>();
    private StepSortAdapter stepSortAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_step_sort;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stepSortAdapter = new StepSortAdapter(getActivity(), stepList, R.layout.item_step_sort);
        lvStepSort.setAdapter(stepSortAdapter);
        srlStep.setOnRefreshListener(this);
        srlStep.setColorSchemeColors(Color.parseColor("#25CDBC"));
        getStepSort();
    }

    private void getStepSort() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(Ranking.class).stepSort(UserManager.getUserInfo().getUserId(),
                showCount,
                currentPage,
                UserManager.getUserInfo().getPhone(),
                UserManager.getUserInfo().getSecret()), HttpIdentifier.STEP_SORT);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        StepSort stepSort = JSON.parseObject(result, StepSort.class);
        stepList = stepSort.getList();
        if (currentPage==1){
            stepSortAdapter.setDatas(stepList);
            tvSort.setText(stepSort.getDb().getSort()+"");
            if (stepSort.getDb().getSort()>=4){
                tvSort.setBackgroundResource(R.color.white);
                tvSort.setTextColor(Colors.shallowColor);
            }else{
                tvSort.setBackgroundResource(R.drawable.shape_ranking_text);
                tvSort.setTextColor(Colors.WHITE);
            }
            tvNick.setText(R.string.wo);
            tvSteps.setText(stepSort.getDb().getSTEPS()+"");
            GlideUtil.loadCircleAvatar(getActivity(),UserManager.getUserInfo().getHeadUrl(),ivAvatar);
//            GlideUtil.loadImage(getActivity(),UserManager.getUserInfo().getHeadUrl(),ivAvatar);
//            ivAvatar.setBorderColor(getResources().getColor(R.color.maincolor));
            return;
        }
        if (stepList != null && stepList.size() != 0){
            stepSortAdapter.addAll(stepList);
        } else {
            showToast(getString(R.string.zanwushuju));
            currentPage--;
        }
        srlStep.setRefreshing(false);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        currentPage++;
        getStepSort();
    }
}
