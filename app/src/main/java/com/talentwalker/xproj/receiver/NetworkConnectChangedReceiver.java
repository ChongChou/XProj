package com.talentwalker.xproj.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.talentwalker.xproj.common.NetworkUtils;
import com.talentwalker.xproj.event.NetworkChangedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Charles on 2017/11/23.
 * 网络状态变化广播接收器
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    public static boolean isNetworkConnected = false;
    public static int sCurrentNetType = NetworkUtils.NETWORK_TYPE_NONE;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //获取联网状态的NetworkInfo对象
            NetworkInfo info = intent
                    .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                //如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        sCurrentNetType = NetworkUtils.NETWORK_TYPE_WIFI;
                    } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        sCurrentNetType = NetworkUtils.NETWORK_TYPE_MOBILE;
                    } else {
                        sCurrentNetType = NetworkUtils.NETWORK_TYPE_OTHER;
                    }
                    isNetworkConnected = true;
                } else {
                    sCurrentNetType = NetworkUtils.NETWORK_TYPE_NONE;
                    isNetworkConnected = false;
                }
                EventBus.getDefault().post(new NetworkChangedEvent(isNetworkConnected, sCurrentNetType));
            }
        }
    }
}
