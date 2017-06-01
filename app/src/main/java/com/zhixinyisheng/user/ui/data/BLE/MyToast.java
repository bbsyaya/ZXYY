package com.zhixinyisheng.user.ui.data.BLE;

import android.content.Context;
import android.widget.Toast;

public class MyToast {
	private Context context;
    private Toast toast = null;
    public MyToast(Context context) {
         this.context = context;  
    }
    public void toastShow(String text) {
        if(toast == null)  
        {  
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }  
        else {  
            toast.setText(text);  
        }  
        toast.show();  
    } 
    public void cancel() {
        toast.cancel();  
    }

    public static void showToast(Context context, String str){
        Toast.makeText(context, str, android.widget.Toast.LENGTH_SHORT).show();
    }


}
