package com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.helpers.CheckingTableExists;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuytData;

import java.util.ArrayList;

public class GheNgoiController {

    private static GheNgoiController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<GheNgoi> listGheNgoi;

    private GheNgoiController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listGheNgoi = new ArrayList<>();
    }

    public static GheNgoiController getInstance() {
        if (sInstance == null) {
            sInstance = new GheNgoiController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // if we run the app for the very first time, we need to insert the default values like this
        if (!CheckingTableExists.doesTableExists(m_SQLiteHelper.getReadableDatabase(), GheNgoi.GHE_NGOI_TABLE_NAME, GheNgoi.COLUMNS_NAMES))
            InitializeDataForTheFirstTime();

        // Get data
        getData();
    }

    private void InitializeDataForTheFirstTime() {
        // Synthesize data

        // 1 xeBuyt == 20 gheNgoi
        ArrayList<GheNgoi> tempList = new ArrayList<>();
        for (int j = 0; j < XeBuytData.MA_XE_BUYT.length; j++) {
            for (int i = 0; i < GheNgoiData.MA_GHE_NGOI.length; i++) {
                GheNgoi g = new GheNgoi(
                        GheNgoiData.MA_GHE_NGOI[i] + (j * GheNgoiData.MA_GHE_NGOI.length),
                        GheNgoiData.SO_HIEU_GHE[i],
                        GheNgoiData.VI_TRI_VAT_LY[i],
                        0,
                        XeBuytData.MA_XE_BUYT[j]);
                tempList.add(g);
            }
        }


        // Insert to database
        for (GheNgoi g : tempList)
            insertData(g);
    }

    public void insertData(GheNgoi gheNgoi) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(GheNgoi.MA_GHE_NGOI, gheNgoi.maGheNgoi);
        values.put(GheNgoi.SO_HIEU_GHE, gheNgoi.soHieuGhe);
        values.put(GheNgoi.VI_TRI_VAT_LY, gheNgoi.viTriVatLy);
        values.put(GheNgoi.TINH_TRANG, gheNgoi.tinhTrang);
        values.put(GheNgoi.MA_XE_BUYT, gheNgoi.maXeBuyt);

        writableDatabase.insert(GheNgoi.GHE_NGOI_TABLE_NAME, null, values);

        // Add new data to the list
        listGheNgoi.add(gheNgoi);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void updateData(GheNgoi gheNgoi) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(GheNgoi.SO_HIEU_GHE, gheNgoi.soHieuGhe);
        values.put(GheNgoi.VI_TRI_VAT_LY, gheNgoi.viTriVatLy);
        values.put(GheNgoi.TINH_TRANG, gheNgoi.tinhTrang);
        values.put(GheNgoi.MA_XE_BUYT, gheNgoi.maXeBuyt);

        writableDatabase.update(GheNgoi.GHE_NGOI_TABLE_NAME, values,
                GheNgoi.MA_GHE_NGOI + " = ?",
                new String[]{String.valueOf(gheNgoi.maGheNgoi)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    private void getData() {
        // Clear the list
        listGheNgoi.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(GheNgoi.GHE_NGOI_TABLE_NAME,
                GheNgoi.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maGheNgoi = cursor.getInt(cursor.getColumnIndex(GheNgoi.MA_GHE_NGOI));
                String soHieuGhe = cursor.getString(cursor.getColumnIndex(GheNgoi.SO_HIEU_GHE));
                String viTriVatLy = cursor.getString(cursor.getColumnIndex(GheNgoi.VI_TRI_VAT_LY));
                int tinhTrang = cursor.getInt(cursor.getColumnIndex(GheNgoi.TINH_TRANG));
                int maXeBuyt = cursor.getInt(cursor.getColumnIndex(GheNgoi.MA_XE_BUYT));

                GheNgoi g = new GheNgoi(maGheNgoi, soHieuGhe, viTriVatLy, tinhTrang, maXeBuyt);
                listGheNgoi.add(g);

                cursor.moveToNext();
            }

            // Close all the connect to DB to free memory
            cursor.close();
            readableDatabase.close();
            m_SQLiteHelper.close();
        }
    }

    public ArrayList<GheNgoi> getTheListOfGheNgoiTrongShowOnScreen(int maXeBuyt) {
        ArrayList<GheNgoi> returnList = new ArrayList<>();
        for (int i = 0; i < listGheNgoi.size(); i++)
            if (listGheNgoi.get(i).maXeBuyt == maXeBuyt && listGheNgoi.get(i).tinhTrang == 0)
                returnList.add(listGheNgoi.get(i));

        return returnList;
    }

    public GheNgoi getGheNgoiByMaGheNgoi(int maGheNgoi) {
        for (int i = 0; i < listGheNgoi.size(); i++)
            if (listGheNgoi.get(i).maGheNgoi == maGheNgoi)
                return listGheNgoi.get(i);

        return null;
    }

    public int getMaXeBuytByMaGheNgoi(int maGheNgoi) {
        for (int i = 0; i < listGheNgoi.size(); i++)
            if (listGheNgoi.get(i).maGheNgoi == maGheNgoi)
                return listGheNgoi.get(i).maXeBuyt;

        return -1;
    }

    public void deleteData(int id) {
        if (!doesThisIDExist(id))
            return;

        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        // Delete a specified row with the corresponding id
        writableDatabase.delete(GheNgoi.GHE_NGOI_TABLE_NAME,
                GheNgoi.MA_GHE_NGOI + " = " + id,
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
        writableDatabase.delete(GheNgoi.GHE_NGOI_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    // Method for checking whether an ID in a table exists
    public boolean doesThisIDExist(int id) {
        for (int i = 0; i < listGheNgoi.size(); i++)
            if (listGheNgoi.get(i).maGheNgoi == id)
                return true;

        return false;
    }

}
