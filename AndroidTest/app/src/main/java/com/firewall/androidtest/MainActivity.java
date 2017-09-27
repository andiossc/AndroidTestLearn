package com.firewall.androidtest;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firewall.androidtest.progressbar.HorizontalProgress;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private HorizontalProgress mProgress_1,mProgress_2;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgress_1 = (HorizontalProgress) findViewById(R.id.progress_1);
        mProgress_2 = (HorizontalProgress) findViewById(R.id.progress_2);
        progress = mProgress_1.getProgress();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress++;
                mProgress_1.setProgress(progress);
                mProgress_1.setTitleProgress("迅停单 "+progress+"%");
                mProgress_1.invalidate();
                mHandler.postDelayed(this,500);
                if(progress>=100){
                    mHandler.removeCallbacks(this);
//                    progress = 0;
                }
            }
        },500);
    }
}
