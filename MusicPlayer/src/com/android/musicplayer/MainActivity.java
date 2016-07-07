package com.android.musicplayer;

import com.example.musicplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends MyActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//»•µÙ±ÍÃ‚
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
	}
}
