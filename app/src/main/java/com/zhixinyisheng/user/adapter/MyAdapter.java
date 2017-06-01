package com.zhixinyisheng.user.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 
 * ViewPager适配器
 * */
public class MyAdapter extends PagerAdapter {

	ArrayList<View> views = new ArrayList<View>();
	public MyAdapter(ArrayList<View> views){
		this.views = views;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (View) arg1;
	}

	/** 每次滑动时需要产生的组件 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(views.get(position));
		return views.get(position);
	}

	/** 每次滑动切换时需要销毁的 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(views.get(position));
		
	}


}
