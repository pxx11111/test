package com.android.musicplayer;

import com.example.musicplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
		// ȥ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recent);
		
		// �������
		FindAllView();

		// ��Ӽ�����
		AddListener();
	}

	private void FindAllView() {
		// TODO Auto-generated method stub
		ReturnBt = (ImageButton) findViewById(R.id.RecentBack);
		View view1 = findViewById(R.id.recent1);
		View view2 = findViewById(R.id.recent2);
		Intent intent=getIntent();
		SharedPreferences sp=getSharedPreferences("info", MODE_PRIVATE);
		int c=sp.getInt("color", -1);
		view1.setBackgroundColor(c);
		view2.setBackgroundColor(c);
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
