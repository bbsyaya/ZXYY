package com.zhixinyisheng.user.adapter;

import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.FriendList;
import com.zhixinyisheng.user.domain.MyFriend;

import java.util.List;


/**
 * 查看我的健康数据
 * Created by gjj on 2016/10/23.
 */
public class ChooseAdapter extends CommonAdapter<FriendList.ListBean>{

    public ChooseAdapter(Context context, List<FriendList.ListBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }



    @Override
    public void convert(ViewHolder holder, FriendList.ListBean item, int positon) {


        holder.setImageByUrl(R.id.imageview123,item.getHeadUrl());
//        Glide.with(context).load(item.getHEADURL()).
//                placeholder(R.mipmap.ic_launcher2).
//                error(R.mipmap.ic_launcher2).
//                bitmapTransform(new CropCircleTransformation(context)).
//                into((ImageView) holder.getView(R.id.friend_img_item));
//

        holder.setTextViewText(R.id.textview123,item.getName());
        if(item.getDatas()==1){
            holder.setCheckBoxChecked(R.id.checkbox123,true);
        }else {
            holder.setCheckBoxChecked(R.id.checkbox123,false);
        }

    }


}
