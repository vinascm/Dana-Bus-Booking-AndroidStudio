package com.example.tweak.danabusbooking.first_function;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tweak.danabusbooking.HomeActivity;
import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.MainController;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoi;
import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoiController;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVe;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVeController;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.database_management.controllers.ve.Ve;
import com.example.tweak.danabusbooking.database_management.controllers.ve.VeController;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytController;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TicketBookingActivity extends AppCompatActivity {

    private TextView maVeTextView;
    private TextView ngaySanXuatTextView;
    private TextView ngayHetHanTextView;
    private TextView tenTuyenTextView;
    private TextView gheNgoiTextView;
    private TextView giaTienTextView;
    private TextView tinhTrangTextView;

    private Button cancelBookingButton;
    private Button verifyBookingButton;

    // Keeping track of reference for later update
    private Ve newTicket;
    private GheNgoi gheNgoiBooking;
    private XeBuyt xeBuytBooking;
    private float giaTienVe;
    private int maTuyenDuongOfThisVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);

        // Initializing variables views
        initializeVariables();

        // Set up proper data
        int maGheNgoiBooking = getIntent().getExtras().getInt("maGheNgoiBooking");
        maTuyenDuongOfThisVe = MainController.getInstance().getMaTuyenDuongByMaGheNgoi(maGheNgoiBooking);

        // Get a new ticket
        newTicket = VeController.getInstance().getAnAvailableTicketByMaTuyenDuong(maTuyenDuongOfThisVe);
        gheNgoiBooking = GheNgoiController.getInstance().getGheNgoiByMaGheNgoi(maGheNgoiBooking);
        xeBuytBooking = XeBuytController.getInstance().getXeBuytByMaXeBuyt(gheNgoiBooking.maXeBuyt);

        // Analyze and assign data to the views
        float tileGiamGia = MainController.getInstance().getTheTiLeGiamGiaByKhachHang(SharedVariables.currentMaKhachHang);
        float giaTienVeGoc = MainController.getInstance().getTheGiaTienVeGoc(newTicket.maTuyenDuong, newTicket.maVe);

        giaTienVe = giaTienVeGoc * (100 - tileGiamGia) / 100;

        String moTaGheNgoiBooking = gheNgoiBooking.soHieuGhe + " - " + gheNgoiBooking.viTriVatLy
                + " thuộc xe hiệu: " + xeBuytBooking.soHieuXe + " - Biển số xe: " + xeBuytBooking.bienSoXe;

        assignDataToViews(SharedVariables.convertIntToString(newTicket.maVe), newTicket.ngaySanXuat, newTicket.ngayHetHan,
                TuyenDuongController.getInstance().getTenTuyenDuongByMaTuyenDuong(maTuyenDuongOfThisVe),
                moTaGheNgoiBooking, SharedVariables.convertIntToString(Math.round(giaTienVe)) + " vnd",
                "Vé mới");
    }

    private void initializeVariables() {
        maVeTextView = findViewById(R.id.maVeTextView);
        ngaySanXuatTextView = findViewById(R.id.ngaySanXuatTextView);
        ngayHetHanTextView = findViewById(R.id.ngayHetHanTextView);
        tenTuyenTextView = findViewById(R.id.tenTuyenTextView);
        gheNgoiTextView = findViewById(R.id.gheNgoiTextView);
        giaTienTextView = findViewById(R.id.giaTienTextView);
        tinhTrangTextView = findViewById(R.id.tinhTrangTextView);

        cancelBookingButton = findViewById(R.id.cancelBookingButton);
        verifyBookingButton = findViewById(R.id.verifyBookingButton);

        cancelBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // return to the previous activity
                finish();
            }
        });

        verifyBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If user has already verified the ticket, then...

                // All we have to do is to save this new user_ticket to the table KhangHangVe
                // Also, we have to set the state of the old ticket to be 1 (not available)
                // And the state of the seat on the table GheNgoi to be 1 (not available)

                newTicket.tinhTrang = 1;
                VeController.getInstance().updateData(newTicket);

                gheNgoiBooking.tinhTrang = 1;
                GheNgoiController.getInstance().updateData(gheNgoiBooking);

                // Add new data to DB
                KhachHangVe khachHangVe = new KhachHangVe(newTicket.maVe, SharedVariables.currentMaKhachHang,
                        gheNgoiBooking.maGheNgoi, giaTienVe, 1,
                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()).toString(),
                        null);

                KhachHangVeController.getInstance().insertData(khachHangVe);

                popUpNotifyingAnotherTask();
            }
        });
    }

    private void popUpNotifyingAnotherTask() {
        // Hide components
        (findViewById(R.id.topLayout)).setVisibility(View.GONE);
        cancelBookingButton.setVisibility(View.GONE);
        verifyBookingButton.setVisibility(View.GONE);

        final Dialog dialog = new Dialog(this, R.style.DialogAnimationTheme2);
        dialog.setContentView(R.layout.dialog_continue_booking);

        // Preventing dialog box from getting dismissed on outside touch
        dialog.setCanceledOnTouchOutside(false);
        // Preventing dialog box from getting dismissed on back button pressed
        dialog.setCancelable(false);

        Button continueBookingButton = (Button) dialog.findViewById(R.id.continueBookingButton);
        Button returnHistoryPageButton = (Button) dialog.findViewById(R.id.returnHistoryPageButton);
        TextView notificationTextView = (TextView) dialog.findViewById(R.id.notificationTextView);

        // We have to remove this maTuyenDuong that has been booked for
        // preparing another booking another tuyenDuong
        SharedVariables.theListOfMaTuyenDuongThroughActivity.remove(Integer.valueOf(maTuyenDuongOfThisVe));
        if (SharedVariables.theListOfMaTuyenDuongThroughActivity.size() == 0) {
            // Force to return back to history page
            notificationTextView.setText("Bạn đã đặt được vé thành công. Hãy quay về mục quản lý giao dịch vé để kiểm tra thông tin!");
            continueBookingButton.setVisibility(View.GONE);
        } else {
            notificationTextView.setText("Bạn đã đặt được vé thành công. Bạn có thể tiếp tục đặt vé cho những tuyến còn lại hoặc trở về mục quản lý giao dịch vé!");
        }

        // Continuing to booking
        continueBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go straight to the activity of showing thongTin tuyenDuong

                Intent i = new Intent(getApplicationContext(), FirstFunctionActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                finish();
                dialog.dismiss();
            }
        });

        returnHistoryPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedVariables.lastIndexFunctionPress = 2;

                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void assignDataToViews(String maVe, String ngaySanXuat, String ngayHetHan, String tenTuyen, String gheNgoi, String giaTien, String tinhTrang) {
        maVeTextView.setText(maVe);
        ngaySanXuatTextView.setText(ngaySanXuat);
        ngayHetHanTextView.setText(ngayHetHan);
        tenTuyenTextView.setText(tenTuyen);
        gheNgoiTextView.setText(gheNgoi);
        giaTienTextView.setText(giaTien);
        tinhTrangTextView.setText(tinhTrang);
    }

}
