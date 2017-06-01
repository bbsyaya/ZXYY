package com.zhixinyisheng.user.adapter.laboratory;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.ArrayListAdapter;
import com.zhixinyisheng.user.domain.lab.Laboratory;
import com.zhixinyisheng.user.util.lab.LabChineseUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * Created by 焕焕 on 2017/2/22.
 */
public class LabEditAdapter extends ArrayListAdapter<Laboratory> {
    private Integer index = -1;
    EditText etLabChinese, etLabData, etLabUnit;

    public LabEditAdapter(Context context) {
        super(context, R.layout.item_laboratory_edit);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getInflater().inflate(R.layout.item_laboratory_edit, parent, false);
        etLabChinese = (EditText) convertView.findViewById(R.id.et_lab_chinese);
        etLabData = (EditText) convertView.findViewById(R.id.et_lab_data);
        etLabUnit = (EditText) convertView.findViewById(R.id.et_lab_unit);
        final Laboratory laboratory = getItem(position);
        boolean judge = LabChineseUtil.useList(LabChineseUtil.arr, laboratory.getChineseName());
        if (judge) {
            etLabChinese.setBackgroundResource(R.drawable.btn_white_shape);
        } else {
            etLabChinese.setBackgroundResource(R.drawable.btn_error_shape);
        }
        etLabChinese.setText(laboratory.getChineseName());
        etLabData.setText(laboratory.getResultData());
        etLabUnit.setText(laboratory.getUnit());

        etLabChinese.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                laboratory.setChineseName(s.toString());
                boolean judge = LabChineseUtil.useList(LabChineseUtil.arr, s.toString());
                if (judge) {
                    etLabChinese.setBackgroundResource(R.drawable.btn_white_shape);
                    notifyDataSetChanged();
                } else {
                    etLabChinese.setBackgroundResource(R.drawable.btn_error_shape);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return convertView;
    }


    @Override
    public void bindViewHolder(int position, Object viewHolder) {
//        ViewHolder holder= (ViewHolder) viewHolder;
//        Laboratory laboratory = getItem(position);
//        holder.etLabChinese.setText(laboratory.getChineseName());
//        holder.etLabData.setText(laboratory.getResultData());
//        holder.etLabUnit.setText(laboratory.getUnit());
//        holder.etLabChinese.setTag(position);
//        holder.etLabChinese.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    index = (Integer) v.getTag();
//                }
//                return false;
//            }
//        });
//        class MyTextWatcher implements TextWatcher {
//            public MyTextWatcher(ViewHolder holder) {
//                mHolder = holder;
//            }
//
//            private ViewHolder mHolder;
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s != null && !"".equals(s.toString())) {
//                    int position = (Integer) mHolder.etLabChinese.getTag();
//                    getData().get(position).setChineseName(s.toString());
////                    mData.get(position).put("list_item_inputvalue",
////                            s.toString());// 当EditText数据发生改变的时候存到data变量中
//                }
//            }
//        }
//        holder.etLabChinese.addTextChangedListener(new MyTextWatcher(holder));
    }

    @Override
    public Object createViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    static class ViewHolder {
        @Bind(R.id.et_lab_chinese)
        EditText etLabChinese;
        @Bind(R.id.et_lab_data)

        EditText etLabData;
        @Bind(R.id.et_lab_unit)
        EditText etLabUnit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
