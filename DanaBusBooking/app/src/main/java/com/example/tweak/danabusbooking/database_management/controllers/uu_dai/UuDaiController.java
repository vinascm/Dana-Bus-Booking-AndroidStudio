package com.example.tweak.danabusbooking.database_management.controllers.uu_dai;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.helpers.CheckingTableExists;

import java.util.ArrayList;

public class UuDaiController {

    private static UuDaiController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<UuDai> listUuDai;

    private UuDaiController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listUuDai = new ArrayList<>();
    }

    public static UuDaiController getInstance() {
        if (sInstance == null) {
            sInstance = new UuDaiController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // if we run the app for the very first time, we need to insert the default values like this
        if (!CheckingTableExists.doesTableExists(m_SQLiteHelper.getReadableDatabase(), UuDai.UU_DAI_TABLE_NAME, UuDai.COLUMNS_NAMES))
            InitializeDataForTheFirstTime();

        // Get data
        getData();
    }

    private void InitializeDataForTheFirstTime() {
        // Synthesize data
        ArrayList<UuDai> tempList = new ArrayList<>();
        for (int i = 0; i < UuDaiData.MA_UU_DAI.length; i++) {
            UuDai u = new UuDai(UuDaiData.MA_UU_DAI[i], UuDaiData.MO_TA[i], UuDaiData.TI_LE_GIAM_GIA[i]);
            tempList.add(u);
        }

        // Insert to database
        for (UuDai u : tempList)
            insertData(u);
    }

    public void insertData(UuDai uuDai) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UuDai.MA_UU_DAI, uuDai.maUuDai);
        values.put(UuDai.MO_TA, uuDai.moTa);
        values.put(UuDai.TI_LE_GIAM_GIA, uuDai.tiLeGiamGia);

        writableDatabase.insert(UuDai.UU_DAI_TABLE_NAME, null, values);

        // Add new data to the list
        listUuDai.add(uuDai);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void updateData(UuDai uuDai) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UuDai.MO_TA, uuDai.moTa);
        values.put(UuDai.TI_LE_GIAM_GIA, uuDai.tiLeGiamGia);

        writableDatabase.update(UuDai.UU_DAI_TABLE_NAME, values,
                UuDai.MA_UU_DAI + " = ?",
                new String[]{String.valueOf(uuDai.maUuDai)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public UuDai getUuDaiByMaUuDai(int maUuDai) {
        for (UuDai uuDai : listUuDai)
            if (uuDai.maUuDai == maUuDai)
                return uuDai;

        return null;
    }

    private void getData() {
        // Clear the list
        listUuDai.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(UuDai.UU_DAI_TABLE_NAME,
                UuDai.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maUuDai = cursor.getInt(cursor.getColumnIndex(UuDai.MA_UU_DAI));
                String moTa = cursor.getString(cursor.getColumnIndex(UuDai.MO_TA));
                int tiLeGiamGia = cursor.getInt(cursor.getColumnIndex(UuDai.TI_LE_GIAM_GIA));

                UuDai u = new UuDai(maUuDai, moTa, tiLeGiamGia);
                listUuDai.add(u);

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
        writableDatabase.delete(UuDai.UU_DAI_TABLE_NAME,
                UuDai.MA_UU_DAI + " = " + id,
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
        writableDatabase.delete(UuDai.UU_DAI_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    // Method for checking whether an ID in a table exists
    public boolean doesThisIDExist(int id) {
        for (int i = 0; i < listUuDai.size(); i++)
            if (listUuDai.get(i).maUuDai == id)
                return true;

        return false;
    }

}
