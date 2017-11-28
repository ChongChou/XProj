package com.talentwalker.xproj.common;

import android.content.Context;
import android.support.annotation.NonNull;

import com.talentwalker.xproj.BuildConfig;
import com.talentwalker.xproj.MainApplication;

import java.util.Locale;

/**
 * Created by Charles on 2017/10/10.
 * Utils
 */

public final class CommonUtils {

    /**
     * 从Resource id转为字符串，进行为空判断
     * @param context   上下文
     * @param resId     字符串id
     * @return          字符串内容
     */
    public static String getStringFromResource(Context context, int resId) {
        checkNotNull(context, "CommonUtils => getStringFromResource, context is null");
        return context.getString(resId);
    }

    /**
     * 检查object是否为空，为空则抛出空指针异常
     * @param object        检查参数
     * @param message       异常信息
     * @param <T>           参数泛型
     * @return              返回参数
     */
    @NonNull
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * 获取应用包名
     * @return  应用包名
     */
    public static String getPackageName() {
        return MainApplication.getInstance().getPackageName();
    }

    /**
     * 获取应用版本号
     * @return  BuildConfig.VERSION_NAME
     */
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取本地语言
     * @return  Locale.getDefault().getLanguage()
     */
    public static String getLocaleLanguage() {
        return Locale.getDefault().getLanguage();
    }
}
