package com.talentwalker.xproj.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.talentwalker.xproj.common.CommonUtils.checkNotNull;

/**
 * Created by Charles on 2017/11/23.
 * 网络相关工具类
 */

public final class NetworkUtils {
    /** 网络类型：没有网络 */
    public final static int NETWORK_TYPE_NONE = -1;
    /** 网络类型：wifi网络 */
    public final static int NETWORK_TYPE_WIFI = 1;
    /** 网络类型：mobile网络 */
    public final static int NETWORK_TYPE_MOBILE = 2;
    /** 网络类型：其他网络，蓝牙等 */
    public final static int NETWORK_TYPE_OTHER = 3;

    /**
     * 网络是否已连接
     * @param context 上下文
     * @return  boolean
     */
    public static boolean isNetworkConnected(Context context) {
        checkNotNull(context,"Context cannot be null");
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        checkNotNull(connectivityManager, "ConnectivityManager should not be null");
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * 判断wifi是否可用
     * @param context 上下文
     * @return boolean
     */
    public boolean isWifiConnected(Context context) {
        checkNotNull(context,"Context cannot be null");
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        checkNotNull(connectivityManager, "ConnectivityManager should not be null");
        NetworkInfo mWiFiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWiFiNetworkInfo != null && mWiFiNetworkInfo.isAvailable();
    }

    /**
     * 判断移动网络是否可用
     * @param context 上下文
     * @return boolean
     */
    public boolean isMobileConnected(Context context) {
        checkNotNull(context,"AppContext cannot be null");
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        checkNotNull(connectivityManager, "ConnectivityManager should not be null");
        NetworkInfo mMobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mMobileNetworkInfo != null && mMobileNetworkInfo.isAvailable();
    }

    /**
     * 获取当前网络类型
     * @param context 上下文
     * @return NetworkUtils#NETWORK_TYPE_*
     */
    public static int getCurrentNetworkType(Context context) {
        int netType = NETWORK_TYPE_NONE;
        checkNotNull(context,"AppContext cannot be null");
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        checkNotNull(connectivityManager, "ConnectivityManager should not be null");
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            netType = NETWORK_TYPE_MOBILE;
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETWORK_TYPE_WIFI;
        }
        return netType;
    }
}
