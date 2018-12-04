package com.example.tweak.danabusbooking.utils;

import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVe;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;

import java.util.ArrayList;

public class SharedVariables {

    public static int lastIndexFunctionPress = 0;
    public static int currentMaKhachHang;
    public static ArrayList<Integer> theListOfMaTuyenDuongThroughActivity;
    public static ArrayList<XeBuyt> theListOfBusesThroughActivity;
    public static KhachHangVe khachHangVeDetail;

    public static String convertIntToString(int inputValue) {
        return String.valueOf(inputValue);
    }

    public static String conVertFloatToString(float inputValue) {
        return String.valueOf(inputValue);
    }
}
