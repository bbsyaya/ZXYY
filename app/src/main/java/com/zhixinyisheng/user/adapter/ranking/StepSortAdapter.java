package com.zhixinyisheng.user.adapter.ranking;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.datas.StepSort;
import com.zhixinyisheng.user.util.Colors;
import com.zhixinyisheng.user.util.GlideUtil;

import java.util.List;

import static com.zhixinyisheng.user.R.id.iv_avatar;

/**
 * 步数
 * Created by 焕焕 on 2017/4/19.
 */

public class StepSortAdapter extends CommonAdapter<StepSort.ListBean>{
    private TextView tvSort,tvNick,tvSteps;
    private ImageView ivAvatar;
    private Context context;
    public StepSortAdapter(Context context, List<StepSort.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, StepSort.ListBean item, int positon) {
        tvSort = holder.getView(R.id.tv_sort);
        tvNick = holder.getView(R.id.tv_nick);
        tvSteps = holder.getView(R.id.tv_steps);
        ivAvatar = holder.getView(iv_avatar);
        tvSort.setText(item.getSort()+"");
        if (item.getSort()==1){
            tvSort.setBackgroundResource(R.drawable.shape_ranking_text);
            tvSort.setTextColor(Colors.WHITE);
        }else if (item.getSort()==2){
            tvSort.setBackgroundResource(R.drawable.shape_ranking_text);
            tvSort.setTextColor(Colors.WHITE);
        }else if (item.getSort()==3){
            tvSort.setBackgroundResource(R.drawable.shape_ranking_text);
            tvSort.setTextColor(Colors.WHITE);
        }else{
            tvSort.setBackgroundResource(R.color.white);
            tvSort.setTextColor(Colors.shallowColor);
        }

        tvSteps.setText(item.getSTEPS()+"");
        GlideUtil.loadCircleAvatar(context,item.getHeadUrl(),ivAvatar);
//        GlideUtil.loadImage(context,item.getHeadUrl(),ivAvatar);
        if (item.getUserId().equals(UserManager.getUserInfo().getUserId())){
//            ivAvatar.setBorderColor(context.getResources().getColor(R.color.maincolor));
            tvNick.setText(context.getString(R.string.wo));
        }else{
//            ivAvatar.setBorderColor(Color.WHITE);
            EaseUserUtils.setUserNick(item.getUserId(), tvNick);
        }
    }
}
