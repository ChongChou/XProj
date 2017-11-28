package com.talentwalker.xproj.network;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.talentwalker.xproj.common.CommonUtils;
import com.talentwalker.xproj.common.Constants;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Charles on 2017/10/11.
 * 请求体实体类
 */

public class RequestEntity<E> {
    private String packageName;
    private String versionCode;
    private String platform;
    private String locale;
    private E data;

    /** 是否构造共有参数 */
    @Expose(serialize = false, deserialize = false)
    private boolean buildCommonParams;

    public RequestEntity(E data) {
        this(data, false);
    }

    /**
     * 构造方法
     * @param data    请求参数
     * @param buildCommonParams    是否过滤公共参数
     */
    public RequestEntity(E data, boolean buildCommonParams) {
        initCommonParams();
        this.buildCommonParams = buildCommonParams;
        this.data = data;
    }

    /**
     * 初始化公共参数
     */
    private void initCommonParams() {
        this.packageName = CommonUtils.getPackageName();
        this.versionCode = CommonUtils.getVersionName();
        this.platform = Constants.PLATFORM;
        this.locale = CommonUtils.getLocaleLanguage();
    }

    /**
     * 获取RequestBody对象
     * @return  RequestBody
     */
    public RequestBody getRequestBody() {
        Gson gson = RetrofitManager.getInstance().getGson();
        String bodyJson;
        if (this.buildCommonParams) {
            bodyJson = gson.toJson(this);
        } else {
            bodyJson = gson.toJson(data);
        }
        MediaType mediaType = MediaType.parse(NetworkConstants.CONTENT_TYPE_JSON);
        return RequestBody.create(mediaType, bodyJson);
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public boolean isBuildCommonParams() {
        return buildCommonParams;
    }

    public void setIsBuildCommonParams(boolean buildCommonParams) {
        this.buildCommonParams = buildCommonParams;
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "packageName='" + packageName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", platform='" + platform + '\'' +
                ", locale='" + locale + '\'' +
                ", data=" + data +
                '}';
    }
}
