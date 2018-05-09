package com.zjj.learnapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zjj.learnapplication.bugly.BuglyActivity;
import com.zjj.learnapplication.permiss.PermissActivity;
import com.zjj.learnapplication.rajava.activity.RaJava2_Activity;
import com.zjj.learnapplication.rxbus.RxbusActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MyRecycViewAdapter.OnItemClickListener{
    @BindView(R.id.recyclerview)
    public RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MyRecycViewAdapter adapter = new MyRecycViewAdapter(this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        switch (position){
            case 0:
                startActivity(new Intent(this, RaJava2_Activity.class));
                break;
            case 1:
                startActivity(new Intent(this, RxbusActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, PermissActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, BuglyActivity.class));
                break;
        }
    }

    @Override
    public void onLongClick(int position) {

    }
}
