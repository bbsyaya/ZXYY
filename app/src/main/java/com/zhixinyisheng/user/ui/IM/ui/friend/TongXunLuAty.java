package com.zhixinyisheng.user.ui.IM.ui.friend;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.and.yzy.frame.util.RetrofitUtils;
import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.PhoneInfoAdapter;
import com.zhixinyisheng.user.domain.PhoneInfo;
import com.zhixinyisheng.user.domain.PhoneValuesInfo;
import com.zhixinyisheng.user.http.Friend;
import com.zhixinyisheng.user.ui.BaseAty;
import com.zhixinyisheng.user.util.Content;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 通讯录找好友
 * Created by 焕焕 on 2016/12/22.
 */
public class TongXunLuAty extends BaseAty {
    List<PhoneInfo> phoneInfos = new ArrayList<>();
    PhoneInfoAdapter phoneInfoAdapter;
    String phoneNums = "";
    List<PhoneValuesInfo> phoneValueInfos = new ArrayList<>();
    List<PhoneValuesInfo> phoneValueInfos_isAdded = new ArrayList<>();
    @Bind(R.id.lv_tongxunlu)
    ListView lvTongxunlu;
    @Bind(R.id.cjs_view)
    View cjsView;
    @Bind(R.id.cjs_tvt)
    TextView cjsTvt;
    @Bind(R.id.iv_sliding)
    ImageView ivSliding;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.cjs_rlb)
    RelativeLayout cjsRlb;
    @Bind(R.id.title_xinxi)
    ImageView titleXinxi;
    @Bind(R.id.main_unread_msg_number)
    TextView mainUnreadMsgNumber;
    @Bind(R.id.cjs_rl_xhd)
    RelativeLayout cjsRlXhd;
    @Bind(R.id.title_close)
    ImageView titleClose;
    @Bind(R.id.title_btn)
    Button titleBtn;
    @Bind(R.id.cjs_rlr)
    RelativeLayout cjsRlr;
    @Bind(R.id.cjs_rl_title)
    RelativeLayout cjsRlTitle;
    @Bind(R.id.title)
    RelativeLayout title;
    /**
     * 获取库Phon表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    /**
     * 联系人名称
     **/
    private ArrayList<String> mContactsName = new ArrayList<String>();

    /**
     * 联系人头像
     **/
    private ArrayList<String> mContactsNumber = new ArrayList<String>();

    /**
     * 联系人头像
     **/
    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();

    @Override
    public int getLayoutId() {
        return R.layout.aty_tongxunlu;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        cjsTvt.setText(getResources().getString(R.string.tongxunlu));
        ivBack.setVisibility(View.VISIBLE);
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        String phoneNumber="";
        String phoneName="";
        while (cursor.moveToNext()) {

            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            try {
                phoneNumber = phoneNumber.replace(" ", "");
            } catch (Exception e) {
                e.printStackTrace();
                phoneNumber= "";
            }
            try {
                phoneNumber = phoneNumber.replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                phoneNumber= "";
            }
            if (!phoneNumber.equals("")){
                PhoneInfo phoneInfo = new PhoneInfo();
                phoneInfo.setPhoneName(phoneName);
                phoneInfo.setPhoneNumber(phoneNumber);
                Log.e("phoneNumber",phoneNumber);
                phoneInfos.add(phoneInfo);
            }

        }
        cursor.close();
        Logger.e("phoneInfos",phoneInfos.size()+"&&&");
        for (int i = 0; i < phoneInfos.size(); i++) {
            phoneNums += phoneInfos.get(i).getPhoneNumber() + ",";

        }
        try {
            phoneNums = phoneNums.substring(0, phoneNums.length() - 1);
        } catch (Exception e) {
            showToast(getString(R.string.weikaiqiduqulianxirenquanxian));
            finish();
            e.printStackTrace();
        }
        phoneValueInfos.clear();
        phoneValueInfos_isAdded.clear();
        showLoadingDialog(null);
//        Logger.e("phoneNums",phoneNums);
        doHttp(RetrofitUtils.createApi(Friend.class).bookList(
                userId, phoneNums, phone, secret), 0);

        /**得到手机通讯录联系人信息**/
//        getPhoneContacts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Content.TONG_XUN_LU == 1) {
            phoneValueInfos.clear();
            phoneValueInfos_isAdded.clear();
            showLoadingDialog(null);
            doHttp(RetrofitUtils.createApi(Friend.class).bookList(
                    userId, phoneNums, phone, secret), 0);
            Content.TONG_XUN_LU = 0;
        }

    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        Logger.e("phoneresult", result);
        try {
            JSONObject o = new JSONObject(result);
            String values = o.getString("values");
            values = values.substring(1, values.length());
            String[] valuess = values.split(",");
            for (int i = 0; i < valuess.length; i++) {
                String[] valuess_item = valuess[i].split("\\|");
                String[] valuess_item_item0 = valuess_item[0].split("=");
                String[] valuess_item_item1 = valuess_item[1].split("=");
                String[] valuess_item_item2 = valuess_item[2].split("=");
                String[] valuess_item_item3 = valuess_item[3].split("=");
                String[] valuess_item_item4 = valuess_item[4].split("=");
                PhoneValuesInfo phoneValuesInfo = new PhoneValuesInfo();
                phoneValuesInfo.setPhone(valuess_item_item0[1]);
                try {
                    phoneValuesInfo.setFriendRemark(valuess_item_item1[1]);
                } catch (Exception e) {
                    phoneValuesInfo.setFriendRemark("");
                }
                phoneValuesInfo.setIsAdded(valuess_item_item2[1]);
                phoneValuesInfo.setIsFriend(valuess_item_item3[1]);
                phoneValuesInfo.setHeadUrl(valuess_item_item4[1]);
                phoneValueInfos.add(phoneValuesInfo);
            }
            Log.e("phsize", phoneValueInfos.size() + "@@@");
            for (int i = 0; i < phoneValueInfos.size(); i++) {
                if (phoneValueInfos.get(i).getIsAdded().equals("1")) {
                    PhoneValuesInfo phoneValuesInfo_isAdded = new PhoneValuesInfo();
                    phoneValuesInfo_isAdded.setPhone(phoneValueInfos.get(i).getPhone());
                    phoneValuesInfo_isAdded.setFriendRemark(phoneValueInfos.get(i).getFriendRemark());
                    phoneValuesInfo_isAdded.setIsAdded(phoneValueInfos.get(i).getIsAdded());
                    phoneValuesInfo_isAdded.setIsFriend(phoneValueInfos.get(i).getIsFriend());
                    phoneValuesInfo_isAdded.setHeadUrl(phoneValueInfos.get(i).getHeadUrl());
                    phoneValueInfos_isAdded.add(phoneValuesInfo_isAdded);
                }
            }
            phoneInfoAdapter = new PhoneInfoAdapter(this, phoneValueInfos_isAdded, R.layout.item_phoneinfo);
            lvTongxunlu.setAdapter(phoneInfoAdapter);

        } catch (Exception e) {
            Log.e("eee222", e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 得到手机通讯录联系人信息
     **/
    private void getPhoneContacts() {
        ContentResolver resolver = getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);


        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
//                phoneNumber = phoneNumber.replace(" ", "");
//                phoneNumber = phoneNumber.replace("-", "");
//                Log.e("phoneNumber", phoneNumber.length()+"**"+phoneNumber+"&&&");

                phoneNums +=phoneNumber;
                Log.e("phonesdsd",phoneNums+"***");
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber)||phoneNumber.equals(""))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                //得到联系人ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

                //得到联系人头像ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                //得到联系人头像Bitamp
                Bitmap contactPhoto = null;

                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                }

                mContactsName.add(contactName);
                mContactsNumber.add(phoneNumber);
                mContactsPhonto.add(contactPhoto);
            }

            phoneCursor.close();
            for (int i = 0; i < mContactsNumber.size(); i++) {
                phoneNums += mContactsNumber.get(i) + ",";
                Log.e("phoneNums 121",phoneNums);
            }
            phoneNums = phoneNums.substring(0, phoneNums.length() - 1);
            phoneValueInfos.clear();
            phoneValueInfos_isAdded.clear();
//            showLoadingDialog(null);
//            Log.e("phoneNums", phoneNums);
//            doHttp(RetrofitUtils.createApi(Friend.class).bookList(
//                    userId, phoneNums, phone, secret), 0);
        }
    }

    @OnClick(R.id.cjs_rlb)
    public void onClick() {
        finish();
    }
}
