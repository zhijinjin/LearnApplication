package com.zjj.learnapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zjj.learnapplication.bugly.BuglyActivity;
import com.zjj.learnapplication.permiss.PermissActivity;
import com.zjj.learnapplication.rajava.activity.RaJava2_Activity;
import com.zjj.learnapplication.rxbus.RxbusActivity;
import com.zjj.mytoolbarlibrary.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements MyRecycViewAdapter.OnItemClickListener,MyToolBar.OnMyToolBarClickLisener{
    @BindView(R.id.recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    public MyToolBar toolBar;
    @BindView(R.id.appbarlayout)
    public AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        MyRecycViewAdapter adapter = new MyRecycViewAdapter(getContext());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        mCollapsingToolbarLayout.setExpandedTitleColor(Color.GREEN);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜
        toolBar.setLeftText("");
        toolBar.setTitleText("");
        showToolbar(true);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean misAppbarExpand = true;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//                int scrollRange = appBarLayout.getTotalScrollRange();
//                Log.d("logoffset", "verticalOffset:" + verticalOffset + ", scrollRange:" + scrollRange);
//                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
//                toolBar.setAlpha((1 - fraction));
//
//                if (fraction < 0.1 && misAppbarExpand) {
//                    misAppbarExpand = false;
//                    //addIconIv.setAlpha(1.0f);
////                    topBalanceTv.setAlpha(1.0f);
////                    addIconIv.setClickable(true);
//                    // toolbar.setVisibility(View.VISIBLE);
//                }
//
//                if (fraction > 0.8 && !misAppbarExpand) {
//                    misAppbarExpand = true;
//                    // addIconIv.setAlpha(0);
////                    topBalanceTv.setAlpha(0);
////                    addIconIv.setClickable(false);
//                    // toolbar.setVisibility(View.GONE);
//                }
//            }
//        });

        return view;
    }

    @Override
    public void onClick(int position) {
        switch (position){
            case 0:
                startActivity(new Intent(getContext(), RaJava2_Activity.class));
                break;
            case 1:
                startActivity(new Intent(getContext(), RxbusActivity.class));
                break;
            case 2:
                startActivity(new Intent(getContext(), PermissActivity.class));
                break;
            case 3:
                startActivity(new Intent(getContext(), BuglyActivity.class));
                break;
        }
    }

    @Override
    public void onLongClick(int position) {

    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onTitleClick() {

    }

    public void showToolbar(boolean show) {
        if (toolBar == null) {
        } else {
            int paddingTop = toolBar.getPaddingTop();
            int paddingBottom = toolBar.getPaddingBottom();
            int paddingLeft = toolBar.getPaddingLeft();
            int paddingRight = toolBar.getPaddingRight();
            int statusHeight = getStatusHeight(getContext());
            ViewGroup.LayoutParams params = toolBar.getLayoutParams();
            int height = params.height;
            /**
             * 利用状态栏的高度，4.4及以上版本给Toolbar设置一个paddingTop值为status_bar的高度，
             * Toolbar延伸到status_bar顶部
             **/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (show) {
                    paddingTop += statusHeight;
                    height += statusHeight;
                } else {
                    paddingTop -= statusHeight;
                    height -= statusHeight;
                }
            }
            params.height = height;
            toolBar.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            toolBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return px
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }



}
