package com.example.tweak.danabusbooking.database_management.controllers.tram_dung;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.helpers.CheckingTableExists;

import java.util.ArrayList;

public class TramDungController {

    private static TramDungController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<TramDung> listTramDung;

    private TramDungController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listTramDung = new ArrayList<>();
    }

    public static TramDungController getInstance() {
        if (sInstance == null) {
            sInstance = new TramDungController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // if we run the app for the very first time, we need to insert the default values like this
        if (!CheckingTableExists.doesTableExists(m_SQLiteHelper.getReadableDatabase(), TramDung.TRAM_DUNG_TABLE_NAME, TramDung.COLUMNS_NAMES))
            InitializeDataForTheFirstTime();

        // Get data
        getData();
    }

    private void InitializeDataForTheFirstTime() {
        // Synthesize data
        ArrayList<TramDung> tempList = new ArrayList<>();
        for (int i = 0; i < TramDungData.MA_TRAM_DUNG.length; i++) {
            TramDung t = new TramDung(TramDungData.MA_TRAM_DUNG[i], TramDungData.TEN_TRAM_DUNG[i],
                    TramDungData.DIA_CHI[i]);
            tempList.add(t);
        }

        // Insert to database
        for (TramDung t : tempList)
            insertData(t);
    }

    public ArrayList<TramDung> getTheListOfTramDungByListMaTramDung(ArrayList<Integer> listMaTramDung) {
        ArrayList<TramDung> returnList = new ArrayList<>();

        for (int i = 0; i < listMaTramDung.size(); i++)
            for (int j = 0; j < listTramDung.size(); j++)
                if (listTramDung.get(j).maTramDung == listMaTramDung.get(i)) {
                    returnList.add(listTramDung.get(j));
                    break;
                }

        return returnList;
    }

    public void insertData(TramDung tramDung) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TramDung.MA_TRAM_DUNG, tramDung.maTramDung);
        values.put(TramDung.TEN_TRAM_DUNG, tramDung.tenTramDung);
        values.put(TramDung.DIA_CHI, tramDung.diaChi);

        writableDatabase.insert(TramDung.TRAM_DUNG_TABLE_NAME, null, values);

        // Add new data to the list
        listTramDung.add(tramDung);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void updateData(TramDung tramDung) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TramDung.TEN_TRAM_DUNG, tramDung.tenTramDung);
        values.put(TramDung.DIA_CHI, tramDung.diaChi);

        writableDatabase.update(TramDung.TRAM_DUNG_TABLE_NAME, values,
                TramDung.MA_TRAM_DUNG + " = ?",
                new String[]{String.valueOf(tramDung.maTramDung)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    private void getData() {
        // Clear the list
        listTramDung.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(TramDung.TRAM_DUNG_TABLE_NAME,
                TramDung.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maTramDung = cursor.getInt(cursor.getColumnIndex(TramDung.MA_TRAM_DUNG));
                String tenTramDung = cursor.getString(cursor.getColumnIndex(TramDung.TEN_TRAM_DUNG));
                String diaChi = cursor.getString(cursor.getColumnIndex(TramDung.DIA_CHI));

                TramDung t = new TramDung(maTramDung, tenTramDung, diaChi);
                listTramDung.add(t);

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
        writableDatabase.delete(TramDung.TRAM_DUNG_TABLE_NAME,
                TramDung.MA_TRAM_DUNG + " = " + id,
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
        writableDatabase.delete(TramDung.TRAM_DUNG_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    // Method for checking whether an ID in a table exists
    public boolean doesThisIDExist(int id) {
        for (int i = 0; i < listTramDung.size(); i++)
            if (listTramDung.get(i).maTramDung == id)
                return true;

        return false;
    }

}
