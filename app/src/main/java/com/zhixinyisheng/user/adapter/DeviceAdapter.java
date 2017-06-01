package com.zhixinyisheng.user.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.and.yzy.frame.adapter.CommonAdapter;
import com.and.yzy.frame.adapter.ViewHolder;
import com.zhixinyisheng.user.R;

import java.util.List;
import java.util.Map;

/**
 * 蓝牙设备的适配器
 * Created by 焕焕 on 2016/10/25.
 */
public class DeviceAdapter extends CommonAdapter<BluetoothDevice>{
    Context context;
    Map<String, Integer> devRssiValues;
    public DeviceAdapter(Context context, List<BluetoothDevice> mList, int itemLayoutId,Map<String, Integer> devRssiValues) {
        super(context, mList, itemLayoutId);
        this.context =context;
        this.devRssiValues = devRssiValues;
    }


    @Override
    public void convert(ViewHolder holder, BluetoothDevice item, int positon) {
//        Log.e("bluejujing",item.getName()+"$$$$"+item.getAddress());
        holder.setTextViewText(R.id.deele_name,item.getName());
        holder.setTextViewText(R.id.deele_address,item.getAddress());
        int rssi = devRssiValues.get(item.getAddress()).intValue();
        if (rssi>-90) {
            holder.setTextViewText(R.id.deele_rssi,context.getResources().getString(R.string.signalStrong));
        } else if (rssi>-110) {
            holder.setTextViewText(R.id.deele_rssi,context.getResources().getString(R.string.signalWeak));
        }else if (rssi>-120) {
            holder.setTextViewText(R.id.deele_rssi,context.getResources().getString(R.string.signalPoor));
        }
    }
}
