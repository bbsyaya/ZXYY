package com.zhixinyisheng.user.ui.data.synthesize_info.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.ArrayListAdapter;
import com.zhixinyisheng.user.ui.data.synthesize_info.model.SynthesizeInfo;
import com.zhixinyisheng.user.util.DateUtils;
import com.zhixinyisheng.user.util.StringFormatUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/15.
 * 综合信息
 */

public class SynthesizeInfoAdapter extends ArrayListAdapter<SynthesizeInfo.ListBean> {
    private InsideAdapter insideAdapter;

    public SynthesizeInfoAdapter(Context context) {
        super(context, R.layout.item_synthesize_info);
        this.context = context;
    }

    private Context context;


    @Override
    public void bindViewHolder(int position, Object viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        SynthesizeInfo.ListBean bean = getItem(position);
        String time = DateUtils.formatDateToHour(bean.getTime());
        //1心率 2血压 3肺活量 4舌诊 5睡眠质量 6BMI 7血糖 8体温 9步数 10 温馨提示

        holder.tvTime.setText(time);
        String content = bean.getContent();
        holder.tvTitle.setText(bean.getTitle());
        int type = bean.getType();
        if (type == 7 || type == 8) {
            holder.gridView.setVisibility(View.VISIBLE);
            holder.tvContent.setVisibility(View.GONE);
        } else {
            holder.gridView.setVisibility(View.GONE);
            holder.tvContent.setVisibility(View.VISIBLE);
        }
        if (type == 1) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_heartrate);
            setTextViewStyle(content + context.getString(R.string.cifenzhong), 0, content.length(), holder.tvContent);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_xl);
        } else if (type == 2) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_xy);
            setTextViewStyle(content + "mmHg", 0, content.length(), holder.tvContent);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_xy);
        } else if (type == 3) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_fhl);
            setTextViewStyle(content, 0, content.length(), holder.tvContent);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_fhl);
        } else if (type == 4) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_sz);
            holder.tvContent.setText(content);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card);
        } else if (type == 5) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.in_smzl);
            holder.tvContent.setText(content);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_smzl);
        } else if (type == 6) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_stzlzs);
            setTextViewStyle(content, 0, content.length(), holder.tvContent);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_bmi);
        } else if (type == 7) {
            insideAdapter = new InsideAdapter(context);
            insideAdapter.setState(1);
            List<String> stringList = Arrays.asList(content.split("╬"));
            insideAdapter.setData(stringList);
            holder.gridView.setAdapter(insideAdapter);
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_xt);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_xy);

        } else if (type == 8) {
            insideAdapter = new InsideAdapter(context);
            insideAdapter.setState(0);
            String[] arrayStr = content.split("╬");
            List<String> stringList = Arrays.asList(arrayStr);
            insideAdapter.setData(stringList);
            holder.gridView.setAdapter(insideAdapter);
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_tw);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_xy);
        } else if (type == 9) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_bs);
            setTextViewStyle(content + context.getString(R.string.StepUnit), 0, content.length(), holder.tvContent);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_bs);
        } else if (type == 10 || type == 11 || type == 13) {
            //10温馨提示给患者-提醒用药 11:温馨提示给患者-药用完了 12:温馨提示给患者-药没用
            // 13 温馨提示-给医生-患者服了药 14 温馨提示-给医生-没服什么药
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_tips);
            holder.tvContent.setText(content);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card);
        } else if (type == 12) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_synthesize_tips_red);
            StringFormatUtil stringFormatUtil = new StringFormatUtil
                    (context, context.getString(R.string.nindeyaowu) + content + context.getString(R.string.shangweifuyong), content, R.color.money_red).fillColor();
            holder.tvContent.setText(stringFormatUtil.getResult());
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_xy);
        } else if (type == 14) {
            setTextLeftDrawable(holder.tvTitle, R.mipmap.ic_synthesize_tips_red);
            holder.ivLeft.setImageResource(R.drawable.shape_corner_card_xy);
            String[] arrarString = content.split("╬");
            if (arrarString != null && arrarString.length > 0) {
                StringFormatUtil stringFormatUtil = new StringFormatUtil
                        (context, context.getString(R.string.nindehuanzhe) + arrarString[0] + context.getString(R.string.deyaowu) + arrarString[1] + context.getString(R.string.shangweifuyongqingjishi), arrarString[1], R.color.money_red).fillColor();
                holder.tvContent.setText(stringFormatUtil.getResult());
            }
        }
    }

    private void setTextViewStyle(String content, int start, int end, TextView textView) {
        SpannableString styledText = new SpannableString(content);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_size_big), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    private void setTextLeftDrawable(TextView textView, int reid) {
        textView.setCompoundDrawablesWithIntrinsicBounds(reid, 0, 0, 0);
    }

    @Override
    public Object createViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.gridView)
        GridView gridView;
        @Bind(R.id.iv_left)
        ImageView ivLeft;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            gridView.setClickable(false);
            gridView.setPressed(false);
            gridView.setEnabled(false);
        }
    }

    class InsideAdapter extends ArrayListAdapter<String> {
        //0体温 1血糖
        private int state = 0;

        public void setState(int state) {
            this.state = state;
        }

        public InsideAdapter(Context context) {
            super(context, R.layout.item_text);
        }

        @Override
        public void bindViewHolder(int position, Object viewHolder) {
            //1,41
            String content = getItem(position);
            TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
            //体温 (1晚上 2中午 3早晨)
            if (state == 0 && !"".equals(content)) {
                String[] arrayStr = content.split(",");
                String flag = arrayStr[0];
                String newContent = arrayStr[1];
                if ("1".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.sheshiduwanshang), 0, newContent.length(), textViewHolder.textView);
                } else if ("2".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.sheshiduzhongwu), 0, newContent.length(), textViewHolder.textView);
                } else if ("3".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.sheshiduzaoshang), 0, newContent.length(), textViewHolder.textView);
                }
            } else if (state == 1 && !"".equals(content)) {
                //114.0╬2,10.0╬3,14.0╬4,20.0╬5,13.0╬6,12.0╬7,11.0
                //1 睡觉前 2 晚餐后 3晚餐前 4午餐后 5午餐前 6早餐后 7早餐前
                String[] arrayStr = content.split(",");
                String flag = arrayStr[0];
                String newContent = arrayStr[1];
                if ("1".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.shuijiaoqian), 0, newContent.length(), textViewHolder.textView);
                } else if ("2".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.wancanhou), 0, newContent.length(), textViewHolder.textView);
                } else if ("3".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.wancanqian), 0, newContent.length(), textViewHolder.textView);
                } else if ("4".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.wucanhou), 0, newContent.length(), textViewHolder.textView);
                } else if ("5".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.wucanhou), 0, newContent.length(), textViewHolder.textView);
                } else if ("6".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.zaocanhou), 0, newContent.length(), textViewHolder.textView);
                } else if ("7".equals(flag)) {
                    setTextViewStyle(newContent + context.getString(R.string.zaocanqian), 0, newContent.length(), textViewHolder.textView);
                }
            }
        }

        @Override
        public Object createViewHolder(View convertView) {
            TextViewHolder textViewHolder = new TextViewHolder(convertView);
            return textViewHolder;
        }

        class TextViewHolder {
            @Bind(R.id.text_view)
            TextView textView;

            TextViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
