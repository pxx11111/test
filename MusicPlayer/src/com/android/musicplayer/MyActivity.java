package com.android.musicplayer;

import android.app.Activity;
import android.content.Intent;

public class MyActivity extends Activity {
	/**
     * ��������activityʱû�ж���
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        super.startActivity(intent);
    }

    /**
     * ��ֹ�˳�activityʱ��˸
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
