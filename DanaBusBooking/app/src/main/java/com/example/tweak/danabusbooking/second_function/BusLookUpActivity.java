package com.example.tweak.danabusbooking.second_function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.adapters.TramDungLookUpAdapter;
import com.example.tweak.danabusbooking.database_management.controllers.tram_dung.TramDung;
import com.example.tweak.danabusbooking.database_management.controllers.tram_dung.TramDungController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt_tram_dung.XeBuytTramDungController;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

public class BusLookUpActivity extends AppCompatActivity {

    private TextView maXeBuytTextView;
    private TextView soHieuXeTextView;
    private TextView bienSoXeTextView;
    private ListView stopStationListView;

    private ArrayList<TramDung> listTramDungShowOnListView;
    private ArrayAdapter<TramDung> m_Adapter;

    private int maXeBuytLookUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_look_up);

        maXeBuytLookUp = getIntent().getExtras().getInt("maXeBuytLookUp");

        // Initializing variables views
        initializeVariables();

        // Assign data
        XeBuyt xeBuytLookUp = XeBuytController.getInstance().getXeBuytByMaXeBuyt(maXeBuytLookUp);
        assignDataToViews(xeBuytLookUp);
    }

    private void getTheListOfTramDung() {
        // At first, we need to get the list of maTramDung
        ArrayList<Integer> listOfMaTramDungByMaXeBuyt = XeBuytTramDungController.getInstance().getTheListOfMaTramDungByMaXeBuyt(maXeBuytLookUp);

        // Then, we will get a list of TramDung object
        listTramDungShowOnListView = TramDungController.getInstance().getTheListOfTramDungByListMaTramDung(listOfMaTramDungByMaXeBuyt);
    }

    private void initializeVariables() {
        maXeBuytTextView = findViewById(R.id.maXeBuytTextView);
        soHieuXeTextView = findViewById(R.id.soHieuXeTextView);
        bienSoXeTextView = findViewById(R.id.bienSoXeTextView);
        stopStationListView = findViewById(R.id.stopStationListView);

        //  Crucial
        getTheListOfTramDung();

        m_Adapter = new TramDungLookUpAdapter(this, R.layout.item_stop_station_look_up_view,
                listTramDungShowOnListView);

        stopStationListView.setAdapter(m_Adapter);
    }

    private void assignDataToViews(XeBuyt xeBuyt) {
        maXeBuytTextView.setText(SharedVariables.convertIntToString(xeBuyt.maXeBuyt));
        soHieuXeTextView.setText(xeBuyt.soHieuXe);
        bienSoXeTextView.setText(xeBuyt.bienSoXe);
    }

}


