package com.talentwalker.xproj.network.service;

import com.talentwalker.xproj.network.ResponseEntity;
import com.talentwalker.xproj.network.entity.UserInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Charles on 2017/10/11.
 * 用户相关api请求
 */

public interface IUserService {
    @POST("user/login")
    Observable<ResponseEntity<UserInfo>> login(@Body RequestBody body);
}
