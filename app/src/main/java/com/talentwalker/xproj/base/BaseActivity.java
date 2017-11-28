package com.talentwalker.xproj.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.talentwalker.xproj.MainApplication;
import com.talentwalker.xproj.common.XLog;

/**
 * Created by Charles on 2017/11/23.
 * BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.getInstance().addActivity(this);
        XLog.d(TAG,"========ActivityOnCreate==========");
    }

    @Override
    protected void onResume() {
        super.onResume();
        XLog.d(TAG,"========ActivityOnResume==========");
    }

    @Override
    protected void onDestroy() {
        MainApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
