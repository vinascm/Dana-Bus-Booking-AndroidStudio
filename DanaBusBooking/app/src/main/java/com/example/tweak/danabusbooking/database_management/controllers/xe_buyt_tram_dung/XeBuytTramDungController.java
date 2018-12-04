package com.example.tweak.danabusbooking.database_management.controllers.xe_buyt_tram_dung;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.controllers.tram_dung.TramDung;
import com.example.tweak.danabusbooking.database_management.helpers.CheckingTableExists;

import java.util.ArrayList;

public class XeBuytTramDungController {

    private static XeBuytTramDungController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<XeBuytTramDung> listXeBuytTramDung;

    private XeBuytTramDungController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listXeBuytTramDung = new ArrayList<>();
    }

    public static XeBuytTramDungController getInstance() {
        if (sInstance == null) {
            sInstance = new XeBuytTramDungController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // if we run the app for the very first time, we need to insert the default values like this
        if (!CheckingTableExists.doesTableExists(m_SQLiteHelper.getReadableDatabase(), XeBuytTramDung.XE_BUYT_TRAM_DUNG_TABLE_NAME, XeBuytTramDung.COLUMNS_NAMES))
            InitializeDataForTheFirstTime();

        // Get data
        getData();
    }

    private void InitializeDataForTheFirstTime() {
        // Synthesize data
        ArrayList<XeBuytTramDung> tempList = new ArrayList<>();
        for (int i = 0; i < XeBuytTramDungData.MA_XE_BUYT.length; i++) {
            XeBuytTramDung xt = new XeBuytTramDung(XeBuytTramDungData.MA_XE_BUYT[i],
                    XeBuytTramDungData.MA_TRAM_DUNG[i],
                    "");
            tempList.add(xt);
        }

        // Insert to database
        for (XeBuytTramDung xt : tempList)
            insertData(xt);
    }

    public ArrayList<Integer> getTheListOfMaTramDungByMaXeBuyt(int maXeBuyt) {
        ArrayList<Integer> returnList = new ArrayList<>();
        for (int i = 0; i < listXeBuytTramDung.size(); i++)
            if (listXeBuytTramDung.get(i).maXeBuyt == maXeBuyt)
                returnList.add(listXeBuytTramDung.get(i).maTramDung);

        return returnList;
    }

    public void insertData(XeBuytTramDung xeBuytTramDung) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(XeBuytTramDung.MA_XE_BUYT, xeBuytTramDung.maXeBuyt);
        values.put(XeBuytTramDung.MA_TRAM_DUNG, xeBuytTramDung.maTramDung);
        values.put(XeBuytTramDung.MO_TA, xeBuytTramDung.moTa);

        writableDatabase.insert(XeBuytTramDung.XE_BUYT_TRAM_DUNG_TABLE_NAME, null, values);

        // Add new data to the list
        listXeBuytTramDung.add(xeBuytTramDung);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void updateData(XeBuytTramDung xeBuytTramDungCu, XeBuytTramDung xeBuytTramDungMoi) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(XeBuytTramDung.MA_XE_BUYT, xeBuytTramDungMoi.maXeBuyt);
        values.put(XeBuytTramDung.MA_TRAM_DUNG, xeBuytTramDungMoi.maTramDung);
        values.put(XeBuytTramDung.MO_TA, xeBuytTramDungMoi.moTa);

        writableDatabase.update(XeBuytTramDung.XE_BUYT_TRAM_DUNG_TABLE_NAME, values,
                XeBuytTramDung.MA_XE_BUYT + " = ? and " +
                        XeBuytTramDung.MA_TRAM_DUNG + " = ?",
                new String[]{String.valueOf(xeBuytTramDungCu.maXeBuyt),
                        String.valueOf(xeBuytTramDungCu.maTramDung)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    private void getData() {
        // Clear the list
        listXeBuytTramDung.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(XeBuytTramDung.XE_BUYT_TRAM_DUNG_TABLE_NAME,
                XeBuytTramDung.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maXeBuyt = cursor.getInt(cursor.getColumnIndex(XeBuytTramDung.MA_XE_BUYT));
                int maTramDung = cursor.getInt(cursor.getColumnIndex(XeBuytTramDung.MA_TRAM_DUNG));
                String moTa = cursor.getString(cursor.getColumnIndex(XeBuytTramDung.MO_TA));

                XeBuytTramDung xt = new XeBuytTramDung(maXeBuyt, maTramDung, moTa);
                listXeBuytTramDung.add(xt);

                cursor.moveToNext();
            }

            // Close all the connect to DB to free memory
            cursor.close();
            readableDatabase.close();
            m_SQLiteHelper.close();
        }
    }

    public void deleteData(XeBuytTramDung xeBuytTramDung) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        // Delete a specified row with the corresponding id
        writableDatabase.delete(XeBuytTramDung.XE_BUYT_TRAM_DUNG_TABLE_NAME,
                XeBuytTramDung.MA_XE_BUYT + " = " + xeBuytTramDung.maXeBuyt + " and " +
                        XeBuytTramDung.MA_TRAM_DUNG + " = " + xeBuytTramDung.maTramDung,
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
        writableDatabase.delete(XeBuytTramDung.XE_BUYT_TRAM_DUNG_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

}
