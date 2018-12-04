package com.example.tweak.danabusbooking.second_function;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {

    private int m_NumberOfTabs;

    public TabLayoutPagerAdapter(FragmentManager fragmentManager, int numberOfTabs) {
        super(fragmentManager);
        this.m_NumberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RouteTabLayout gouteTabLayout = new RouteTabLayout();
                return gouteTabLayout;

            case 1:
                BusTabLayout busTabLayout = new BusTabLayout();
                return busTabLayout;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return m_NumberOfTabs;
    }

}
