package com.example.tweak.danabusbooking.home;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.third_function.BookedTicketTabLayout;
import com.example.tweak.danabusbooking.third_function.CanceledTicketTabLayout;
import com.example.tweak.danabusbooking.third_function.TabLayoutPagerAdapter;
import com.example.tweak.danabusbooking.third_function.TodayTicketTabLayout;

public class FunctionViewPageFragment3 extends Fragment implements ViewPager.OnPageChangeListener, TodayTicketTabLayout.OnFragmentInteractionListener, BookedTicketTabLayout.OnFragmentInteractionListener, CanceledTicketTabLayout.OnFragmentInteractionListener {

    private final String TAB_NAME_1 = "Vé hôm nay";
    private final String TAB_NAME_2 = "Vé đã đặt";
    private final String TAB_NAME_3 = "Vé đã hủy";

    private ViewPager viewPager;
    private TabLayoutPagerAdapter mAdapterViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.item_third_function_view, container, false);

        // Setting up the tabLayout
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        // Add tab to the tabLayout
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_1));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_2));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_3));

        // Set all the tabs in a suitable size
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // View pager is used to show up data
        viewPager = (ViewPager) view.findViewById(R.id.contentPager);

        mAdapterViewPager = new TabLayoutPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mAdapterViewPager);

        // Deprecated but still working
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.addOnPageChangeListener(this);

        // Handle sliding event
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                refreshData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing for now
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing for now
            }
        });

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00b688"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#00b688"));

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        refreshData(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void refreshData(int position) {
        Fragment myFragment = (Fragment) mAdapterViewPager.instantiateItem(viewPager, viewPager.getCurrentItem());
        if (myFragment != null) {
            if (position == 0)
                ((TodayTicketTabLayout) mAdapterViewPager.instantiateItem(viewPager, viewPager.getCurrentItem())).refreshThisFragment();
            else if (position == 1)
                ((BookedTicketTabLayout) mAdapterViewPager.instantiateItem(viewPager, viewPager.getCurrentItem())).refreshThisFragment();
            else if (position == 2)
                ((CanceledTicketTabLayout) mAdapterViewPager.instantiateItem(viewPager, viewPager.getCurrentItem())).refreshThisFragment();
        }
    }

    // Have to override this method
    @Override
    public void onFragmentInteraction(Uri uri) {
        // Do nothing
    }

}