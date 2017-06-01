package com.zhixinyisheng.user.ui.data.xuetang;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.Blood;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.http.FindByPid;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 血糖
 * Created by 焕焕 on 2016/11/7.
 */
public class XueTangFgt extends BaseFgt {
    @Bind(R.id.btn_tianjia)
    ImageView btnTianjia;
    @Bind(R.id.iv_qushi)
    ImageView ivQushi;
    @Bind(R.id.trend_listview_blood)
    ListView lv;
//    @Bind(R.id.tv_bottom_xuetang)
//    TextView tv_bottom_xuetang;
    //存储每天血糖的集合
    private List<Blood> list = new ArrayList<>();
    //一天血糖值的实体类
    private Blood info;

    @Override
    public int getLayoutId() {
        return R.layout.fgt_xuetang;
    }

    @Override
    public void initData() {
        Content.bloodsugar++;
        if (Content.myHealthFlag == 1 || !UserManager.getUserInfo().getUserId().equals(userId)) {
            btnTianjia.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        tv_bottom_xuetang.setHeight(MainActivity.di_height-5);
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueTangZXT(
                userId, time, phone, secret), 0);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (what==6){
            new CalenderDialogTest(getActivity(),null) {
                @Override
                public void getZXTData() {

                    doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueTangZXT(
                            userId, Content.DATA, phone, secret), 0);
                }
            };
        }

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        if (what==0){
            try {
                JSONObject object = new JSONObject(result);
                if (object.getString("result").equals("0000")) {
//                Toast.makeText(getActivity(), "血糖数据获取成功", Toast.LENGTH_SHORT).show();
                    JSONArray array = object.getJSONArray("bloodSugarList");
                    list.clear();


                    if (array.length() >= 7) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = (JSONObject) array.get(i);
                            String BREAKFASTBEFORE = null;
                            try {
                                BREAKFASTBEFORE = obj.getString("BREAKFASTBEFORE");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                BREAKFASTBEFORE = "";
                            }
                            String BREAKFASTBEFOREC = null;
                            try {
                                BREAKFASTBEFOREC = obj.getString("BREAKFASTBEFOREC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                BREAKFASTBEFOREC = "";
                            }
                            String BREAKFASTAFTER = null;
                            try {
                                BREAKFASTAFTER = obj.getString("BREAKFASTAFTER");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                BREAKFASTAFTER = "";
                            }
                            String BREAKFASTAFTERC = null;
                            try {
                                BREAKFASTAFTERC = obj.getString("BREAKFASTAFTERC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                BREAKFASTAFTERC = "";
                            }
                            String LUNCHBEFORE = null;
                            try {
                                LUNCHBEFORE = obj.getString("LUNCHBEFORE");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LUNCHBEFORE = "";
                            }
                            String LUNCHBEFOREC = null;
                            try {
                                LUNCHBEFOREC = obj.getString("LUNCHBEFOREC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LUNCHBEFOREC = "";
                            }
                            String LUNCHAFTER = null;
                            try {
                                LUNCHAFTER = obj.getString("LUNCHAFTER");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LUNCHAFTER = "";
                            }
                            String LUNCHAFTERC = null;
                            try {
                                LUNCHAFTERC = obj.getString("LUNCHAFTERC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LUNCHAFTERC = "";
                            }

                            String DINNERBEFORE = null;
                            try {
                                DINNERBEFORE = obj.getString("DINNERBEFORE");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                DINNERBEFORE = "";
                            }
                            String DINNERBEFOREC = null;
                            try {
                                DINNERBEFOREC = obj.getString("DINNERBEFOREC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                DINNERBEFOREC = "";
                            }
                            String DINNERAFTER = null;
                            try {
                                DINNERAFTER = obj.getString("DINNERAFTER");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                DINNERAFTER = "";
                            }
                            String DINNERAFTERC = null;
                            try {
                                DINNERAFTERC = obj.getString("DINNERAFTERC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                DINNERAFTERC = "";
                            }
                            String SLEEPBEFORE = null;
                            try {
                                SLEEPBEFORE = obj.getString("SLEEPBEFORE");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                SLEEPBEFORE = "";
                            }
                            String SLEEPBEFOREC = null;
                            try {
                                SLEEPBEFOREC = obj.getString("SLEEPBEFOREC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                SLEEPBEFOREC = "";
                            }
                            String time = obj.getString("byTime");
                            info = new Blood();
                            info.setDate(time);
                            info.setMor_before(BREAKFASTBEFORE);
                            info.setMor_before_color(BREAKFASTBEFOREC);
                            info.setMor_after(BREAKFASTAFTER);
                            info.setMor_after_color(BREAKFASTAFTERC);
                            info.setLun_before(LUNCHBEFORE);
                            info.setLun_before_color(LUNCHBEFOREC);
                            info.setLun_after(LUNCHAFTER);
                            info.setLun_after_color(LUNCHAFTERC);
                            info.setDinner_before(DINNERBEFORE);
                            info.setDinner_before_color(DINNERBEFOREC);
                            info.setDinner_after(DINNERAFTER);
                            info.setDinner_after_color(DINNERAFTERC);
                            info.setNight(SLEEPBEFORE);
                            info.setNight_color(SLEEPBEFOREC);
                            list.add(info);
                            Log.e("数据", list.toString());
                        }
                    } else {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = (JSONObject) array.get(i);
                            String BREAKFASTBEFORE = null;
                            try {
                                BREAKFASTBEFORE = obj.getString("BREAKFASTBEFORE");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                BREAKFASTBEFORE = "";
                            }
                            String BREAKFASTBEFOREC = null;
                            try {
                                BREAKFASTBEFOREC = obj.getString("BREAKFASTBEFOREC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                BREAKFASTBEFOREC = "";
                            }
                            String BREAKFASTAFTER = null;
                            try {
                                BREAKFASTAFTER = obj.getString("BREAKFASTAFTER");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                BREAKFASTAFTER = "";
                            }
                            String BREAKFASTAFTERC = null;
                            try {
                                BREAKFASTAFTERC = obj.getString("BREAKFASTAFTERC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                BREAKFASTAFTERC = "";
                            }
                            String LUNCHBEFORE = null;
                            try {
                                LUNCHBEFORE = obj.getString("LUNCHBEFORE");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LUNCHBEFORE = "";
                            }
                            String LUNCHBEFOREC = null;
                            try {
                                LUNCHBEFOREC = obj.getString("LUNCHBEFOREC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LUNCHBEFOREC = "";
                            }
                            String LUNCHAFTER = null;
                            try {
                                LUNCHAFTER = obj.getString("LUNCHAFTER");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LUNCHAFTER = "";
                            }
                            String LUNCHAFTERC = null;
                            try {
                                LUNCHAFTERC = obj.getString("LUNCHAFTERC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LUNCHAFTERC = "";
                            }

                            String DINNERBEFORE = null;
                            try {
                                DINNERBEFORE = obj.getString("DINNERBEFORE");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                DINNERBEFORE = "";
                            }
                            String DINNERBEFOREC = null;
                            try {
                                DINNERBEFOREC = obj.getString("DINNERBEFOREC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                DINNERBEFOREC = "";
                            }
                            String DINNERAFTER = null;
                            try {
                                DINNERAFTER = obj.getString("DINNERAFTER");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                DINNERAFTER = "";
                            }
                            String DINNERAFTERC = null;
                            try {
                                DINNERAFTERC = obj.getString("DINNERAFTERC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                DINNERAFTERC = "";
                            }
                            String SLEEPBEFORE = null;
                            try {
                                SLEEPBEFORE = obj.getString("SLEEPBEFORE");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                SLEEPBEFORE = "";
                            }
                            String SLEEPBEFOREC = null;
                            try {
                                SLEEPBEFOREC = obj.getString("SLEEPBEFOREC");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                SLEEPBEFOREC = "";
                            }
                            String time = obj.getString("byTime");
                            info = new Blood();
                            info.setDate(time);
                            info.setMor_before(BREAKFASTBEFORE);
                            info.setMor_before_color(BREAKFASTBEFOREC);
                            info.setMor_after(BREAKFASTAFTER);
                            info.setMor_after_color(BREAKFASTAFTERC);
                            info.setLun_before(LUNCHBEFORE);
                            info.setLun_before_color(LUNCHBEFOREC);
                            info.setLun_after(LUNCHAFTER);
                            info.setLun_after_color(LUNCHAFTERC);
                            info.setDinner_before(DINNERBEFORE);
                            info.setDinner_before_color(DINNERBEFOREC);
                            info.setDinner_after(DINNERAFTER);
                            info.setDinner_after_color(DINNERAFTERC);
                            info.setNight(SLEEPBEFORE);
                            info.setNight_color(SLEEPBEFOREC);
                            list.add(info);
                            Log.e("数据", list.toString());
                        }

                        for (int i = 0; i < 7 - array.length(); i++) {
                            info = new Blood();
                            info.setDate("");
                            info.setMor_before("");
                            info.setMor_before_color("");
                            info.setMor_after("");
                            info.setMor_after_color("");
                            info.setLun_before("");
                            info.setLun_before_color("");
                            info.setLun_after("");
                            info.setLun_after_color("");
                            info.setDinner_before("");
                            info.setDinner_before_color("");
                            info.setDinner_after("");
                            info.setDinner_after_color("");
                            info.setNight("");
                            info.setNight_color("");
                            list.add(info);
                        }

                    }
                    MyAdapter adpter = new MyAdapter(getActivity(), list);
                    lv.setAdapter(adpter);
                    // adpter.notifyDataSetChanged();

                } else if (object.getString("result").equals("1000")) {
                    Log.e("到这了", "没有查询到数据");
                    list.clear();
                    for (int i = 0; i < 7; i++) {
                        info = new Blood();
                        info.setDate("");
                        info.setMor_before("");
                        info.setMor_before_color("");
                        info.setMor_after("");
                        info.setMor_after_color("");
                        info.setLun_before("");
                        info.setLun_before_color("");
                        info.setLun_after("");
                        info.setLun_after_color("");
                        info.setDinner_before("");
                        info.setDinner_before_color("");
                        info.setDinner_after("");
                        info.setDinner_after_color("");
                        info.setNight("");
                        info.setNight_color("");
                        list.add(info);
                    }
                    MyAdapter adpter = new MyAdapter(getActivity(), list);
                    lv.setAdapter(adpter);
                    // adpter.notifyDataSetChanged();

                } else if (object.getString("result").equals("9993")) {
                    Toast.makeText(getActivity(), "ERROR9993", Toast.LENGTH_SHORT).show();
                } else if (object.getString("result").equals("9999")) {
                    Toast.makeText(getActivity(), "ERROR9999", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            FindByPidEntity findByPidEntity = JSON.parseObject(result,FindByPidEntity.class);
            new CalenderDialogTest(getActivity(),findByPidEntity.getList()) {
                @Override
                public void getZXTData() {
//                        showLoadingDialog(null);
                    doHttp(RetrofitUtils.createApi(DataUrl.class).checkXueTangZXT(
                            userId, Content.DATA, phone, secret), 0);
                }
            };
        }
    }



    class MyAdapter extends BaseAdapter {
        List<Blood> list = new ArrayList<>();
        LayoutInflater inflater;

        public MyAdapter(Context context, List<Blood> list) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.bloodtrend_item, null);
                holder.bre_before = (TextView) convertView.findViewById(R.id.item_mor_befor);
                holder.bre_after = (TextView) convertView.findViewById(R.id.item_mor_after);
                holder.lun_before = (TextView) convertView.findViewById(R.id.item_lunch_before);
                holder.lun_after = (TextView) convertView.findViewById(R.id.item_lunch_after);
                holder.dinner_before = (TextView) convertView.findViewById(R.id.item_dinner_before);
                holder.dinner_after = (TextView) convertView.findViewById(R.id.item_dinner_after);
                holder.night = (TextView) convertView.findViewById(R.id.item_sleebefore);
                holder.date = (TextView) convertView.findViewById(R.id.item_date_blood);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.bre_before.setText(list.get(position).getMor_before());
            if (!list.get(position).getMor_before_color().isEmpty()) {
                if (list.get(position).getMor_before_color().equals("1")) {
                    holder.bre_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
                } else if (list.get(position).getMor_before_color().equals("2")) {
                    holder.bre_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
                } else if (list.get(position).getMor_before_color().equals("3")) {
                    holder.bre_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
                }
            }
            holder.bre_after.setText(list.get(position).getMor_after());
            if (!list.get(position).getMor_after_color().isEmpty()) {
                if (list.get(position).getMor_after_color().equals("1")) {
                    holder.bre_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
                } else if (list.get(position).getMor_after_color().equals("2")) {
                    holder.bre_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
                } else if (list.get(position).getMor_after_color().equals("3")) {
                    holder.bre_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
                }
            }
            holder.lun_before.setText(list.get(position).getLun_before());

            if (!list.get(position).getLun_before_color().isEmpty()) {
                if (list.get(position).getLun_before_color().equals("1")) {
                    holder.lun_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
                } else if (list.get(position).getLun_before_color().equals("2")) {
                    holder.lun_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
                } else if (list.get(position).getLun_before_color().equals("3")) {
                    holder.lun_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
                }
            }

            holder.lun_after.setText(list.get(position).getLun_after());

            if (!list.get(position).getLun_after_color().isEmpty()) {
                if (list.get(position).getLun_after_color().equals("1")) {
                    holder.lun_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
                } else if (list.get(position).getLun_after_color().equals("2")) {
                    holder.lun_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
                } else if (list.get(position).getLun_after_color().equals("3")) {
                    holder.lun_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
                }
            }

            holder.dinner_before.setText(list.get(position).getDinner_before());

            if (!list.get(position).getDinner_before_color().isEmpty()) {
                if (list.get(position).getDinner_before_color().equals("1")) {
                    holder.dinner_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
                } else if (list.get(position).getDinner_before_color().equals("2")) {
                    holder.dinner_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
                } else if (list.get(position).getDinner_before_color().equals("3")) {
                    holder.dinner_before.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
                }
            }
            holder.dinner_after.setText(list.get(position).getDinner_after());

            if (!list.get(position).getDinner_after_color().isEmpty()) {
                if (list.get(position).getDinner_after_color().equals("1")) {
                    holder.dinner_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
                } else if (list.get(position).getDinner_after_color().equals("2")) {
                    holder.dinner_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
                } else if (list.get(position).getDinner_after_color().equals("3")) {
                    holder.dinner_after.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
                }
            }
            holder.date.setText(list.get(position).getDate());
            holder.night.setText(list.get(position).getNight());

            if (!list.get(position).getNight_color().isEmpty()) {
                if (list.get(position).getNight_color().equals("1")) {
                    holder.night.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
                } else if (list.get(position).getNight_color().equals("2")) {
                    holder.night.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
                } else if (list.get(position).getNight_color().equals("3")) {
                    holder.night.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
                }
            }
            return convertView;
        }

        class ViewHolder {
            public TextView bre_before;
            public TextView bre_after;
            public TextView lun_before;
            public TextView lun_after;
            public TextView dinner_after;
            public TextView dinner_before;
            public TextView night;
            public TextView date;
        }
    }

    @OnClick({R.id.btn_tianjia, R.id.iv_qushi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tianjia:
                startActivity(XueTangAddAty.class, null);
                break;
            case R.id.iv_qushi:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(FindByPid.class).bloodsugarFindByPid(
                        userId, phone, secret), 6);


                break;
        }
    }
}
