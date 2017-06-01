package com.zhixinyisheng.user.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.config.SavePath;
import com.and.yzy.frame.util.FileUtils;
import com.and.yzy.frame.util.SPUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.enkill.DataCleanManager;

import butterknife.Bind;
import butterknife.OnClick;

import static com.zhixinyisheng.user.R.id.cjs_tvt;

/**
 * 通用
 * Created by 焕焕 on 2017/3/22.
 */

public class GeneralAty extends BaseAty {
    @Bind(cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_language)
    TextView tvLanguage;
    @Bind(R.id.tv_cache)
    TextView tvCache;

    @Override
    public int getLayoutId() {
        return R.layout.aty_general;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ivBack.setVisibility(View.VISIBLE);
        cjsTvt.setText(R.string.tongyong);
        SPUtils spUtils = new SPUtils("language");
        String langStr = (String) spUtils.get("language", "auto");
        if ("zh".equals(langStr)) {
            tvLanguage.setText(R.string.jiantizhongwen);
        } else if ("en".equals(langStr)) {
            tvLanguage.setText(R.string.yingwen);
        } else if ("auto".equals(langStr)) {
            tvLanguage.setText(R.string.gensuixitong);
        }
        try {
            String size = DataCleanManager.getTotalCacheSize(this);
            tvCache.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        double size = FileUtils.getFileOrFilesSize(SavePath.savePath+"yzyError",2);
//        tvCache.setText(size+"KB");
    }

    @OnClick({R.id.cjs_rlb, R.id.setting_ll_language, R.id.setting_ll_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.setting_ll_language:
                startActivity(ChangeLanguageAty.class, null);//转换语言
                break;
            case R.id.setting_ll_cache:
                new MaterialDialog(this)
                        .setMDNoTitle(true)
                        .setMDMessage(getString(R.string.ninshifouyaoqingchuhuancun))
                        .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                                FileUtils.delAllFile(SavePath.savePath+"yzyError");
                                try {
                                    DataCleanManager.clearAllCache(GeneralAty.this);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String size = DataCleanManager.getTotalCacheSize(GeneralAty.this);
                                    tvCache.setText(size);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                showToast(getString(R.string.qingchuchenggong));
                            }
                        })
                        .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                            @Override
                            public void dialogBtnOnClick() {
                            }
                        })
                        .show();
                break;
        }
    }
}
