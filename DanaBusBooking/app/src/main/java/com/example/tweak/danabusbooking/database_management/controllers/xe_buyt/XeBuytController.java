package com.example.tweak.danabusbooking.database_management.controllers.xe_buyt;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuongController;
import com.example.tweak.danabusbooking.database_management.helpers.CheckingTableExists;

import java.util.ArrayList;

public class XeBuytController {

    private static XeBuytController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<XeBuyt> listXeBuyt;

    private XeBuytController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listXeBuyt = new ArrayList<>();
    }

    public static XeBuytController getInstance() {
        if (sInstance == null) {
            sInstance = new XeBuytController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // if we run the app for the very first time, we need to insert the default values like this
        if (!CheckingTableExists.doesTableExists(m_SQLiteHelper.getReadableDatabase(), XeBuyt.XE_BUYT_TABLE_NAME, XeBuyt.COLUMNS_NAMES))
            InitializeDataForTheFirstTime();

        // Get data
        getData();
    }

    private void InitializeDataForTheFirstTime() {
        // Synthesize data
        ArrayList<XeBuyt> tempList = new ArrayList<>();
        for (int i = 0; i < XeBuytData.MA_XE_BUYT.length; i++) {
            XeBuyt x = new XeBuyt(XeBuytData.MA_XE_BUYT[i], XeBuytData.SO_HIEU_XE[i]
                    , XeBuytData.BIEN_SO_XE[i], XeBuytData.MA_TUYEN_DUONG[i]);
            tempList.add(x);
        }

        // Insert to database
        for (XeBuyt x : tempList)
            insertData(x);
    }

    public void insertData(XeBuyt xeBuyt) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(XeBuyt.MA_XE_BUYT, xeBuyt.maXeBuyt);
        values.put(XeBuyt.SO_HIEU_XE, xeBuyt.soHieuXe);
        values.put(XeBuyt.BIEN_SO_XE, xeBuyt.bienSoXe);
        values.put(XeBuyt.MA_TUYEN_DUONG, xeBuyt.maTuyenDuong);

        writableDatabase.insert(XeBuyt.XE_BUYT_TABLE_NAME, null, values);

        // Add new data to the list
        listXeBuyt.add(xeBuyt);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void updateData(XeBuyt xeBuyt) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(XeBuyt.SO_HIEU_XE, xeBuyt.soHieuXe);
        values.put(XeBuyt.BIEN_SO_XE, xeBuyt.bienSoXe);
        values.put(XeBuyt.MA_TUYEN_DUONG, xeBuyt.maTuyenDuong);

        writableDatabase.update(XeBuyt.XE_BUYT_TABLE_NAME, values,
                XeBuyt.MA_XE_BUYT + " = ?",
                new String[]{String.valueOf(xeBuyt.maXeBuyt)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    private void getData() {
        // Clear the list
        listXeBuyt.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(XeBuyt.XE_BUYT_TABLE_NAME,
                XeBuyt.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maXeBuyt = cursor.getInt(cursor.getColumnIndex(XeBuyt.MA_XE_BUYT));
                String soHieuXe = cursor.getString(cursor.getColumnIndex(XeBuyt.SO_HIEU_XE));
                String bienSoXe = cursor.getString(cursor.getColumnIndex(XeBuyt.BIEN_SO_XE));
                int maTuyenDuong = cursor.getInt(cursor.getColumnIndex(XeBuyt.MA_TUYEN_DUONG));

                XeBuyt x = new XeBuyt(maXeBuyt, soHieuXe, bienSoXe, maTuyenDuong);
                listXeBuyt.add(x);

                cursor.moveToNext();
            }

            // Close all the connect to DB to free memory
            cursor.close();
            readableDatabase.close();
            m_SQLiteHelper.close();
        }
    }

    public ArrayList<XeBuyt> getListXeBuytByMaTuyenDuong(int maTuyenDuong) {
        ArrayList<XeBuyt> returnList = new ArrayList<>();

        for (int i = 0; i < listXeBuyt.size(); i++)
            if (listXeBuyt.get(i).maTuyenDuong == maTuyenDuong)
                returnList.add(listXeBuyt.get(i));

        return returnList;
    }

    public ArrayList<XeBuyt> getListXeBuytShowOnListView(ArrayList<Integer> listMaXeBuyt) {
        ArrayList<XeBuyt> returnList = new ArrayList<>();

        for (int i = 0; i < listMaXeBuyt.size(); i++)
            for (int j = 0; j < listXeBuyt.size(); j++)
                if (listXeBuyt.get(j).maXeBuyt == listMaXeBuyt.get(i)) {
                    returnList.add(listXeBuyt.get(j));
                    break;
                }

        return returnList;
    }

    public XeBuyt getXeBuytByMaXeBuyt(int maXeBuyt) {
        for (int i = 0; i < listXeBuyt.size(); i++)
            if (listXeBuyt.get(i).maXeBuyt == maXeBuyt)
                return listXeBuyt.get(i);

        return null;
    }

    public void deleteData(int id) {
        if (!doesThisIDExist(id))
            return;

        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        // Delete a specified row with the corresponding id
        writableDatabase.delete(XeBuyt.XE_BUYT_TABLE_NAME,
                XeBuyt.MA_XE_BUYT + " = " + id,
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
        writableDatabase.delete(XeBuyt.XE_BUYT_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    // Method for checking whether an ID in a table exists
    public boolean doesThisIDExist(int id) {
        for (int i = 0; i < listXeBuyt.size(); i++)
            if (listXeBuyt.get(i).maXeBuyt == id)
                return true;

        return false;
    }

}
