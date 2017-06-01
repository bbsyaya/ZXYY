package com.zhixinyisheng.user.config;

import android.app.Activity;

import com.zhixinyisheng.user.util.GlideImageLoader;
import com.zhixinyisheng.user.util.GlidePauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 选择本地图片的公共类
 * Created by gjj on 2016/11/5.
 */
public class PhotoThemeHead {
    List<PhotoInfo> list = new ArrayList<>();
     public static FunctionConfig functionConfig;
     public PhotoThemeHead(List<PhotoInfo> list) {
          this.list = list;
     }

     public static void settingtheme(Activity activity,List<PhotoInfo> list){

          //建议在application中配置
          //设置主题
          ThemeConfig theme = ThemeConfig.CYAN;
//        ThemeConfig theme = new ThemeConfig.Builder()
//                .build();
          //配置功能
          functionConfig = new FunctionConfig.Builder()
                  .setEnableCamera(true)
                  .setEnableEdit(true)
                  .setEnableCrop(true)
                  .setForceCrop(true)
                  .setEnableRotate(false)
                  .setCropSquare(false)
                  .setEnablePreview(true)
                  .setMutiSelectMaxSize(8)
                  .setSelected(list)
                  .build();
          CoreConfig coreConfig = new CoreConfig.Builder(activity, new GlideImageLoader(), theme)
                  .setFunctionConfig(functionConfig)
                  .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                  .build();
          GalleryFinal.init(coreConfig);
     }
}
