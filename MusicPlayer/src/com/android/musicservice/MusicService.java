package com.android.musicservice;

import com.android.musicdao.MusicDao;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
	private MusicBroaderCaet broaderCaet;
	private MusicDao musicDao;
	private MediaPlayer mediaPlayer = new MediaPlayer();
	private int playInfo=0x11;// 标记暂停的状态
	private int current, total;// 当前时长，总时长

	@Override
	public void onCreate() {
		super.onCreate();
		broaderCaet = new MusicBroaderCaet();
		IntentFilter filter = new IntentFilter("com.Service");
		registerReceiver(broaderCaet, filter);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	class MusicBroaderCaet extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int falg = intent.getIntExtra("NewMusic", -1);
			int falg1 = intent.getIntExtra("click", -1);
			int playMusic = intent.getIntExtra("playMusic", -1);
			int infoitem=intent.getIntExtra("playitem", -1);
	        musicDao = (MusicDao) intent.getSerializableExtra("music");
            if (falg != -1) {
				playMusic(musicDao);
				if (infoitem != -1) {
					
	                 playMusic(musicDao);
	                 playInfo = 0x12; 
			}}
            if(falg1!=-1)
            {
			if (playMusic != -1) {
				switch (playInfo) {
				case 0x11:// 第一次进入
                 playMusic(musicDao);
			        playInfo = 0x12;
					
					break;
				case 0x12:// 暂停
					mediaPlayer.pause();
					playInfo = 0x13;
					break;
				case 0x13:// 开始
					playMusicresum(musicDao);
					playInfo = 0x12;
					break;

				default:
					break;
				}}}
			

			// 获取进度条当前位置
			int progress = intent.getIntExtra("seekCurrentTime", -1);
			if (progress != -1) {
				current = (int) (((progress * 1.0) / 100) * total);
				mediaPlayer.seekTo(current);
			}

			Intent intent2 = new Intent("com.ACTIVITY");
			intent2.putExtra("playInfo", playInfo);
			intent2.putExtra("playFlag", 1);
			sendBroadcast(intent2);
		}
	}

	public void playMusic(MusicDao musicDao) {
		if (musicDao != null) {
			if (mediaPlayer != null) {
				mediaPlayer.stop();// 停止
				mediaPlayer.reset();// 重置
				try {
					mediaPlayer.setDataSource(musicDao.getMusicPath());
					System.out.println(musicDao.getMusicPath());
					mediaPlayer.prepare();// 准备
					mediaPlayer.start();// 播放

					total = mediaPlayer.getDuration();// 获取当前歌曲总时长
					new Thread() {
						public void run() {
							try {

								while (current < total) {// 当前位置小于总时长时
									sleep(1000);
									current = mediaPlayer.getCurrentPosition();
									Intent intent = new Intent("com.ACTIVITY");
									intent.putExtra("current", current);
									intent.putExtra("total", total);
									sendBroadcast(intent);
								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					}.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	public void playMusicresum(MusicDao musicDao) {				
				//mediaPlayer.stop();// 停止
				//mediaPlayer.reset();// 重置
				mediaPlayer.start();
	}
}
