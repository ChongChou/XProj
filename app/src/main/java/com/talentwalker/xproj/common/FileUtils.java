package com.talentwalker.xproj.common;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.talentwalker.xproj.MainApplication;
import static com.talentwalker.xproj.common.CommonUtils.checkNotNull;

/**
 * Created by Charles on 2017/11/23.
 * 文件相关的工具类
 */

public class FileUtils {
    /**
     * 缓存文件路径
     */
    private static String sCacheDir;

    /**
     * 获取缓存文件路径
     * @return cacheDir
     */
    public static String getAppCacheDir() {
        if (TextUtils.isEmpty(sCacheDir)) {
            Context appContext = checkNotNull(MainApplication.getAppContext(), "AppContext cannot be null");
            //如果存在SD卡则将缓存写入SD卡,否则写入手机内存
            if (appContext.getExternalCacheDir() != null && isSDCardExists()) {
                sCacheDir = appContext.getExternalCacheDir().toString();
            } else {
                sCacheDir = appContext.getCacheDir().toString();
            }
        }
        return sCacheDir;
    }

    /**
     * 是否存在SDCard
     * @return boolean
     */
    private static boolean isSDCardExists() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
