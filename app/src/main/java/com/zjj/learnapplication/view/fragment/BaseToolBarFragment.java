package com.zjj.learnapplication.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.zjj.learnapplication.R;
import com.zjj.mytoolbarlibrary.MyToolBar;


public class BaseToolBarFragment extends Fragment {
    public MyToolBar toolBar;

    public BaseToolBarFragment() {

    }

    public static BaseToolBarFragment newInstance(String param1) {
        BaseToolBarFragment fragment = new BaseToolBarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initToolBar(String title,View view){
        toolBar = view.findViewById(R.id.toolbar);
        if (toolBar != null) {
            toolBar.setTitleText(title);
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
                paddingTop += statusHeight;
                height += statusHeight;
            }
            params.height = height;
            toolBar.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
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
