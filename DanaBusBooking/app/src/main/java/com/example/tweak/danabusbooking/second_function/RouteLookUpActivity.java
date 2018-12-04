package com.example.tweak.danabusbooking.second_function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.utils.SharedVariables;

public class RouteLookUpActivity extends AppCompatActivity {

    private TextView maTuyenDuongTextView;
    private TextView tenTuyenDuongTextView;
    private TextView khoangCachVatLyTextView;
    private TextView benDiTextView;
    private TextView benDoTextView;
    private TextView tanSuatTextView;
    private TextView giaVeTextView;
    private TextView gioMoBenTextView;
    private TextView gioDongBenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_look_up);

        // Initializing variables views
        initializeVariables();

        // Assign data
        int maTuyenDuongLookUp = getIntent().getExtras().getInt("maTuyenDuongLookUp");
        TuyenDuong tuyenDuongLookUp = TuyenDuongController.getInstance().getTuyenDuongByMaTuyenDuong(maTuyenDuongLookUp);
        assignDataToViews(tuyenDuongLookUp);
    }

    private void initializeVariables() {
        maTuyenDuongTextView = findViewById(R.id.maTuyenDuongTextView);
        tenTuyenDuongTextView = findViewById(R.id.tenTuyenDuongTextView);
        khoangCachVatLyTextView = findViewById(R.id.khoangCachVatLyTextView);
        benDiTextView = findViewById(R.id.benDiTextView);
        benDoTextView = findViewById(R.id.benDoTextView);
        tanSuatTextView = findViewById(R.id.tanSuatTextView);
        giaVeTextView = findViewById(R.id.giaVeTextView);
        gioMoBenTextView = findViewById(R.id.gioMoBenTextView);
        gioDongBenTextView = findViewById(R.id.gioDongBenTextView);
    }

    private void assignDataToViews(TuyenDuong tuyenDuong) {
        maTuyenDuongTextView.setText(SharedVariables.convertIntToString(tuyenDuong.maTuyenDuong));
        tenTuyenDuongTextView.setText(tuyenDuong.tenTuyenDuong);
        khoangCachVatLyTextView.setText(SharedVariables.conVertFloatToString(tuyenDuong.khoangCachVatLy) + " km");
        benDiTextView.setText(tuyenDuong.benDi);
        benDoTextView.setText(tuyenDuong.benDo);
        tanSuatTextView.setText(tuyenDuong.tanSuat);
        giaVeTextView.setText(SharedVariables.convertIntToString(Math.round(tuyenDuong.giaVe)) + " vnd");
        gioMoBenTextView.setText(tuyenDuong.gioMoBen);
        gioDongBenTextView.setText(tuyenDuong.gioDongBen);
    }

}


