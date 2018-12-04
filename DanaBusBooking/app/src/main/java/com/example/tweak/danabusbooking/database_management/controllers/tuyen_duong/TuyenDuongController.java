package com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tweak.danabusbooking.database_management.SQLiteHelper;
import com.example.tweak.danabusbooking.database_management.helpers.CheckingTableExists;

import java.util.ArrayList;

public class TuyenDuongController {

    private static TuyenDuongController sInstance = null;

    private SQLiteHelper m_SQLiteHelper;

    // Keeping track of all the data rows
    public ArrayList<TuyenDuong> listTuyenDuong;

    private TuyenDuongController() {
        // Initializing the DBHelper for this class
        m_SQLiteHelper = new SQLiteHelper();

        listTuyenDuong = new ArrayList<>();
    }

    public static TuyenDuongController getInstance() {
        if (sInstance == null) {
            sInstance = new TuyenDuongController();
        }
        return sInstance;
    }

    // Getting values in DB when running the app for the first time
    public void getDataForTheFirstTime() {
        // if we run the app for the very first time, we need to insert the default values like this
        if (!CheckingTableExists.doesTableExists(m_SQLiteHelper.getReadableDatabase(), TuyenDuong.TUYEN_DUONG_TABLE_NAME, TuyenDuong.COLUMNS_NAMES))
            InitializeDataForTheFirstTime();

        // Get data
        getData();
    }

    private void InitializeDataForTheFirstTime() {
        // Synthesize data
        ArrayList<TuyenDuong> tempList = new ArrayList<>();
        for (int i = 0; i < TuyenDuongData.MA_TUYEN_DUONG.length; i++) {
            TuyenDuong t = new TuyenDuong(TuyenDuongData.MA_TUYEN_DUONG[i],
                    TuyenDuongData.TEN_TUYEN_DUONG[i],
                    TuyenDuongData.KHOACH_CACH_VAT_LY[i],
                    TuyenDuongData.BEN_DI[i],
                    TuyenDuongData.BEN_DO[i],
                    TuyenDuongData.TAN_SUAT[i],
                    TuyenDuongData.GIA_VE[i],
                    TuyenDuongData.GIO_MO_BEN[i],
                    TuyenDuongData.GIO_DONG_BEN[i]
            );
            tempList.add(t);
        }

        // Insert to database
        for (TuyenDuong t : tempList)
            insertData(t);
    }

    public void insertData(TuyenDuong tuyenDuong) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TuyenDuong.MA_TUYEN_DUONG, tuyenDuong.maTuyenDuong);
        values.put(TuyenDuong.TEN_TUYEN_DUONG, tuyenDuong.tenTuyenDuong);
        values.put(TuyenDuong.KHOACH_CACH_VAT_LY, tuyenDuong.khoangCachVatLy);
        values.put(TuyenDuong.BEN_DI, tuyenDuong.benDi);
        values.put(TuyenDuong.BEN_DO, tuyenDuong.benDo);
        values.put(TuyenDuong.TAN_SUAT, tuyenDuong.tanSuat);
        values.put(TuyenDuong.GIA_VE, tuyenDuong.giaVe);
        values.put(TuyenDuong.GIO_MO_BEN, tuyenDuong.gioMoBen);
        values.put(TuyenDuong.GIO_DONG_BEN, tuyenDuong.gioDongBen);

        writableDatabase.insert(TuyenDuong.TUYEN_DUONG_TABLE_NAME, null, values);

        // Add new data to the list
        listTuyenDuong.add(tuyenDuong);

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public void updateData(TuyenDuong tuyenDuong) {
        SQLiteDatabase writableDatabase = m_SQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TuyenDuong.TEN_TUYEN_DUONG, tuyenDuong.tenTuyenDuong);
        values.put(TuyenDuong.KHOACH_CACH_VAT_LY, tuyenDuong.khoangCachVatLy);
        values.put(TuyenDuong.BEN_DI, tuyenDuong.benDi);
        values.put(TuyenDuong.BEN_DO, tuyenDuong.benDo);
        values.put(TuyenDuong.TAN_SUAT, tuyenDuong.tanSuat);
        values.put(TuyenDuong.GIA_VE, tuyenDuong.giaVe);
        values.put(TuyenDuong.GIO_MO_BEN, tuyenDuong.gioMoBen);
        values.put(TuyenDuong.GIO_DONG_BEN, tuyenDuong.gioDongBen);

        writableDatabase.update(TuyenDuong.TUYEN_DUONG_TABLE_NAME, values,
                TuyenDuong.MA_TUYEN_DUONG + " = ?",
                new String[]{String.valueOf(tuyenDuong.maTuyenDuong)});

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    public ArrayList<TuyenDuong> getListTuyenDuongShowOnListView(ArrayList<Integer> listMaTuyenDuong) {
        ArrayList<TuyenDuong> returnList = new ArrayList<>();

        for (int i = 0; i < listMaTuyenDuong.size(); i++)
            for (int j = 0; j < listTuyenDuong.size(); j++)
                if (listTuyenDuong.get(j).maTuyenDuong == listMaTuyenDuong.get(i)) {
                    returnList.add(listTuyenDuong.get(j));
                    break;
                }

        return returnList;
    }

    public String getTenTuyenDuongByMaTuyenDuong(int maTuyenDuong) {
        for (int i = 0; i < listTuyenDuong.size(); i++)
            if (listTuyenDuong.get(i).maTuyenDuong == maTuyenDuong)
                return listTuyenDuong.get(i).tenTuyenDuong;

        return "";
    }

    public TuyenDuong getTuyenDuongByMaTuyenDuong(int maTuyenDuong) {
        for (int i = 0; i < listTuyenDuong.size(); i++)
            if (listTuyenDuong.get(i).maTuyenDuong == maTuyenDuong)
                return listTuyenDuong.get(i);

        return null;
    }

    private void getData() {
        // Clear the list
        listTuyenDuong.clear();

        SQLiteDatabase readableDatabase = m_SQLiteHelper.getReadableDatabase();

        // Cursor is a temporary buffer area that holds results returned from a database query
        Cursor cursor = readableDatabase.query(TuyenDuong.TUYEN_DUONG_TABLE_NAME,
                TuyenDuong.COLUMNS_NAMES, null, null, null, null, null);

        // Move to the first row of results
        cursor.moveToFirst();

        if (cursor != null) {
            // Loop until no row left
            while (!cursor.isAfterLast()) {
                int maTuyenDuong = cursor.getInt(cursor.getColumnIndex(TuyenDuong.MA_TUYEN_DUONG));
                String tenTuyenDuong = cursor.getString(cursor.getColumnIndex(TuyenDuong.TEN_TUYEN_DUONG));
                float khoangCachVatLy = cursor.getFloat(cursor.getColumnIndex(TuyenDuong.KHOACH_CACH_VAT_LY));
                String benDi = cursor.getString(cursor.getColumnIndex(TuyenDuong.BEN_DI));
                String benDo = cursor.getString(cursor.getColumnIndex(TuyenDuong.BEN_DO));
                String tanSuat = cursor.getString(cursor.getColumnIndex(TuyenDuong.TAN_SUAT));
                float giaVe = cursor.getFloat(cursor.getColumnIndex(TuyenDuong.GIA_VE));
                String gioMoBen = cursor.getString(cursor.getColumnIndex(TuyenDuong.GIO_MO_BEN));
                String gioDongBen = cursor.getString(cursor.getColumnIndex(TuyenDuong.GIO_DONG_BEN));

                TuyenDuong t = new TuyenDuong(maTuyenDuong, tenTuyenDuong, khoangCachVatLy, benDi, benDo, tanSuat,
                        giaVe, gioMoBen, gioDongBen);
                listTuyenDuong.add(t);

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
        writableDatabase.delete(TuyenDuong.TUYEN_DUONG_TABLE_NAME,
                TuyenDuong.MA_TUYEN_DUONG + " = " + id,
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
        writableDatabase.delete(TuyenDuong.TUYEN_DUONG_TABLE_NAME, null, null);

        // Refresh the list
        getData();

        // Close all the connect to DB to free memory
        writableDatabase.close();
        m_SQLiteHelper.close();
    }

    // Method for checking whether an ID in a table exists
    public boolean doesThisIDExist(int id) {
        for (int i = 0; i < listTuyenDuong.size(); i++)
            if (listTuyenDuong.get(i).maTuyenDuong == id)
                return true;

        return false;
    }

}
