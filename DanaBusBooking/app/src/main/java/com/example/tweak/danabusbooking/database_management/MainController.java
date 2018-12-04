package com.example.tweak.danabusbooking.database_management;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoi;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHang;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.database_management.controllers.uu_dai.UuDai;
import com.example.tweak.danabusbooking.database_management.controllers.ve.Ve;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;

import java.util.ArrayList;

public class MainController {

    private static MainController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    private MainController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();
    }

    public static MainController getInstance() {
        if (sInstance == null) {
            sInstance = new MainController();
        }
        return sInstance;
    }

    // select count(*) from xebuyt x join ghengoi g on x.MaXeBuyt = g.maXebuyt
    // where x.MaXeBuyt = 1
    // and g.TinhTrang = 0
    public boolean doesThisBusHasAnAvailableSeat(XeBuyt xeBuyt) {
        String query = "select count(*) from " + XeBuyt.XE_BUYT_TABLE_NAME + " x "
                + " join " + GheNgoi.GHE_NGOI_TABLE_NAME + " g "
                + " on x." + XeBuyt.MA_XE_BUYT + " = g." + GheNgoi.MA_XE_BUYT
                + " where g." + GheNgoi.TINH_TRANG + " = 0 and x." + XeBuyt.MA_XE_BUYT + " = " + xeBuyt.maXeBuyt;

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getInt(0) > 0)
                return true;
        }

        return false;
    }

    //  select x.MaTuyenDuong from gheNgoi g join XEBUYT x on g.MaXeBuyt = x.MaXeBuyt where g.MaGheNgoi = ?
    public int getMaTuyenDuongByMaGheNgoi(int maGheNgoi) {
        String query = "select x." + TuyenDuong.MA_TUYEN_DUONG + " from " + GheNgoi.GHE_NGOI_TABLE_NAME + " g "
                + " join " + XeBuyt.XE_BUYT_TABLE_NAME + " x "
                + " on g." + GheNgoi.MA_XE_BUYT + " = x." + XeBuyt.MA_XE_BUYT
                + " where g." + GheNgoi.MA_GHE_NGOI + " = " + maGheNgoi;

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery(query, null);

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    // select u.tiLeGiamGia from khachhang k join uudai u on k.MaUuDai = u.MaUuDai where k.maKhachHang = 1;
    public float getTheTiLeGiamGiaByKhachHang(int maKhachHang) {
        String query = "select u." + UuDai.TI_LE_GIAM_GIA + " from " + KhachHang.KHACH_HANG_TABLE_NAME + " k "
                + " join " + UuDai.UU_DAI_TABLE_NAME + " u "
                + " on k." + KhachHang.MA_UU_DAI + " = u." + UuDai.MA_UU_DAI
                + " where k." + KhachHang.MA_KHACH_HANG + " = " + maKhachHang;

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery(query, null);

        cursor.moveToFirst();
        return cursor.getFloat(0);
    }

    // select t.giaVe from ve v join tuyenDuong t
    // on v.MaTuyenDuong = t.MaTuyenDuong
    // where v.MaTuyenDuong = 1 and v.MaVe = 1
    public float getTheGiaTienVeGoc(int maTuyenDuong, int maVe) {
        String query = "select t." + TuyenDuong.GIA_VE + " from " + Ve.VE_TABLE_NAME + " v "
                + " join " + TuyenDuong.TUYEN_DUONG_TABLE_NAME + " t "
                + " on v." + Ve.MA_TUYEN_DUONG + " = t." + TuyenDuong.MA_TUYEN_DUONG
                + " where v." + Ve.MA_TUYEN_DUONG + " = " + maTuyenDuong
                + " and v." + Ve.MA_VE + " = " + maVe;

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery(query, null);

        cursor.moveToFirst();
        return cursor.getFloat(0);
    }

    // select maTuyenDuong from tuyenDuong
    // where tenTuyenduong like '%hung%' or MaTuyenDuong like '%3%'
    public ArrayList<Integer> getTheListOfMaTuyenDuongByTypingLetter(String letter) {
        ArrayList<Integer> returnList = new ArrayList<>();

        String query = "Select " + TuyenDuong.MA_TUYEN_DUONG + " from "
                + TuyenDuong.TUYEN_DUONG_TABLE_NAME + " where "
                + TuyenDuong.TEN_TUYEN_DUONG + " like '%"
                + letter + "%' or "
                + TuyenDuong.MA_TUYEN_DUONG + " like '%"
                + letter + "%'";

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery(query, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maTuyenDuong = cursor.getInt(0);
                returnList.add(maTuyenDuong);

                cursor.moveToNext();
            }

            // Close all the connect to DB to free memory
            cursor.close();
            readableDatabase.close();
            m_SQLiteHelper.close();
        }

        return returnList;
    }

    // select maXeBuyt from xeBuyt where SoHieuXe like '%a%' or BienSoXe like '%z%' or maXeBuyt like '%3%'
    public ArrayList<Integer> getTheListOfMaXeBuytByTypingLetter(String letter) {
        ArrayList<Integer> returnList = new ArrayList<>();

        String query = "Select " + XeBuyt.MA_XE_BUYT + " from "
                + XeBuyt.XE_BUYT_TABLE_NAME + " where "
                + XeBuyt.SO_HIEU_XE + " like '%"
                + letter + "%' or "
                + XeBuyt.BIEN_SO_XE + " like '%"
                + letter + "%' or "
                + XeBuyt.MA_XE_BUYT + " like '%"
                + letter + "%'";

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery(query, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maXeBuyt = cursor.getInt(0);
                returnList.add(maXeBuyt);

                cursor.moveToNext();
            }

            // Close all the connect to DB to free memory
            cursor.close();
            readableDatabase.close();
            m_SQLiteHelper.close();
        }

        return returnList;
    }

    // Return the list of the only buyts which have available seats
    public ArrayList<XeBuyt> getListXeBuytsWithAvailableSeats(ArrayList<XeBuyt> inputList) {
        ArrayList<XeBuyt> returnList = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++)
            if (doesThisBusHasAnAvailableSeat(inputList.get(i)))
                returnList.add(inputList.get(i));

        return returnList;
    }

}
