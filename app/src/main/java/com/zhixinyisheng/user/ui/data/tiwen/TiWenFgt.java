package com.zhixinyisheng.user.ui.data.tiwen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.Tempture;
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
 * 体温
 * Created by 焕焕 on 2016/11/3.
 */
public class TiWenFgt extends BaseFgt {


    @Bind(R.id.btn_tianjia)
    ImageView btnTianjia;
    @Bind(R.id.iv_qushi)
    ImageView ivQushi;
    @Bind(R.id.trend_listview)
    ListView trendListview;
    //保存每天体温数据的集合
    private List<Tempture> list = new ArrayList<>();
    //每天体温的实体类
    private Tempture info;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_tiwen;
    }

    @Override
    public void initData() {
        Content.animalHeat++;
        if (Content.myHealthFlag==1|| !UserManager.getUserInfo().getUserId().equals(userId)){
            btnTianjia.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkTiWenZXT(
                userId,time,phone,secret),0);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what){
            case 0:
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("result").equals("0000")){
//                        Toast.makeText(Trend.this, "体温数据获取成功", Toast.LENGTH_SHORT).show();
                        JSONArray array=object.getJSONArray("list");
                        list.clear();
                        if (array.length()>=7) {
                            for (int i = 0; i <array.length() ; i++) {
                                JSONObject obj = (JSONObject) array.get(i);
                                String id =obj.getString("BYTIME");
                                String mor = null;
                                try {
                                    mor = obj.getString("MORNINGVALUE");
                                    if(mor.equals("0.0")){
                                        mor = "";
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mor="";
                                }
                                String afternoon = null;
                                try {
                                    afternoon = obj.getString("AFTERNOONVALUE");
                                    if (afternoon.equals("0.0")){
                                        afternoon = "";
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    afternoon="";
                                }
                                String evening = null;
                                try {
                                    evening = obj.getString("EVENINGVALUE");
                                    if(evening.equals("0.0")){
                                        evening = "";
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    evening="";
                                }
                                info=new Tempture();
                                info.setTime(id);
                                info.setTem_mor(mor);
                                info.setTem_aft(afternoon);
                                info.setTem_nit(evening);
                                list.add(info);

                            }
                        }else {
                            for (int i = 0; i <array.length() ; i++) {
                                JSONObject obj = (JSONObject) array.get(i);
                                String id =obj.getString("BYTIME");
                                String mor = null;
                                try {
                                    mor = obj.getString("MORNINGVALUE");
                                    if (mor.equals("0.0")){
                                        mor = "";
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mor="";
                                }
                                String afternoon = null;
                                try {
                                    afternoon = obj.getString("AFTERNOONVALUE");
                                    if (afternoon.equals("0.0")){
                                        afternoon = "";
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    afternoon="";
                                }
                                String evening = null;
                                try {
                                    evening = obj.getString("EVENINGVALUE");
                                    if (evening.equals("0.0")){
                                        evening = "";
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    evening="";
                                }
                                info=new Tempture();
                                info.setTime(id);
                                info.setTem_mor(mor);
                                info.setTem_aft(afternoon);
                                info.setTem_nit(evening);
                                list.add(info);
                                Log.e("集合list",list.toString());

                            }

                            for (int i = 0; i <7-array.length() ; i++) {
                                info=new Tempture();
                                info.setTime("");
                                info.setTem_mor("");
                                info.setTem_aft("");
                                info.setTem_nit("");
                                list.add(info);
                                Log.e("集合list",list.toString());

                            }

                        }
                        MyAdapter adapter =new MyAdapter(getActivity(),list);
                        trendListview.setAdapter(adapter);

                    }else if(object.getString("result").equals("9993")){
//                        Toast.makeText(Trend.this, "必传参数不可为空", Toast.LENGTH_SHORT).show();
                    }else if(object.getString("result").equals("9999")){
//                        Toast.makeText(Trend.this, "服务器系统异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 6:

                FindByPidEntity findByPidEntity = JSON.parseObject(result,FindByPidEntity.class);
                new CalenderDialogTest(getActivity(),findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
//                        showLoadingDialog(null);
                        doHttp(RetrofitUtils.createApi(DataUrl.class).checkTiWenZXT(
                                userId, Content.DATA,phone,secret),0);
                    }
                };
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        if (what==6){
            new CalenderDialogTest(getActivity(),null) {
                @Override
                public void getZXTData() {

                    doHttp(RetrofitUtils.createApi(DataUrl.class).checkTiWenZXT(
                            userId, Content.DATA,phone,secret),0);
                }
            };
        }
    }

    @Override
    public void requestData() {

    }
    //根据体温值判断状态
    public String judgeColor(String s){
        String color = "";
        if (!s.isEmpty()) {

            double n = Double.valueOf(s.toString());
            //int num =Integer.parseInt(s);
            if (n >= 36.1 && n <= 37.2) {
                color = "黑";
            } else if (n >= 37.3 && n <= 38.4) {
                color = "黄";
            } else if (n > 38.4) {
                color = "红";
            }
        }

        return color;

    }
    /**
     * 自定义适配器
     */
    class MyAdapter extends BaseAdapter {
        List<Tempture> list = new ArrayList<Tempture>();
        LayoutInflater inflater;

        public MyAdapter(Context context, List<Tempture> list){
            this.inflater=LayoutInflater.from(context);
            this.list=list;

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
            if (convertView==null){
                holder =new ViewHolder();
                convertView =inflater.inflate(R.layout.trend_list_item,null);
                holder.moring= (TextView) convertView.findViewById(R.id.item_mor);
                holder.afternoon= (TextView) convertView.findViewById(R.id.item_after);
                holder.night= (TextView) convertView.findViewById(R.id.item_night);
                holder.date= (TextView) convertView.findViewById(R.id.item_date);
                convertView.setTag(holder);

            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            holder.moring.setText(list.get(position).getTem_mor());
            if (judgeColor(list.get(position).getTem_mor()).equals("黑")) {
                holder.moring.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
            }else if (judgeColor(list.get(position).getTem_mor()).equals("黄")){
                holder.moring.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
            }
            else if (judgeColor(list.get(position).getTem_mor()).equals("红")){
                holder.moring.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
            }
            holder.afternoon.setText(list.get(position).getTem_aft());
            if (judgeColor(list.get(position).getTem_aft()).equals("黑")) {
                holder.afternoon.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
            }else if (judgeColor(list.get(position).getTem_aft()).equals("黄")){
                holder.afternoon.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
            }
            else if (judgeColor(list.get(position).getTem_aft()).equals("红")){
                holder.afternoon.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
            }
            holder.night.setText(list.get(position).getTem_nit());

            if (judgeColor(list.get(position).getTem_nit()).equals("黑")) {
                holder.night.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_normal));
            }else if (judgeColor(list.get(position).getTem_nit()).equals("黄")){
                holder.night.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_warning));
            }
            else if (judgeColor(list.get(position).getTem_nit()).equals("红")){
                holder.night.setTextColor(getActivity().getResources().getColor(R.color.bloodsugar_danger));
            }
            holder.date.setText(list.get(position).getTime());
            return convertView;
        }

        class ViewHolder{
            public TextView moring;
            public TextView afternoon;
            public TextView night;
            public TextView date;
        }
    }
    @OnClick({R.id.btn_tianjia, R.id.iv_qushi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tianjia:
                startActivity(TiWenAddAty.class,null);
                break;
            case R.id.iv_qushi:
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(FindByPid.class).animalheatFindByPid(
                        userId, phone, secret), 6);

                break;
        }
    }
}
