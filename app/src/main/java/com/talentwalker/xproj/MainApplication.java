package com.talentwalker.xproj;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Process;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.talentwalker.xproj.common.CommonUtils;
import com.talentwalker.xproj.common.Constants;
import com.talentwalker.xproj.common.CrashHandler;
import com.talentwalker.xproj.common.XLog;
import com.talentwalker.xproj.receiver.NetworkConnectChangedReceiver;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Charles on 2017/10/10.
 * Application
 */

public class MainApplication extends Application {
    private static MainApplication mAppInstance;
    private static Context mAppContext;
    private Set<Activity> allActivities;
    private NetworkConnectChangedReceiver mNetworkChangedReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
        mAppContext = getApplicationContext();

        CrashHandler.init(new CrashHandler());

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        allActivities = new HashSet<>();
        XLog.initLogger(mAppContext);
        registerBroadcastReceiver();
    }

    @Override
    public void onTerminate() {
        unregisterBroadcastReceiver();
        super.onTerminate();
    }

    /**
     * 注册广播接收器
     */
    private void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkChangedReceiver = new NetworkConnectChangedReceiver();
        mAppContext.registerReceiver(mNetworkChangedReceiver, intentFilter);
    }

    /**
     * 解除注册
     */
    private void unregisterBroadcastReceiver() {
        if (mNetworkChangedReceiver != null) {
            mAppContext.unregisterReceiver(mNetworkChangedReceiver);
            mNetworkChangedReceiver = null;
        }
    }

    /**
     * 添加activity
     * @param activity {@link Activity}
     */
    public void addActivity(Activity activity) {
        allActivities.add(activity);
    }

    /**
     * 移除activity
     * @param activity {@link Activity}
     */
    public void removeActivity(Activity activity) {
        if (allActivities != null) {
            allActivities.remove(activity);
        }
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        unregisterBroadcastReceiver();
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity activity : allActivities) {
                    if (activity != null) {
                        activity.finish();
                    }
                }
            }
            allActivities.clear();
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 获取Application实例
     * @return  MainApplication
     */
    public static synchronized MainApplication getInstance() {
        return mAppInstance;
    }

    /**
     * 获取ApplicationContext
     * @return MainApplication instance.getApplicationContext();
     */
    public static Context getAppContext() {
        return mAppContext;
    }
}
