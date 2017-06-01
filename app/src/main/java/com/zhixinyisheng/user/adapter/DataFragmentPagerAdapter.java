package com.zhixinyisheng.user.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.zhixinyisheng.user.ui.BaseFgt;

import java.util.ArrayList;

public class DataFragmentPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<BaseFgt> fragments;
	private FragmentManager fm;

	public DataFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

//	@Override
//	public void destroyItem(ViewGroup container, int position, Object object) {
//		Log.d("vampire", "destroyItem: ");
//		if (position!= fragments.size()&&position!=0)
//		super.destroyItem(container, position+1, object);
//		super.destroyItem(container, position-1, object);
//	}

	public DataFragmentPagerAdapter(FragmentManager fm,
									ArrayList<BaseFgt> fragments) {
		super(fm);
		this.fm = fm;
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {

		return fragments.get(position);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragments(ArrayList<BaseFgt> fragments) {
		if (this.fragments != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
		}
		this.fragments = fragments;
		notifyDataSetChanged();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Fragment fragment = (Fragment) super.instantiateItem(container, position);
		this.fm.beginTransaction().show(fragment).commit();
		return fragment;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		Fragment fragment = fragments.get(position);
		fm.beginTransaction().hide(fragment).commit();
	}


}
