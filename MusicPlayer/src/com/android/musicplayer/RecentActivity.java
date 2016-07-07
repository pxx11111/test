package com.android.musicplayer;

import com.example.musicplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class RecentActivity extends MyActivity {
	private ImageButton ReturnBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recent);
		
		// 查找组件
		FindAllView();

		// 添加监听器
		AddListener();
	}

	private void FindAllView() {
		// TODO Auto-generated method stub
		ReturnBt = (ImageButton) findViewById(R.id.RecentBack);
	}

	private void AddListener() {
		// TODO Auto-generated method stub
		ReturnBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RecentActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recent, menu);
		return true;
	}

}
