package com.talentwalker.xproj.common;


import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.talentwalker.xproj.R;

/**
 * Created by Charles on 2017/11/24.
 * 封装Logger
 */

public final class XLog {
    private XLog() {}

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void v(String msg) {
        Logger.v(msg);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }

    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void v(String tag, String msg) {
        Logger.t(tag).v(msg);
    }

    public static void json(String tag, String json) {
        Logger.t(tag).json(json);
    }

    public static void xml(String tag, String xml) {
        Logger.t(tag).xml(xml);
    }

    /**
     * 初始化Logger
     */
    public static void initLogger(Context context) {
        // 以应用名称作为log tag
        String tag = CommonUtils.getStringFromResource(context, R.string.app_name);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().tag(tag).build();
        LogAdapter adapter = new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return Constants.LOG_ENABLE;
            }
        };
        Logger.addLogAdapter(adapter);
    }
}
