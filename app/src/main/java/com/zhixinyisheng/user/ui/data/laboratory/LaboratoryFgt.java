package com.zhixinyisheng.user.ui.data.laboratory;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.config.SavePath;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.FormBotomDialogBuilder;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.laboratory.LaboratoryListAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.PhotoTheme;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.FindByPidEntity;
import com.zhixinyisheng.user.domain.datas_zhexiantu.Ndbdl_zhexiantu;
import com.zhixinyisheng.user.domain.lab.LaboratoryList;
import com.zhixinyisheng.user.http.DataZhexiantu;
import com.zhixinyisheng.user.http.Laboratory;
import com.zhixinyisheng.user.interfaces.OnSubitemClickListener;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.util.Content;
import com.zhixinyisheng.user.util.lab.DividerItemDecorationForLab;
import com.zhixinyisheng.user.util.tool.UnzipAssets;
import com.zhixinyisheng.user.view.ZheXianTuPop;
import com.zhixinyisheng.user.view.calendar.CalenderDialogTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 化验单
 * Created by 焕焕 on 2017/2/19.
 */
public class LaboratoryFgt extends BaseFgt implements  OnSubitemClickListener {
    private final int REQUEST_CODE_CAMERA = 2220;
    private final int REQUEST_CODE_GALLERY = 2221;
    public static boolean isLabLoading = false;
    @Bind(R.id.ll_lab_empty)
    LinearLayout llLabEmpty;
    @Bind(R.id.ll_lab_have)
    LinearLayout llLabHave;
    @Bind(R.id.rv_lab_main)
    RecyclerView rvLabMain;
    @Bind(R.id.iv_lab_camera)
    ImageView ivLabCamera;
    @Bind(R.id.iv_lab_shouxie)
    ImageView ivLabShouXie;

    private List<PhotoInfo> mPhotoList;
    private LaboratoryListAdapter laboratoryListAdapter;
    List<LaboratoryList.ListBean> data = new ArrayList<>();
    private int page = 1;
    ZheXianTuPop zxtp;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    @Override
    public int getLayoutId() {
        return R.layout.fgt_laboratory;
    }

    @Override
    public void initData() {
        page = 1;
        initAdapter();
        mPhotoList = new ArrayList<>();
        PhotoTheme.settingtheme(getActivity(), mPhotoList);
        showLoadingContentDialog();
        loadLabData();
        laboratoryListAdapter.setOnSubitemClickListener(this);
        IntentFilter intentFilter = new IntentFilter(Constant.LAB_INTER_ADAPTER);
        getActivity().registerReceiver(myBroadcast_adapter, intentFilter);
        if (Content.myHealthFlag == 1 || !UserManager.getUserInfo().getUserId().equals(userId)){
            ivLabCamera.setVisibility(View.GONE);
            ivLabShouXie.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        if (isLabLoading) {
            page = 1;
            showLoadingContentDialog();
            loadLabData();
        }
    }

    private void loadLabData() {
        doHttp(RetrofitUtils.createApi(Laboratory.class).list(userId,
                "10", String.valueOf(page), Content.DATA, phone, secret), HttpIdentifier.LABORATORY_LIST);
    }

    BroadcastReceiver myBroadcast_adapter = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            Bundle bundle = intent.getExtras();
            String labName = bundle.getString("labName");
            judgeZXT(labName);
        }
    };

    private void judgeZXT(String labName) {
        if (labName.equals(getString(R.string.niaodanbaidingliang))) {
            zxtp = new ZheXianTuPop(getContext(), "UPVALUE", 0, 25, getString(R.string.niaodanbaidingliang), getString(R.string.niaodanbaidingliangquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ndbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.bizhong))) {
            zxtp = new ZheXianTuPop(getContext(), "PROPORTION", 0, 3, getString(R.string.bizhong), getString(R.string.bizhongquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.hongxibao))) {
            zxtp = new ZheXianTuPop(getContext(), "REDBLOODCELL", 0, 3, getString(R.string.hongxibao), getString(R.string.hongxibaoquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.baixibao))) {
            zxtp = new ZheXianTuPop(getContext(), "HEMAMEBA", 0, 100, getString(R.string.baixibao), getString(R.string.baixibaoquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.hongxibaogaobei))) {
            zxtp = new ZheXianTuPop(getContext(), "REDBLOODCELLHIGH", 0, 15, getString(R.string.hongxibaogaobei), getString(R.string.hongxibaogaobeiquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.baixibaogaobei))) {
            zxtp = new ZheXianTuPop(getContext(), "HEMAMEBAHIGH", 0, 15, getString(R.string.baixibaogaobei), getString(R.string.baixibaogaobeiquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.shangpixibao))) {
            zxtp = new ZheXianTuPop(getContext(), "EPITHELIALCELL", 0, 50, getString(R.string.shangpixibao), getString(R.string.shangpixibaoquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.guanxing))) {
            zxtp = new ZheXianTuPop(getContext(), "TUBE", 0, 5, getString(R.string.guanxing), getString(R.string.guanxingquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.bingliguanxing))) {
            zxtp = new ZheXianTuPop(getContext(), "PATHCAST", 0, 50, getString(R.string.bingliguanxing), getString(R.string.bingliguanxingquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.xiaoyuanshangpixibao))) {
            zxtp = new ZheXianTuPop(getContext(), "SREC", 0, 50, getString(R.string.xiaoyuanshangpixibao), getString(R.string.xiaoyuanshangpixibaoquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ncg_zhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.ningxuemeishijian))) {
            zxtp = new ZheXianTuPop(getContext(), "TT", 0, 30, getString(R.string.ningxuemeizhi), getString(R.string.ningxuemeiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).nxjcZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.ningxuemeiyuanshijian))) {
            zxtp = new ZheXianTuPop(getContext(), "PT", 0, 20, getString(R.string.ningxuemeiyuanzhi), getString(R.string.ningxuemeiyuanquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).nxjcZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.xianweidanbaiyuan))) {
            zxtp = new ZheXianTuPop(getContext(), "FIB", 0, 10, getString(R.string.xianweidanbaiyuan), getString(R.string.xianweidanbaiyuanquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).nxjcZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.huohuabufenshijian))) {
            zxtp = new ZheXianTuPop(getContext(), "APTT", 0, 50, getString(R.string.huohuabufenzhi), getString(R.string.huohuabufenquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).nxjcZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.xuejiangceding))) {
            zxtp = new ZheXianTuPop(getContext(), "DD", 0, 2, getString(R.string.xuejiangzhi), getString(R.string.xuejiangquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).nxjcZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.shenshizhihoudu))) {
            zxtp = new ZheXianTuPop(getContext(), "RCTK", 0, 5, getString(R.string.shenshizhihoudu), getString(R.string.shenshizhihouduquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).szccZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zuidawuhuishengquyu))) {
            zxtp = new ZheXianTuPop(getContext(), "TBAA", 0, 10, getString(R.string.zuidawuhuishengquyu), getString(R.string.zuidawuhuishengquyuquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).szccZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zuidaqianghuishengban))) {
            zxtp = new ZheXianTuPop(getContext(), "TBSES", 0, 10, getString(R.string.zuidaqianghuishengban), getString(R.string.zuidaqianghuishengbanquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).szccZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.niaosudan))) {
            zxtp = new ZheXianTuPop(getContext(), "UREANITROGEN", 0, 40, getString(R.string.niaosudan), getString(R.string.niaosudanquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).sgnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.jigan))) {
            zxtp = new ZheXianTuPop(getContext(), "CREATININE", 0, 2500, getString(R.string.jiganzhi), getString(R.string.jiganquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).sgnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.niaosuan))) {
            zxtp = new ZheXianTuPop(getContext(), "UA", 0, 1000, getString(R.string.niaosuan), getString(R.string.niaosuanquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).sgnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.weiqiudanbai))) {
            zxtp = new ZheXianTuPop(getContext(), "MICROGLOBULIN", 0, 10, getString(R.string.weiqiudanbai), getString(R.string.weiqiudanbaiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).sgnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.guangyisu))) {
            zxtp = new ZheXianTuPop(getContext(), "CYSTATINC", 0, 10, getString(R.string.guangyisu), getString(R.string.guanyisuquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).sgnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.tongxingbanguangansuan))) {
            zxtp = new ZheXianTuPop(getContext(), "HCY", 0, 100, getString(R.string.tongxingbanguangansuan), getString(R.string.tongxingbanguangansuanquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).sgnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.gubingzhuananmei))) {
            zxtp = new ZheXianTuPop(getContext(), "ALT", 0, 200, getString(R.string.gubingzhuananmei), getString(R.string.gubingzhuananmeiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.gubingzhuananxiananmei))) {
            zxtp = new ZheXianTuPop(getContext(), "PAT", 0, 200, getString(R.string.gubingzhuananxiananmei), getString(R.string.guanxianjizhuananmeiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.gucaozhuananmei))) {
            zxtp = new ZheXianTuPop(getContext(), "AST", 0, 200, getString(R.string.gucaozhuananmei), getString(R.string.gucaozhuananmeiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zongdanbai))) {
            zxtp = new ZheXianTuPop(getContext(), "TPO", 0, 150, getString(R.string.zongdanbai), getString(R.string.zongdanbaiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.baidanbai))) {
            zxtp = new ZheXianTuPop(getContext(), "RICIM", 0, 150, getString(R.string.baidanbaizhi), getString(R.string.baidanbaiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.qiudanbai))) {
            zxtp = new ZheXianTuPop(getContext(), "GLOBULOSE", 0, 150, getString(R.string.qiudanbai), getString(R.string.qiudanbaiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zongdanhongsu))) {
            zxtp = new ZheXianTuPop(getContext(), "TBIL", 0, 100, getString(R.string.zongdanhongsu), getString(R.string.zongdanhongsuquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.ganyousanzhi))) {
            zxtp = new ZheXianTuPop(getContext(), "TRIGLYCERIDE", 0, 10, getString(R.string.ganyousanzhi), getString(R.string.ganyousanzhiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zongdanguchun))) {
            zxtp = new ZheXianTuPop(getContext(), "CHOLESTEROL", 0, 20, getString(R.string.zongdanguchun), getString(R.string.zongdanguchunquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.gaomiduzhidanbai))) {
            zxtp = new ZheXianTuPop(getContext(), "HDL", 0, 5, getString(R.string.gaomiduzhidanbai), getString(R.string.gaomiduzhidanbaiquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).ggnZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.jia))) {
            zxtp = new ZheXianTuPop(getContext(), "potassium", 0, 10, getString(R.string.jia), getString(R.string.jiaquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).djzZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.na))) {
            zxtp = new ZheXianTuPop(getContext(), "sodium", 0, 200, getString(R.string.na), getString(R.string.naquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).djzZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.lv))) {
            zxtp = new ZheXianTuPop(getContext(), "chlorin", 0, 150, getString(R.string.lv), getString(R.string.lvquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).djzZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.mei))) {
            zxtp = new ZheXianTuPop(getContext(), "magnesium", 0, 2, getString(R.string.mei), getString(R.string.meiquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).djzZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.gai))) {
            zxtp = new ZheXianTuPop(getContext(), "calcium", 0, 3, getString(R.string.gai), getString(R.string.gaiquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).djzZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.lin))) {
            zxtp = new ZheXianTuPop(getContext(), "phosphorus", 0, 2, getString(R.string.lin), getString(R.string.linquxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).djzZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zongeryanghuatan))) {
            zxtp = new ZheXianTuPop(getContext(), "carbonDioxide", 0, 50, getString(R.string.zongeryanghuatan), getString(R.string.zongeryanghuatanquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).djzZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.yinlizijianxi))) {
            zxtp = new ZheXianTuPop(getContext(), "AG", 0, 30, getString(R.string.yinlizijianxi), getString(R.string.yinlizijianxiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).djzZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.mianyiqiudanbaig))) {
            zxtp = new ZheXianTuPop(getContext(), "IGG", 0, 50, getString(R.string.mianyiqiudanbaig), getString(R.string.mianyiqiudanbaigquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.mianyiqiudanbaia))) {
            zxtp = new ZheXianTuPop(getContext(), "IGA", 0, 50, getString(R.string.mianyiqiudanbaia), getString(R.string.mianyiqiudanbaiaquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.mianyiqiudanbaim))) {
            zxtp = new ZheXianTuPop(getContext(), "IGM", 0, 20, getString(R.string.mianyiqiudanbaim), getString(R.string.mianyiqiudanbaimquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.mianyiqiudanbaik))) {
            zxtp = new ZheXianTuPop(getContext(), "IGK", 0, 20, getString(R.string.mianyiqiudanbaik), getString(R.string.mianyiqiudanbaikquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.mianyiqiudanbairu))) {
            zxtp = new ZheXianTuPop(getContext(), "IGR", 0, 20, getString(R.string.mianyiqiudanbairu), getString(R.string.mianyiqiudanbairuquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.butic1q))) {
            zxtp = new ZheXianTuPop(getContext(), "COQ", 0, 500, getString(R.string.butic1q), getString(R.string.butic1qquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.butibyinzi))) {
            zxtp = new ZheXianTuPop(getContext(), "BTB", 0, 1000, getString(R.string.butibyinzi), getString(R.string.butibyinziquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zongbutich50))) {
            zxtp = new ZheXianTuPop(getContext(), "CH50", 0, 150, getString(R.string.zongbutich50), getString(R.string.zongbutich50quxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.butic3))) {
            zxtp = new ZheXianTuPop(getContext(), "BTC3", 0, 20, getString(R.string.butic3), getString(R.string.butic3quxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.butic4))) {
            zxtp = new ZheXianTuPop(getContext(), "BTC4", 0, 15, getString(R.string.butic4), getString(R.string.butic4quxian), 0) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.cfanyingdanbai))) {
            zxtp = new ZheXianTuPop(getContext(), "CFYDB", 0, 20, getString(R.string.cfanyingdanbai), getString(R.string.cfanyingdanbaiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).myqdbZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.baixibaojishu))) {
            zxtp = new ZheXianTuPop(getContext(), "WBC", 0, 20, getString(R.string.baixibaojishu), getString(R.string.baixibaojishuquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).xcgZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.hongxibaojishu))) {
            zxtp = new ZheXianTuPop(getContext(), "RBC", 0, 20, getString(R.string.hongxibaojishu), getString(R.string.hongxibaojishuquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).xcgZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zhongxinglixibaobaifenshu))) {
            zxtp = new ZheXianTuPop(getContext(), "NEUTROPHIL", 0, 100, getString(R.string.zhongxinglixibaobaifenshu), getString(R.string.zhongxinglibaifenshuquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).xcgZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.zhongxinglixibaojueduishu))) {
            zxtp = new ZheXianTuPop(getContext(), "NEUT", 0, 20, getString(R.string.zhongxinglixibaojueduishu), getString(R.string.zhongxinglijueduishuquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).xcgZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        } else if (labName.equals(getString(R.string.xuehongdanbai))) {
            zxtp = new ZheXianTuPop(getContext(), "OXYPHORASE", 0, 300, getString(R.string.xuehongdanbai), getString(R.string.xuehongdanbaiquxian), 1) {
                @Override
                public void getZX(String PARAMS) {
                    doHttp(RetrofitUtils.createApi(DataZhexiantu.class).xcgZhexiantu(phone, secret, userId, Content.DATA, PARAMS), 5);
                }
            };

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (myBroadcast_adapter != null) {
                getActivity().unregisterReceiver(myBroadcast_adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void initAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLabMain.setLayoutManager(layoutManager);
        DividerItemDecorationForLab dividerItemDecoration = new DividerItemDecorationForLab(getActivity(),
                DividerItemDecorationForLab.VERTICAL_LIST);
        dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
        rvLabMain.addItemDecoration(dividerItemDecoration);
        laboratoryListAdapter = new LaboratoryListAdapter(getActivity());
        rvLabMain.setAdapter(laboratoryListAdapter);
        View viewFooter = LayoutInflater.from(getActivity()).inflate(R.layout.foot_main, null, false);
        laboratoryListAdapter.addFooterView(viewFooter);
        rvLabMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = layoutManager .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1){
                    page++;
                    loadLabData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.LABORATORY_LIST:
                isLabLoading = false;
                LaboratoryList laboratoryList = JSON.parseObject(result, LaboratoryList.class);
                if (page == 1 && Content.DATA.equals(time)) {
                    if (laboratoryList.getSize().equals("0")) {
                        llLabHave.setVisibility(View.GONE);
                        llLabEmpty.setVisibility(View.VISIBLE);
                        if (!UserManager.getUserInfo().getUserId().equals(userId)){
                            llLabEmpty.setVisibility(View.GONE);
                        }
                    } else {
                        llLabHave.setVisibility(View.VISIBLE);
                        llLabEmpty.setVisibility(View.GONE);
                    }
                }
                data = laboratoryList.getList();
                if (page == 1) {
                    data.get(0).setVisiable(true);
                    laboratoryListAdapter.setData(data);
                    return;
                }
                if (data != null && data.size() != 0) {
                    laboratoryListAdapter.addData(data);
                } else {
//                    showToast(getString(R.string.zanwushuju));
                    page--;
                }
                break;
            case 6:
                FindByPidEntity findByPidEntity = JSON.parseObject(result, FindByPidEntity.class);
                new CalenderDialogTest(getActivity(), findByPidEntity.getList()) {
                    @Override
                    public void getZXTData() {
                        page = 1;
                        loadLabData();
                    }
                };
                break;
            case HttpIdentifier.ZHEXIANTU:
                Ndbdl_zhexiantu ndbdl_zhexiantu = JSON.parseObject(result, Ndbdl_zhexiantu.class);
                for (int i = 0; i < ndbdl_zhexiantu.getSize(); i++) {
                    String strt = ndbdl_zhexiantu.getList().get(i).getBYTIME();
                    String strj = ndbdl_zhexiantu.getList().get(i).getNUM();
                    if (!"".equals(strj)) {
                        zxtp.Xlabel.add(strt.substring(5, 7) + "."
                                + strt.substring(8, 10));
                        zxtp.data.add(strj);
                        zxtp.data1.add(strj);
                    }

                }
                zxtp.dialog_bz.showDialog();
                zxtp.dialog_bz.showZX(zxtp.Xlabel, zxtp.Ylabel, zxtp.data, zxtp.data1);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File file = new File(SavePath.savePath + "tessdata");
                    if (!file.exists()) {
                        Intent intent1 = new Intent(getActivity(), TessDataService.class);
                        getActivity().startService(intent1);
                        Toast.makeText(getActivity(), R.string.zhengzaihoutaixiazai, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), R.string.qingkaiqiwenjiancunchuquanxian, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @OnClick({R.id.iv_lab_camera, R.id.iv_lab_rili, R.id.iv_lab_shouxie, R.id.iv_shouxie, R.id.btn_laboratory_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_lab_rili://日历
                showLoadingDialog(null);
                doHttp(RetrofitUtils.createApi(Laboratory.class).findByPid(
                        userId, phone, secret), 6);
                break;
            case R.id.iv_lab_shouxie:
            case R.id.iv_shouxie:
                startActivity(LabHandwritingAty.class, null);
                break;
            case R.id.iv_lab_camera:
            case R.id.btn_laboratory_send:
//                downLoadZip();
//                File file = new File(SavePath.savePath+"tessdata");
//                if (!file.exists()) {
//                    showToast("文字识别所需要的文件未下载完毕！");
//                    return;
//                }
//                File file = new File(SavePath.savePath+"tessdata");
//                if (TessDataService.isOpen==1&&!file.exists()){
//                    showToast(getString(R.string.wenzishibiesuoxuyaodewenjianweixiazaiwanbi));
//                    return;
//                }
//
//                if (!file.exists()){
//                    new MaterialDialog(getActivity())
//                            .setMDNoTitle(true)
//                            .setMDMessage(getString(R.string.ninxuyaoxiazaiyuyinshibiewenjian))
//                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
//                                @Override
//                                public void dialogBtnOnClick() {
//                                    startTessDataService();
//                                }
//                            })
//                            .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
//                                @Override
//                                public void dialogBtnOnClick() {
//
//                                }
//                            })
//                            .show();
//                }else{
                    choosePic();
//                }
                break;
        }
    }
    private void startTessDataService() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            File file = new File(SavePath.savePath + "tessdata");
            if (!file.exists()) {
                Intent intent1 = new Intent(getActivity(), TessDataService.class);
                getActivity().startService(intent1);
                Toast.makeText(getActivity(), R.string.zhengzaihoutaixiazaizhongwenshibieku, Toast.LENGTH_SHORT).show();
            }
        }

    }
    /**
     * 下载图文识别要用的资源文件
     */
    private void downLoadZip() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UnzipAssets.unZip(getActivity(), "tessdata.zip", SavePath.savePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.weikaiqiduxiewenjianquanxian));
                        }
                    });

                }
            }
        }).start();
    }

    /**
     * 底部弹出Popupwindow
     */

    private void choosePic() {
        final FormBotomDialogBuilder builder = new FormBotomDialogBuilder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.choose_photo_layout, null);
        builder.setFB_AddCustomView(v);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fbtn_one:
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, PhotoTheme.functionConfig, mOnHanlderResultCallback);
                        break;
                    case R.id.fbtn_two:
                        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, PhotoTheme.functionConfig, mOnHanlderResultCallback);
                        break;
                    case R.id.fbtn_three:
                        builder.dismiss();
                        break;
                }
                builder.dismiss();
            }
        };

        v.findViewById(R.id.fbtn_one).setOnClickListener(listener);
        v.findViewById(R.id.fbtn_two).setOnClickListener(listener);
        v.findViewById(R.id.fbtn_three).setOnClickListener(listener);
        builder.show();
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.clear();
                mPhotoList.addAll(resultList);
                Bundle bundle = new Bundle();
                bundle.putString("localPath", mPhotoList.get(0).getPhotoPath());
                startActivity(LaboratoryCropFirstAty.class, bundle);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onSubitemClick(int position, View v) {

        LaboratoryList.ListBean bean = laboratoryListAdapter.getData().get(position);
        boolean isVisiable = bean.isVisiable();
        if (isVisiable) {
            bean.setVisiable(false);
        } else {
            bean.setVisiable(true);
        }
        laboratoryListAdapter.setVisiable(position);
    }


}
