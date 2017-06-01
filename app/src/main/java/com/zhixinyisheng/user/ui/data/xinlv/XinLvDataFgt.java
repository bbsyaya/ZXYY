package com.zhixinyisheng.user.ui.data.xinlv;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.XinLvZXT;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.view.MyViewBD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 心率的数据界面
 * Created by 焕焕 on 2016/10/22.
 */
public class XinLvDataFgt extends BaseFgt {
    @Bind(R.id.xl_tv1)
    TextView xlTv1;
    @Bind(R.id.xl_rl1)
    RelativeLayout xlRl1;
    @Bind(R.id.xl_mv)
    MyViewBD mv;
    @Bind(R.id.xl_ll1)
    LinearLayout xlLl1;
    @Bind(R.id.xl_v1)
    View xlV1;
    @Bind(R.id.xl_purflv)
    ListView xlPurflv;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<String> Xlabel = new ArrayList<String>(),
            data = new ArrayList<String>(), data1 = new ArrayList<String>(),
            Ylabel = new ArrayList<String>();
    List<String> Xlabel_ada = new ArrayList<String>(),
            Xlabel_ada1 = new ArrayList<String>(),
            data_ada = new ArrayList<String>(),
            data_ada1 = new ArrayList<String>(),
            item_id = new ArrayList<String>();

    @Override
    public int getLayoutId() {
        return R.layout.fgt_xinlvdata;
    }

    @Override
    public void initData() {
        for (int i = 40; i <= 200; i = i + 16) {
            Ylabel.add(i + "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoadingDialog("正在获取数据...");
        doHttp(RetrofitUtils.createApi(DataUrl.class).checkXinLvZXT(
                userId, time, phone, secret), 0);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case 0:
                Xlabel.clear();
                data.clear();
                data1.clear();
                XinLvZXT xinLvZXT = JSON.parseObject(result, XinLvZXT.class);
                if (xinLvZXT.getSize() == 0) {

                } else {
                    for (int i = 0; i < xinLvZXT.getList().size(); i++) {
                        String strt = xinLvZXT.getList().get(i).getBYTIME();
                        int strsj = xinLvZXT.getList().get(i).getNum();
                        Xlabel.add(strt.substring(5, 7) + "."
                                + strt.substring(8, 10));
                        if (Double.valueOf(strsj) < 40) {
                            data.add("40");
                        } else {
                            data.add(strsj + "");
                        }
                        data1.add(strsj + "");
                    }
                    mv.drawZhexian(Xlabel, Ylabel, data, data1, 1);

//                    getCurrent(0);


                }
                break;
        }

    }
}
