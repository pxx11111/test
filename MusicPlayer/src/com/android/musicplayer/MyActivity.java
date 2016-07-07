package com.android.musicplayer;

import android.app.Activity;
import android.content.Intent;

public class MyActivity extends Activity {
	/**
     * 设置启动activity时没有动画
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        super.startActivity(intent);
    }

    /**
     * 防止退出activity时闪烁
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
