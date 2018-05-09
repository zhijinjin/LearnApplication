package com.zjj.learnapplication.bugly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tencent.bugly.beta.Beta;
import com.zjj.learnapplication.R;


public class BuglyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugly);
         findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 CrashReport.testJavaCrash();
                 String str = null;
                 String s = str.toString();
             }
         });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Beta.checkUpgrade();//检查版本号
            }
        });
    }
}
