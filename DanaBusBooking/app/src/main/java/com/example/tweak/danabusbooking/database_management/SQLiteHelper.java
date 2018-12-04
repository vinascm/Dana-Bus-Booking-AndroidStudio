package com.example.tweak.danabusbooking.database_management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi.GheNgoi;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang.KhachHang;
import com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve.KhachHangVe;
import com.example.tweak.danabusbooking.database_management.controllers.tram_dung.TramDung;
import com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong.TuyenDuong;
import com.example.tweak.danabusbooking.database_management.controllers.uu_dai.UuDai;
import com.example.tweak.danabusbooking.database_management.controllers.ve.Ve;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt.XeBuyt;
import com.example.tweak.danabusbooking.database_management.controllers.xe_buyt_tram_dung.XeBuytTramDung;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "danangBusBookingfinal1.db";
    private static final SQLiteDatabase.CursorFactory CURSOR_FACTORY = null;
    private static final int DATABASE_VERSION = 1;

    private static Context sContext;

    public SQLiteHelper() {
        super(sContext, DATABASE_NAME, CURSOR_FACTORY, DATABASE_VERSION);
    }

    // This method will have to be called prior to the constructor above
    public static void getContextReference(Context context) {
        sContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the UuDai table
        db.execSQL(UuDai.getStringQueryToCreateThisTable());

        // Create the KhachHang table
        db.execSQL(KhachHang.getStringQueryToCreateThisTable());

        // Create the TuyenDuong table
        db.execSQL(TuyenDuong.getStringQueryToCreateThisTable());

        // Create the XeBuyt table
        db.execSQL(XeBuyt.getStringQueryToCreateThisTable());

        // Create the TramDung table
        db.execSQL(TramDung.getStringQueryToCreateThisTable());

        // Create the XeBuytTramDung table
        db.execSQL(XeBuytTramDung.getStringQueryToCreateThisTable());

        // Create the Ve table
        db.execSQL(Ve.getStringQueryToCreateThisTable());

        // Create the GheNgoi table
        db.execSQL(GheNgoi.getStringQueryToCreateThisTable());

        // Create the KhachHangVe table
        db.execSQL(KhachHangVe.getStringQueryToCreateThisTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Do nothing for now
    }

}
