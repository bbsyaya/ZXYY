package com.zhixinyisheng.user.ui.mydoctor.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.BigImageAdapter;
import com.zhixinyisheng.user.ui.BaseAty;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 看大图
 */
public class LookBigPicActivity extends BaseAty implements ViewPager.OnPageChangeListener {
    public final static String EXTRA_LIST = "EXTRA_LIST";
    public final static String EXTRA_POS = "POS";
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tv_tag)
    TextView tvTag;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    private BigImageAdapter mBigImageAdapter;
    private List<String> imageList;
    private String choosePos, listSize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_look_big_pic;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("看大图");

        String picUrl = getIntent().getStringExtra(EXTRA_LIST);

        if (picUrl != null && !"".equals(picUrl)) {
            String[] arrayStr = picUrl.split(",");
            imageList = Arrays.asList(arrayStr);
        }
        mBigImageAdapter = new BigImageAdapter(this, imageList);
        choosePos = getIntent().getStringExtra(EXTRA_POS);


        viewPager.setAdapter(mBigImageAdapter);
        viewPager.setOnPageChangeListener(this);

        listSize = String.valueOf(imageList.size());
        tvTag.setText(1 + "/" + listSize);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        String select = String.valueOf(position + 1);
        tvTag.setText(select + "/" + listSize);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.iv_title_left)
    public void onClick() {
        this.finish();
    }
}
