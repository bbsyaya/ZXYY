package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhixinyisheng.user.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人: Yuanyx
 * <p>
 * 创建时间: 2016/12/15  17:12
 * <p>
 * 类说明:
 */

public class BigImageAdapter extends PagerAdapter {
    private static final String TAG = "BigImageAdapter";
    private Context mContext;
    private List<String> imageList;
    private List<ImageView> imageViewList = new ArrayList<>();

    public BigImageAdapter(Context context, List<String> imageList) {
        this.mContext = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        if (imageList != null) {
            return imageList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e(TAG, "destroyItem--->" + position);
        container.removeView(imageViewList.get(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e(TAG, "instantiateItem--->" + position);
        ImageView imageView = new ImageView(mContext);
        String tImage = imageList.get(position);
        imageViewList.add(imageView);
        GlideUtil.loadlocalImage(mContext, tImage, imageView);
        container.addView(imageView);
        return imageView;
    }
}
