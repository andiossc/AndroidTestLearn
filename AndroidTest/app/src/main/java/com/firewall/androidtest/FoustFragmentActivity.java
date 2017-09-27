package com.firewall.androidtest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Firewall on 2017/9/14.
 */

public class FoustFragmentActivity extends Activity {

    private FragmentManager mFragmentManager;
    private Fragment mFragment1,mFragment2,mFragment3,mFragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_four_fragment);
        initView();
    }

    private void initView() {
        mFragmentManager = getFragmentManager();
        mFragment1 = mFragmentManager.findFragmentById(R.id.fragment_1);
        mFragment1.getView().setBackgroundColor(Color.RED);
        ((TextView)mFragment1.getView().findViewById(R.id.textview)).setText("预约通告");
        mFragment2 = mFragmentManager.findFragmentById(R.id.fragment_2);
        mFragment2.getView().setBackgroundColor(Color.GREEN);
        ((TextView)mFragment2.getView().findViewById(R.id.textview)).setText("进场通道");
        mFragment3 = mFragmentManager.findFragmentById(R.id.fragment_3);
        mFragment3.getView().setBackgroundColor(Color.BLUE);
        ((TextView)mFragment3.getView().findViewById(R.id.textview)).setText("出场通道");
        mFragment4 = mFragmentManager.findFragmentById(R.id.fragment_4);
        mFragment4.getView().setBackgroundColor(Color.GRAY);
        ((TextView)mFragment4.getView().findViewById(R.id.textview)).setText("即付通道");
    }
}
