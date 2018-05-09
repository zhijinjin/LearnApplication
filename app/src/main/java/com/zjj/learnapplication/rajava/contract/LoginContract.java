package com.zjj.learnapplication.rajava.contract;

import com.zjj.baselibrary.http.HttpResult;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhijinjin (951507056@qq.com)
 * on 2018/4/20.
 */

public interface LoginContract {

    interface View {
        void loginSuccess(HttpResult result);
        void loginSuccess(String result);
        void loginError(HttpResult result);
    }

    interface Presenter{
        void login(String name);
        void login3(String name);
        void LoginAndgetBalance(String name);
        void loginSuccess(HttpResult result);
        void loginSuccess(String result);
        void loginError(HttpResult result);
    }
}
