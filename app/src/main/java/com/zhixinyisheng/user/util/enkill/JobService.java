package com.zhixinyisheng.user.util.enkill;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.Build;

import com.orhanobut.logger.Logger;
import com.zhixinyisheng.user.ui.data.BLE.QQ.MyNotificationService;
import com.zhixinyisheng.user.ui.data.BLE.zhenghe.TestService;


/**
 * Created by penglu on 2016/8/29.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobService extends android.app.job.JobService {
    private static final String TAG="JobService";
    @Override
    public boolean onStartJob(JobParameters params) {
        Logger.d(TAG,"onStartJob");

        startService(new Intent(JobService.this,TestService.class));
        startService(new Intent(JobService.this,MyNotificationService.class));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Logger.d(TAG,"onStopJob");
        startService(new Intent(JobService.this,TestService.class));
        startService(new Intent(JobService.this,MyNotificationService.class));
        return false;
    }
}
