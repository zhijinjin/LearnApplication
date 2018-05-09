package com.zjj.learnapplication.rajava.model;


import com.alibaba.fastjson.JSON;
import com.zjj.baselibrary.http.HttpResult;
import com.zjj.baselibrary.http.MyOkHttp;
import com.zjj.baselibrary.http.ParameterMap;
import com.zjj.learnapplication.rajava.contract.LoginContract;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by zhijinjin (951507056@qq.com)
 * on 2018/4/20.
 */

public class LoginModel {
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LoginContract.Presenter presenter;

    public LoginModel(LoginContract.Presenter presenter){
        this.presenter = presenter;
    }

    private Observable<HttpResult> getLoginObservable(final String name){
        return Observable.create(new ObservableOnSubscribe<HttpResult>() {
            @Override
            public void subscribe(ObservableEmitter<HttpResult> emitter) throws Exception {
                ParameterMap param = new ParameterMap();
                param.put("secret",name);
                HttpResult result = MyOkHttp.post("http://mainnet.asch.cn/api/accounts/open/",param);
                emitter.onNext(result);
            }
        });
    }

    private Observable<HttpResult> getBalanceObservable(final String name){
        return Observable.create(new ObservableOnSubscribe<HttpResult>() {
            @Override
            public void subscribe(ObservableEmitter<HttpResult> emitter) throws Exception {
                ParameterMap param = new ParameterMap();
                param.put("address", "AJxAh56VDTjYHtffypdDxeQD52KG19mvcY");
                HttpResult result = MyOkHttp.get("http://mainnet.asch.cn/api/accounts",param);
                emitter.onNext(result);
            }
        });
    }

    private Observer<HttpResult> getLoginObserver(){

        return new Observer<HttpResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(HttpResult result) {
                compositeDisposable.clear();
                presenter.loginSuccess(result);
            }

            @Override
            public void onError(Throwable e) {
                compositeDisposable.clear();
                new Throwable(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    /**
     * 基本的Rxjava流程
     * @param name
     * @throws Exception
     */
    public void Login(final String name)throws Exception{
       getLoginObservable(name)
               .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLoginObserver());
    }


    /**
     * 使用flatMap操作符
     * @param name
     * @throws Exception
     */
    public void LoginAndgetBalance(final String name)throws Exception{
        getLoginObservable(name).subscribeOn(Schedulers.io())
                .flatMap(new Function<HttpResult, ObservableSource<HttpResult>>() {
            @Override
            public ObservableSource<HttpResult> apply(final HttpResult result) throws Exception {
                return Observable.create(new ObservableOnSubscribe<HttpResult>() {
                    @Override
                    public void subscribe(ObservableEmitter<HttpResult> emitter) throws Exception {
                        if(!result.isSuccessful()){
                            presenter.loginError(result);
                        }else{
                            com.alibaba.fastjson.JSONObject obj = JSON.parseObject(result.getRawJson());
                            com.alibaba.fastjson.JSONObject accountObj = obj.getJSONObject("account");
                            ParameterMap param = new ParameterMap();
                            param.put("address",accountObj.get("address"));
                            HttpResult result = MyOkHttp.get("http://mainnet.asch.cn/api/accounts",param);
                            emitter.onNext(result);
                        }
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLoginObserver());
    }

    /**
     * Zip 使用
     * @param name
     */
    public void Login3(String name){
        Observable.zip(getLoginObservable(name).subscribeOn(Schedulers.io()),
                getBalanceObservable(name).subscribeOn(Schedulers.io()),
                new BiFunction<HttpResult, HttpResult, String>() {
                    @Override
                    public String apply(HttpResult result, HttpResult result2) throws Exception {
                        if(result.isSuccessful() && result2.isSuccessful()){
                            com.alibaba.fastjson.JSONObject obj = JSON.parseObject(result.getRawJson());
                            com.alibaba.fastjson.JSONObject accountObj1 = obj.getJSONObject("account");
                            com.alibaba.fastjson.JSONObject obj2 = JSON.parseObject(result2.getRawJson());
                            com.alibaba.fastjson.JSONObject accountObj2 = obj2.getJSONObject("latestBlock");
                            return accountObj1.get("publicKey")+"\n"+accountObj2.get("height");
                        }else{
                            return "失败";
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(String result) {
                compositeDisposable.clear();
                presenter.loginSuccess(result);
            }

            @Override
            public void onError(Throwable e) {
                compositeDisposable.clear();
                new Throwable(e.getMessage());
            }

            @Override
            public void onComplete() {
                compositeDisposable.clear();
            }
        });
    }
}
