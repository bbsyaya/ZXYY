package com.zhixinyisheng.user.ui.data.BLE.common;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class StringUtils {

    public static String randomWord(String word) {
        char[] ch = word.toCharArray();

        for (int i = 0; i < ch.length * 2; i++) {
            double value = Math.random() * 10000;
            int idx = (int) (value % ch.length);
            double newvalue = Math.random() * 10000;
            int newidx = (int) (newvalue % ch.length);
            char temp = ch[idx];
            ch[idx] = ch[newidx];
            ch[newidx] = temp;
        }
        word = String.valueOf(ch);
        return word;
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    public static String toHex(byte[] b) {  //String to  byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String md5(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] b = md.digest(key.getBytes());
            String md5 = toHex(b);
            String ret = md5.substring(0, 10);
            return ret;
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String removeQuotes(String str) {
        if ((str == null) || (str.length() == 0))
            return str;
        if (str.indexOf("\"") == 0) str = str.substring(1, str.length());   //去锟斤拷锟斤拷一锟斤拷 "
        if (str.lastIndexOf("\"") == (str.length() - 1))
            str = str.substring(0, str.length() - 1);  //去锟斤拷锟斤拷锟揭伙拷锟� "

        return str;
    }


    @SuppressLint("NewApi")
    public static void copyToClipboard(Context context, String message) {
//    	if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
//    	    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//    	    clipboard.setText(message);
//    	} else {
//    	    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//    	    android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", message);
//    	            clipboard.setPrimaryClip(clip);
//    	}

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(message);
    }

    @SuppressLint("NewApi")
    public static CharSequence getFromClipboard(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return clipboard.getText();
    }

    /* Convert byte[] to hex string.锟斤拷锟斤拷锟斤拷锟角匡拷锟皆斤拷byte转锟斤拷锟斤拷int锟斤拷然锟斤拷锟斤拷锟斤拷Integer.toHexString(int)锟斤拷转锟斤拷锟斤拷16锟斤拷锟斤拷锟街凤拷
       * @param src byte[] data
       * @return hex string
       */
    public static String bytesToHexString(byte[] src) {
        return bytesToHexString(src, 0, src.length);
    }

    public static String bytesToHexString(byte[] src, int offset, int lenght) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || lenght <= 0) {
            return null;
        }
        for (int i = 0; i < lenght; i++) {
            int v = src[i + offset] & 0xFF;
            stringBuilder.append(' ');
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) hexChar.indexOf(c);
    }

    final static String hexChar = "0123456789ABCDEF";

    public static String checkHexString(String hexString) {
        StringBuilder sb = new StringBuilder();
        hexString = hexString.toUpperCase();
        for (int i = 0; i < hexString.length(); i++) {
            char ch = hexString.charAt(i);
            if (ch == ' ')
                continue;

            if ((hexChar.indexOf(ch) < 0)) {
                Log.i("String", "unknow char " + ch);
                return null;
            }


            sb.append(ch);
        }

        String tmp = sb.toString();

        Log.i("String", "check string=" + tmp + ",lenght=" + tmp.length());

        if (tmp.length() % 2 == 1)
            return null;

        return tmp;
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static void showPacket(String tag, byte[] buf, int length) {

        int line = length >> 4;

        int tail = length & 15; //剩锟铰讹拷锟斤拷锟斤拷

        Log.d(tag, Integer.toString(length) + "==V==============================================");
        for (int i = 0; i < line; i++) {
            Log.d(tag, bytesToHexString(buf, i * 16, 16));
        }

        if (tail > 0)
            Log.d(tag, bytesToHexString(buf, line * 16, tail));

        Log.d(tag, Integer.toString(length) + "==^==============================================");
    }
}
