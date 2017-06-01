package com.zhixinyisheng.user.ui.data.laboratory;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.DateTool;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.listview.ListViewForScrollView;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.laboratory.LabEditAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.lab.Laboratory;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.LanguageUtil;
import com.zhixinyisheng.user.util.lab.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 化验单识别后的编辑界面
 * Created by 焕焕 on 2017/2/21.
 */
public class LaboratoryEditAty extends BaseAty {
    public static final String COLUMN_SEPARATOR = "Γ╬Γ";//列分隔符
    public static final String ROW_SEPARATOR = "╞€∫╡";//行分隔符


    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_lab_time)
    TextView tvLabTime;
    @Bind(R.id.lv_lab_edit)
    ListViewForScrollView lvLabEdit;
    List<Laboratory> list_laboratory = new ArrayList<>();
    LabEditAdapter laboratoryEditAdapter;
    private String content = "";
    private CustomDatePicker customDatePicker;
    private String PROPORTION, PROTEIN, BLD, OXYPHORASE, UPVALUE, UPVALUE_UNIT, CREATININE, CREATININE_UNIT,
            UREANITROGEN, UREANITROGEN_UNIT, ALT, ALT_UNIT, potassium,
            potassium_UNIT, calcium, calcium_UNIT, phosphorus, phosphorus_UNIT;
    private int type = 0;//化验单种类
    private String chineseName, resultData, unit;
    private List<String> firstList = new ArrayList<>();
    private List<String> secondList = new ArrayList<>();
    private List<String> thirdList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.aty_laboratory_edit;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.huayandan);
        ivBack.setVisibility(View.VISIBLE);
        initDatePicker();
        tvLabTime.setText(DateTool.getFormatTimeForLab(System.currentTimeMillis()));
//        IntentFilter intentFilter = new IntentFilter(Constant.LAB_CROP_FIRST);
//        registerReceiver(myBroadcast_first, intentFilter);
//        showLoadingDialog(null);
        laboratoryEditAdapter = new LabEditAdapter(this);
        lvLabEdit.setAdapter(laboratoryEditAdapter);
        setNetData();
//        setData();

    }

    private void setNetData() {
        list_laboratory.clear();
        Bundle bundle = getIntent().getExtras();
        String chineseName = bundle.getString("a");
        String resultData = bundle.getString("b");
        String unit = bundle.getString("c");

        String[] first =chineseName.split("\\\\");
        for (int i = 0; i < first.length; i++) {
            firstList.add(first[i]);
        }
        String[] second = resultData.split("\\\\");
        for (int i = 0; i < first.length; i++) {
            if (i < second.length) {
                secondList.add(second[i]);
            } else {
                secondList.add("");
            }
        }
        String[] third = unit.split("\\\\");
        for (int i = 0; i < first.length; i++) {
            if (i < third.length) {
                thirdList.add(third[i]);
            } else {
                thirdList.add("");
            }
        }

        for (int i = 0; i <first.length ; i++) {
            Laboratory laboratory = new Laboratory();
            String chinese = firstList.get(i).replace(" ", "");
            if (firstList.get(i).equals("")){
                continue;
            }

            if (LanguageUtil.judgeLanguage().equals("zh")){
                chinese = chineseReplace(chinese);
            }

            laboratory.setChineseName(chinese);
            String resultDataa = secondList.get(i).replace(" ", "");
            if (LanguageUtil.judgeLanguage().equals("zh")){
                resultDataa = dataReplace(resultDataa);
            }

            laboratory.setResultData(resultDataa);
            String unitt = thirdList.get(i).replace(" ", "");
            laboratory.setUnit(unitt);
            list_laboratory.add(laboratory);
        }
        laboratoryEditAdapter.setData(list_laboratory);
    }

    private void setData() {
        list_laboratory.clear();
        String[] first = Content.chineseName.split("\n");
        for (int i = 0; i < first.length; i++) {
            firstList.add(first[i]);
        }
        String[] second = Content.resultData.split("\n");
        for (int i = 0; i < first.length; i++) {
            if (i < second.length) {
                secondList.add(second[i]);
            } else {
                secondList.add("");
            }
        }
        String[] third = Content.unit.split("\n");
        for (int i = 0; i < first.length; i++) {
            if (i < third.length) {
                thirdList.add(third[i]);
            } else {
                thirdList.add("");
            }
        }
        int a1 = first.length;
//        int a2 = second.length;
//        int a3 = third.length;
//        int min = (a1 < a2 ? a1 : a2) < a3 ? (a1 < a2 ? a1 : a2) : a3;
        for (int i = 0; i < a1; i++) {
            Laboratory laboratory = new Laboratory();
            String chinese = firstList.get(i).replace(" ", "");
            if (LanguageUtil.judgeLanguage().equals("zh")){
                chinese = chineseReplace(chinese);
            }

            laboratory.setChineseName(chinese);
            String resultData = secondList.get(i).replace(" ", "");
            if (LanguageUtil.judgeLanguage().equals("zh")){
                resultData = dataReplace(resultData);
            }

            laboratory.setResultData(resultData);
            String unit = thirdList.get(i).replace(" ", "");
            laboratory.setUnit(unit);
            list_laboratory.add(laboratory);
        }
        laboratoryEditAdapter.setData(list_laboratory);
    }

    private String dataReplace(String resultData) {
        resultData = resultData.replace("()", "0");
        resultData = resultData.replace("〔)", "0");
        resultData = resultData.replace("（〕", "0");
        resultData = resultData.replace("〔〕", "0");
        resultData = resultData.replace("(〕", "0");
        resultData = resultData.replace("l", "1");
        resultData = resultData.replace("】", "1");
        resultData = resultData.replace("【", "1");
        resultData = resultData.replace("Z", "2");
        resultData = resultData.replace("z", "2");
        resultData = resultData.replace("S", "5");
        resultData = resultData.replace("s", "5");
        resultData = resultData.replace("〇", "0");
        resultData = resultData.replace("o", "0");
        resultData = resultData.replace("O", "0");
        resultData = resultData.replace("Q", "0");
        resultData = resultData.replace(",", ".");
        resultData = resultData.replace("'", ".");
        resultData = resultData.replace("′", ".");
        resultData = resultData.replace("L", "1.");
        resultData = resultData.replace("十", "+");
        resultData = resultData.replace("_", ".");
        resultData = resultData.replace("_", ".");
        resultData = resultData.replace("-", ".");
        resultData = resultData.replace("|", "1");
        resultData = resultData.replace("]", "1");
        resultData = resultData.replace("[", "1");
        resultData = resultData.replace("‖", "1");
        return resultData;
    }

    private String chineseReplace(String chinese) {
        chinese = chinese.replace("纲", "细");
        chinese = chinese.replace("乡丁", "红");
        chinese = chinese.replace("乡「", "红");
        chinese = chinese.replace("r'r", "白");
        chinese = chinese.replace("i1", "上");
        chinese = chinese.replace("咖", "管型");
        chinese = chinese.replace("菅", "管");
        chinese = chinese.replace("管唰", "管型");
        chinese = chinese.replace("约", "红");
        chinese = chinese.replace("岫", "高");
        chinese = chinese.replace("讪", "高");
        chinese = chinese.replace("」1", "上");
        chinese = chinese.replace("i;", "上");
        chinese = chinese.replace("t", "上");
        chinese = chinese.replace("剐", "高");
        chinese = chinese.replace("帅", "管型");
        chinese = chinese.replace("刑", "型");
        chinese = chinese.replace("枪", "检");
        chinese = chinese.replace("沓", "查");
        chinese = chinese.replace("啊", "野");
        chinese = chinese.replace("鬲", "高");
        chinese = chinese.replace("炳", "病");
        chinese = chinese.replace("业", "上");
        chinese = chinese.replace("枯", "粘");
        chinese = chinese.replace("钏", "细");
        chinese = chinese.replace("铡", "管型");
        chinese = chinese.replace("简", "管");
        chinese = chinese.replace("简型", "管型");
        chinese = chinese.replace("痫", "病");
        chinese = chinese.replace("黏液绍", "黏液丝");
        chinese = chinese.replace("口细胞", "白细胞");
        chinese = chinese.replace("醐", "酮");
        chinese = chinese.replace("<", "(");
        chinese = chinese.replace(">", ")");
        chinese = chinese.replace("〈", "(");
        chinese = chinese.replace("〉", ")");
        chinese = chinese.replace("〔", "(");
        chinese = chinese.replace("〕", ")");
        chinese = chinese.replace("色细", "包细");
        chinese = chinese.replace("仁", "上");
        chinese = chinese.replace("臼", "白");
        return chinese;
    }

//    BroadcastReceiver myBroadcast_first = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (!Content.chineseName.equals("")&&!Content.resultData.equals("")&&!Content.unit.equals("")){
//                dismissLoadingDialog();
//                setData();
//            }
//
//        }
//    };
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tvLabTime.setText(now.split(" ")[0]);
        Content.time_lab = now.split(" ")[0];
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间

                tvLabTime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Content.chineseName = "";
        Content.resultData = "";
        Content.unit = "";

    }

    @OnClick({R.id.tv_lab_time, R.id.iv_lab_shouxie, R.id.cjs_rlb, R.id.btn_laboratory_commit, R.id.btn_laboratory_repeat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_lab_time:
                customDatePicker.show(tvLabTime.getText().toString());
                break;
            case R.id.iv_lab_shouxie:
                startActivity(LabHandwritingAty.class, null);
                break;
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.btn_laboratory_commit:
                content = "";
                int count = lvLabEdit.getChildCount();
                for (int i = 0; i < count; i++) {
                    RelativeLayout layout = (RelativeLayout) lvLabEdit.getChildAt(i);
                    EditText et_chinese = (EditText) layout.findViewById(R.id.et_lab_chinese);
                    EditText et_data = (EditText) layout.findViewById(R.id.et_lab_data);
                    EditText et_unit = (EditText) layout.findViewById(R.id.et_lab_unit);
                    String chinese = et_chinese.getText().toString().trim();
                    String data = et_data.getText().toString().trim();
                    String unit = et_unit.getText().toString().trim();
                    content += chinese
                            + COLUMN_SEPARATOR + data
                            + COLUMN_SEPARATOR + unit + ROW_SEPARATOR;
                    if (chinese.contains("比重")) {
                        PROPORTION = data;
                        type = 1;
                    } else if (chinese.contains("蛋白质")) {
                        PROTEIN = data;
                        type = 1;
                    } else if (chinese.contains("潜血") || chinese.contains("隐血")) {
                        BLD = data;
                        type = 1;
                    } else if (chinese.contains("红蛋")) {
                        OXYPHORASE = data;
                        type = 2;
                    } else if (chinese.contains("尿蛋白定量")) {
                        UPVALUE = data;
                        UPVALUE_UNIT = unit;
                        type = 3;
                    } else if (chinese.contains("酐")) {
                        CREATININE = data;
                        CREATININE_UNIT = unit;
                        type = 4;
                    } else if (chinese.contains("素氮")) {
                        UREANITROGEN = data;
                        UREANITROGEN_UNIT = unit;
                        type = 4;
                    } else if (chinese.contains("谷丙转氨酶")) {
                        ALT = data;
                        ALT_UNIT = unit;
                        type = 5;
                    } else if (chinese.contains("钾")) {
                        potassium = data;
                        potassium_UNIT = unit;
                        type = 6;
                    } else if (chinese.contains("钙")) {
                        calcium = data;
                        calcium_UNIT = unit;
                        type = 6;
                    } else if (chinese.contains("磷")) {
                        phosphorus = data;
                        phosphorus_UNIT = unit;
                        type = 6;
                    }

                }
                content = content.substring(0, content.length() - ROW_SEPARATOR.length());
                String time = tvLabTime.getText().toString() + ":00";
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(com.zhixinyisheng.user.http.Laboratory.class).save(UserManager.getUserInfo().getPhone(),
                        UserManager.getUserInfo().getSecret(), userId, content, time), HttpIdentifier.LABORATORY_SAVE);
                break;
            case R.id.btn_laboratory_repeat:
                finish();
                break;

        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.LABORATORY_SAVE:
                LaboratoryFgt.isLabLoading = true;
                importantSave();
                break;
            case HttpIdentifier.LABORATORY_IMPORTANCE:
                Logger.e("lablabim ", result);
                showToast(getString(R.string.tijiaochenggong));
                finish();
                break;
        }
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);
        switch (what){
            case HttpIdentifier.LABORATORY_IMPORTANCE:
                finish();
                break;
        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
        switch (what){
            case HttpIdentifier.LABORATORY_IMPORTANCE:
                finish();
                break;
        }
    }

    private void importantSave() {
        if (type == 1) {
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(com.zhixinyisheng.user.http.Laboratory.class).importantSave1(UserManager.getUserInfo().getPhone(),
                    UserManager.getUserInfo().getSecret(), userId, type, UserManager.getUserInfo().getSex(), PROPORTION, PROTEIN, BLD, LanguageUtil.judgeLanguage()), HttpIdentifier.LABORATORY_IMPORTANCE);
        } else if (type == 2) {
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(com.zhixinyisheng.user.http.Laboratory.class).importantSave2(UserManager.getUserInfo().getPhone(),
                    UserManager.getUserInfo().getSecret(), userId, type, UserManager.getUserInfo().getSex(), OXYPHORASE, LanguageUtil.judgeLanguage()), HttpIdentifier.LABORATORY_IMPORTANCE);
        } else if (type == 3) {
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(com.zhixinyisheng.user.http.Laboratory.class).importantSave3(UserManager.getUserInfo().getPhone(),
                    UserManager.getUserInfo().getSecret(), userId, type, UserManager.getUserInfo().getSex(), UPVALUE, UPVALUE_UNIT, LanguageUtil.judgeLanguage()), HttpIdentifier.LABORATORY_IMPORTANCE);
        } else if (type == 4) {
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(com.zhixinyisheng.user.http.Laboratory.class).importantSave4(UserManager.getUserInfo().getPhone(),
                    UserManager.getUserInfo().getSecret(), userId, type, UserManager.getUserInfo().getSex(), CREATININE, CREATININE_UNIT, UREANITROGEN, UREANITROGEN_UNIT, LanguageUtil.judgeLanguage()), HttpIdentifier.LABORATORY_IMPORTANCE);
        } else if (type == 5) {
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(com.zhixinyisheng.user.http.Laboratory.class).importantSave5(UserManager.getUserInfo().getPhone(),
                    UserManager.getUserInfo().getSecret(), userId, type, UserManager.getUserInfo().getSex(), ALT, ALT_UNIT, LanguageUtil.judgeLanguage()), HttpIdentifier.LABORATORY_IMPORTANCE);
        } else if (type == 6) {
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(com.zhixinyisheng.user.http.Laboratory.class).importantSave6(UserManager.getUserInfo().getPhone(),
                    UserManager.getUserInfo().getSecret(), userId, type, UserManager.getUserInfo().getSex(), potassium, potassium_UNIT, calcium, calcium_UNIT, phosphorus, phosphorus_UNIT, LanguageUtil.judgeLanguage()), HttpIdentifier.LABORATORY_IMPORTANCE);
        } else if (type == 0) {
            showToast(getString(R.string.tijiaochenggong));
            finish();
        }
    }
}
