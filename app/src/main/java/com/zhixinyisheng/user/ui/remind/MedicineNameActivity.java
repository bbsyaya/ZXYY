package com.zhixinyisheng.user.ui.remind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.DataUrl;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedicineNameActivity extends BaseAty {
    public static final String EXTRA_NAME = "extra_name";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.lv_medicine_name)
    ListView lvMedicineName;
    @Bind(R.id.et_name)
    EditText etName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_medicine_name;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("药品名称");
        tvTitleRight.setText("确定");
        userId= UserManager.getUserInfo().getUserId();
        loadData();
    }

    private void loadData() {
        doHttp(RetrofitUtils.createApi(DataUrl.class).selectMedcineName(phone,secret,userId), HttpIdentifier.SELECT_EDIT_MEDICINE_NAME);
    }

    @OnClick({R.id.iv_title_left, R.id.tv_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                this.finish();
                break;
            case R.id.tv_title_right:
                String name = etName.getText().toString().trim();
                if ("".equals(name)) {
                    showToast("请输入药品名称");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(EXTRA_NAME, name);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
