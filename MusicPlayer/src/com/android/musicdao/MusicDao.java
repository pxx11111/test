package com.android.musicdao;

import java.io.Serializable;

public class MusicDao implements Serializable {
	private static final long serialVersionUID = 1L;
	private String musicName;
	private String MusicOther;
	private String musicPath;
	private long musicTime;

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getMusicOther() {
		return MusicOther;
	}

	public void setMusicOther(String musicOther) {
		MusicOther = musicOther;
	}

	public String getMusicPath() {
		return musicPath;
	}

	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}

	public long getMusicTime() {
		return musicTime;
	}

	public void setMusicTime(long musicTime) {
		this.musicTime = musicTime;
	}
}
