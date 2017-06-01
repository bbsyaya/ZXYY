package com.zhixinyisheng.user.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import com.zhixinyisheng.user.R;

import java.util.ArrayList;
import java.util.List;

public class DialogZXT {
	Context context;
	TextView tvti, tvna;
	MyViewTW mv;
	String strti,strna;
	List<String> Ylabel = new ArrayList<String>();
	public DialogZXT(Context context, String strti, String strna, List<String> Ylabel) {
		super();
		this.context = context;
		this.strti = strti;
		this.strna = strna;
		this.Ylabel = Ylabel;
	}

	public void showDialog() {
		Dialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.activity_hydzxt);
		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
		init(window);
	}

	public void showZX(List<String> Xlabel, List<String> Ylabel, List<String> data,
					   List<String> data1) {
		
		if (Xlabel.toString().equals("[]")) {
			Xlabel.add("");
		}
		mv.drawZhexian(Xlabel, Ylabel, data, data1);
	}

	private void init(Window w) {
		tvti = (TextView) w.findViewById(R.id.hydzx_tvti);
		tvna = (TextView) w.findViewById(R.id.hydzx_tvna);
		tvti.setText(strti);
		tvna.setText(strna);
		mv = (MyViewTW) w.findViewById(R.id.hydzx_mv);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
