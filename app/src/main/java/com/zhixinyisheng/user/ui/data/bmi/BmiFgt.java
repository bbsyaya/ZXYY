package com.zhixinyisheng.user.ui.data.bmi;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.view.ScaleRulerView;
import com.zhixinyisheng.user.view.SlantedTextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * bmi指数界面
 * Created by 焕焕 on 2016/11/2.
 */
public class BmiFgt extends BaseFgt {
    @Bind(R.id.scaleWheelView_height)
    ScaleRulerView mHeightWheelView;
    @Bind(R.id.tv_user_height_value)
    TextView mHeightValue;

    @Bind(R.id.scaleWheelView_weight)
    ScaleRulerView mWeightWheelView;
    @Bind(R.id.tv_user_weight_value)
    TextView mWeightValue;
    @Bind(R.id.iv_qushi)
    ImageView ivQushi;
    @Bind(R.id.btn_jisuan)
    Button btnJisuan;
    @Bind(R.id.tv_bmi_result)
    TextView tvBmiResult;
    @Bind(R.id.tv_message)
    TextView tvMessage;
    @Bind(R.id.rl_bmi_result)
    RelativeLayout rlBmiResult;
    @Bind(R.id.slant_one)
    SlantedTextView slantOne;
    @Bind(R.id.slant_two)
    SlantedTextView slantTwo;
    private float mHeight = 170;
    private float mMaxHeight = 220;
    private float mMinHeight = 100;


    private float mWeight = 60.0f;
    private float mMaxWeight = 200;
    private float mMinWeight = 25;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_bmi;
    }

    @Override
    public void initData() {
        Content.bmi++;
        try {
            mHeightValue.setText((int) mHeight + "");
            mWeightValue.setText(mWeight + "");
            mHeightWheelView.initViewParam(mHeight, mMaxHeight, mMinHeight);
            mWeightWheelView.initViewParam(mWeight, mMaxWeight, mMinWeight);
            mHeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
                @Override
                public void onValueChange(float value) {
                    mHeightValue.setText((int) value + "");
                    mHeight = value;
                }
            });
            mWeightWheelView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
                @Override
                public void onValueChange(float value) {
                    mWeightValue.setText(value + "");

                    mWeight = value;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }






    }
    /**
     * BMI：黑（18.5-23.9）、黄（24-27.9）、红（≥28）、蓝（<18.5）
     * 反馈信息：黑：身材不错，快说说你是怎么保持的！
     * 黄：看起来肉肉的，但还没到胖纸的程度，比上不足比下有余，稍微努努力，就能瘦成一道闪电，我看好你哦~
     * 红：喂！你挡住我信号啦！
     * 蓝：您过轻哦，建议要更多地关注营养摄入问题,尤其是铁、叶酸、维生素B12等造血物质的摄入。
     */
    private void jisuanbmi() {
        double i, o;
        double p;
        String rangeValue,colorFlag;
        i = mHeight;
        o = mWeight;
        p = o / ((i / 100) * (i / 100));

        if (p>=18.5&&p<=23.9){
            rangeValue="1";
            colorFlag = "1";
        }else if (p>=24&&p<=27.9){
            rangeValue = "2";
            colorFlag = "2";
        }else {
            rangeValue = "4";
            colorFlag = "3";
        }
        doHttp(RetrofitUtils.createApi(DataUrl.class).addBMI(
                userId,p+"",rangeValue,time,colorFlag,phone,secret),0);
        String s = String.format("%.2f", p);
        rlBmiResult.setVisibility(View.VISIBLE);
        tvBmiResult.setText(getResources().getString(R.string.home_bmi)+"：" + s);
        tvMessage.setVisibility(View.VISIBLE);
        if (p < 18.5) {
            tvMessage.setText(getResources().getString(R.string.YourWeightLight));
        } else if (18.5 <= p && p <= 23.9) {
            tvMessage.setText(getResources().getString(R.string.YouHavePerfect));
        } else if (p > 23.9 && p <= 27.9) {
            tvMessage.setText(getResources().getString(R.string.YouLookFat));
        } else if (p > 27.9) {
            tvMessage.setText(getResources().getString(R.string.YouAreOverweight));
        }

    }

    @OnClick({R.id.iv_qushi, R.id.btn_jisuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_qushi:
                startActivity(BmiQuShiAty.class,null);
                break;
            case R.id.btn_jisuan:
                jisuanbmi();
                break;
        }
    }
}
