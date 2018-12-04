package com.example.tweak.danabusbooking.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweak.danabusbooking.HomeActivity;
import com.example.tweak.danabusbooking.LoginActivity;
import com.example.tweak.danabusbooking.R;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHang;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHangController;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVe;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVeController;
import com.example.tweak.danabusbooking.database_management.controllers.uu_dai.UuDai;
import com.example.tweak.danabusbooking.database_management.controllers.uu_dai.UuDaiController;
import com.example.tweak.danabusbooking.database_management.controllers.uu_dai.UuDaiData;
import com.example.tweak.danabusbooking.fourth_function.EditingUserInformationActivity;
import com.example.tweak.danabusbooking.utils.ConvertImageData;
import com.example.tweak.danabusbooking.utils.SharedVariables;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class FunctionViewPageFragment4 extends Fragment {

    private final int EDITING_USER_INFORMATION_REQUEST_CODE = 1;

    private TextView tenTextView;
    private TextView emailTextView;
    private TextView diaChiTextView;
    private TextView tuoiTextView;
    private TextView gioiTinhTextView;
    private TextView ngheNghiepTextView;
    private TextView soVeDaDatTextView;
    private TextView soVeDaHuyTextView;
    private TextView cheDoUuDaiTextView;
    private TextView tiLeGiamGiaTextView;
    private ImageView avatarImageView;

    private Button logOutButton;
    private Button editUserInformationButton;

    private KhachHang referenceKhachHang;
    private UuDai referenceUuDai;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.item_fourth_function_view, container, false);

        initializeVariables(view);

        referenceKhachHang = KhachHangController.getInstance().getKhachHangByMaKhachHang(SharedVariables.currentMaKhachHang);
        referenceUuDai = UuDaiController.getInstance().getUuDaiByMaUuDai(referenceKhachHang.maUuDai);
        assignDataToViews(referenceKhachHang, referenceUuDai);

        return view;
    }

    private void initializeVariables(View view) {
        tenTextView = view.findViewById(R.id.tenTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        diaChiTextView = view.findViewById(R.id.diaChiTextView);
        tuoiTextView = view.findViewById(R.id.tuoiTextView);
        gioiTinhTextView = view.findViewById(R.id.gioiTinhTextView);
        ngheNghiepTextView = view.findViewById(R.id.ngheNghiepTextView);
        soVeDaDatTextView = view.findViewById(R.id.soVeDaDatTextView);
        soVeDaHuyTextView = view.findViewById(R.id.soVeDaHuyTextView);
        cheDoUuDaiTextView = view.findViewById(R.id.cheDoUuDaiTextView);
        tiLeGiamGiaTextView = view.findViewById(R.id.tiLeGiamGiaTextView);
        avatarImageView = view.findViewById(R.id.avatarImageView);

        editUserInformationButton = view.findViewById(R.id.editUserInformationButton);
        logOutButton = view.findViewById(R.id.logOutButton);

        editUserInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tempIntent = new Intent(getContext(), EditingUserInformationActivity.class);
                startActivityForResult(tempIntent, EDITING_USER_INFORMATION_REQUEST_CODE);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log out to the login activity
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result of EDITING_USER_INFORMATION_REQUEST_CODE
        if (requestCode == EDITING_USER_INFORMATION_REQUEST_CODE && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);

            // Get the newest one
            referenceKhachHang = KhachHangController.getInstance().getKhachHangByMaKhachHang(SharedVariables.currentMaKhachHang);
            referenceUuDai = UuDaiController.getInstance().getUuDaiByMaUuDai(referenceKhachHang.maUuDai);
            assignDataToViews(referenceKhachHang, referenceUuDai);
        }
    }

    private void assignDataToViews(KhachHang khachHang, UuDai uuDai) {
        tenTextView.setText(khachHang.tenKhachHang);
        emailTextView.setText("(" + khachHang.email + ")");
        diaChiTextView.setText(khachHang.diaChi);

        tuoiTextView.setText(khachHang.tuoi);

        if (khachHang.gioiTinh.equalsIgnoreCase("nam"))
            gioiTinhTextView.setText("Nam");
        else if (khachHang.gioiTinh.equalsIgnoreCase("nu"))
            gioiTinhTextView.setText("Nữ");
        else if (khachHang.gioiTinh.equalsIgnoreCase("kxd"))
            gioiTinhTextView.setText("Không xác định");
        else
            gioiTinhTextView.setText(khachHang.gioiTinh);

        ngheNghiepTextView.setText(khachHang.ngheNghiep);
        soVeDaDatTextView.setText(getSoVeDaDat());
        soVeDaHuyTextView.setText(getSoVeDaHuy());
        cheDoUuDaiTextView.setText(getMoTaHangKhachHang(khachHang.maUuDai));
        tiLeGiamGiaTextView.setText(uuDai.tiLeGiamGia + "%");

        // Avatar display
        if (khachHang.anhNhanDien != null) {
            // Update avatar picture
            avatarImageView.setImageBitmap(ConvertImageData.bytesToBitmap(khachHang.anhNhanDien));
        }
    }

    private String getMoTaHangKhachHang(int maUuDai) {
        if (maUuDai == 1)
            return UuDaiData.MO_TA[0];
        else if (maUuDai == 2)
            return UuDaiData.MO_TA[1];
        else if (maUuDai == 3)
            return UuDaiData.MO_TA[2];
        else return UuDaiData.MO_TA[3];
    }

    private String getSoVeDaDat() {
        ArrayList<KhachHangVe> listKhachHangVeDaDat = KhachHangVeController.getInstance().
                getListKhachHangVeByMaKhachHang(SharedVariables.currentMaKhachHang, 1, false);

        return String.valueOf(listKhachHangVeDaDat.size());
    }

    private String getSoVeDaHuy() {
        ArrayList<KhachHangVe> listKhachHangVeDaHuy = KhachHangVeController.getInstance().
                getListKhachHangVeByMaKhachHang(SharedVariables.currentMaKhachHang, 0, false);

        return String.valueOf(listKhachHangVeDaHuy.size());
    }

}