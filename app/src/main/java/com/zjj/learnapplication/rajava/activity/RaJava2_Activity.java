package com.zjj.learnapplication.rajava.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.zjj.baselibrary.http.HttpResult;
import com.zjj.learnapplication.R;
import com.zjj.learnapplication.rajava.contract.LoginContract;
import com.zjj.learnapplication.rajava.presenter.LoginPresenter;

import es.dmoral.toasty.Toasty;

public class RaJava2_Activity extends AppCompatActivity  implements LoginContract.View{

    KProgressHUD hud ;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        final LoginPresenter presenter = new LoginPresenter(this);

        final EditText mEmailView = (EditText) findViewById(R.id.email);

        tv_show = (TextView) findViewById(R.id.show);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hud.show();
                presenter.login(mEmailView.getText().toString());
            }
        });

        findViewById(R.id.email_sign_in_button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hud.show();
                presenter.LoginAndgetBalance(mEmailView.getText().toString());
            }
        });


        findViewById(R.id.email_sign_in_button3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hud.show();
                presenter.login3(mEmailView.getText().toString());
            }
        });

    }



    @Override
    public void loginSuccess(HttpResult result) {
        hud.dismiss();
        tv_show.setText(result.getRawJson());
        Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void loginSuccess(String result) {
        hud.dismiss();
        tv_show.setText(result);
        Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void loginError(HttpResult result) {
        hud.dismiss();
        Toasty.error(this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
    }
}

