package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.domain.DoctorHomePage;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.doctor.DoctorDynamic;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.util.DateUtils;
import com.zhixinyisheng.user.util.GlideUtil;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.StringFormatUtil;
import com.zhixinyisheng.user.view.MyGridView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yuanyx on 2016/12/29.
 */

public class DoctorDynamicAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "DoctorDynamicAdapter2";
    Context context;
    private boolean isMe;
    private List<DoctorDynamic.ListBean> data;
    public OnSubitemClickListener onSubitemClickListener;
    private static final int ITEM_TYPE_DOCTOR_INFO = 0;
    private static final int ITEM_TYPE = 1;
    private DoctorHomePage doctorHomePage;

    public void setData(List<DoctorDynamic.ListBean> data) {
        data.add(0, new DoctorDynamic.ListBean());
        this.data = data;
        notifyDataSetChanged();
    }

    public void setDoctorInfo(DoctorHomePage doctorHomePage) {
        this.doctorHomePage = doctorHomePage;
        notifyItemChanged(0);
    }

    public DoctorDynamic.ListBean getItem(int pos) {
        return data.get(pos);
    }

    public void addData(List<DoctorDynamic.ListBean> newList) {
        data.addAll(newList);
        notifyDataSetChanged();
    }

    public void setOnSubitemClickListener(OnSubitemClickListener onSubitemClickListener) {
        this.onSubitemClickListener = onSubitemClickListener;
    }

    public DoctorDynamicAdapter2(Context context) {
        this.context = context;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder");
        if (viewType == ITEM_TYPE_DOCTOR_INFO) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_doctor_info, parent, false);
            OneViewHolder holder = new OneViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_docotr_dynamic, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.e(TAG, "onBindViewHolder");
        if (position == 0) {
            OneViewHolder holder = (OneViewHolder) viewHolder;
            if (LanguageUtil.judgeLanguage().equals("zh")){
                holder.ivShare.setImageResource(R.mipmap.ic_share);
            }else{
                holder.ivShare.setImageResource(R.mipmap.ic_share_en);
            }
            holder.onSubitemClickListener = onSubitemClickListener;
            drawDoctorData(doctorHomePage, holder);
            return;
        }

        ItemViewHolder holder = (ItemViewHolder) viewHolder;

        if (position == 1) {
            holder.viewLine.setVisibility(View.GONE);
        }
        holder.onSubitemClickListener = onSubitemClickListener;
        holder.pos = position;
        DoctorDynamic.ListBean bean = data.get(position);
        drawDynamicItem(bean, holder);
    }

    /**
     * 绘制动态的item数据
     */
    private void drawDynamicItem(DoctorDynamic.ListBean bean, ItemViewHolder holder) {
        String content = bean.getContent();
        if (content != null && content.length() >= 45) {
            StringFormatUtil spanStr = new StringFormatUtil(context, content.substring(0, 35) + "...",
                    "...", R.color.maincolor).fillColor();
            holder.tvContenet.setText(spanStr.getResult());
        } else {
            holder.tvContenet.setText(content);
        }

        if (isMe) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }

        int isZan = bean.getIsZan();
        if (isZan == 0) {
            holder.tvZan.setSelected(false);
        } else if (isZan == 1) {
            holder.tvZan.setSelected(true);
        }
        holder.tvZan.setText(bean.getZan() + "");
        String strTime = DateUtils.convertTimeToFormat(bean.getCreatetime() / (long) 1000);
        holder.tvDate.setText(strTime);

        ImageAdapter imageAdapter = new ImageAdapter(context);
        holder.gvPic.setAdapter(imageAdapter);

        String picUrl = bean.getPicUrl();
        if (picUrl != null && !"".equals(picUrl)) {
            String[] arrayStr = picUrl.split(",");
            List<String> stringList = Arrays.asList(arrayStr);
            imageAdapter.setData(stringList);
        } else {
            imageAdapter.clearData();
        }
    }

    /**
     * 绘制医生数据
     *
     * @param doctorHomePage
     */
    private void drawDoctorData(DoctorHomePage doctorHomePage, OneViewHolder holder) {
        if (doctorHomePage != null) {
            DoctorHomePage.UserPdBean docotrInfo = doctorHomePage.getUserPd();
            DoctorHomePage.SeeSumBean seeSum = doctorHomePage.getSeeSum();
            GlideUtil.loadCircleAvatar(context, docotrInfo.getHeadUrl(), holder.ivAvatar);

            String frinedsRemark=docotrInfo.getFrinedsRemark();
            String dispayName="";
            String name=docotrInfo.getName();
            String userName=docotrInfo.getUsername();
            String card=docotrInfo.getCard();
            if(!"".equals(name)){
                dispayName=name;
            }else if(!"".equals(userName)){
                dispayName=userName;
            }else {
                dispayName=card;
            }
            if(!"".equals(frinedsRemark) && frinedsRemark.length()>5){
                StringFormatUtil spanStr = new StringFormatUtil(context, frinedsRemark.substring(0, 5) + "...",
                        "...", R.color.maincolor).fillColor();
                holder.tvName.setText(dispayName + context.getString(R.string.tdaifu)+"\t("+spanStr.toString()+")");
            }else if(!"".equals(frinedsRemark)){
                holder.tvName.setText(dispayName + context.getString(R.string.tdaifu)+"\t("+frinedsRemark+")");
            }else{
                holder.tvName.setText(dispayName + context.getString(R.string.tdaifu));
            }
            holder.tvDepartment.setText(docotrInfo.getSection() + " \t" + docotrInfo.getHospital());
            holder.tvSeeNum.setText(String.valueOf(seeSum.getCount()));
            holder.tvAppraiseNum.setText(String.valueOf(docotrInfo.getAppraiseNums()));

            String goodPercent = docotrInfo.getGoodPercent();
            if ("".equals(goodPercent) || goodPercent == null) {
                goodPercent = "0";
            }
            int goodPer = (int) ((Double.parseDouble(goodPercent)) * 100);
            String gooPe = (goodPer) + "%";
            holder.tvGoodNums.setText(gooPe);

            String intr = docotrInfo.getIntro();
            if (intr.length() > 30) {
                holder.tvIntroduce2.setText("\t\t" + intr.substring(0, 30) + "...");
            } else if (intr.length() == 0) {
                holder.tvIntroduce2.setText(R.string.ttwu);
            } else {
                holder.tvIntroduce2.setText("\t\t" + intr);
            }
            String honor = docotrInfo.getHonor();
            if (honor.length() > 30) {
                holder.tvHonor2.setText("\t\t" + honor.substring(0, 30) + "...");
            } else if (honor.length() == 0) {
                holder.tvHonor2.setText(R.string.ttwu);
            } else {
                holder.tvHonor2.setText("\t\t" + honor);
            }
            if (isMe) {
                holder.llCharge.setVisibility(View.VISIBLE);
                holder.tvDynamic.setText(R.string.wodefabu);
                holder.tvApprove.setVisibility(View.VISIBLE);
                holder.tvIntroduce.setText(R.string.wodejianjie);
                holder.tvHonor.setText(R.string.woderongyu);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_DOCTOR_INFO;
        } else {
            return ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() != 0) {
            return data.size();
        }
        return 0 + 1;

    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private OnSubitemClickListener onSubitemClickListener;
        private int pos;

        @Bind(R.id.tv_contenet)
        TextView tvContenet;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_zan)
        TextView tvZan;
        @Bind(R.id.iv_delete)
        ImageView ivDelete;
        @Bind(R.id.gv_pic)
        MyGridView gvPic;
        @Bind(R.id.view_line)
        View viewLine;


        @OnClick({R.id.tv_zan, R.id.iv_delete, R.id.rl_ctrl})
        public void onClick(View v) {
            if (onSubitemClickListener != null) {
                onSubitemClickListener.onSubitemClick(pos, v);
            }
        }

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            gvPic.setClickable(false);
            gvPic.setPressed(false);
            gvPic.setEnabled(false);
//            ImageAdapter imageAdapter = new ImageAdapter(context);
//            gvPic.setAdapter(imageAdapter);
        }
    }

    class OneViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_department)
        TextView tvDepartment;
        @Bind(R.id.tv_see_num)
        TextView tvSeeNum;
        @Bind(R.id.tv_appraise_nums)
        TextView tvAppraiseNum;
        @Bind(R.id.tv_good_nums)
        TextView tvGoodNums;
        @Bind(R.id.tv_introduce2)
        TextView tvIntroduce2;
        @Bind(R.id.tv_honor2)
        TextView tvHonor2;


        @Bind(R.id.tv_introduce)
        TextView tvIntroduce;
        @Bind(R.id.tv_honor)
        TextView tvHonor;
        @Bind(R.id.tv_dynamic)
        TextView tvDynamic;
        @Bind(R.id.tv_approve)
        TextView tvApprove;
        @Bind(R.id.iv_share)
        ImageView ivShare;

        @Bind(R.id.ll_charge)
        LinearLayout llCharge;

        private OnSubitemClickListener onSubitemClickListener;

        @OnClick({R.id.iv_share, R.id.tv_introduce2, R.id.tv_honor2, R.id.rl_personalizedcharge,
                R.id.rl_myincome, R.id.iv_avatar})
        public void onClick(View view) {
            if (onSubitemClickListener != null) {
                onSubitemClickListener.onSubitemClick(0, view);
            }
        }

        OneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
