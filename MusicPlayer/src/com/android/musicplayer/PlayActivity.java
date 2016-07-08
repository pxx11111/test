package com.android.musicplayer;

import java.util.List;

import com.android.musicdao.MusicDao;
import com.android.musicplayer.LocateActivity.MyBroadCastActivity;
import com.android.musicservice.MusicService;
import com.android.musicutils.MusicUtils;
import com.android.pictureblur.BlurImageview;
import com.example.musicplayer.R;

import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PlayActivity extends MyActivity {
	private ImageView image;
	private RelativeLayout PlayLayout;
	private ImageButton ReturnBt;
	private ImageButton upButton;
	private ImageButton playButton;
	private ImageButton downButton;
	private TextView StarTime;
	private TextView EndTime;
	private SeekBar PlaySeek;
	private List<MusicDao> MusicList;
	private TextView Infor;
	private MusicDao Music;
	private int musicIndext;// ��¼��ǰ������λ��
	private int playInfo = 0x11;// ��ͣ����״̬��0x11��һ�ν��룬0x12��ͣ��0x13����
	boolean Flush = true;
	private MyBroadCastActivity activity;
	private int seekCurrentTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_play);

		// �������
		FindAllView();

		// ��������ע��㲥
		SetServiceBroadcast();

		// ��Ӽ�����
		AddListener();
	}

	private void FindAllView() {
		// TODO Auto-generated method stub
		image = (ImageView) findViewById(R.id.PlayImage);
		PlayLayout = (RelativeLayout) findViewById(R.id.PlayLayout);
		BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(
				R.drawable.example);
		PlayLayout.setBackground(BlurImageview.BlurImages(bd.getBitmap(),
				PlayActivity.this));
		ReturnBt = (ImageButton) findViewById(R.id.PlayReturn);
		Infor = (TextView) findViewById(R.id.PlayInfo);
		upButton = (ImageButton) findViewById(R.id.PlayBefore);
		playButton = (ImageButton) findViewById(R.id.Play);
		downButton = (ImageButton) findViewById(R.id.PlayNext);
		StarTime = (TextView) findViewById(R.id.StartTime);
		EndTime = (TextView) findViewById(R.id.EndTime);
		PlaySeek = (SeekBar) findViewById(R.id.PlayProgressBar);
	}

	private void SetServiceBroadcast() {
		MusicList = MusicUtils.getMusic(PlayActivity.this);
		// ��������
		Intent intent = new Intent(PlayActivity.this, MusicService.class);
		startService(intent);
		// ע��㲥
		activity = new MyBroadCastActivity();
		IntentFilter filter = new IntentFilter("com.ACTIVITY");
		registerReceiver(activity, filter);
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
				Intent intent1 = new Intent("com.Service");
				intent1.putExtra("flush", true);
				intent1.putExtra("music", Music);
				sendBroadcast(intent1);
				PlayActivity.this.finish();
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
				playButton.setImageResource(R.drawable.stop);
				Infor.setText(Music.getMusicName() + "		("
						+ Music.getMusicOther() + ")");
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
				Infor.setText(Music.getMusicName() + "		("
						+ Music.getMusicOther() + ")");
			}
		});
		
		PlaySeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				// ����ǰ״̬���͸��㲥���Ÿ�����ǰ��λ��
				seekCurrentTime = PlaySeek.getProgress();
				Intent intent1 = new Intent("com.Service");
				intent1.putExtra("seekCurrentTime", seekCurrentTime);
				sendBroadcast(intent1);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	// ���չ㲥�����ݷ�������Ϣ����ui����
	public class MyBroadCastActivity extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int flag = intent.getIntExtra("playFlag", -1);
			playInfo = intent.getIntExtra("playInfo", 0x11);
			if (flag != -1) {
				switch (playInfo) {
				case 0x11:
					playButton.setImageResource(R.drawable.play);
					break;
				case 0x12:
					playButton.setImageResource(R.drawable.stop);
					break;
				case 0x13:
					playButton.setImageResource(R.drawable.play);
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
					playButton.setImageResource(R.drawable.stop);
				}
				Infor.setText(Music.getMusicName() + "		("
						+ Music.getMusicOther() + ")");
				Intent intent1 = new Intent("com.Service");
				intent1.putExtra("flush", false);
				intent1.putExtra("music", Music);
				sendBroadcast(intent1);
			}
			// ���½�������ʱ����ʾ
			int current = intent.getIntExtra("current", -1);
			int total = intent.getIntExtra("total", -1);
			if (current != -1) {
				// ��ƽ�����
				PlaySeek.setProgress((int) ((current * 1.0) / total * 100));
				// ������ʾʱ��
				StarTime.setText(intToString(current));
				EndTime.setText(intToString(total));
			}
		}
	}
	
	
	
	// ������ʱ��ת���ɶ�Ӧ�ķ��ӣ���
		public String intToString(int cur) {
			int cur_f = cur / 1000 / 60;// ��
			int cur_s = cur / 1000 % 60;// ��

			return getT(cur_f) + ":" + getT(cur_s);
		}

		// ����ʱ����ȵ���ʾ
		public String getT(int x) {

			if (x < 10) {
				return "0" + x;
			} else

				return x + "";
		}

}
