package com.talentwalker.xproj.mvp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Charles on 2017/11/23.
 * mvp - base presenter
 */

public class MvpPresenter<V extends MvpView> {
    protected V mMvpView;
    private CompositeDisposable mCompositeDisposable;

    public void attachView(V mvpView) {
        this.mMvpView = mvpView;
    }

    public void detachView() {
        this.mMvpView = null;
        onUnsubscribe();
    }

    private void onUnsubscribe() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    public void addObservable(Observable observable, DisposableObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        Disposable disposable = (Disposable) observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        mCompositeDisposable.add(disposable);
    }
}
