package com.zhixinyisheng.user.ui.data.ranking;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayout;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.ranking.VitalCapacitySortAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.datas.VitalCapacitySort;
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
 * 肺活量排行榜
 * Created by 焕焕 on 2017/4/19.
 */

public class VitalCapacitySortFgt extends BaseFgt implements SwipyRefreshLayout.OnRefreshListener {
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
    @Bind(R.id.srl_step)
    SwipyRefreshLayout srlStep;
    private int currentPage = 1;
    private List<VitalCapacitySort.ListBean> vitalList = new ArrayList<>();
    private VitalCapacitySortAdapter vitalCapacitySortAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_step_sort;//跟步数的一样，没必要再重建
    }

    @Override
    public void initData() {
        vitalCapacitySortAdapter = new VitalCapacitySortAdapter(getActivity(),vitalList,R.layout.item_step_sort);
        lvStepSort.setAdapter(vitalCapacitySortAdapter);
        srlStep.setOnRefreshListener(this);
        srlStep.setColorSchemeColors(Color.parseColor("#25CDBC"));
        getStepSort();
    }

    private void getStepSort() {
        showLoadingDialog(null);
        doHttp(RetrofitUtils.createApi(Ranking.class).vitalCapacitySort(UserManager.getUserInfo().getUserId(),
                showCount,
                currentPage,
                UserManager.getUserInfo().getPhone(),
                UserManager.getUserInfo().getSecret()), HttpIdentifier.STEP_SORT);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        VitalCapacitySort vitalCapacitySoft = JSON.parseObject(result,VitalCapacitySort.class);
        vitalList = vitalCapacitySoft.getList();
        if (currentPage==1){
            vitalCapacitySortAdapter.setDatas(vitalList);
            tvSort.setText(vitalCapacitySoft.getDb().getSort()+"");
            if (vitalCapacitySoft.getDb().getSort()>=4){
                tvSort.setBackgroundResource(R.color.white);
                tvSort.setTextColor(Colors.shallowColor);
            }else{
                tvSort.setBackgroundResource(R.drawable.shape_ranking_text);
                tvSort.setTextColor(Colors.WHITE);
            }
            tvNick.setText(R.string.wo);
            tvSteps.setText((int)vitalCapacitySoft.getDb().getNUM()+"");
            GlideUtil.loadCircleAvatar(getActivity(),UserManager.getUserInfo().getHeadUrl(),ivAvatar);
//            GlideUtil.loadImage(getActivity(),UserManager.getUserInfo().getHeadUrl(),ivAvatar);
//            ivAvatar.setBorderColor(getResources().getColor(R.color.maincolor));
            return;
        }
        if (vitalList != null && vitalList.size() != 0){
            vitalCapacitySortAdapter.addAll(vitalList);
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
