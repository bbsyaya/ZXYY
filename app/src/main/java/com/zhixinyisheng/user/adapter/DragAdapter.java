package com.zhixinyisheng.user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.domain.ChannelItem;
import com.zhixinyisheng.user.ui.MyApplication;
import com.zhixinyisheng.user.util.Content;

import java.util.List;


public class DragAdapter extends BaseAdapter {

	/** TAG*/
	private final static String TAG = "DragAdapter";
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	private Context context;
	/** 控制的postion */
	private int holdPosition;
	/** 是否改变 */
	private boolean isChanged = false;
	/** 列表数据是否改变 */
	private boolean isListChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的频道列表） */
	public List<ChannelItem> channelList;
	/** TextView 频道内容 */
	private TextView item_text;
	/** 要删除的position */
	public int remove_position = -1;
	/**右上角的关闭按钮*/
	private ImageView item_close;
	public DragAdapter(Context context, List<ChannelItem> channelList) {
		this.context = context;
		this.channelList = channelList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ChannelItem getItem(int position) {
		// TODO Auto-generated method stub
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.channel_item, null);
		item_text = (TextView) view.findViewById(R.id.text_item);
		item_close = (ImageView) view.findViewById(R.id.item_close);
		if (Content.isFinish==0){
			item_close.setVisibility(View.GONE);
		}else{
			item_close.setVisibility(View.VISIBLE);
		}
		ChannelItem channel = getItem(position);
		if (channel.getId()==1) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_heartRate));
		} else if (channel.getId()==2) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_bloodPressure));
		} else if (channel.getId()==3) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_lungCapacity));
		} else if (channel.getId()==9) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_steps));
		} else if (channel.getId()==5) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_sleep));
		} else if (channel.getId()==6) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_bmi));
		} else if (channel.getId()==8) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_temperature));
		} else if (channel.getId()==10) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_testSheet));
		} else if (channel.getId()==7) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_bloodSuger));
		} else if (channel.getId()==4) {
			item_text.setText(MyApplication.getApp().getResources().getString(R.string.home_tongue));
		}

//		item_text.setText(channel.getName());
//		if ((position == 0) || (position == 1)|| (position == 2)|| (position == 3)){
////			item_text.setTextColor(context.getResources().getColor(R.color.black));
//			item_text.setEnabled(false);
//		}
		if (isChanged && (position == holdPosition) && !isItemShow) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (position == -1 + channelList.size())) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
		}
		if(remove_position == position){
			item_text.setText("");
		}
		return view;
	}

	/** 添加频道列表 */
	public void addItem(ChannelItem channel) {
		channelList.add(channel);
		isListChanged = true;
		notifyDataSetChanged();
	}

	/** 拖动变更频道排序 */
	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		ChannelItem dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		isListChanged = true;
		notifyDataSetChanged();
	}

	/** 获取频道列表 */
	public List<ChannelItem> getChannnelLst() {
		return channelList;
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	/** 删除频道列表 */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		isListChanged = true;
		notifyDataSetChanged();
	}

	/** 设置频道列表 */
	public void setListDate(List<ChannelItem> list) {
		channelList = list;
	}

	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}

	/** 排序是否发生改变 */
	public boolean isListChanged() {
		return isListChanged;
	}

	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}
}