package com.android.musicplayer;

import com.android.pictureblur.BlurImageview;
import com.example.musicplayer.R;

import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PlayActivity extends MyActivity {
	private ImageView image;
	private RelativeLayout PlayLayout;
	private ImageButton ReturnBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_play);

		// 查找组件
		FindAllView();

		// 添加监听器
		AddListener();
	}

	private void FindAllView() {
		// TODO Auto-generated method stub
		image = (ImageView)findViewById(R.id.PlayImage);
		PlayLayout = (RelativeLayout) findViewById(R.id.PlayLayout);
		BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.example);
		PlayLayout.setBackground(BlurImageview.BlurImages(bd.getBitmap(), PlayActivity.this));
		ReturnBt = (ImageButton) findViewById(R.id.PlayReturn);
	}

	private void AddListener() {
		// TODO Auto-generated method stub
		Animation anim = AnimationUtils.loadAnimation(PlayActivity.this,
				R.anim.rotateanim);
		image.setAnimation(anim);
		
		ReturnBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PlayActivity.this.finish();
			}
		});
	}


}
