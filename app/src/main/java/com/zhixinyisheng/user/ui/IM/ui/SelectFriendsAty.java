package com.zhixinyisheng.user.ui.IM.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.application.Constant;
import com.and.yzy.frame.util.RetrofitUtils;
import com.and.yzy.frame.view.dialog.MaterialDialog;
import com.hyphenate.easeui.domain.EaseUser;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.http.IMUrl;
import com.zhixinyisheng.user.interfaces.OnFriendsLoadedListener;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.ui.sos.HelpActivity;
import com.zhixinyisheng.user.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 创建人: Fu
 * <p/>
 * 创建时间: 2016/11/10  8:54
 * <p/>
 * 类说明: 选择好友
 */
public class SelectFriendsAty extends BaseAty {
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.aty_selfri_rl_fris)
    RelativeLayout atySelfriRlFris;
    @Bind(R.id.aty_selfri_rl_qd)
    RelativeLayout atySelfriRlQd;
    @Bind(R.id.aty_selfri_rl_qx)
    RelativeLayout atySelfriRlQx;
    @Bind(R.id.aty_selfri_cb_qx)
    CheckBox aty_selfri_cb_qx;
    //    @Bind(R.id.sidebar)
//    MyEaseSidebar sidebar;
//    @Bind(R.id.aty_myf_floating_header)
//    TextView atyMyfFloatingHeader;
    private MyContactListFragment seleteFriendsFgt;

    boolean is_qx = false;//全选变量

    //准备提交的集合
    List<EaseUser> list = new ArrayList<>();

    int noNum = 0;

    @Override
    public int getLayoutId() {
        return R.layout.aty_seletefriend;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        if (Constant.GZCKSOS == 0) {
            cjsTvt.setText(R.string.shezhiwodeguanzhu);
            //以下是判断全选按钮的代码
            showLoadingDialog(null);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                for (EaseUser eu : MyContactListFragment.list) {
                                    if (eu.getNo().equals("1")) {
                                        noNum++;
                                    }
                                }
                                Logger.e("noNum", noNum + "%%%" + MyContactListFragment.list.size());
                                if (noNum == MyContactListFragment.list.size()) {
                                    is_qx = true;
                                    aty_selfri_cb_qx.setChecked(true);
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else if (Constant.GZCKSOS == 1) {
            cjsTvt.setText(R.string.shezhichakanjiankangshuju);
            //以下是判断全选按钮的代码
            showLoadingDialog(null);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                for (EaseUser eu : MyContactListFragment.list) {
                                    if (eu.getDatas().equals("1")) {
                                        noNum++;
                                    }
                                }
//                                Logger.e("noNum", noNum + "%%%" + MyContactListFragment.list.size());
                                if (noNum == MyContactListFragment.list.size()) {
                                    is_qx = true;
                                    aty_selfri_cb_qx.setChecked(true);
                                }

                            }
                        });


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (Constant.GZCKSOS == 2) {
            cjsTvt.setText(R.string.shezhisosduixiang);
            //以下是判断全选按钮的代码
            showLoadingDialog(null);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                for (EaseUser eu : MyContactListFragment.list) {
                                    if (eu.getSos().equals("1")) {
                                        noNum++;
                                    }
                                }
//                                Logger.e("noNum", noNum + "%%%" + MyContactListFragment.list.size());
                                if (noNum == MyContactListFragment.list.size()) {
                                    is_qx = true;
                                    aty_selfri_cb_qx.setChecked(true);
                                }

                            }
                        });


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        ivBack.setVisibility(View.VISIBLE);
        //好友列表frg
        seleteFriendsFgt = new MyContactListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.aty_selfri_rl_fris, seleteFriendsFgt).show(seleteFriendsFgt)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.cjs_rlb, R.id.aty_selfri_rl_qd, R.id.aty_selfri_rl_qx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.aty_selfri_rl_qd:

                list.clear();

                if (Constant.GZCKSOS == 0) {
                    for (EaseUser eu : MyContactListFragment.list) {
                        if (eu.getNo().equals("1")) {
                            list.add(eu);
                        }
                    }
                } else if (Constant.GZCKSOS == 1) {
                    for (EaseUser eu : MyContactListFragment.list) {
                        if (eu.getDatas().equals("1")) {
                            list.add(eu);
                        }
                    }
                } else if (Constant.GZCKSOS == 2) {
                    for (EaseUser eu : MyContactListFragment.list) {
                        if (eu.getSos().equals("1")) {
                            list.add(eu);
                        }
                    }

                }
                String sos = "";
                if (list.size() > 0) {
                    for (EaseUser eu : list) {
                        sos += eu.getUsername() + ",";
                    }
                    sos = sos.substring(0, sos.length() - 1);
                }


                showLoadingDialog(null);

                //设置SOS发送对象
                if (Constant.GZCKSOS == 0) {
                    doHttp(RetrofitUtils.createApi(IMUrl.class).addAllToCareFrineds(UserManager.getUserInfo().getPhone(),
                            UserManager.getUserInfo().getSecret(),
                            UserManager.getUserInfo().getUserId(),
                            sos), 0);
                } else if (Constant.GZCKSOS == 1) {
                    doHttp(RetrofitUtils.createApi(IMUrl.class).seeSet(UserManager.getUserInfo().getPhone(),
                            UserManager.getUserInfo().getSecret(),
                            UserManager.getUserInfo().getUserId(),
                            sos), 1);
                } else if (Constant.GZCKSOS == 2) {
                    doHttp(RetrofitUtils.createApi(IMUrl.class).sosSet(UserManager.getUserInfo().getPhone(),
                            UserManager.getUserInfo().getSecret(),
                            UserManager.getUserInfo().getUserId(),
                            sos), 2);
                }

                break;

            case R.id.aty_selfri_rl_qx:
                if (is_qx) {
                    aty_selfri_cb_qx.setChecked(false);
                    is_qx = false;
                    if (Constant.GZCKSOS == 0) {
                        for (EaseUser eu : MyContactListFragment.list) {
                            eu.setNo("0");
                        }
                    } else if (Constant.GZCKSOS == 1) {
                        for (EaseUser eu : MyContactListFragment.list) {
                            eu.setDatas("0");
                        }
                    } else if (Constant.GZCKSOS == 2) {
                        for (EaseUser eu : MyContactListFragment.list) {
                            eu.setSos("0");
                        }
                    }


                    seleteFriendsFgt.refresh();

                } else {//全选
                    aty_selfri_cb_qx.setChecked(true);
                    is_qx = true;
                    if (Constant.GZCKSOS == 0) {
                        for (EaseUser eu : MyContactListFragment.list) {
                            eu.setNo("1");
                        }
                    } else if (Constant.GZCKSOS == 1) {
                        for (EaseUser eu : MyContactListFragment.list) {
                            eu.setDatas("1");
                        }
                    } else if (Constant.GZCKSOS == 2) {
                        for (EaseUser eu : MyContactListFragment.list) {
                            eu.setSos("1");
                        }
                    }
                    seleteFriendsFgt.refresh();

                }
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, final int what) {
        super.onSuccess(result, call, response, what);


        //数据库中全部更新
//        DemoDBManager.getInstance().saveContactList(MyContactListFragment.list);

        //初始化环信IM SDK
        DemoHelper.getInstance().asyncFetchContactsFromServer(null);
        showLoadingDialog(null);
        DemoHelper.getInstance().setOnFriendsLoadedListener(new OnFriendsLoadedListener() {
            @Override
            public void friendsLoaded() {
                dismissLoadingDialog();
                showToast(getString(R.string.shezhichenggong));
                setSuccess(what);
            }

            @Override
            public void LoadedError() {
                dismissLoadingDialog();
                showToast(getString(R.string.qingjianchawangluolianjie));
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissLoadingDialog();
                            showToast(getString(R.string.shezhichenggong));
                            setSuccess(what);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    /**
     * 设置成功之后的业务逻辑
     * */
    private void setSuccess(int what) {
        if (Constant.isHelpOpen == 1) {
            Constant.isHelpOpen=0;
            if (what == 2) {
                boolean flag = PermissionChecker.checkSelfPermission(SelectFriendsAty.this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
                if (!flag) {
                    new MaterialDialog(SelectFriendsAty.this)
                            .setMDTitle(getString(R.string.zhongyaotishi))
                            .setMDMessage(getString(R.string.xuyaokaiqidingwei))
                            .setMDConfirmBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {
                                    if (PermissionsUtil.is6()) {
                                        PermissionsUtil.checkPermissionBy6(new PermissionListener() {
                                            @Override
                                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                                startActivity(HelpActivity.class, null);
                                            }

                                            @Override
                                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                            }

                                            @Override
                                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                                token.continuePermissionRequest();
                                            }
                                        }, Manifest.permission.ACCESS_FINE_LOCATION);
                                    }

                                }
                            })
                            .setMDCancelBtnClick(new MaterialDialog.DialogBtnCallBack() {
                                @Override
                                public void dialogBtnOnClick() {

                                }
                            })
                            .show();
                } else {
                    startActivity(HelpActivity.class, null);
                }
            }
        }

        finish();
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onFailure(result, call, response, what);

        showToast(getString(R.string.shezhishibai));


    }
}
