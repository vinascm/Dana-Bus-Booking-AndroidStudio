package com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHang;

import java.util.ArrayList;

public class KhachHangVeController {

    private static KhachHangVeController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<KhachHangVe> listKhachHangVe;

    private KhachHangVeController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listKhachHangVe = new ArrayList<>();
    }

    public static KhachHangVeController getInstance() {
        if (sInstance == null) {
            sInstance = new KhachHangVeController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // Get data
        getData();
    }

    public void insertData(KhachHangVe khachHangVe) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KhachHangVe.MA_VE, khachHangVe.maVe);
        values.put(KhachHangVe.MA_KHACH_HANG, khachHangVe.maKhachHang);
        values.put(KhachHangVe.MA_GHE_NGOI, khachHangVe.maGheNgoi);
        values.put(KhachHangVe.GIA_TIEN_VE, khachHangVe.giaTienVe);
        values.put(KhachHangVe.TINH_TRANG, khachHangVe.tinhTrang);
        values.put(KhachHangVe.NGAY_DAT, khachHangVe.ngayDat);
        values.put(KhachHangVe.NGAY_HUY, khachHangVe.ngayHuy);

        writableDatabase.insert(KhachHangVe.KHACH_HANG_VE_TABLE_NAME, null, values);

        // Add new data to the list
        listKhachHangVe.add(khachHangVe);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void updateData(KhachHangVe khachHangVe) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KhachHangVe.MA_GHE_NGOI, khachHangVe.maGheNgoi);
        values.put(KhachHangVe.GIA_TIEN_VE, khachHangVe.giaTienVe);
        values.put(KhachHangVe.TINH_TRANG, khachHangVe.tinhTrang);
        values.put(KhachHangVe.NGAY_DAT, khachHangVe.ngayDat);
        values.put(KhachHangVe.NGAY_HUY, khachHangVe.ngayHuy);

        writableDatabase.update(KhachHangVe.KHACH_HANG_VE_TABLE_NAME, values,
                KhachHangVe.MA_VE + " = ? and " + KhachHang.MA_KHACH_HANG + " = ?",
                new String[]{String.valueOf(khachHangVe.maVe), String.valueOf(khachHangVe.maKhachHang)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public ArrayList<KhachHangVe> getListKhachHangVeByMaKhachHang(int maKhachHang, int tinhTrang, boolean getAll) {
        ArrayList<KhachHangVe> returnList = new ArrayList<>();

        for (int i = listKhachHangVe.size() - 1; i >= 0; i--)
            if (listKhachHangVe.get(i).maKhachHang == maKhachHang && getAll)
                returnList.add(listKhachHangVe.get(i));
            else if (listKhachHangVe.get(i).maKhachHang == maKhachHang && listKhachHangVe.get(i).tinhTrang == tinhTrang)
                returnList.add(listKhachHangVe.get(i));

        return returnList;
    }

    public KhachHangVe getAKhachHangVeByMaVeAndMaKhachHang(int maVe, int maKhachHang) {
        for (int i = 0; i < listKhachHangVe.size(); i++)
            if (listKhachHangVe.get(i).maVe == maVe && listKhachHangVe.get(i).maKhachHang == maKhachHang)
                return listKhachHangVe.get(i);

        return null;
    }

    private void getData() {
        // Clear the list
        listKhachHangVe.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(KhachHangVe.KHACH_HANG_VE_TABLE_NAME,
                KhachHangVe.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maVe = cursor.getInt(cursor.getColumnIndex(KhachHangVe.MA_VE));
                int maKhachHang = cursor.getInt(cursor.getColumnIndex(KhachHangVe.MA_KHACH_HANG));
                int maGheNgoi = cursor.getInt(cursor.getColumnIndex(KhachHangVe.MA_GHE_NGOI));
                float giaTienVe = cursor.getFloat(cursor.getColumnIndex(KhachHangVe.GIA_TIEN_VE));
                int tinhTrang = cursor.getInt(cursor.getColumnIndex(KhachHangVe.TINH_TRANG));
                String ngayDat = cursor.getString(cursor.getColumnIndex(KhachHangVe.NGAY_DAT));
                String ngayHuy = cursor.getString(cursor.getColumnIndex(KhachHangVe.NGAY_HUY));

                KhachHangVe kv = new KhachHangVe(maVe, maKhachHang, maGheNgoi, giaTienVe, tinhTrang, ngayDat, ngayHuy);
                listKhachHangVe.add(kv);

                cursor.moveToNext();
            }

            // Close all the connect to DB to free memory
            cursor.close();
            readableDatabase.close();
            m_SQLiteHelper.close();
        }
    }

    public void deleteData(int id) {
        if (!doesThisIDExist(id))
            return;

        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        // Delete a specified row with the corresponding id
        writableDatabase.delete(KhachHangVe.KHACH_HANG_VE_TABLE_NAME,
                KhachHangVe.MA_VE + " = " + id,
                null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void deleteAllData() {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        // Delete all the rows in the table
        writableDatabase.delete(KhachHangVe.KHACH_HANG_VE_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    // Method for checking whether an ID in a table exists
    public boolean doesThisIDExist(int id) {
        for (int i = 0; i < listKhachHangVe.size(); i++)
            if (listKhachHangVe.get(i).maVe == id)
                return true;

        return false;
    }

}
