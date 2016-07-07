package com.android.musicplayer;



import java.util.List;

import com.android.diyadapter.SongListAdpter;
import com.android.musicdao.MusicDao;
import com.android.musicservice.MusicService;
import com.android.musicutils.MusicUtils;
import com.example.musicplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LocateActivity extends MyActivity {
	private ImageButton ReturnBt;
	private View Bottom;
	private View Head;
	private ListView SongList;
	private SongListAdpter adp;
	private List<MusicDao> MusicList;
	private ImageButton upButton;
	private ImageButton playButton;
	private ImageButton downButton;
	private TextView Name;
	private TextView Singer;
	private MusicDao Music;
	private int musicIndext = 0;// 记录当前歌曲的位置
	private int playInfo = 0x11;// 暂停键的状态，0x11第一次进入，0x12暂停，0x13播放
	private MyBroadCastActivity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_locate);

		// 查找组件
		FindAllView();
		
		//设置自定义适配器
		SetMyAdapter();
		
		//启动服务及注册广播
		SetServiceBroadcast();

		// 添加监听器
		AddListener();
	}

	private void FindAllView() {
		// TODO Auto-generated method stub
		ReturnBt = (ImageButton) findViewById(R.id.Activity_Back);
		Bottom = (View) findViewById(R.id.LocateBottom);
		Head = (View) findViewById(R.id.LocateHead);
		SongList = (ListView) findViewById(R.id.LocateListview);
		Name = (TextView) findViewById(R.id.PlayName);
		Singer = (TextView) findViewById(R.id.PlaySinger);
		upButton = (ImageButton) findViewById(R.id.location_im1);
		playButton = (ImageButton) findViewById(R.id.location_im2);
		downButton = (ImageButton) findViewById(R.id.location_im3);
		Intent inte=getIntent();
		int c=inte.getIntExtra("color", -1);
		Head.setBackgroundColor(c);
	}
	
	private void SetMyAdapter() {
		// TODO Auto-generated method stub
		MusicList = MusicUtils.getMusic(LocateActivity.this);
		adp = new SongListAdpter(LocateActivity.this, MusicList);
		SongList.setAdapter(adp);
	}
	
	private void SetServiceBroadcast(){
		// 启动服务
		Intent intent = new Intent(LocateActivity.this, MusicService.class);
		startService(intent);
		// 注册广播
		activity = new MyBroadCastActivity();
		IntentFilter filter = new IntentFilter("com.ACTIVITY");
		registerReceiver(activity, filter);
	}

	private void AddListener() {
		// TODO Auto-generated method stub
		ReturnBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LocateActivity.this.finish();
			}
		});
		
		Bottom.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LocateActivity.this, PlayActivity.class);
				startActivity(intent);
			}
		});
		
		SongList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println(1);
				Music = MusicList.get(arg2);
				musicIndext = arg2;
				Intent intent = new Intent("com.Service");
				Log.i("-------", "---music_Object----" + Music);
				intent.putExtra("music", Music);
				intent.putExtra("NewMusic", 1);
				intent.putExtra("playitem", 1);
				sendBroadcast(intent);	
			}
		});
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

				// 更新进度条和时间显示
				int current = intent.getIntExtra("current", -1);
				int total = intent.getIntExtra("total", -1);

			}

		}

}
