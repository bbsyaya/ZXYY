package com.zhixinyisheng.user.ui.data.BLE.control;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.view.delete.SwipeMenu;
import com.and.yzy.frame.view.delete.SwipeMenuCreator;
import com.and.yzy.frame.view.delete.SwipeMenuItem;
import com.and.yzy.frame.view.delete.SwipeMenuListView;
import com.and.yzy.frame.view.switchbutton.SwitchButton;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.data.BLE.common.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 闹钟
 */
public class AlarmActivity extends BaseAty {


    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.alarm_smlv)
    SwipeMenuListView alarmSmlv;

    List<AlarmEN> list = new ArrayList<AlarmEN>();
    AlarmDAO dao;
    MyAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        SetBLEActivity.atst1.add(this);
        cjsTvt.setText(R.string.shezhinaozhong);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText(R.string.tianjianaozhong);
        ivBack.setVisibility(View.VISIBLE);
        dao = AlarmDAO.getInstance(this);
        list = dao.findAll();
        adapter = new MyAdapter();
        alarmSmlv.setAdapter(adapter);
        initSwipeMenu();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                AlarmEN aen = new AlarmEN(intent.getIntExtra("alarm_hour", 0),
                        intent.getIntExtra("alarm_minute", 0),
                        intent.getStringExtra("alarm_str1"),
                        intent.getStringExtra("alarm_str2"),
                        intent.getStringExtra("alarm_str3"),
                        intent.getStringExtra("alarm_str4"),
                        intent.getStringExtra("alarm_str5"),
                        intent.getStringExtra("alarm_str6"),
                        intent.getStringExtra("alarm_str7"),
                        intent.getStringExtra("alarm_stron"));
                list.add(aen);
                dao.save(aen);
                adapter.notifyDataSetChanged();
                //全部重新设置闹钟(解决ID冲突问题)
                setAlarm();

            }
        }
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    /**
     * 初始化滑动菜单
     */
    private void initSwipeMenu() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(80));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        alarmSmlv.setMenuCreator(creator);

        // step 2. listener item click event
        alarmSmlv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {

                switch (index) {
                    case 0:
                        list.remove(position);
                        dao.delete();
                        adapter.notifyDataSetChanged();
                        //全部重新设置闹钟(解决ID冲突问题)
                        setAlarm();
                        break;

                }
                return false;
            }
        });

    }
    @OnClick({R.id.cjs_rlb, R.id.title_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.title_btn:
                Intent intent = new Intent(this, SetAlarmAty.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View v, ViewGroup arg2) {
            ViewHolder vh = null;
            if (v == null) {
                v = getLayoutInflater().inflate(R.layout.alarm_item, null);
                vh = new ViewHolder(v);
                v.setTag(vh);
            } else {
                vh = (ViewHolder) v.getTag();
            }
            final AlarmEN aen = list.get(position);

            if (aen.getMinute() < 10) {
                vh.tvt.setText(aen.getHour() + ":0" + aen.getMinute());
            } else {
                vh.tvt.setText(aen.getHour() + ":" + aen.getMinute());
            }

            showXq(aen.getStr1(), aen.getStr2(), aen.getStr3(), aen.getStr4(),
                    aen.getStr5(), aen.getStr6(), aen.getStr7(), vh.tvcf);
            if (aen.getStr_on().equals("1")) {
                vh.swbtn.setChecked(true);
            } else if (aen.getStr_on().equals("0")) {
                vh.swbtn.setChecked(false);
            }
            vh.swbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    if (arg1) {
                        aen.setStr_on("1");
                        setAlarm();
                    } else {
                        aen.setStr_on("0");
                        setAlarm();
                    }

                }
            });
            return v;
        }
    }

    class ViewHolder {
        TextView tvt, tvcf;
        SwitchButton swbtn;

        public ViewHolder(View v) {
            tvt = (TextView) v.findViewById(R.id.alai_tvt);
            tvcf = (TextView) v.findViewById(R.id.alai_tvcf);
            swbtn = (SwitchButton) v.findViewById(R.id.alai_swbtn);
        }
    }

    private void showXq(String str1, String str2, String str3, String str4,
                        String str5, String str6, String str7, TextView tvcf) {
        String str = "";
        if (str1.equals("1")) {
            str += getString(R.string.zhouyi);
        }
        if (str2.equals("1")) {
            str += getString(R.string.zhouer);
        }
        if (str3.equals("1")) {
            str += getString(R.string.zhousan);
        }
        if (str4.equals("1")) {
            str += getString(R.string.zhousi);
        }
        if (str5.equals("1")) {
            str += getString(R.string.zhouwu);
        }
        if (str6.equals("1")) {
            str += getString(R.string.zhouliu);
        }
        if (str7.equals("1")) {
            str += getString(R.string.zhouri);
        }
        if (str1.equals("1") && str2.equals("1") && str3.equals("1")
                && str4.equals("1") && str5.equals("1") && str6.equals("1")
                && str7.equals("1")) {
            tvcf.setText(R.string.meitian);
        } else if (str1.equals("0") && str2.equals("0") && str3.equals("0")
                && str4.equals("0") && str5.equals("0") && str6.equals("0")
                && str7.equals("0")) {
            tvcf.setText(R.string.danci);
        } else {
            tvcf.setText(str);
        }

    }

    // 设置闹钟
    public void setAlarm() {
        List<String> list_nz = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list_nz.add(getString(list.get(i), i));
        }
        //拼所有闹钟
        String str_synz = "";
        for (String str : list_nz) {
            str_synz += str;

        }

        //拼命令的总长度
        String zcd = Integer.toHexString((list_nz.size() + 1) * 5);
        if (zcd.length() == 1) {
            zcd = "000" + zcd;
        } else if (zcd.length() == 2) {
            zcd = "00" + zcd;
        }

        //拼闹钟字段长度
        String fcd = Integer.toHexString(list_nz.size() * 5);
        if (fcd.length() == 1) {
            fcd = "0" + fcd;
        }

        String sg = "ab00" + zcd + "0000000001000200" + fcd + str_synz;// 命令
//		Log.e("闹钟命令", sg);

        //发送闹钟命令
        if (sg.length() <= 40) {
            Utils.sendSHML(this, sg);
        } else {
            for (int i = 0; i <= sg.length() / 40; i++) {
                if (i == sg.length() / 40) {
                    Utils.sendSHML(this, sg.substring(i * 40, sg.length()));
                } else {
                    Utils.sendSHML(this, sg.substring(i * 40, i * 40 + 40));
                }
            }
        }


//        MyToast.showToast(AlarmActivity.this, "添加闹钟成功");

    }

    @NonNull
    private String getString(AlarmEN aen, int no) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String alarme = "";
        String time = sdf.format(System.currentTimeMillis());
        String[] str = time.split("-");
        String sn = str[0].substring(2, 4), sy = str[1], sr = str[2];
        String sne = shiZhuanEr(sn, 6), sye = shiZhuanEr(sy, 4), sre = shiZhuanEr(
                sr, 5), sse = shiZhuanEr(aen.getHour() + "", 5), sfe = shiZhuanEr(
                aen.getMinute() + "", 6), sde = shiZhuanEr(
                no + "", 3);//在集合中的位置
        // "000"+"0或1"+"0000000"
        alarme = sne + sye + sre + sse + sfe + sde + "000" + aen.getStr_on()
                + aen.getStr1() + aen.getStr2() + aen.getStr3() + aen.getStr4()
                + aen.getStr5() + aen.getStr6() + aen.getStr7();
        Log.e("闹钟二进制命令", alarme);
        String alarml = "";
        for (int i = 0; i < alarme.length() / 4; i++) {
            String sShiLiu = Integer.toHexString(Integer.parseInt(
                    alarme.substring(i * 4, i * 4 + 4), 2));

            alarml += sShiLiu;
        }
        return alarml;
    }

    // 十进制转二进制
    private String shiZhuanEr(String str, int n) {
        String strf = Integer.toBinaryString(Integer.valueOf(str));
        if (n == 2) {
            if (strf.length() == 1) {
                strf = "0" + strf;
            }
        } else if (n == 3) {
            if (strf.length() == 1) {
                strf = "00" + strf;
            } else if (strf.length() == 2) {
                strf = "0" + strf;
            }
        } else if (n == 4) {
            if (strf.length() == 1) {
                strf = "000" + strf;
            } else if (strf.length() == 2) {
                strf = "00" + strf;
            } else if (strf.length() == 3) {
                strf = "0" + strf;
            }
        } else if (n == 5) {
            if (strf.length() == 1) {
                strf = "0000" + strf;
            } else if (strf.length() == 2) {
                strf = "000" + strf;
            } else if (strf.length() == 3) {
                strf = "00" + strf;
            } else if (strf.length() == 4) {
                strf = "0" + strf;
            }
        } else if (n == 6) {
            if (strf.length() == 1) {
                strf = "00000" + strf;
            } else if (strf.length() == 2) {
                strf = "0000" + strf;
            } else if (strf.length() == 3) {
                strf = "000" + strf;
            } else if (strf.length() == 4) {
                strf = "00" + strf;
            } else if (strf.length() == 5) {
                strf = "0" + strf;
            }
        } else if (n == 7) {
            if (strf.length() == 1) {
                strf = "000000" + strf;
            } else if (strf.length() == 2) {
                strf = "00000" + strf;
            } else if (strf.length() == 3) {
                strf = "0000" + strf;
            } else if (strf.length() == 4) {
                strf = "000" + strf;
            } else if (strf.length() == 5) {
                strf = "00" + strf;
            } else if (strf.length() == 6) {
                strf = "0" + strf;
            }
        } else if (n == 8) {
            if (strf.length() == 1) {
                strf = "0000000" + strf;
            } else if (strf.length() == 2) {
                strf = "000000" + strf;
            } else if (strf.length() == 3) {
                strf = "00000" + strf;
            } else if (strf.length() == 4) {
                strf = "0000" + strf;
            } else if (strf.length() == 5) {
                strf = "000" + strf;
            } else if (strf.length() == 6) {
                strf = "00" + strf;
            } else if (strf.length() == 7) {
                strf = "0" + strf;
            }
        }
        return strf;
    }

    @Override
    public void requestData() {

    }

}
