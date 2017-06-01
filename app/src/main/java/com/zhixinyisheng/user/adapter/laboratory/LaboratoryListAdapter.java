package com.zhixinyisheng.user.adapter.laboratory;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.lab.HomeShowType;
import com.zhixinyisheng.user.domain.lab.LaboratoryInterList;
import com.zhixinyisheng.user.domain.lab.LaboratoryList;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.ui.data.laboratory.LaboratoryEditAty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 化验单首页查询适配器
 * Created by 焕焕 on 2017/2/25.
 */
public class LaboratoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LaboratoryList.ListBean> data;
    public OnSubitemClickListener onSubitemClickListener;
    Context context;
    private List<LaboratoryInterList> interList = new ArrayList<>();
    public LaboratoryListInterAdapter laboratoryListInterAdapter;
    private View VIEW_FOOTER;
    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_FOOTER = 1002;

    public void setData(List<LaboratoryList.ListBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<LaboratoryList.ListBean> getData() {
        return data;
    }

    public void setVisiable(int pos) {
        notifyItemChanged(pos);
    }

    public LaboratoryList.ListBean getItem(int pos) {
        return data.get(pos);
    }

    public void addData(List<LaboratoryList.ListBean> newList) {
        data.addAll(newList);
        notifyDataSetChanged();
    }

    public void setOnSubitemClickListener(OnSubitemClickListener onSubitemClickListener) {
        this.onSubitemClickListener = onSubitemClickListener;
    }

    public LaboratoryListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new MyHolder(VIEW_FOOTER);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_lab_list, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isFooterView(position)) {
            final ItemViewHolder holder = (ItemViewHolder) viewHolder;
            LaboratoryList.ListBean bean = data.get(position);
            holder.onSubitemClickListener = onSubitemClickListener;
            holder.pos = position;
            holder.tvListTime.setText(bean.getHomeShowTime());
            holder.tvInterTime.setText(bean.getHomeShowTime().substring(0, 10));
            String type = bean.getHomeShowType();//类型
            String id = bean.getId();
            if (type.equals(HomeShowType.CAMERA)) {
                holder.ivIsWriting.setBackgroundResource(R.mipmap.ic_lab_camera);
                holder.rlLabType.setVisibility(View.GONE);
            } else {
                holder.ivIsWriting.setBackgroundResource(R.mipmap.ic_lab_hand);
                holder.rlLabType.setVisibility(View.VISIBLE);
            }
            judgeType(holder, type);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.rvLabInter.setLayoutManager(layoutManager);
            laboratoryListInterAdapter = new LaboratoryListInterAdapter(context);
            holder.rvLabInter.setAdapter(laboratoryListInterAdapter);
            List<LaboratoryInterList> beanList = handlerList(id);
            laboratoryListInterAdapter.setData(beanList);
            boolean isVisiable = bean.isVisiable();
            if (isVisiable) {
                holder.llInter.setVisibility(View.VISIBLE);
                holder.ivListArrow.setImageResource(R.mipmap.hyd_btn_sq);
            } else {
                holder.llInter.setVisibility(View.GONE);
                holder.ivListArrow.setImageResource(R.mipmap.hyd_btn_zk);
            }
        }
    }


    private List<LaboratoryInterList> handlerList(String idBind) {
        interList.clear();
        int count = data.size();
        for (int i = 0; i < count; i++) {
            LaboratoryList.ListBean bean = data.get(i);
            String type = bean.getHomeShowType();//可以不要了
            String id = bean.getId();
            if (id.equals(idBind)) {
                if (type.equals(HomeShowType.XCG)) {//血常规
                    selectXCG(bean);
                } else if (type.equals(HomeShowType.NCG)) {//尿常规
                    selectNCG(bean);
                } else if (type.equals(HomeShowType.NDBDL)) {//尿蛋白定量
                    selectNDBDL(bean);
                } else if (type.equals(HomeShowType.MYQDB)) {//免疫球蛋白
                    selectMYQDB(bean);
                } else if (type.equals(HomeShowType.GGN)) {//肝功能
                    selectGGN(bean);
                } else if (type.equals(HomeShowType.SGN)) {//肾功能
                    selectSGN(bean);
                } else if (type.equals(HomeShowType.SZCC)) {//肾脏彩超
                    selectSZCC(bean);
                } else if (type.equals(HomeShowType.DJZ)) {//电解质
                    selectDJZ(bean);
                } else if (type.equals(HomeShowType.YGWX)) {//乙肝五项
                    selectYGWX(bean);
                } else if (type.equals(HomeShowType.KTXL)) {//抗体系列
                    selectKTXL(bean);
                } else if (type.equals(HomeShowType.NX)) {//凝血
                    selectNX(bean);
                } else if (type.equals(HomeShowType.BDJC)) {//病毒检测
                    selectBDJC(bean);
                } else if (type.equals(HomeShowType.CAMERA)) {//拍照
                    String content = bean.getContent();
                    String[] row = content.split(LaboratoryEditAty.ROW_SEPARATOR);
                    for (int j = 0; j < row.length; j++) {
                        String[] column = row[j].split(LaboratoryEditAty.COLUMN_SEPARATOR);
                        LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
                        try {
                            laboratoryInterList.setName(column[0]);
                        } catch (Exception e) {
                            e.printStackTrace();
                            laboratoryInterList.setName(" ");
                        }
                        try {
                            laboratoryInterList.setData(column[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                            laboratoryInterList.setData(" ");
                        }
                        try {
                            laboratoryInterList.setUnit(column[2]);
                        } catch (Exception e) {
                            e.printStackTrace();
                            laboratoryInterList.setUnit(" ");
                        }
                        laboratoryInterList.setZheXianTu(false);
                        interList.add(laboratoryInterList);
                    }
                    return interList;
                }
            }
        }
        return interList;
    }

    private void selectBDJC(LaboratoryList.ListBean bean) {
        if (!bean.getEB().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getEB());
            laboratoryInterList.setName(context.getString(R.string.ebbingdukangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getEB().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getSH().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getSH());
            laboratoryInterList.setName(context.getString(R.string.meiduluoxuantikangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getSH().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAIDS().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAIDS());
            laboratoryInterList.setName(context.getString(R.string.kangaizibingkangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getAIDS().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCELL().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCELL());
            laboratoryInterList.setName(context.getString(R.string.kangjuxibaobingdukangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getCELL().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectNX(LaboratoryList.ListBean bean) {
        if (!bean.getTt().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTt());
            laboratoryInterList.setName(context.getString(R.string.ningxuemeishijian));
            laboratoryInterList.setUnit(context.getString(R.string.miao));
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getTt());
            if ((data >= 0 && data < 12) || (data > 16 && data <= 22)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data > 22) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getPt().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getPt());
            laboratoryInterList.setName(context.getString(R.string.ningxuemeiyuanshijian));
            laboratoryInterList.setUnit(context.getString(R.string.miao));
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getPt());
            if ((data >= 8 && data < 11) || (data > 14 && data <= 17)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 8 || data > 17) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getFib().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getFib());
            laboratoryInterList.setName(context.getString(R.string.xianweidanbaiyuan));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getFib());
            if ((data >= 1.5 && data < 2) || (data > 4 && data <= 5)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 1.5 || data > 5) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAptt().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAptt());
            laboratoryInterList.setName(context.getString(R.string.huohuabufenningxuehuomeishijian));
            laboratoryInterList.setUnit(context.getString(R.string.miao));
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getFib());
            if ((data >= 15 && data < 25) || (data > 37 && data <= 47)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 15 || data > 47) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getDd().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getDd());
            laboratoryInterList.setName(context.getString(R.string.xuejiangderjuticeding));
            laboratoryInterList.setUnit("mg/L");
            laboratoryInterList.setZheXianTu(true);
            if (!bean.getDd().equals("")) {
                Double data = Double.valueOf(bean.getDd());
                if ((data > 0.5 && data <= 1)) {
                    laboratoryInterList.setColorFlag("2");
                } else if (data > 1) {
                    laboratoryInterList.setColorFlag("3");
                } else {
                    laboratoryInterList.setColorFlag("1");
                }
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectKTXL(LaboratoryList.ListBean bean) {
        if (!bean.getAna().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAna());
            laboratoryInterList.setName(context.getString(R.string.kanghekangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getAna().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAntiPM1().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAntiPM1());
            laboratoryInterList.setName(context.getString(R.string.kangpm1kangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getAntiPM1().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAntiSa().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAntiSa());
            laboratoryInterList.setName(context.getString(R.string.kangsakangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getAntiSa().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getZxlxbbj().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getZxlxbbj());
            laboratoryInterList.setName(context.getString(R.string.kangzhongxinglixibaobaojiang));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getZxlxbbj().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAntscl70().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAntscl70());
            laboratoryInterList.setName(context.getString(R.string.kangsc170kangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getAntscl70().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAntssA().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAntssA());
            laboratoryInterList.setName(context.getString(R.string.kangssakangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getAntssA().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAntssB().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAntssB());
            laboratoryInterList.setName(context.getString(R.string.kangssbkangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getAntssB().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getU1rnp().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getU1rnp());
            laboratoryInterList.setName(context.getString(R.string.kangu1rnpkangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getU1rnp().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getDna().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getDna());
            laboratoryInterList.setName(context.getString(R.string.kangshuangliandnakangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getDna().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAca().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAca());
            laboratoryInterList.setName(context.getString(R.string.kangzhuosidiankangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getAca().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectYGWX(LaboratoryList.ListBean bean) {
        if (!bean.getHBSAG().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHBSAG());
            laboratoryInterList.setName(context.getString(R.string.yiganbiaomiankangyuan));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getHBSAG().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getHBS().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHBS());
            laboratoryInterList.setName(context.getString(R.string.yiganbiaomiankangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getHBS().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getHBEAG().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHBEAG());
            laboratoryInterList.setName(context.getString(R.string.yiganekangyuan));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getHBEAG().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getHBE().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHBE());
            laboratoryInterList.setName(context.getString(R.string.yiganekangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getHBE().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getHBC().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHBC());
            laboratoryInterList.setName(context.getString(R.string.yiganhexinkangti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getHBC().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectDJZ(LaboratoryList.ListBean bean) {
        if (!bean.getPotassium().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getPotassium());
            laboratoryInterList.setName(context.getString(R.string.jia));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getPotassium());
            if ((data > 3 && data < 3.5) || (data > 5.3 && data <= 6)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data <= 3 || data > 6) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getSodium().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getSodium());
            laboratoryInterList.setName(context.getString(R.string.na));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getSodium());
            if ((data >= 100 && data <= 136) || (data >= 148 && data <= 160)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 100 || data > 160) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getChlorin().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getChlorin());
            laboratoryInterList.setName(context.getString(R.string.lv));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getChlorin());
            if ((data >= 85 && data <= 98) || (data >= 111 && data <= 125)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 85 || data > 125) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getMagnesium().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getMagnesium());
            laboratoryInterList.setName(context.getString(R.string.mei));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getMagnesium());
            if ((data >= 0.4 && data <= 0.6) || (data >= 1.2 && data <= 2.5)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 0.4 || data > 1.5) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCalcium().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCalcium());
            laboratoryInterList.setName(context.getString(R.string.gai));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getCalcium());
            if ((data >= 1.95 && data <= 2.02) || (data >= 2.55 && data < 2.75)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 1.95 || data > 2.75) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getPhosphorus().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getPhosphorus());
            laboratoryInterList.setName(context.getString(R.string.lin));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getPhosphorus());
            if ((data <= 0.75 && data >= 0.89) || (data >= 1.35 && data <= 1.45)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 0.75 || data > 1.45) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCarbonDioxide().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCarbonDioxide());
            laboratoryInterList.setName(context.getString(R.string.zongeryanghuatan));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getCarbonDioxide());
            if ((data <= 17 && data >= 21) || (data >= 30 && data <= 35)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 17 || data > 35) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAG().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAG());
            laboratoryInterList.setName(context.getString(R.string.yinlizijianxi));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getAG());
            if ((data <= 5 && data >= 7) || (data >= 16 && data <= 20)) {
                laboratoryInterList.setColorFlag("2");
            } else if (data < 5 || data > 20) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectSZCC(LaboratoryList.ListBean bean) {
        if (!bean.getLEFTKIDNEY().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getLEFTKIDNEY());
            laboratoryInterList.setName(context.getString(R.string.zuoshentiji));
            laboratoryInterList.setUnit("cm");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getLEFTKIDNEY().split("\\*")[1].equals("")) {
            } else {
                Double data = Double.valueOf(bean.getLEFTKIDNEY().split("\\*")[1]);
                if (data > 120) {
                    laboratoryInterList.setColorFlag("2");
                } else if (data < 70) {
                    laboratoryInterList.setColorFlag("3");
                } else {
                    laboratoryInterList.setColorFlag("1");
                }
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getRIGHTKIDNEY().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getRIGHTKIDNEY());
            laboratoryInterList.setName(context.getString(R.string.youshentiji));
            laboratoryInterList.setUnit("cm");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getRIGHTKIDNEY().split("\\*")[1].equals("")) {
            } else {
                Double data = Double.valueOf(bean.getRIGHTKIDNEY().split("\\*")[1]);
                if (data > 120) {
                    laboratoryInterList.setColorFlag("2");
                } else if (data < 70) {
                    laboratoryInterList.setColorFlag("3");
                } else {
                    laboratoryInterList.setColorFlag("1");
                }
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getRCTK().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getRCTK());
            laboratoryInterList.setName(context.getString(R.string.shenshizhihoudu));
            laboratoryInterList.setUnit("mm");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getRCTK());
            if (data < 1.5 && data > 2) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getTBAA().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTBAA());
            laboratoryInterList.setName(context.getString(R.string.zuidawuhuishengquyu));
            laboratoryInterList.setUnit("mm");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getTBAA());
            if (data <= 3 && data > 0) {
                laboratoryInterList.setColorFlag("2");
            } else if (data > 3) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getTBSES().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTBSES());
            laboratoryInterList.setName(context.getString(R.string.zuidaqianghuishengban));
            laboratoryInterList.setUnit("mm");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getTBSES());
            if (data > 0) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getTLKBF().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTLKBF());
            laboratoryInterList.setName(context.getString(R.string.zuoshenxueliuqingkuang));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            interList.add(laboratoryInterList);
        }
        if (!bean.getTRKBF().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTRKBF());
            laboratoryInterList.setName(context.getString(R.string.youshenxueliuqingkuang));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            interList.add(laboratoryInterList);
        }
        if (!bean.getCSYSTEM().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCSYSTEM());
            laboratoryInterList.setName(context.getString(R.string.jihexitongqingkuang));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            interList.add(laboratoryInterList);
        }
    }

    private void selectSGN(LaboratoryList.ListBean bean) {
        if (!bean.getUREANITROGEN().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getUREANITROGEN());
            laboratoryInterList.setName(context.getString(R.string.niaosudan));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double nsd_num = Double.valueOf(bean.getUREANITROGEN());
            if (nsd_num >= 8.3 && nsd_num <= 19) {
                laboratoryInterList.setColorFlag("2");
            } else if (nsd_num < 2.9 || nsd_num > 19) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCREATININE().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCREATININE());
            laboratoryInterList.setName(context.getString(R.string.jigan));
            laboratoryInterList.setUnit("μmmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double jg_num = Double.valueOf(bean.getCREATININE());
            if (jg_num >= 107 && jg_num <= 442) {
                laboratoryInterList.setColorFlag("2");
            } else if (jg_num > 442 || jg_num < 44) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getUA().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getUA());
            laboratoryInterList.setName(context.getString(R.string.niaosudan));
            laboratoryInterList.setUnit("μmmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double ns_num = Double.valueOf(bean.getUA());
            if (ns_num > 428 || ns_num < 208) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getMICROGLOBULIN().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getMICROGLOBULIN());
            laboratoryInterList.setName(context.getString(R.string.weiqiudanbai));
            laboratoryInterList.setUnit("U/L");
            laboratoryInterList.setZheXianTu(true);
            Double wqdb_num = Double.valueOf(bean.getMICROGLOBULIN());
            if (wqdb_num < 0 || wqdb_num > 2.7) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCYSTATINC().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCYSTATINC());
            laboratoryInterList.setName(context.getString(R.string.guangyisu));
            laboratoryInterList.setUnit("mg/L");
            laboratoryInterList.setZheXianTu(true);
            Double ygs_num = Double.valueOf(bean.getCYSTATINC());
            if (ygs_num > 1.03 || ygs_num < 0.59) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getHCY().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHCY());
            laboratoryInterList.setName(context.getString(R.string.tongxingbanguangansuan));
            laboratoryInterList.setUnit("μmmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double txbgas_num = Double.valueOf(bean.getHCY());
            if (txbgas_num > 20) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectGGN(LaboratoryList.ListBean bean) {
        if (!bean.getALT().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getALT());
            laboratoryInterList.setName(context.getString(R.string.gubingzhuananmei));
            laboratoryInterList.setUnit("U/L");
            laboratoryInterList.setZheXianTu(true);
            Double gbzam_num = Double.valueOf(bean.getALT());
            if (gbzam_num > 40) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getPAT().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getPAT());
            laboratoryInterList.setName(context.getString(R.string.gubingzhuananxiananmei));
            laboratoryInterList.setUnit("U/L");
            laboratoryInterList.setZheXianTu(true);
            Double gbxjzam_num = Double.valueOf(bean.getPAT());
            if (gbxjzam_num > 50) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getAST().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getAST());
            laboratoryInterList.setName(context.getString(R.string.gucaozhuananmei));
            laboratoryInterList.setUnit("U/L");
            laboratoryInterList.setZheXianTu(true);
            Double gczam_num = Double.valueOf(bean.getAST());
            if (gczam_num > 40) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getTPO().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTPO());
            laboratoryInterList.setName(context.getString(R.string.zongdanbai));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double zdb_num = Double.valueOf(bean.getTPO());
            if (zdb_num > 83 || (zdb_num >= 40 && zdb_num < 60)) {
                laboratoryInterList.setColorFlag("2");
            } else if (zdb_num < 40) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getRICIM().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getRICIM());
            laboratoryInterList.setName(context.getString(R.string.baidanbai));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double bdb_num = Double.valueOf(bean.getRICIM());
            if (bdb_num > 56 || (bdb_num >= 25 && bdb_num < 35)) {
                laboratoryInterList.setColorFlag("2");
            } else if (bdb_num < 25) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getGLOBULOSE().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getGLOBULOSE());
            laboratoryInterList.setName(context.getString(R.string.qiudanbai));
            laboratoryInterList.setUnit("U/L");
            laboratoryInterList.setZheXianTu(true);
            Double qdb_num = Double.valueOf(bean.getGLOBULOSE());
            if (qdb_num < 20 || qdb_num > 35) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getTBIL().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTBIL());
            laboratoryInterList.setName(context.getString(R.string.zongdanhongsu));
            laboratoryInterList.setUnit("μmmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double zdhs_num = Double.valueOf(bean.getTBIL());
            if (zdhs_num > 22) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getTRIGLYCERIDE().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTRIGLYCERIDE());
            laboratoryInterList.setName(context.getString(R.string.ganyousanzhi));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double gysz_num = Double.valueOf(bean.getTRIGLYCERIDE());
            if (gysz_num < 0.56 || gysz_num > 1.71) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCHOLESTEROL().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCHOLESTEROL());
            laboratoryInterList.setName(context.getString(R.string.zongdanguchun));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double zdgc_num = Double.valueOf(bean.getCHOLESTEROL());
            if (zdgc_num < 0.92 || zdgc_num > 5.72) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getHDL().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHDL());
            laboratoryInterList.setName(context.getString(R.string.gaomiduzhidanbai));
            laboratoryInterList.setUnit("mmol/L");
            laboratoryInterList.setZheXianTu(true);
            Double gmdzdb_num = Double.valueOf(bean.getHDL());
            if (gmdzdb_num < 0.8) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectMYQDB(LaboratoryList.ListBean bean) {
        if (!bean.getIGG().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getIGG());
            laboratoryInterList.setName(context.getString(R.string.mianyiqiudanbaig));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double icc_num = Double.valueOf(bean.getIGG());
            if (icc_num < 7 || icc_num > 16) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getIGA().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getIGA());
            laboratoryInterList.setName(context.getString(R.string.mianyiqiudanbaia));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double ica_num = Double.valueOf(bean.getIGA());
            if (ica_num < 0.7 || ica_num > 4) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getIGM().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getIGM());
            laboratoryInterList.setName(context.getString(R.string.mianyiqiudanbaim));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double icm_num = Double.valueOf(bean.getIGM());
            if (icm_num < 0.4 || icm_num > 2.3) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getIGK().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getIGK());
            laboratoryInterList.setName(context.getString(R.string.mianyiqiudanbaik));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double ick_num = Double.valueOf(bean.getIGK());
            if (ick_num < 1.7 || ick_num > 3.7) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getIGR().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getIGR());
            laboratoryInterList.setName(context.getString(R.string.mianyiqiudanbairu));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double icr_num = Double.valueOf(bean.getIGR());
            if (icr_num < 0.9 || icr_num > 2.1) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCOQ().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCOQ());
            laboratoryInterList.setName(context.getString(R.string.butic1q));
            laboratoryInterList.setUnit("mg/L");
            laboratoryInterList.setZheXianTu(true);
            Double coq_num = Double.valueOf(bean.getCOQ());
            if (coq_num < 159 || coq_num > 233) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getBTB().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getBTB());
            laboratoryInterList.setName(context.getString(R.string.butibyinzi));
            laboratoryInterList.setUnit("mg/L");
            laboratoryInterList.setZheXianTu(true);
            Double btb_num = Double.valueOf(bean.getBTB());
            if (btb_num < 100 || btb_num > 400) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCH50().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCH50());
            laboratoryInterList.setName(context.getString(R.string.zongbutich50));
            laboratoryInterList.setUnit("U/mL");
            laboratoryInterList.setZheXianTu(true);
            Double ch50_num = Double.valueOf(bean.getCH50());
            if (ch50_num < 31 || ch50_num > 54) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getBTC3().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getBTC3());
            laboratoryInterList.setName(context.getString(R.string.butic3));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double btc3_num = Double.valueOf(bean.getBTC3());
            if (btc3_num < 0.9 || btc3_num > 1.8) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getBTC4().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getBTC4());
            laboratoryInterList.setName(context.getString(R.string.butic4));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double btc4_num = Double.valueOf(bean.getBTC4());
            if (btc4_num < 0.1 || btc4_num > 0.4) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getCFYDB().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getCFYDB());
            laboratoryInterList.setName(context.getString(R.string.cfanyingdanbai));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double cfydb_num = Double.valueOf(bean.getCFYDB());
            if (cfydb_num < 0 || cfydb_num > 8.2) {
                laboratoryInterList.setColorFlag("3");
            } else if (cfydb_num > 3 && cfydb_num <= 8.2) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectNDBDL(LaboratoryList.ListBean bean) {
        if (!bean.getUPVALUE().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getUPVALUE());
            laboratoryInterList.setName(context.getString(R.string.niaodanbaidingliang));
            laboratoryInterList.setUnit("g/24h");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getUPVALUE());
            if (data >= 0.16 && data <= 2) {
                laboratoryInterList.setColorFlag("2");
            } else if (data > 2) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectNCG(LaboratoryList.ListBean bean) {
        if (!bean.getPROTEIN().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getPROTEIN());
            laboratoryInterList.setName(context.getString(R.string.danbaizhi));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getPROTEIN().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getBLD().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getBLD());
            laboratoryInterList.setName(context.getString(R.string.qianxue));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getBLD().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getPROPORTION().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getPROPORTION());
            laboratoryInterList.setName(context.getString(R.string.bizhong));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getPROPORTION());
            if (data < 1.003 || data > 1.035) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getREDBLOODCELL().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getREDBLOODCELL());
            laboratoryInterList.setName(context.getString(R.string.hongxibao));
            laboratoryInterList.setUnit("/ul");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getREDBLOODCELL());
            if (data > 25) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getHEMAMEBA().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHEMAMEBA());
            laboratoryInterList.setName(context.getString(R.string.baixibao));
            laboratoryInterList.setUnit("/ul");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getHEMAMEBA());
            if (data > 21.3) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getKET().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getKET());
            laboratoryInterList.setName(context.getString(R.string.tongti));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getKET().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getREDBLOODCELLHIGH().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getREDBLOODCELLHIGH());
            laboratoryInterList.setName(context.getString(R.string.hongxibaogaobei));
            laboratoryInterList.setUnit("/HPF");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getREDBLOODCELLHIGH());
            if (data > 4.5) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getHEMAMEBAHIGH().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getHEMAMEBA());
            laboratoryInterList.setName(context.getString(R.string.baixibaogaobei));
            laboratoryInterList.setUnit("/HPF");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getHEMAMEBAHIGH());
            if (data > 5.4) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getGLUCOSE().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getGLUCOSE());
            laboratoryInterList.setName(context.getString(R.string.putaotang));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getGLUCOSE().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getUBG().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getUBG());
            laboratoryInterList.setName(context.getString(R.string.niaodanyuan));
            laboratoryInterList.setUnit(" ");
            laboratoryInterList.setZheXianTu(false);
            if (bean.getUBG().contains("+")) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getEPITHELIALCELL().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getEPITHELIALCELL());
            laboratoryInterList.setName(context.getString(R.string.shangpixibao));
            laboratoryInterList.setUnit("/ul");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getEPITHELIALCELL());
            if (data > 21.3) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getTUBE().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getTUBE());
            laboratoryInterList.setName(context.getString(R.string.guanxing));
            laboratoryInterList.setUnit("/ul");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getTUBE());
            if (data > 1.3) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getPATHCAST().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getPATHCAST());
            laboratoryInterList.setName(context.getString(R.string.bingliguanxing));
            laboratoryInterList.setUnit("/ul");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getPATHCAST());
            if (data > 0) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getSREC().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getSREC());
            laboratoryInterList.setName(context.getString(R.string.xiaoyuanshangpixibao));
            laboratoryInterList.setUnit("/ul");
            laboratoryInterList.setZheXianTu(true);
            Double data = Double.valueOf(bean.getSREC());
            if (data > 0) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    private void selectXCG(LaboratoryList.ListBean bean) {
        if (!bean.getWBC().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getWBC());
            laboratoryInterList.setName(context.getString(R.string.baixibaojishu));
            laboratoryInterList.setUnit("*10^9/L");
            laboratoryInterList.setZheXianTu(true);
            Double wbc_num = Double.valueOf(bean.getWBC());
            if (wbc_num <= 3.5 || wbc_num >= 9.5) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getRBC().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getRBC());
            laboratoryInterList.setName(context.getString(R.string.hongxibaojishu));
            laboratoryInterList.setUnit("*10^9/L");
            laboratoryInterList.setZheXianTu(true);
            Double rbc_num = Double.valueOf(bean.getRBC());
            if (rbc_num <= 4.3 || rbc_num >= 5.8) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getNEUTROPHIL().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getNEUTROPHIL());
            laboratoryInterList.setName(context.getString(R.string.zhongxinglixibaobaifenshu));
            laboratoryInterList.setUnit("%");
            laboratoryInterList.setZheXianTu(true);
            Double wbc_num = Double.valueOf(bean.getNEUTROPHIL());
            if (wbc_num <= 40 || wbc_num >= 75) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getNEUT().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getNEUT());
            laboratoryInterList.setName(context.getString(R.string.zhongxinglixibaojueduishu));
            laboratoryInterList.setUnit("*10^9/L");
            laboratoryInterList.setZheXianTu(true);
            Double NEUT_num = Double.valueOf(bean.getNEUT());
            if (NEUT_num <= 1.8 || NEUT_num >= 6.3) {
                laboratoryInterList.setColorFlag("2");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
        if (!bean.getOXYPHORASE().equals("")) {
            LaboratoryInterList laboratoryInterList = new LaboratoryInterList();
            laboratoryInterList.setData(bean.getOXYPHORASE());
            laboratoryInterList.setName(context.getString(R.string.xuehongdanbai));
            laboratoryInterList.setUnit("g/L");
            laboratoryInterList.setZheXianTu(true);
            Double OXYPHORASE_num = Double.valueOf(bean.getOXYPHORASE());
            if (OXYPHORASE_num >= 100 && OXYPHORASE_num < 130) {
                laboratoryInterList.setColorFlag("2");
            } else if (OXYPHORASE_num < 100 || OXYPHORASE_num > 175) {
                laboratoryInterList.setColorFlag("3");
            } else {
                laboratoryInterList.setColorFlag("1");
            }
            interList.add(laboratoryInterList);
        }
    }

    /**
     * 判断类型
     */
    private void judgeType(ItemViewHolder holder, String type) {
        if (type.equals(HomeShowType.NCG)) {
            holder.tvInterType.setText(R.string.niaochanggui);
        } else if (type.equals(HomeShowType.XCG)) {
            holder.tvInterType.setText(R.string.xuechanggui);
        } else if (type.equals(HomeShowType.NDBDL)) {
            holder.tvInterType.setText(R.string.niaodanbaidingliang);
        } else if (type.equals(HomeShowType.SGN)) {
            holder.tvInterType.setText(R.string.shengongneng);
        } else if (type.equals(HomeShowType.GGN)) {
            holder.tvInterType.setText(R.string.gangongneng);
        } else if (type.equals(HomeShowType.DJZ)) {
            holder.tvInterType.setText(R.string.dianjiezhi);
        } else if (type.equals(HomeShowType.SZCC)) {
            holder.tvInterType.setText(R.string.shenzangcaichao);
        } else if (type.equals(HomeShowType.MYQDB)) {
            holder.tvInterType.setText(R.string.mianyiqiudanbai);
        } else if (type.equals(HomeShowType.YGWX)) {
            holder.tvInterType.setText(R.string.yiganwuxiang);
        } else if (type.equals(HomeShowType.NX)) {
            holder.tvInterType.setText(R.string.ningxue);
        } else if (type.equals(HomeShowType.BDJC)) {
            holder.tvInterType.setText(R.string.bingdujiance);
        } else if (type.equals(HomeShowType.KTXL)) {
            holder.tvInterType.setText(R.string.kangtixilie);
        } else if (type.equals(HomeShowType.CAMERA)) {
            holder.tvInterType.setText(R.string.paizhao);
        }
    }

    @Override
    public int getItemCount() {
//        if (data == null) {
//            return 0;
//        }
//        return data.size();
        int count = (data == null ? 0 : data.size());
        if (VIEW_FOOTER != null) {
            count++;
        }
        return count;
    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
//            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private OnSubitemClickListener onSubitemClickListener;
        private int pos;
        @Bind(R.id.tv_list_time)
        TextView tvListTime;
        @Bind(R.id.iv_list_arrow)
        ImageView ivListArrow;
        @Bind(R.id.tv_inter_type)
        TextView tvInterType;
        @Bind(R.id.tv_inter_time)
        TextView tvInterTime;
        @Bind(R.id.rv_lab_inter)
        RecyclerView rvLabInter;
        @Bind(R.id.ll_inter)
        LinearLayout llInter;
        @Bind(R.id.iv_isWriting)
        ImageView ivIsWriting;
        @Bind(R.id.rl_outer)
        RelativeLayout rlOuter;
        @Bind(R.id.rl_lab_type)
        RelativeLayout rlLabType;

        @OnClick(R.id.rl_outer)
        public void onClick(View view) {
            if (onSubitemClickListener != null) {
                onSubitemClickListener.onSubitemClick(pos, llInter);
            }
        }

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
