package com.android.musicplayer;

import com.example.musicplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class AppStartActivity extends MyActivity {

	private ImageView iv1;
	private ImageView iv2;
	private ImageView iv3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// …Ë÷√»´∆¡
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_app_start);
		iv1 = (ImageView) findViewById(R.id.App_logohead);
		iv2 = (ImageView) findViewById(R.id.App_logo);
		iv3 = (ImageView) findViewById(R.id.App_logoinfo);

		Animation a1 = AnimationUtils.loadAnimation(AppStartActivity.this,
				R.anim.myalpha);
		iv1.setAnimation(a1);
		iv2.setAnimation(a1);
		iv3.setAnimation(a1);

		a1.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				Intent intent1 = new Intent(AppStartActivity.this,
						ListActivity.class);

				startActivity(intent1);
				AppStartActivity.this.finish();
			}
		});

	}

}
