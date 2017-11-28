package com.talentwalker.xproj.network;

/**
 * Created by Charles on 2017/10/11.
 * 网络相关常量类
 */

public final class NetworkConstants {
    /** Host地址 */
    public static final String HOST_RELEASE = "https://api.xproj.in/api/";
    public static final String HOST_DEBUG = "http://188.188.188.86:8080/api/";

    /** Http请求log tag */
    public static final String HTTP_LOG_TAG = "TalentNetwork";
    /** Http请求超时时间，单位：s */
    public static final long HTTP_TIME_OUT = 15;

    /** 内容类型：json */
    public static final String CONTENT_TYPE_JSON = "application/json";
}
