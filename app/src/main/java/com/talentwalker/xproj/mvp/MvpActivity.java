package com.talentwalker.xproj.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.talentwalker.xproj.base.BaseActivity;

/**
 * Created by Charles on 2017/11/23.
 *
 */

public abstract class MvpActivity<P extends MvpPresenter> extends BaseActivity {
    protected P mMvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mMvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMvpPresenter != null) {
            mMvpPresenter.detachView();
        }
    }
}
