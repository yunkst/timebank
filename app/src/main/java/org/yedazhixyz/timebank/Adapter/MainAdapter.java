package org.yedazhixyz.timebank.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.yedazhixyz.timebank.Fragment.F_Timer;
import org.yedazhixyz.timebank.Fragment.F_TimerDetail;

/**
 * Created by KFEB4 on 2016/7/21.
 * 主页的Adapter
 */
public class MainAdapter extends FragmentPagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new F_Timer();
            case 1:
                return new F_TimerDetail();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "计时器";
            case 1:
                return "计时信息";
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
}
