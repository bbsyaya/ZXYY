package com.zhixinyisheng.user.ui.sidebar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.yzy.frame.application.BaseApplication;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.UserInfo;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.ui.IM.DemoHelper;
import com.zhixinyisheng.user.util.AppJsonUtil;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 个人信息
 * Created by gjj on 2016/10/27.
 */
public class PersonInformation extends BaseAty{
    @Bind(R.id.iv_back)
    ImageView iv_back;//返回键
    @Bind(R.id.cjs_tvt)
    TextView cjs_tvt;//标题
    @Bind(R.id.img_person_head)
    ImageView img_person_head;//头像
    @Bind(R.id.tv_person_name)
    TextView tv_person_name;//昵称
    @Bind(R.id.img_person_man)
    ImageView img_person_man;//男
    @Bind(R.id.img_person_woman)
    ImageView img_person_woman;//女
    @Bind(R.id.tv_person_age)
    TextView tv_person_age;//年龄
    @Bind(R.id.tv_person_adress)
    TextView tv_person_adress;//地址
    @Bind(R.id.tv_person_signature)
    TextView tv_person_signature;//个性签名

    private UserInfo userinfo;

    @Override
    public int getLayoutId() {
        return R.layout.personinfomation;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        cjs_tvt.setText(getResources().getString(R.string.MyProfile));
        iv_back.setVisibility(View.VISIBLE);
    }



    @Override
    protected void onResume() {
        super.onResume();
        setdata();
        /**
         * 保存当钱登录人信息
         */
        DemoHelper.getInstance().getUserProfileManager().setCurrentUserNick(UserManager.getUserInfo().getName());
        DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(UserManager.getUserInfo().getHeadUrl());
        BaseApplication.username = UserManager.getUserInfo().getUsername();
        BaseApplication.headurl = UserManager.getUserInfo().getHeadUrl();
//        doHttp(RetrofitUtils.createApi(LeftUrl.class).lookperson(userId),1);//TODO 要改
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {

        Logger.json(result);

        userinfo = AppJsonUtil.getObject(result,UserInfo.class);
        userinfo.setSecret(secret);
        UserManager.setUserInfo(userinfo);
        setdata();
        /**
         * 保存当钱登录人信息
         */
        DemoHelper.getInstance().getUserProfileManager().setCurrentUserNick(UserManager.getUserInfo().getName());
        DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(UserManager.getUserInfo().getHeadUrl());
        BaseApplication.username = UserManager.getUserInfo().getUsername();
        BaseApplication.headurl = UserManager.getUserInfo().getHeadUrl();
        super.onSuccess(result, call, response, what);
    }

    private void setdata(){
        /**
         * Glide设置圆形头像
         */
        Glide.with(this).load(UserManager.getUserInfo().getHeadUrl())
                .placeholder(R.mipmap.ic_launcher2)
                .error(R.mipmap.ic_launcher2).bitmapTransform(new CropCircleTransformation(this)).into(img_person_head);
//        if(UserManager.getUserInfo().getUsername().equals("")){
//            tv_person_name.setText("知心医生用户");
//        }else {
//            tv_person_name.setText(UserManager.getUserInfo().getUsername());
//        }

        if (UserManager.getUserInfo().getName().equals("")) {
            tv_person_name.setText(UserManager.getUserInfo().getUsername());
            if (UserManager.getUserInfo().getUsername().equals("")){
                tv_person_name.setText(UserManager.getUserInfo().getCard()+"");
            }
        } else {
            tv_person_name.setText(UserManager.getUserInfo().getName());
        }

        if(UserManager.getUserInfo().getSex()==1){
            img_person_man.setVisibility(View.VISIBLE);
            img_person_woman.setVisibility(View.GONE);
        }else {
            img_person_woman.setVisibility(View.VISIBLE);
            img_person_man.setVisibility(View.GONE);
        }
        tv_person_age.setText(UserManager.getUserInfo().getAge()+getResources().getString(R.string.YearsOld));
        tv_person_adress.setText(UserManager.getUserInfo().getAddress());
        tv_person_signature.setText(UserManager.getUserInfo().getRemark());
        if (UserManager.getUserInfo().getRemark().equals("")){
            tv_person_signature.setText(getResources().getString(R.string.gexingqianmingHint));
        }
    }



    @OnClick({R.id.tv_person_name,R.id.img_person_head,R.id.cjs_rlb,R.id.person_bianjiziliao})
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()){
            case R.id.tv_person_name:
                new AlertDialog.Builder(this).setTitle(R.string.xingbie).setSingleChoiceItems(
                        new String[]{getString(R.string.nan), getString(R.string.nv)}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Logger.e("nasmndman",which+"");
//                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.img_person_head:
                Intent intent = new Intent(PersonInformation.this, HeadImageDetailAty.class);
                int[] location = new int[2];
                img_person_head.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", img_person_head.getWidth());
                intent.putExtra("height", img_person_head.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.cjs_rlb:
                finish();
                break;
            case R.id.person_bianjiziliao:
                startActivity(EditorInformation.class,null);
                break;
        }


    }
}
