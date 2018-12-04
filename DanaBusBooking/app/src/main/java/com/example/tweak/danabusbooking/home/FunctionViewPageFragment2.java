package com.example.tweak.danabusbooking.home;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.second_function.BusTabLayout;
import com.example.tweak.danabusbooking.second_function.RouteTabLayout;
import com.example.tweak.danabusbooking.second_function.TabLayoutPagerAdapter;
import com.example.tweak.danabusbooking.third_function.BookedTicketTabLayout;
import com.example.tweak.danabusbooking.third_function.CanceledTicketTabLayout;
import com.example.tweak.danabusbooking.third_function.TodayTicketTabLayout;

public class FunctionViewPageFragment2 extends Fragment implements RouteTabLayout.OnFragmentInteractionListener, BusTabLayout.OnFragmentInteractionListener {

    private final String TAB_NAME_1 = "Thông tin tuyến";
    private final String TAB_NAME_2 = "Thông tin xe buýt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.item_second_function_view, container, false);

        // Setting up the tabLayout
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        // Add tab to the tabLayout
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_1));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_2));

        // Set all the tabs in a suitable size
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // View pager is used to show up data
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.contentPager);

        viewPager.setAdapter(new TabLayoutPagerAdapter(getFragmentManager(), tabLayout.getTabCount()));

        // Deprecated but still working
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Handle sliding event
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#FF0000"));

        return view;
    }

    // Have to override this method
    @Override
    public void onFragmentInteraction(Uri uri) {
        // Do nothing
    }

}