package com.zjj.learnapplication.rajava.presenter;

import com.zjj.baselibrary.http.HttpResult;
import com.zjj.learnapplication.rajava.contract.LoginContract;
import com.zjj.learnapplication.rajava.model.LoginModel;

/**
 * Created by zhijinjin (951507056@qq.com)
 * on 2018/4/20.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view){
        this.view = view;
    }

    public void LoginAndgetBalance(String name) {
        try {
            new LoginModel(this).LoginAndgetBalance(name);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResult result =  new HttpResult();
            view.loginError(result);
        }
    }

    @Override
    public void loginSuccess(HttpResult result) {
        view.loginSuccess(result);
    }

    @Override
    public void loginSuccess(String result) {
        view.loginSuccess(result);
    }

    @Override
    public void loginError(HttpResult result) {
        view.loginError(result);
    }

    @Override
    public void login(String name) {
        try {
            new LoginModel(this).Login(name);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResult result =  new HttpResult();
            view.loginError(result);
        }
    }

    @Override
    public void login3(String name) {
        try {
            new LoginModel(this).Login3(name);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResult result =  new HttpResult();
            view.loginError(result);
        }
    }


}
