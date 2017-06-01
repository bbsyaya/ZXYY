package com.zhixinyisheng.user.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.and.yzy.frame.util.SPUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 转换语言（测试）
 * Created by 焕焕 on 2017/3/20.
 */

public class ChangeLanguageAty extends BaseAty {
    @Bind(R.id.rb_system)
    RadioButton rbSystem;
    @Bind(R.id.rb_chinese)
    RadioButton rbChinese;
    @Bind(R.id.rb_english)
    RadioButton rbEnglish;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.title_btn)
    Button titleBtn;

    @Override
    public int getLayoutId() {
        return R.layout.aty_change_language;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(R.string.duoyuyan);
        ivBack.setVisibility(View.VISIBLE);
        titleBtn.setVisibility(View.VISIBLE);
        titleBtn.setText(R.string.finish);
        SPUtils spUtils = new SPUtils("language");
        String langStr = (String) spUtils.get("language", "auto");
        if ("zh".equals(langStr)) {
            rbChinese.setChecked(true);
        } else if ("en".equals(langStr)) {
            rbEnglish.setChecked(true);
        } else if ("auto".equals(langStr)) {
            rbSystem.setChecked(true);
        }
    }


    @OnClick({R.id.rb_system,R.id.rb_chinese,R.id.rb_english,R.id.cjs_rlb,R.id.title_btn, R.id.rl_system, R.id.rl_chinese, R.id.rl_english})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.title_btn:
                if (rbSystem.isChecked()) {
                    switchLang("auto");
                } else if (rbChinese.isChecked()) {
                    switchLang("zh");
                } else if (rbEnglish.isChecked()) {
                    switchLang("en");
                }
                break;
            case R.id.rl_system:
            case R.id.rb_system:
                initChecked();
                rbSystem.setChecked(true);
                break;
            case R.id.rl_chinese:
            case R.id.rb_chinese:
                initChecked();
                rbChinese.setChecked(true);
                break;
            case R.id.rl_english:
            case R.id.rb_english:
                initChecked();
                rbEnglish.setChecked(true);
                break;
        }
    }

    private void initChecked() {
        rbSystem.setChecked(false);
        rbChinese.setChecked(false);
        rbEnglish.setChecked(false);
    }

}
