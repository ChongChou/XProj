package com.talentwalker.xproj.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talentwalker.xproj.BuildConfig;
import com.talentwalker.xproj.MainApplication;
import com.talentwalker.xproj.common.FileUtils;
import com.talentwalker.xproj.common.NetworkUtils;
import com.talentwalker.xproj.common.XLog;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Charles on 2017/10/10.
 * Retrofit管理器，retrofit对象单例化
 */

public final class RetrofitManager {

    private Retrofit mRetrofit;
    private Gson mGson;
    private OkHttpClient mHttpClient;

    private static final long MAX_CACHE_SIZE = 1024 * 1024 * 50L;

    /**
     * 私有化构造方法
     */
    private RetrofitManager() {
        mGson = buildGson();
        mHttpClient = buildHttpClient();
        mRetrofit = buildRetrofit();
    }

    public static RetrofitManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final static class InstanceHolder {
        private final static RetrofitManager INSTANCE = new RetrofitManager();
    }

    /**
     * 根据Service创建Retrofit请求
     * @param service   请求Service
     * @param <T>       Service
     * @return  Retrofit.create
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 获取Gson实例
     * @return  构造Retrofit使用的Gson实例
     */
    public Gson getGson() {
        return mGson;
    }

    /**
     * 获取HttpClient对象
     * @return  构造Retrofit使用的OkHttpClient实例
     */
    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }

    /**
     * 构造Retrofit对象
     * @return  {@link Retrofit}
     */
    private Retrofit buildRetrofit() {
        String baseUrl = BuildConfig.DEBUG ? NetworkConstants.HOST_DEBUG : NetworkConstants.HOST_RELEASE;
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(mGson))
                // 添加Retrofit到RxJava转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 添加HttpClient
                .client(mHttpClient)
                .build();
    }

    /**
     * 构造OkHttpClient对象
     * @return  {@link OkHttpClient}
     */
    private OkHttpClient buildHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // 设置log拦截器
            builder.addInterceptor(buildLoggingInterceptor());
        }
        // 设置缓存路径
        builder.cache(buildCache());
        // 设置请求头部拦截器
        builder.addInterceptor(buildHeaderInterceptor());
        // 设置缓存拦截器
        builder.addInterceptor(buildCacheInterceptor());
        // 设置超时时间
        builder.connectTimeout(NetworkConstants.HTTP_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(NetworkConstants.HTTP_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(NetworkConstants.HTTP_TIME_OUT, TimeUnit.SECONDS);
        // 设置出错时重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    /**
     * 构造okHttp拦截器，用于添加请求公用的头部
     * @return  {@link Interceptor}
     */
    private Interceptor buildHeaderInterceptor() {
        return chain -> {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Accept", "application/json");
            builder.addHeader("Content-Type", "application/json;charset=UTF-8");
            return chain.proceed(builder.build());
        };
    }

    /**
     * 构造HttpLogging拦截器，用于打印输出
     * @return  {@link HttpLoggingInterceptor}
     */
    private HttpLoggingInterceptor buildLoggingInterceptor() {
        HttpLoggingInterceptor.Logger logger = message ->
                XLog.d(NetworkConstants.HTTP_LOG_TAG, message);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 构造缓存拦截器
     * @return {@link Interceptor}
     */
    private Interceptor buildCacheInterceptor() {
        return chain->{
            Request request = chain.request();
            if (!NetworkUtils.isNetworkConnected(MainApplication.getAppContext())) {
                 request = request.newBuilder()
                         .cacheControl(CacheControl.FORCE_CACHE)
                         .build();
            }
            Response response = chain.proceed(request);
            Response.Builder newBuilder = response.newBuilder();
            if (NetworkUtils.isNetworkConnected(MainApplication.getAppContext())) {
                int maxAge = 0;
                // 有网络时 设置缓存超时时间0个小时
                newBuilder.header("Cache-Control", "public, max-age=" + maxAge);
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            return newBuilder.build();
        };
    }

    /**
     * 构建Cache对象
     * @return {@link Cache}
     */
    private Cache buildCache() {
        File cacheFile = new File(FileUtils.getAppCacheDir(), "/NetworkCache");
        return new Cache(cacheFile, MAX_CACHE_SIZE);
    }

    /**
     * 构建Gson对象
     * @return  {@link Gson}
     */
    private Gson buildGson() {
        return new GsonBuilder()
                // 序列化时，不忽略null
                .serializeNulls()
                // 设置json键名匹配java类字段名
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                // 过滤设置了Expose为false的字段
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
