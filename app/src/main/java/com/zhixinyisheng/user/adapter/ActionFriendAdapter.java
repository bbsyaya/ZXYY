package com.zhixinyisheng.user.adapter;
/**
 * Created by tarena on 2016/11/4.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.domain.EaseUser;
import com.zhixinyisheng.user.R;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * created by Vampire
 * on: 2016/11/4 下午4:05
 */
public class ActionFriendAdapter extends BaseAdapter {
    private Context context;
    private List<EaseUser> picLists;

    public ActionFriendAdapter(Context context,List<EaseUser> picLists) {
        this.context = context;
        this.picLists = picLists;
    }


    @Override
    public int getCount() {
        return picLists.size();
    }

    @Override
    public Object getItem(int i) {
        return picLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MyViewHolder myViewHolder;
            if (view == null){
                view = LayoutInflater.from(context).inflate(R.layout.item_botom_friend,null,false);
                myViewHolder = new MyViewHolder(view);
                view.setTag(myViewHolder);
            }else {
                myViewHolder = (MyViewHolder) view.getTag();
            }

        EaseUser eu = (EaseUser) getItem(i);

            if (!eu.getUsername().equals("加好")){

                Glide.with(context).load(eu.getAvatar())
                        .placeholder(R.mipmap.ic_launcher2)//占位图
                        .error(R.mipmap.ic_launcher2)//加载错误图
                        .bitmapTransform(new CropCircleTransformation(context))//裁剪圆形
                        .into(myViewHolder.imageView);


//                Glide.with(context).load(picLists.get(i)).asBitmap().centerCrop().into(new BitmapImageViewTarget(myViewHolder.imageView){
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        super.setResource(resource);
//                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),resource);
//                        roundedBitmapDrawable.setCircular(true);
//                        myViewHolder.imageView.setImageDrawable(roundedBitmapDrawable);
//                    }
//                });
            }else {

                myViewHolder.imageView.setImageResource(R.drawable.add_pressed);
            }



        return view;
    }

    private class MyViewHolder {
        private ImageView imageView;
        public MyViewHolder(View view) {
            imageView= (ImageView) view.findViewById(R.id.img_bottom_item);
        }
    }


}
