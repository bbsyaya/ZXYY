package com.zhixinyisheng.user.ui.data.synthesize_info.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.BaseApplication;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.remind.Weather;
import com.zhixinyisheng.user.domain.remind.WeatherEn;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.IM.ui.FriendsDetialAty;
import com.zhixinyisheng.user.ui.MainAty;
import com.zhixinyisheng.user.ui.data.synthesize_info.adapter.SynthesizeInfoAdapter;
import com.zhixinyisheng.user.ui.data.synthesize_info.model.SynthesizeInfo;
import com.zhixinyisheng.user.ui.remind.UseMedicineActivity;
import com.zhixinyisheng.user.util.LanguageUtil;

import java.util.List;

import butterknife.Bind;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 综合信息
 * Created by 焕焕 on 2017/4/12.
 */

public class IntegratedInfoFgt extends BaseFgt implements AdapterView.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.lv_info)
    ListView lvInfo;
    TextView tvCity;
    //    TextView tvTime;
    ImageView ivArrow;
    TextView tvQing;
    TextView tvWd;
    TextView tvHighOrLow;
    TextView tvWeatherQuality;
    TextView tvContent;
    LinearLayout llWeather;
    LinearLayout llWeatherItem;
    private SynthesizeInfoAdapter synthesizeInfoAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_integrated_info;
    }

    @Override
    public void initData() {
        synthesizeInfoAdapter = new SynthesizeInfoAdapter(getActivity());
        View viewFooter = LayoutInflater.from(getActivity()).inflate(R.layout.foot_main, null, false);
        lvInfo.addFooterView(viewFooter);
        View viewHeader = LayoutInflater.from(getActivity()).inflate(R.layout.head_item_weather, null, false);
        tvCity = (TextView) viewHeader.findViewById(R.id.tv_city);
//        tvTime = (TextView) viewHeader.findViewById(R.id.tv_time);
        ivArrow = (ImageView) viewHeader.findViewById(R.id.iv_arrow);
        tvQing = (TextView) viewHeader.findViewById(R.id.tv_qing);
        tvWd = (TextView) viewHeader.findViewById(R.id.tv_wd);
        tvWeatherQuality = (TextView) viewHeader.findViewById(R.id.tv_weather_quality);
        tvHighOrLow = (TextView) viewHeader.findViewById(R.id.tv_high_or_low);
        tvContent = (TextView) viewHeader.findViewById(R.id.tv_content);
        llWeather = (LinearLayout) viewHeader.findViewById(R.id.ll_weather);
        llWeatherItem = (LinearLayout) viewHeader.findViewById(R.id.ll_weather_item);
        llWeatherItem.setOnClickListener(this);
        lvInfo.addHeaderView(viewHeader);
        lvInfo.setAdapter(synthesizeInfoAdapter);
        lvInfo.setOnItemClickListener(this);
        if (LanguageUtil.judgeLanguage().equals("zh")) {
            getZhWeather();
        }else if (LanguageUtil.judgeLanguage().equals("en")){
            getEnWeather();
        }
        loadData();
    }

    private void getEnWeather() {
        Call<ResponseBody> bodyCall = RetrofitUtils.createApi(DataUrl.class).weatherEnApi("shijiazhuang","d22bf2999055bdf81c467cbd46973e58");
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Request request = call.request();
                try {
                    String result = response.body().string();
//                    Log.e("getEnWeather",result);
                    WeatherEn weatherEn = JSON.parseObject(result, WeatherEn.class);
                    if (weatherEn.getCod()==200){//成功的时候
                        tvCity.setText(weatherEn.getName());
                        tvQing.setText(weatherEn.getWeather().get(0).getDescription());
                        tvWd.setText(weatherEn.getMain().getTemp()-273.15+"℃");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void getZhWeather() {
        Call<ResponseBody> bodyCall = RetrofitUtils.createApi(DataUrl.class).weatherApi(UserManager.getUserInfo().getCity(), "17fb265155d84352bfc342175bb3988f");
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Request request = call.request();
                try {
                    String result = response.body().string();
                    Weather weather = JSON.parseObject(result, Weather.class);
                    dispalyWeather(weather);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        doHttp(RetrofitUtils.createApi(DataUrl.class).synthesizeInfo(phone, secret, userId), HttpIdentifier.SYNTHESIZE_INFO);
    }

    private void loadData() {
        showLoadingContentDialog();
        doHttp(RetrofitUtils.createApi(DataUrl.class).synthesizeInfo(phone, secret, userId), HttpIdentifier.SYNTHESIZE_INFO);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.SYNTHESIZE_INFO:
                SynthesizeInfo synthesizeInfo = JSON.parseObject(result, SynthesizeInfo.class);
                List<SynthesizeInfo.ListBean> beanList = synthesizeInfo.getList();
                synthesizeInfoAdapter.setData(beanList);
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
    }

    private void dispalyWeather(Weather weather) {
        Weather.HeWeather5Bean weatherBean = weather.getHeWeather5().get(0);
        Weather.HeWeather5Bean.BasicBean basicBean = weatherBean.getBasic();
        Weather.HeWeather5Bean.NowBean nowBean = weatherBean.getNow();
        tvCity.setText(basicBean.getCity());
        String date = basicBean.getUpdate().getLoc();
//        tvTime.setText(date.substring(11, date.length()));
        tvQing.setText(nowBean.getCond().getTxt());
        //日报
        List<Weather.HeWeather5Bean.DailyForecastBean> dailyForecastBeanList = weatherBean.getDaily_forecast();
        //当天的信息
        Weather.HeWeather5Bean.DailyForecastBean dailyForecastBean = dailyForecastBeanList.get(0);
        tvWd.setText(nowBean.getTmp() + "°");

        Weather.HeWeather5Bean.DailyForecastBean.TmpBean tmpBean = dailyForecastBean.getTmp();
        tvHighOrLow.setText(tmpBean.getMin() + "~" + tmpBean.getMax() + "℃ ");

        Weather.HeWeather5Bean.AqiBean apiBean = weatherBean.getAqi();
        Weather.HeWeather5Bean.AqiBean.CityBean cityBean = apiBean.getCity();
        int pm25 = Integer.parseInt(cityBean.getPm25());
        if (pm25 < 50 || pm25 < 100) {
            tvWeatherQuality.setTextColor(Color.parseColor("#00c5cd"));
        } else if (pm25 < 150 || pm25 < 200) {
            tvWeatherQuality.setTextColor(Color.parseColor("#ff843f"));
        } else {
            tvWeatherQuality.setTextColor(Color.parseColor("#ec2727"));
        }
        tvWeatherQuality.setText(pm25 + cityBean.getQlty());

        Weather.HeWeather5Bean.SuggestionBean suggestionBean = weatherBean.getSuggestion();
        Weather.HeWeather5Bean.SuggestionBean.DrsgBean drsgBean = suggestionBean.getDrsg();
        tvContent.setText(drsgBean.getTxt());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Activity activity = getActivity();
        int count = synthesizeInfoAdapter.getData().size() + 2;
        if (!(activity instanceof MainAty) || position == 0 || position == count - 1) {
            return;
        }
        MainAty mainAty = (MainAty) activity;
        //1心率 2血压 3肺活量 4舌诊 5睡眠质量 6BMI 7血糖 8体温 9步数 10 温馨提示
        SynthesizeInfo.ListBean bean = synthesizeInfoAdapter.getItem(position - 1);
        int type = bean.getType();
        if (type == 1) {
            mainAty.setCurrentItem(2);
        } else if (type == 2) {
            mainAty.setCurrentItem(3);
        } else if (type == 3) {
            mainAty.setCurrentItem(4);
        } else if (type == 4) {
            mainAty.setCurrentItem(5);
        } else if (type == 5) {
            mainAty.setCurrentItem(7);
        } else if (type == 6) {
            mainAty.setCurrentItem(8);
        } else if (type == 7) {
            mainAty.setCurrentItem(10);
        } else if (type == 8) {
            mainAty.setCurrentItem(9);
        } else if (type == 9) {
            mainAty.setCurrentItem(6);
        } else if (type == 10) {//患者端：医生提醒患者该用药了
            startActivity(UseMedicineActivity.class, null);
        } else if (type == 11) {//患者端：医生提醒给患者药用完了
            startActivity(UseMedicineActivity.class, null);
        } else if (type == 12) {//患者端：患者的药没有服用
            startActivity(UseMedicineActivity.class, null);
        } else if (type == 13) {//医生端：患者服了药
            BaseApplication.userId = bean.getInfo();
            startActivity(FriendsDetialAty.class,null);
        } else if (type == 14) {//医生端：患者没服什么药
            BaseApplication.userId = bean.getInfo();
            startActivity(FriendsDetialAty.class,null);
        }
    }

    @Override
    public void onClick(View v) {
        if (LanguageUtil.judgeLanguage().equals("zh")) {
            if (llWeather.getVisibility() == View.GONE) {
                ivArrow.setImageResource(R.mipmap.hyd_btn_sq);
                llWeather.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.VISIBLE);
            } else {
                ivArrow.setImageResource(R.mipmap.hyd_btn_zk);
                llWeather.setVisibility(View.GONE);
                tvContent.setVisibility(View.GONE);
            }
        }else if (LanguageUtil.judgeLanguage().equals("en")){
            if (llWeather.getVisibility() == View.GONE) {
                ivArrow.setImageResource(R.mipmap.hyd_btn_sq);
                llWeather.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.GONE);
            } else {
                ivArrow.setImageResource(R.mipmap.hyd_btn_zk);
                llWeather.setVisibility(View.GONE);
                tvContent.setVisibility(View.GONE);
            }
        }



    }
}
