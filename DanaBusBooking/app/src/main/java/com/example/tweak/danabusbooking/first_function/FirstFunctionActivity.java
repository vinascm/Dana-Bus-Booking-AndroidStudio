package com.example.tweak.danabusbooking.first_function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.tweak.danabusbooking.HomeActivity;
import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.adapters.TuyenDuongThroughActivityAdapter;
import com.example.tweak.danabusbooking.database_management.MainController;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class FirstFunctionActivity extends AppCompatActivity {

    private ArrayList<TuyenDuong> listTuyenDuongShowOnListView;
    private ArrayAdapter<TuyenDuong> m_Adapter;

    private ListView routeThroughActivityListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_function);

        routeThroughActivityListView = findViewById(R.id.routeThroughActivityListView);

        //  Crucial
        updateTheListOfTuyenDuong();

        m_Adapter = new TuyenDuongThroughActivityAdapter(this, R.layout.item_route_view_through_activity,
                listTuyenDuongShowOnListView);

        routeThroughActivityListView.setAdapter(m_Adapter);
    }

    // This is used to force only HomeActivity shows up when pressing the backButton
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    // Go through to check whether a route has any available seats and vice versa
    private void updateTheListOfTuyenDuong() {
        // Get the list of TuyenDuong objects to show on listView
        listTuyenDuongShowOnListView = TuyenDuongController.getInstance()
                .getListTuyenDuongShowOnListView(SharedVariables.theListOfMaTuyenDuongThroughActivity);

        // Update the daHetCho attributes in listTuyenDuongShowOnListView to see
        // if an item in it has: daHetCho = true by checking is there any available seats
        for (int i = 0; i < listTuyenDuongShowOnListView.size(); i++) {
            ArrayList<XeBuyt> xeBuyts = XeBuytController.getInstance().
                    getListXeBuytByMaTuyenDuong(listTuyenDuongShowOnListView.get(i).maTuyenDuong);

            for (int j = 0; j < xeBuyts.size(); j++)
                if (MainController.getInstance().doesThisBusHasAnAvailableSeat(xeBuyts.get(j))) {
                    listTuyenDuongShowOnListView.get(i).daHetCho = false;
                    break;
                }
        }
    }

}


