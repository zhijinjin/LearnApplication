package com.zjj.learnapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhijinjin (951507056@qq.com)
 * on 2018/5/10.
 */

public class TabFragmentAadater extends FragmentPagerAdapter{

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public TabFragmentAadater(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
}
