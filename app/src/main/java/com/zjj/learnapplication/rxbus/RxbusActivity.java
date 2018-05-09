package com.zjj.learnapplication.rxbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zjj.baselibrary.activity.BaseHttpActivity;
import com.zjj.baselibrary.http.HttpResult;
import com.zjj.baselibrary.http.MyOkHttp;
import com.zjj.baselibrary.http.ParameterMap;
import com.zjj.learnapplication.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RxbusActivity extends BaseHttpActivity {
    @BindView(R.id.bt1)
    public Button bt;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbus);
        ButterKnife.bind(this);

        disposable  =Rxbus.getDefault().register(RxModel.class, AndroidSchedulers.mainThread(),
                new Consumer<RxModel>() {
                    @Override
                    public void accept(RxModel rxModel) throws Exception {
                        bt.setText(rxModel.getName());
                    }
                });



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ParameterMap param = new ParameterMap();
                        param.put("secret","young cannon fancy tone orphan island decide labor oval fantasy genre path");
                        HttpResult result = MyOkHttp.post("http://mainnet.asch.cn/api/accounts/open/",param);
                        RxModel model = new RxModel();
                        model.setName(result.getRawJson());

                        Rxbus.getDefault().post(model);
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
