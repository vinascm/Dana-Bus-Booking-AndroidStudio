package com.example.tweak.danabusbooking.third_function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.MainController;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoi;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoiController;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVe;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.database_management.controllers.ve.Ve;
import com.example.tweak.danabusbooking.database_management.controllers.ve.VeController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;
import com.example.tweak.danabusbooking.utils.SharedVariables;

public class TicketDetailActivity extends AppCompatActivity {

    private TextView maVeTextView;
    private Button tinhTrangNgayButton;
    private TextView thongTinNgayTextView;
    private TextView tenTuyenTextView;
    private TextView gheNgoiTextView;
    private TextView giaTienTextView;
    private TextView ngaySanXuatTextView;
    private TextView ngayHetHanTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        // Initializing variables views
        initializeVariables();

        // Get the khachHangVe object through this
        KhachHangVe khachHangVe = SharedVariables.khachHangVeDetail;

        int maGheNgoiDetail = khachHangVe.maGheNgoi;
        int maTuyenDuongDetail = MainController.getInstance().getMaTuyenDuongByMaGheNgoi(maGheNgoiDetail);

        // Get a new ticket
        Ve veDetail = VeController.getInstance().getVeByMaVe(khachHangVe.maVe);
        GheNgoi gheNgoiDetail = GheNgoiController.getInstance().getGheNgoiByMaGheNgoi(maGheNgoiDetail);
        XeBuyt xeBuytDetail = XeBuytController.getInstance().getXeBuytByMaXeBuyt(gheNgoiDetail.maXeBuyt);

        String moTaGheNgoiDetail = gheNgoiDetail.soHieuGhe + " - " + gheNgoiDetail.viTriVatLy
                + " thuộc xe hiệu: " + xeBuytDetail.soHieuXe + " - Biển số xe: " + xeBuytDetail.bienSoXe;

        assignDataToViews(SharedVariables.convertIntToString(khachHangVe.maVe),
                khachHangVe.tinhTrang == 1 ? "Ngày đặt" : "Ngày hủy",
                khachHangVe.tinhTrang == 1 ? khachHangVe.ngayDat : khachHangVe.ngayHuy,
                TuyenDuongController.getInstance().getTenTuyenDuongByMaTuyenDuong(maTuyenDuongDetail),
                moTaGheNgoiDetail,
                SharedVariables.convertIntToString(Math.round(khachHangVe.giaTienVe)) + " vnd",
                veDetail.ngaySanXuat,
                veDetail.ngayHetHan);
    }

    private void initializeVariables() {
        maVeTextView = findViewById(R.id.maVeTextView);
        tinhTrangNgayButton = findViewById(R.id.tinhTrangNgayButton);
        thongTinNgayTextView = findViewById(R.id.thongTinNgayTextView);
        tenTuyenTextView = findViewById(R.id.tenTuyenTextView);
        gheNgoiTextView = findViewById(R.id.gheNgoiTextView);
        giaTienTextView = findViewById(R.id.giaTienTextView);
        ngaySanXuatTextView = findViewById(R.id.ngaySanXuatTextView);
        ngayHetHanTextView = findViewById(R.id.ngayHetHanTextView);
    }

    private void assignDataToViews(String maVe, String tinhTrangNgay, String thongTinNgay, String tenTuyen,
                                   String gheNgoi, String giaTien, String ngaySanXuat, String ngayHetHan) {
        maVeTextView.setText(maVe);
        tinhTrangNgayButton.setText(tinhTrangNgay);
        thongTinNgayTextView.setText(thongTinNgay);
        tenTuyenTextView.setText(tenTuyen);
        gheNgoiTextView.setText(gheNgoi);
        giaTienTextView.setText(giaTien);
        ngaySanXuatTextView.setText(ngaySanXuat);
        ngayHetHanTextView.setText(ngayHetHan);
    }

}


