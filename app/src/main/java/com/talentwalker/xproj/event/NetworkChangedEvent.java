package com.talentwalker.xproj.event;

import com.talentwalker.xproj.common.NetworkUtils;
/**
 * Created by Charles on 2017/11/23.
 * 网络状态改变事件
 */

public class NetworkChangedEvent {
    private boolean isConnected;
    private int netType;

    public static final int NETWORK_TYPE_NONE = NetworkUtils.NETWORK_TYPE_NONE;
    public static final int NETWORK_TYPE_WIFI = NetworkUtils.NETWORK_TYPE_WIFI;
    public static final int NETWORK_TYPE_MOBILE = NetworkUtils.NETWORK_TYPE_MOBILE;

    public NetworkChangedEvent(boolean isConnected, int netType) {
        this.isConnected = isConnected;
        this.netType = netType;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * 获取网络类型
     * @return {@link NetworkChangedEvent#NETWORK_TYPE_NONE}
     *         {@link NetworkChangedEvent#NETWORK_TYPE_WIFI}
     *         {@link NetworkChangedEvent#NETWORK_TYPE_MOBILE}
     */
    public int getNetType() {
        return netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }
}
