package com.zhixinyisheng.user.ui.data.BLE.control;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BtSerializeation {
    private static byte charToByte(char paramChar) {
        return (byte) "0123456789ABCDEF".indexOf(paramChar);
    }

    public static byte[] laidianTX() {
        byte[] arrayOfByte = new byte[15];
//    paramString.toCharArray();
//    paramString.length();
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 7;
        arrayOfByte[8] = 1;
        arrayOfByte[10] = 14;
        arrayOfByte[12] = 2;
        arrayOfByte[13] = 1;
        arrayOfByte[14] = 3;
        return arrayOfByte;
    }

    //开启震动(如果是设置不要提醒来电,关闭电话广播(用动态广播))
    public static byte[] notifyIncommingCall() {
        byte[] arrayOfByte = new byte[13];
//    paramString.toCharArray();
//    paramString.length();
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 2;
        arrayOfByte[10] = 1;
        return arrayOfByte;
    }

    //关闭震动
    public static byte[] disNotifyIncommingCall() {
        byte[] arrayOfByte = new byte[13];
//    paramString.toCharArray();
//    paramString.length();
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 2;
        arrayOfByte[10] = 0;
        arrayOfByte[11] = 0;
        arrayOfByte[12] = 0;
        return arrayOfByte;
    }

    //设置闹钟
    public static byte[] setAlarm(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        Date date = new Date(System.currentTimeMillis());
        char[] localObject = new SimpleDateFormat("yyyyMMdd").format(date).toCharArray();
        int i = charToByte(localObject[2]) * 10 + charToByte(localObject[3]);
        int j = charToByte(localObject[4]) * 10 + charToByte(localObject[5]);
        int k = charToByte(localObject[6]) * 10 + charToByte(localObject[7]);
        byte[] arrayOfByte = new byte[23];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 15;
        arrayOfByte[8] = 1;
        arrayOfByte[10] = 2;
        arrayOfByte[12] = 10;
        arrayOfByte[13] = ((byte) (i << 2 | j >> 2));
        arrayOfByte[14] = ((byte) ((j & 0x3) << 6 | k << 1 | paramInt2 >> 4));
        arrayOfByte[15] = ((byte) ((paramInt2 & 0xF) << 4 | paramInt3 >> 2));
        arrayOfByte[16] = ((byte) ((paramInt3 & 0x3) << 6 | paramInt1 << 3));
        arrayOfByte[17] = ((byte) paramInt4);
        return arrayOfByte;
    }

    //设置健康目标
    public static byte[] setTargetStep(int paramInt) {
        byte[] arrayOfByte = new byte[20];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 8;
        arrayOfByte[8] = 1;
        arrayOfByte[10] = 3;
        arrayOfByte[12] = 4;
        arrayOfByte[13] = ((byte) (paramInt >> 24));
        arrayOfByte[14] = ((byte) ((0xFF0000 & paramInt) >> 16));
        arrayOfByte[15] = ((byte) ((0xFF00 & paramInt) >> 8));
        arrayOfByte[16] = ((byte) (paramInt & 0xFF));
        return arrayOfByte;
    }

    //设置个人信息
    public static byte[] setUserProfile(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        byte[] arrayOfByte = new byte[16];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 8;
        arrayOfByte[8] = 1;
        arrayOfByte[10] = 4;
        arrayOfByte[12] = 4;
        arrayOfByte[13] = ((byte) (paramInt1 << 7 | paramInt2));
        arrayOfByte[14] = ((byte) paramInt3);
        arrayOfByte[15] = ((byte) paramInt4);
        return arrayOfByte;
    }

    //和手机同步时间,主要就是返回数据
    public static byte[] syncTime() {
        byte[] arrayOfByte = new byte[21];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 9;
        char[] arrayOfChar = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())).toCharArray();
        int i = charToByte(arrayOfChar[2]);
        int j = charToByte(arrayOfChar[3]);
        int k = charToByte(arrayOfChar[4]) * 10 + charToByte(arrayOfChar[5]);
        int m = charToByte(arrayOfChar[6]);
        int n = charToByte(arrayOfChar[7]);
        int i1 = charToByte(arrayOfChar[8]) * 10 + charToByte(arrayOfChar[9]);
        int i2 = charToByte(arrayOfChar[10]) * 10 + charToByte(arrayOfChar[11]);
        int i3 = charToByte(arrayOfChar[12]);
        int i4 = charToByte(arrayOfChar[13]);
        arrayOfByte[8] = 1;
        arrayOfByte[10] = 1;
        arrayOfByte[12] = 4;
        arrayOfByte[13] = ((byte) (i * 10 + j << 2 | k >> 2));
        arrayOfByte[14] = ((byte) ((k & 0x3) << 6 | m * 10 + n << 1 | i1 >> 4));
        arrayOfByte[15] = ((byte) ((i1 & 0xF) << 4 | i2 >> 2));
        arrayOfByte[16] = ((byte) ((i2 & 0x3) << 6 | i3 * 10 + i4));
        return arrayOfByte;
    }

    //设置重启
    public static byte[] setReboot() {
        byte[] arrayOfByte = new byte[16];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 1;
        arrayOfByte[10] = 6;
        arrayOfByte[12] = 0;
        return arrayOfByte;
    }

    //清空数据
    public static byte[] clearData() {
        byte[] arrayOfByte = new byte[16];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 1;
        arrayOfByte[10] = 5;
        arrayOfByte[12] = 0;
        return arrayOfByte;
    }


    //来电命令
    public static byte[] shLDml() {
        byte[] arrayOfByte = new byte[13];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 2;
        arrayOfByte[10] = 1;
        return arrayOfByte;
    }
    //QQ命令
    public static byte[] shQQml() {
        byte[] arrayOfByte = new byte[13];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 2;
        arrayOfByte[10] = 2;
        return arrayOfByte;
    }
    //微信命令
    public static byte[] shWXml() {
        byte[] arrayOfByte = new byte[13];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 2;
        arrayOfByte[10] = 3;
        return arrayOfByte;
    }
    //短信命令
    public static byte[] shDXml() {
        byte[] arrayOfByte = new byte[13];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 2;
        arrayOfByte[10] = 4;
        return arrayOfByte;
    }
    //呼救进行命令
    public static byte[] shHJJXml() {
        byte[] arrayOfByte = new byte[13];
        arrayOfByte[0] = -85;
        arrayOfByte[3] = 5;
        arrayOfByte[8] = 2;
        arrayOfByte[10] = 5;
        return arrayOfByte;
    }



}