package com.android.musicutils;

import java.util.ArrayList;
import java.util.List;

import com.android.musicdao.MusicDao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


public class MusicUtils {
	public static List<MusicDao> getMusic(Context context) {
		List<MusicDao> list = new ArrayList<MusicDao>();

		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

		while (cursor.moveToNext()) {

			MusicDao MusicDao = new MusicDao();
			// 获取歌曲名字，演唱者，路径，时长
			String name = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE));
			String how = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST));
			String pathString = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA));
			long time = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION));

			// 过滤，将时间小于20s的歌曲过滤
			if (time > 2000) {
				MusicDao.setMusicName(name);
				MusicDao.setMusicOther(how);
				MusicDao.setMusicPath(pathString);
				MusicDao.setMusicTime(time);
				list.add(MusicDao);
			}

		}

		return list;

	}

}
