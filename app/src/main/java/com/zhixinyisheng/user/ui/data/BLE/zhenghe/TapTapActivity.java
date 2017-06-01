package com.zhixinyisheng.user.ui.data.BLE.zhenghe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.view.delete.SwipeMenu;
import com.and.yzy.frame.view.delete.SwipeMenuCreator;
import com.and.yzy.frame.view.delete.SwipeMenuItem;
import com.and.yzy.frame.view.delete.SwipeMenuListView;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.common.StaticCommon;
import com.zhixinyisheng.user.ui.data.BLE.control.ZYTZDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * taptap
 * Created by 焕焕 on 2016/11/11.
 */
public class TapTapActivity extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tap_smlv)
    SwipeMenuListView smlv;
    List<String> list = new ArrayList<String>();
    List<Boolean> list_b = new ArrayList<Boolean>();
    ZYTZDao dao;
    //点击的那一项进行编辑
    int int_list_position = 0;
    String str_list_content = "";
    MyAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.aty_taptap;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.hujiaochangyongyu);
        ivBack.setVisibility(View.VISIBLE);
        dao = ZYTZDao.getInstance(this);
        list = dao.findAll(userId);
        for (int i = 0; i < list.size(); i++) {
            list_b.add(false);
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(TestService.spf.getString("SH_TAP", ""))) {
                list_b.set(i, true);
            }
        }
        adapter = new MyAdapter();
        smlv.setAdapter(adapter);
        initSwipeMenu();
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
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
        smlv.setMenuCreator(creator);

        // step 2. listener item click event
        smlv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {

                switch (index) {
                    case 0:
                        list.remove(position);
                        dao.delete(userId);
                        adapter.notifyDataSetChanged();
                        break;

                }
                return false;
            }
        });


        smlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long arg3) {
                if (list_b.get(position)) {
                    list_b.set(position, false);
                    StaticCommon.ZYTZ = "";
                    TestService.editor.putString("SH_TAP",StaticCommon.ZYTZ);
                    TestService.editor.commit();
                } else {
                    for (int i = 0; i < list_b.size(); i++) {
                        list_b.set(i, false);
                        if (i == position) {
                            list_b.set(position, true);
                            StaticCommon.ZYTZ = list.get(position);
                            TestService.editor.putString("SH_TAP",StaticCommon.ZYTZ);
                            TestService.editor.commit();
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                list.add(intent.getStringExtra("bjxx"));
                for (int i = 0; i < list_b.size(); i++) {
                    list_b.set(i, false);
                }
                list_b.add(true);
                MyLog.showLog("tap",list_b.toString());
                StaticCommon.ZYTZ = intent.getStringExtra("bjxx");
                TestService.editor.putString("SH_TAP",StaticCommon.ZYTZ);
                TestService.editor.commit();
                dao.save(intent.getStringExtra("bjxx"), userId);
                adapter.notifyDataSetChanged();
            } else if (requestCode == 2) {
                for (int i = 0; i < list_b.size(); i++) {
                    list_b.set(i, false);
                }
                list_b.set(int_list_position, true);
                StaticCommon.ZYTZ = intent.getStringExtra("bjxx");
                TestService.editor.putString("SH_TAP",StaticCommon.ZYTZ);
                TestService.editor.commit();
                list.set(int_list_position,intent.getStringExtra("bjxx"));
                dao.update(intent.getStringExtra("bjxx"),str_list_content,userId);
                adapter.notifyDataSetChanged();
            }

        }

    }


    @OnClick({R.id.cjs_rlb, R.id.tap_rlxj})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.tap_rlxj:
                Bundle bundle = new Bundle();
                bundle.putString("content", "");
                startActivityForResult(TapBJActivity.class, bundle, 1);
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
        public View getView(final int position, View v, ViewGroup arg2) {
            ViewHolder vh = null;
            if (v == null) {
                v = getLayoutInflater().inflate(R.layout.tap_item, null);
                vh = new ViewHolder(v);
                v.setTag(vh);
            } else {
                vh = (ViewHolder) v.getTag();
            }
            vh.tv.setText(list.get(position));
            if (list_b.get(position)) {
                vh.ivxz.setImageResource(R.drawable.ct_btn_selected_s);
            } else {
                vh.ivxz.setImageResource(R.drawable.ct_btn_selected_n);
            }

            vh.ivbj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    int_list_position = position;
                    str_list_content = list.get(position);

                    Bundle bundle = new Bundle();
                    bundle.putString("content", list.get(position));
                    startActivityForResult(TapBJActivity.class, bundle, 2);
//                    openActivity(TapBJActivity.class, bundle, 2);
                }
            });


            return v;
        }
    }

    class ViewHolder {
        TextView tv;
        ImageView ivbj, ivxz;


        public ViewHolder(View v) {
            tv = (TextView) v.findViewById(R.id.tapi_tv);
            ivbj = (ImageView) v.findViewById(R.id.tapi_iv);
            ivxz = (ImageView) v.findViewById(R.id.tapi_ivxz);
        }
    }
}
