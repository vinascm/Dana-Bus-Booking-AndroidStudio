package com.example.tweak.danabusbooking.database_management.controllers.ve;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.helpers.CheckingTableExists;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VeController {

    private static VeController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<Ve> listVe;

    private VeController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listVe = new ArrayList<>();
    }

    public static VeController getInstance() {
        if (sInstance == null) {
            sInstance = new VeController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // if we run the app for the very first time, we need to insert the default values like this
        if (!CheckingTableExists.doesTableExists(m_SQLiteHelper.getReadableDatabase(), Ve.VE_TABLE_NAME, Ve.COLUMNS_NAMES))
            InitializeDataForTheFirstTime();

        // Get data
        getData();
    }

    private void InitializeDataForTheFirstTime() {
        // Synthesize data

        // The expired day is calculated based on the day it comes out fast forward 30 days later
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.add(Calendar.DATE, 30);

        String ngaySanXuat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()).toString();
        String ngayHetHan = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(newCalendar.getTime()).toString();

        // Each route, we produce like 100 tickets initially
        ArrayList<Ve> tempList = new ArrayList<>();
        for (int j = 0; j < TuyenDuongData.MA_TUYEN_DUONG.length; j++) {
            for (int i = 0; i < VeData.MA_VE.length; i++) {
                Ve v = new Ve(
                        VeData.MA_VE[i] + (j * VeData.MA_VE.length),
                        ngaySanXuat
                        , ngayHetHan,
                        TuyenDuongData.MA_TUYEN_DUONG[j],
                        0);
                tempList.add(v);
            }
        }

        // Insert to database
        for (Ve v : tempList)
            insertData(v);
    }

    public Ve getVeByMaVe(int maVe) {
        for (int i = 0; i < listVe.size(); i++)
            if (listVe.get(i).maVe == maVe)
                return listVe.get(i);

        return null;
    }

    public void insertData(Ve ve) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Ve.MA_VE, ve.maVe);
        values.put(Ve.NGAY_SAN_XUAT, ve.ngaySanXuat);
        values.put(Ve.NGAY_HET_HAN, ve.ngayHetHan);
        values.put(Ve.MA_TUYEN_DUONG, ve.maTuyenDuong);
        values.put(Ve.TINH_TRANG, ve.tinhTrang);

        writableDatabase.insert(Ve.VE_TABLE_NAME, null, values);

        // Add new data to the list
        listVe.add(ve);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void updateData(Ve ve) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Ve.NGAY_SAN_XUAT, ve.ngaySanXuat);
        values.put(Ve.NGAY_HET_HAN, ve.ngayHetHan);
        values.put(Ve.MA_TUYEN_DUONG, ve.maTuyenDuong);
        values.put(Ve.TINH_TRANG, ve.tinhTrang);

        writableDatabase.update(Ve.VE_TABLE_NAME, values,
                Ve.MA_VE + " = ?",
                new String[]{String.valueOf(ve.maVe)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public Ve getAnAvailableTicketByMaTuyenDuong(int maTuyenDuong) {
        for (int i = 0; i < listVe.size(); i++)
            if (listVe.get(i).tinhTrang == 0 && listVe.get(i).maTuyenDuong == maTuyenDuong)
                return listVe.get(i);

        return null;
    }

    public ArrayList<Ve> getListVeShowOnListView(ArrayList<Integer> listMaVe) {
        ArrayList<Ve> returnList = new ArrayList<>();

        for (int i = 0; i < listMaVe.size(); i++)
            for (int j = 0; j < listVe.size(); j++)
                if (listVe.get(j).maVe == listMaVe.get(i)) {
                    returnList.add(listVe.get(j));
                    break;
                }

        return returnList;
    }

    private void getData() {
        // Clear the list
        listVe.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(Ve.VE_TABLE_NAME,
                Ve.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maVe = cursor.getInt(cursor.getColumnIndex(Ve.MA_VE));
                String ngaySanXuat = cursor.getString(cursor.getColumnIndex(Ve.NGAY_SAN_XUAT));
                String ngayHetHan = cursor.getString(cursor.getColumnIndex(Ve.NGAY_HET_HAN));
                int maTuyenDuong = cursor.getInt(cursor.getColumnIndex(Ve.MA_TUYEN_DUONG));
                int tinhTrang = cursor.getInt(cursor.getColumnIndex(Ve.TINH_TRANG));

                Ve v = new Ve(maVe, ngaySanXuat, ngayHetHan, maTuyenDuong, tinhTrang);
                listVe.add(v);

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
        writableDatabase.delete(Ve.VE_TABLE_NAME,
                Ve.MA_VE + " = " + id,
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
        writableDatabase.delete(Ve.VE_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    // Method for checking whether an ID in a table exists
    public boolean doesThisIDExist(int id) {
        for (int i = 0; i < listVe.size(); i++)
            if (listVe.get(i).maVe == id)
                return true;

        return false;
    }

}
