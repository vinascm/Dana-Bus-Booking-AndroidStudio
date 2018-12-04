package com.example.tweak.danabusbooking.database_management.controllers.khach_hang;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;

import java.util.ArrayList;

public class KhachHangController {

    private static KhachHangController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<KhachHang> listKhachHang;

    private KhachHangController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listKhachHang = new ArrayList<>();
    }

    public static KhachHangController getInstance() {
        if (sInstance == null) {
            sInstance = new KhachHangController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // Get data
        getData();
    }

    public void insertData(KhachHang khachHang) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KhachHang.TEN_KHACH_HANG, khachHang.tenKhachHang);
        values.put(KhachHang.TUOI, khachHang.tuoi);
        values.put(KhachHang.GIOI_TINH, khachHang.gioiTinh);
        values.put(KhachHang.ANH_NHAN_DIEN, khachHang.anhNhanDien);
        values.put(KhachHang.DIA_CHI, khachHang.diaChi);
        values.put(KhachHang.NGHE_NGHIEP, khachHang.ngheNghiep);
        values.put(KhachHang.EMAIL, khachHang.email);
        values.put(KhachHang.PASSWORD, khachHang.password);
        values.put(KhachHang.MA_UU_DAI, khachHang.maUuDai);

        int maKhachHang = (int)writableDatabase.insert(KhachHang.KHACH_HANG_TABLE_NAME, null, values);

        khachHang.maKhachHang = maKhachHang;

        // Add new data to the list
        listKhachHang.add(khachHang);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public boolean isThisUserInDB(String email, String password) {
        for (KhachHang k : listKhachHang)
            if (k.email.equalsIgnoreCase(email) && k.password.equalsIgnoreCase(password))
                return true;

        return false;
    }

    public int getMaKhachHangByEmail(String email) {
        System.out.println("email fuck: " + email);
        for (KhachHang k : listKhachHang)
            if (k.email.equalsIgnoreCase(email))
                return k.maKhachHang;

        return -1;
    }

    public KhachHang getKhachHangByMaKhachHang(int maKhachHang) {
        for (KhachHang khachHang : listKhachHang)
            if (khachHang.maKhachHang == maKhachHang)
                return khachHang;

        return null;
    }

    public boolean isThisEmailInDB(String email) {
        for (KhachHang k : listKhachHang)
            if (k.email.equalsIgnoreCase(email))
                return true;

        return false;
    }

    public void updateData(KhachHang khachHang) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KhachHang.TEN_KHACH_HANG, khachHang.tenKhachHang);
        values.put(KhachHang.TUOI, khachHang.tuoi);
        values.put(KhachHang.GIOI_TINH, khachHang.gioiTinh);
        values.put(KhachHang.ANH_NHAN_DIEN, khachHang.anhNhanDien);
        values.put(KhachHang.DIA_CHI, khachHang.diaChi);
        values.put(KhachHang.NGHE_NGHIEP, khachHang.ngheNghiep);
        values.put(KhachHang.EMAIL, khachHang.email);
        values.put(KhachHang.PASSWORD, khachHang.password);
        values.put(KhachHang.MA_UU_DAI, khachHang.maUuDai);

        writableDatabase.update(KhachHang.KHACH_HANG_TABLE_NAME, values,
                KhachHang.MA_KHACH_HANG + " = ?",
                new String[]{String.valueOf(khachHang.maKhachHang)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    private void getData() {
        // Clear the list
        listKhachHang.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(KhachHang.KHACH_HANG_TABLE_NAME,
                KhachHang.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maKhachhang = cursor.getInt(cursor.getColumnIndex(KhachHang.MA_KHACH_HANG));
                String tenKhachHang = cursor.getString(cursor.getColumnIndex(KhachHang.TEN_KHACH_HANG));
                String tuoi = cursor.getString(cursor.getColumnIndex(KhachHang.TUOI));
                String gioiTinh = cursor.getString(cursor.getColumnIndex(KhachHang.GIOI_TINH));
                byte[] anhNhanDien = cursor.getBlob(cursor.getColumnIndex(KhachHang.ANH_NHAN_DIEN));
                String diaChi = cursor.getString(cursor.getColumnIndex(KhachHang.DIA_CHI));
                String ngheNghiep = cursor.getString(cursor.getColumnIndex(KhachHang.NGHE_NGHIEP));
                String email = cursor.getString(cursor.getColumnIndex(KhachHang.EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(KhachHang.PASSWORD));
                int maUuDai = cursor.getInt(cursor.getColumnIndex(KhachHang.MA_UU_DAI));

                KhachHang k = new KhachHang(maKhachhang, tenKhachHang, tuoi, gioiTinh, anhNhanDien, diaChi, ngheNghiep, email, password, maUuDai);
                listKhachHang.add(k);

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
        writableDatabase.delete(KhachHang.KHACH_HANG_TABLE_NAME,
                KhachHang.MA_KHACH_HANG + " = " + id,
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
        writableDatabase.delete(KhachHang.KHACH_HANG_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    // Method for checking whether an ID in a table exists
    public boolean doesThisIDExist(int id) {
        for (int i = 0; i < listKhachHang.size(); i++)
            if (listKhachHang.get(i).maKhachHang == id)
                return true;

        return false;
    }

}
