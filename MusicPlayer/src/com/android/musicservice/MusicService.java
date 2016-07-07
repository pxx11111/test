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
	private int playInfo=0x11;// �����ͣ��״̬
	private int current, total;// ��ǰʱ������ʱ��

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
				case 0x11:// ��һ�ν���
                 playMusic(musicDao);
			        playInfo = 0x12;
					
					break;
				case 0x12:// ��ͣ
					mediaPlayer.pause();
					playInfo = 0x13;
					break;
				case 0x13:// ��ʼ
					playMusicresum(musicDao);
					playInfo = 0x12;
					break;

				default:
					break;
				}}}
			

			// ��ȡ��������ǰλ��
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
				mediaPlayer.stop();// ֹͣ
				mediaPlayer.reset();// ����
				try {
					mediaPlayer.setDataSource(musicDao.getMusicPath());
					System.out.println(musicDao.getMusicPath());
					mediaPlayer.prepare();// ׼��
					mediaPlayer.start();// ����

					total = mediaPlayer.getDuration();// ��ȡ��ǰ������ʱ��
					new Thread() {
						public void run() {
							try {

								while (current < total) {// ��ǰλ��С����ʱ��ʱ
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
				//mediaPlayer.stop();// ֹͣ
				//mediaPlayer.reset();// ����
				mediaPlayer.start();
	}
}
