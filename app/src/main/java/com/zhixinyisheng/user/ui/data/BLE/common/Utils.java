/**
 *
 */
package com.zhixinyisheng.user.ui.data.BLE.common;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhixinyisheng.user.ui.data.BLE.MyLog;
import com.zhixinyisheng.user.ui.data.BLE.MyToast;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;

/**
 * @author hxy
 */
public class Utils {

    public static void showDialog(Context context, String msg, int icon_res) {
        Toast mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);



        LinearLayout toastView = (LinearLayout) mToast.getView();

        //锟斤拷锟斤拷LinearLayout锟侥诧拷锟斤拷取锟斤拷
        toastView.setOrientation(LinearLayout.HORIZONTAL);

        //锟斤拷锟斤拷ImageView
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(icon_res);

        //锟斤拷toastView锟斤拷锟�View锟斤拷锟斤拷
        toastView.addView(imageCodeProject, 0);

        mToast.setGravity(Gravity.CENTER, 0, 0);

        //锟斤拷示Toast
        mToast.show();

    }

    public static void showDialog(Context context, int str_res, int icon_res) {
        String tmp = context.getString(str_res);
        showMessage(context, tmp, icon_res);
    }

    public static void showMessage(Context context, int str_res) {
        String tmp = context.getString(str_res);
        showMessage(context, tmp);
    }

    public static void showMessage(Context context, String msg) {
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(Context context, String msg, int timeout) {
        Toast.makeText(context, msg, timeout).show();
    }

    public static void showMessage(Context context, int str_res, int timeout) {
        String tmp = context.getString(str_res);
        showMessage(context, tmp, timeout);
    }

    public static boolean openUrl(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(url));
            intent.setAction(Intent.ACTION_VIEW);
            context.startActivity(intent); //锟斤拷锟斤拷锟斤拷锟斤拷锟�
            return true;
        } catch (android.content.ActivityNotFoundException e) {//没锟叫帮拷装锟斤拷应锟斤拷锟斤拷锟斤拷锟�
            e.printStackTrace();
            return false;
        }
    }

    public static void startActivityForResult(Activity context, int activity_id, Class activityClass, String arg1) {

        Intent intent = new Intent(context, activityClass);


        if (arg1 != null) {


            intent.putExtra("arg1", arg1);
        }

        context.startActivityForResult(intent, activity_id);
    }


    public static void startActivity(Context context, Class activityClass, String arg1) {

        Intent intent = new Intent(context, activityClass);


        if (arg1 != null) {


            intent.putExtra("arg1", arg1);
        }

        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class activityClass, int arg1) {

        Intent intent = new Intent(context, activityClass);

        intent.putExtra("arg1", arg1);

        context.startActivity(intent);
    }


    public static boolean getBooleanConfig(Context context, String name, boolean defaultValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        return pref.getBoolean(name, defaultValue);
    }

    public static void setBooleanConfig(Context context, String name, boolean value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        pref.edit().putBoolean(name, value).commit();
    }

    public static int getIntConfig(Context context, String name, int defaultValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        return pref.getInt(name, defaultValue);
    }

    public static void setIntConfig(Context context, String name, int value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        pref.edit().putInt(name, value).commit();
    }

    public static String getStringConfig(Context context, String name, String defaultValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        return pref.getString(name, defaultValue);
    }

    public static boolean saveStringConfig(Context context, String name, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        String oldValue = pref.getString(name, null);
        if ((oldValue != null) && (oldValue.equals(value))) {
            return false;

        }

        pref.edit().putString(name, value).commit();

        return true;
    }

    public static void clearConfig(Context context, String name) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        pref.edit().remove(name).commit();
    }

    public static void setStringConfig(Context context, String name, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        pref.edit().putString(name, value).commit();
    }

    //Android 锟斤拷源锟斤拷疲锟�name锟斤拷锟斤拷id锟斤拷锟斤拷转锟斤拷锟斤拷锟斤拷态锟斤拷取,
    //通锟斤拷锟斤拷源锟斤拷疲锟斤拷锟斤拷锟�ic_launcher锟斤拷锟斤拷取锟斤拷应锟斤拷id
    public static int getDrawableResId(Context context, String name) {
        Resources res = context.getResources();

        //return res.getIdentifier(name,null,null);//锟斤拷锟较碉拷址 锟斤拷锟斤拷 锟斤拷:type/name           (org.anjoy.act:drawable/ic)

        return res.getIdentifier(name, "drawable", context.getPackageName());//锟斤拷锟斤拷锟斤拷锟� ic

    }

    //
    public static String getResourceName(Context context, int id) {
        Resources res = context.getResources();
        return res.getResourceEntryName(id);//锟矫碉拷锟斤拷锟斤拷 name
        //return res.getResourceName(id);//锟矫碉拷锟斤拷锟斤拷 锟斤拷/type/name
    }

    public static void stopThread(Thread thread) {
        try {
            if (thread.isAlive())
                thread.interrupt();
            Thread.sleep(300);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            thread = null;
        }
    }

    public static int getTextColor(Context context, int color_res) {
        return context.getResources().getColor(color_res);
    }

    //发送手环命令
    public static void sendSHML(Context context, String str) {
        try {
            String message = StringUtils.checkHexString(str);
            if (message == null) {
                MyToast.showToast(context,"不能发送命令给手环!");
                return;
            }
            byte[] value = StringUtils.hexStringToBytes(message);
            TestService.mService.writeRXCharacteristic(value);
        } catch (Exception e) {
            MyLog.showLog("手环异常",e.toString());
            MyToast.showToast(context,"不能发送命令给手环!");
        }
    }

    //发送手环命令
    public static void sendSHML(String str) {
        try {
            String message = StringUtils.checkHexString(str);
            if (message == null) {
                MyLog.showLog("手环异常","不能发送命令");
                return;
            }
            byte[] value = StringUtils.hexStringToBytes(message);
            TestService.mService.writeRXCharacteristic(value);
        } catch (Exception e) {
            MyLog.showLog("手环异常",e.toString());
        }
    }

    //发送手环命令
    public static void sendSHMLForSoS(String str) {
        try {
            String message = StringUtils.checkHexString(str);
            if (message == null) {
                MyLog.showLog("手环异常","不能发送命令");
                return;
            }
            byte[] value = StringUtils.hexStringToBytes(message);
            TestService.mService.writeRXCharacteristic(value);
        } catch (Exception e) {
            MyLog.showLog("手环异常",e.toString());
        }
    }



}
