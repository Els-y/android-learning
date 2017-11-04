package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by els on 17-3-10.
 */

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Thread id is " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
