package com.android.musicplayer;

import java.util.ArrayList;

import com.android.skin.ChangeSkin;
import com.android.skin.OnStartButton;
import com.example.musicplayer.R;
import com.yskang.colorpicker.ColorPicker;
import com.yskang.colorpicker.OnColorSelectedListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends MyActivity {
	private DrawerLayout ListDrawer;
	private LinearLayout Recent;
	private LinearLayout Local;
	private ImageButton ListBt;
	private View list_above;
	private View skin;
	private int colorsize=0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);
		
		// 查找组件
		FindAllView();

		// 添加监听器
		AddListener();
	}

	private void FindAllView() {
		// TODO Auto-generated method stub
		Recent = (LinearLayout) findViewById(R.id.List_Recentgroup);
		Local = (LinearLayout) findViewById(R.id.List_Localgroup);
		ListBt = (ImageButton) findViewById(R.id.List_Back);
		ListDrawer = (DrawerLayout) findViewById(R.id.ListDrawer);
		skin=findViewById(R.id.Alert_layout1);
		list_above=findViewById(R.id.List_above);
		
	}

	private void AddListener() {
		// TODO Auto-generated method stub
		Recent.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListActivity.this, LocateActivity.class);
				intent.putExtra("color", colorsize);
				startActivity(intent);
			}
		});
		
		Local.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListActivity.this, RecentActivity.class);
				startActivity(intent);
			}
		});
		
		ListBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListDrawer.openDrawer(GravityCompat.START);
			}
		});
		skin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ColorPicker colorPicker_1;
				 int color_1 = Color.rgb(55, 128, 128);
					int color_2 = Color.argb(128, 128, 128, 255);
					ArrayList<Integer> presetColors=new ArrayList<Integer>();
			ChangeSkin change=new ChangeSkin();
			presetColors=change.getarraylist(presetColors);
			OnColorSelectedListener ColorSelectedListener = new OnColorSelectedListener() {
				

				@Override
				public void onSelected(int selectedColor) {
					list_above.setBackgroundColor(selectedColor);
					colorsize = selectedColor;
					
				}
			};
			colorPicker_1 = new ColorPicker(ListActivity.this,color_1, ColorSelectedListener, presetColors);
			skin.setOnClickListener(new OnStartButton(colorPicker_1
					.getDialog()));
				Toast.makeText(ListActivity.this,"success", 1).show();
			}
				
	}
			);
	}
		
}
