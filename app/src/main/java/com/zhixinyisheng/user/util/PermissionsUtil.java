package com.zhixinyisheng.user.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;

import com.and.yzy.frame.application.BaseApplication;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zhixinyisheng.user.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class PermissionsUtil {

    /**
     * 判断系统是否大于6.0
     * @return
     */
    public static  boolean is6(){

        return Build.VERSION.SDK_INT>= Build.VERSION_CODES.M;
    }


    /**
     * 检查单个6.0以上的权限是否授权
     *
     * @param permissionListener
     * @param permission
     * @return
     */

    public static void checkPermissionBy6(PermissionListener permissionListener, String permission){
        if (Dexter.isRequestOngoing()){
            return;
        }
        Dexter.checkPermission(permissionListener, permission);

    }



    /**
     * 显示申请权限的对话框
     * @param context
     * @param token
     * @param permissionMessage
     */
    public static  void showPermissDialogBy6(Context context, final PermissionToken token, String permissionMessage) {

        new AlertDialog.Builder(context).setTitle(R.string.quanxianqingqiu)
                .setMessage(permissionMessage)
                .setNegativeButton(R.string.jujue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(R.string.yunxu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                token.cancelPermissionRequest();
            }
        }).show();


    }


    /**
     * 检查单个6.0以下的权限是否授权
     * @param permission
     * @return
     */
    public static boolean checkPermission(String permission){

        return PermissionChecker.checkSelfPermission(BaseApplication.getApplicationCotext(),
                permission)
                == PackageManager.PERMISSION_GRANTED;
    }



}
