package com.zhixinyisheng.user.ui.data.shezhen;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarena on 2016/11/4.
 */

public class GetPicColor {
    private Bitmap bitmap;
    private  byte[] buffer;
    private int A,R,G,B;
    private String aC ,rC , gC,bC;
    private List<Integer> aList,rList,gList,bList;
    private int picColor;
    private int height;
    private int width;
    private int sumA ,sumR,sumG,sumB;

    public GetPicColor(Bitmap bitmap) {
        this.bitmap = bitmap;

    }

    public GetPicColor(byte[] buffer) {
        this.buffer = buffer;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String[] getColor(){

        int height = bitmap.getHeight();
        int weight = bitmap.getWidth();

        aList = new ArrayList<>();
        rList = new ArrayList<>();
        gList = new ArrayList<>();
        bList = new ArrayList<>();


        height = (int) (bitmap.getHeight()*0.3);
        width = (int) (bitmap.getWidth()*0.3);
        Log.e("GetPicColor height", height+"###"+width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                picColor = bitmap.getPixel(j,i);
                A = Color.alpha(picColor);
                R = Color.red(picColor);
                G = Color.green(picColor);
                B = Color.blue(picColor);
                Log.e("GetPicColor", "A+R+G+B:" + (A +","+ R +","+ G+"," + B));
                aList.add(A);
                rList.add(R);
                gList.add(G);
                bList.add(B);

            }
        }
        for (Integer integer : aList) {
            sumA += integer;
        }
        for (Integer integer : rList) {
            sumR += integer;
        }
        for (Integer integer : gList) {
            sumG += integer;
        }
        for (Integer integer : bList) {
            sumB += integer;
        }
        A = sumA /aList.size();
        R = sumR / rList.size();
        G = sumG / gList.size();
        B = sumB / bList.size();
        aC = Integer.toHexString(A);
        rC = Integer.toHexString(R);
        gC = Integer.toHexString(G);
        bC = Integer.toHexString(B);
        Logger.e("GetPicColor",aC + rC + gC + bC );
        String[] strings = new String[]{Integer.parseInt(rC, 16)+"",Integer.parseInt(gC, 16)+"",Integer.parseInt(bC, 16)+""};
        return strings;



    }


}
