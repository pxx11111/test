package com.android.musicplayer;

import java.util.ArrayList;
import java.util.List;

import com.android.musicdao.MusicDao;
import com.android.musicplayer.LocateActivity.MyBroadCastActivity;
import com.android.musicservice.MusicService;
import com.android.musicutils.MusicUtils;
import com.android.skin.ChangeSkin;
import com.android.skin.OnStartButton;
import com.example.musicplayer.R;
import com.yskang.colorpicker.ColorPicker;
import com.yskang.colorpicker.OnColorSelectedListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
	private View Bottom;
	private ImageButton upButton;
	private ImageButton playButton;
	private ImageButton downButton;
	private TextView Name;
	private TextView Singer;
	private MusicDao Music;
	private int musicIndext = 0;// 记录当前歌曲的位置
	private int playInfo = 0x11;// 暂停键的状态，0x11第一次进入，0x12暂停，0x13播放
	boolean Flush = true;
	private MyBroadCastActivity activity;
	private List<MusicDao> MusicList;
	private int color = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list);

		// 查找组件
		FindAllView();

		// 启动服务及注册广播
		SetServiceBroadcast();

		// 添加监听器
		AddListener();
	}

	private void FindAllView() {
		// TODO Auto-generated method stub
		Recent = (LinearLayout) findViewById(R.id.List_Recentgroup);
		Local = (LinearLayout) findViewById(R.id.List_Localgroup);
		ListBt = (ImageButton) findViewById(R.id.List_Back);
		ListDrawer = (DrawerLayout) findViewById(R.id.ListDrawer);
		Bottom = (View) findViewById(R.id.ListBottom);
		Name = (TextView) findViewById(R.id.PlayName);
		Singer = (TextView) findViewById(R.id.PlaySinger);
		upButton = (ImageButton) findViewById(R.id.location_im1);
		playButton = (ImageButton) findViewById(R.id.location_im2);
		downButton = (ImageButton) findViewById(R.id.location_im3);
		skin = findViewById(R.id.Alert_layout1);
		list_above = findViewById(R.id.List_above);
		SharedPreferences sp=getSharedPreferences("info", MODE_PRIVATE);
		int c=sp.getInt("color", -1);
		list_above.setBackgroundColor(c);
		Bottom.setBackgroundColor(c);

	}

	private void SetServiceBroadcast() {
		MusicList = MusicUtils.getMusic(ListActivity.this);
		// 启动服务
		Intent intent = new Intent(ListActivity.this, MusicService.class);
		startService(intent);
		// 注册广播
		activity = new MyBroadCastActivity();
		IntentFilter filter = new IntentFilter("com.ACTIVITY");
		registerReceiver(activity, filter);
	}

	private void AddListener() {
		// TODO Auto-generated method stub
		Recent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent("com.Service");
				intent1.putExtra("music", Music);
				intent1.putExtra("flush", true);
				sendBroadcast(intent1);
				Intent intent = new Intent(ListActivity.this,
						LocateActivity.class);
				startActivity(intent);
			}
		});

		Local.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent("com.Service");
				intent1.putExtra("music", Music);
				intent1.putExtra("flush", true);
				sendBroadcast(intent1);
				Intent intent = new Intent(ListActivity.this,
						RecentActivity.class);
				
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

		Bottom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent("com.Service");
				intent1.putExtra("music", Music);
				intent1.putExtra("flush", true);
				sendBroadcast(intent1);
				Intent intent = new Intent(ListActivity.this,
						PlayActivity.class);
				startActivity(intent);
			}
		});

		upButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.Service");
				if (musicIndext == 0) {
					musicIndext = MusicList.size() - 1;
					Music = MusicList.get(musicIndext);
				} else {
					Music = MusicList.get(--musicIndext);
				}
				intent.putExtra("playitem", 1);
				intent.putExtra("NewMusic", 1);
				intent.putExtra("music", Music);
				intent.putExtra("index", musicIndext);
				sendBroadcast(intent);
				playButton.setImageResource(R.drawable.playbar_btn_pause);
				Name.setText(Music.getMusicName());
				Singer.setText(Music.getMusicOther());
			}
		});

		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.Service");
				intent.putExtra("click", 1);
				intent.putExtra("playMusic", 1);
				Music = MusicList.get(musicIndext);
				intent.putExtra("music", Music);
				sendBroadcast(intent);
				Name.setText(Music.getMusicName());
				Singer.setText(Music.getMusicOther());
			}
		});

		downButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.Service");
				if (musicIndext == MusicList.size() - 1) {
					musicIndext = 0;
					Music = MusicList.get(musicIndext);
				} else {
					Music = MusicList.get(++musicIndext);
				}
				intent.putExtra("playitem", 1);
				intent.putExtra("NewMusic", 1);
				intent.putExtra("music", Music);
				intent.putExtra("index", musicIndext);
				sendBroadcast(intent);
				playButton.setImageResource(R.drawable.playbar_btn_pause);
				Name.setText(Music.getMusicName());
				Singer.setText(Music.getMusicOther());
			}
		});

		// TODO Auto-generated method stub
		ColorPicker colorPicker_1;
		int color_1 = Color.rgb(55, 128, 128);
		ArrayList<Integer> presetColors = new ArrayList<Integer>();
		ChangeSkin change = new ChangeSkin();
		presetColors = change.getarraylist(presetColors);
		OnColorSelectedListener ColorSelectedListener = new OnColorSelectedListener() {
			@Override
			public void onSelected(int selectedColor) {
				list_above.setBackgroundColor(selectedColor);
				Bottom.setBackgroundColor(selectedColor);
				SharedPreferences sp=getSharedPreferences("info", MODE_PRIVATE);
				Editor ed=sp.edit();
				ed.putInt("color",selectedColor);
				ed.commit();
			}
		};
		colorPicker_1 = new ColorPicker(ListActivity.this, color_1,
				ColorSelectedListener, presetColors);
		skin.setOnClickListener(new OnStartButton(colorPicker_1.getDialog()));
	}

	// 接收广播，根据反馈的信息更改ui界面
	public class MyBroadCastActivity extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int flag = intent.getIntExtra("playFlag", -1);
			playInfo = intent.getIntExtra("playInfo", 0x11);
			if (flag != -1) {
				switch (playInfo) {
				case 0x11:
					playButton.setImageResource(R.drawable.playbar_btn_play);
					break;
				case 0x12:
					playButton.setImageResource(R.drawable.playbar_btn_pause);
					break;
				case 0x13:
					playButton.setImageResource(R.drawable.playbar_btn_play);
					break;

				default:
					break;
				}
			}

			Music = (MusicDao) intent.getSerializableExtra("music");
			Flush = intent.getBooleanExtra("flush", false);
			if (Music != null && Flush) {
				musicIndext = intent.getIntExtra("index", 0);
				if (intent.getBooleanExtra("playing", false)) {
					playButton.setImageResource(R.drawable.playbar_btn_pause);
				}
					Name.setText(Music.getMusicName());
					Singer.setText(Music.getMusicOther());
					Intent intent1 = new Intent("com.Service");
					intent1.putExtra("flush", false);
					intent1.putExtra("music", Music);
					sendBroadcast(intent1);
			}
		}

	}

}
