package com.example.tweak.danabusbooking.first_function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.adapters.TuyenDuongThroughActivityAdapter;
import com.example.tweak.danabusbooking.adapters.XeBuytThroughActivityAdapter;
import com.example.tweak.danabusbooking.database_management.MainController;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class FirstFunction_2_Activity extends AppCompatActivity {

    private ArrayList<XeBuyt> listXeBuytShowOnListView;
    private ArrayAdapter<XeBuyt> m_Adapter;

    private ListView busThroughActivityListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_function_2);

        busThroughActivityListView = findViewById(R.id.busThroughActivityListView);

        //  Crucial
        updateTheListOfXeBuyt();

        m_Adapter = new XeBuytThroughActivityAdapter(this, R.layout.item_bus_view_through_activity,
                listXeBuytShowOnListView);

        busThroughActivityListView.setAdapter(m_Adapter);
    }

    // Go through to check whether a route has any available seats and vice versa
    private void updateTheListOfXeBuyt() {
        listXeBuytShowOnListView = SharedVariables.theListOfBusesThroughActivity;
    }

}


