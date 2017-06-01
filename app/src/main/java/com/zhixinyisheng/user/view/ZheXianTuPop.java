package com.zhixinyisheng.user.view;

import android.content.Context;
import android.os.Handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
public abstract class ZheXianTuPop {
    String PARAMS;
    int start;
    int end;
    String strti;
    String strna;
    Context context;
    int int_list_bs;

    private Handler handler;
    public DialogZXT dialog_bz;
    String path = "";


   public List<String> Xlabel = new ArrayList<String>(),
            Ylabel = new ArrayList<String>();
    public List<String> data = new ArrayList<String>(),
            data1 = new ArrayList<String>();
    public ZheXianTuPop(Context context,String PARAMS, int start, int end, String strti,
                        String strna,int int_list_bs) {
        this.end = end;
        this.PARAMS = PARAMS;
        this.start = start;
        this.strna = strna;
        this.strti = strti;
        this.context = context;
        this.int_list_bs = int_list_bs;
        Xlabel.clear();
        data.clear();
        data1.clear();
        getZX(PARAMS);
        Ylabel.clear();
        // 纵坐标画整数和小数
        if (int_list_bs == 0) {
            double d = new BigDecimal((end - start) / 10f).setScale(1,
                    BigDecimal.ROUND_HALF_UP).doubleValue();
            for (double i = start; i <= end; i = i + d) {
                Ylabel.add(new BigDecimal(i).setScale(1,
                        BigDecimal.ROUND_HALF_UP).doubleValue()
                        + "");
            }
        } else if (int_list_bs == 1) {
            int d = (end - start) / 10;
            for (int i = start; i <= end; i = i + d) {
                Ylabel.add(i + "");
            }
        }
        dialog_bz = new DialogZXT(context, strti, strna, Ylabel);
    }
    public abstract void  getZX(String PARAMS);
}
