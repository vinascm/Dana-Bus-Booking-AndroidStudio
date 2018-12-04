package com.example.tweak.danabusbooking.database_management.controllers.xe_buyt;

public class XeBuyt {

    // The name of this table
    public static final String XE_BUYT_TABLE_NAME = "XE_BUYT_TABLE_NAME";

    // The fields of this table
    public static final String MA_XE_BUYT = "MA_XE_BUYT";
    public static final String SO_HIEU_XE = "SO_HIEU_XE";
    public static final String BIEN_SO_XE = "BIEN_SO_XE";
    public static final String MA_TUYEN_DUONG = "MA_TUYEN_DUONG";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_XE_BUYT,
            SO_HIEU_XE,
            BIEN_SO_XE,
            MA_TUYEN_DUONG
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + XE_BUYT_TABLE_NAME
                + " ( "
                + MA_XE_BUYT + " INTEGER PRIMARY KEY, "
                + SO_HIEU_XE + " TEXT, "
                + BIEN_SO_XE + " TEXT, "
                + MA_TUYEN_DUONG + " INTEGER"
                + " ) ";
    }

    public int maXeBuyt;
    public String soHieuXe;
    public String bienSoXe;
    public int maTuyenDuong;

    public XeBuyt(int maXeBuyt, String soHieuXe, String bienSoXe, int maTuyenDuong) {
        this.maXeBuyt = maXeBuyt;
        this.soHieuXe = soHieuXe;
        this.bienSoXe = bienSoXe;
        this.maTuyenDuong = maTuyenDuong;
    }

    @Override
    public String toString() {
        return "XeBuyt{" +
                "maXeBuyt=" + maXeBuyt +
                ", soHieuXe='" + soHieuXe + '\'' +
                ", bienSoXe='" + bienSoXe + '\'' +
                ", maTuyenDuong=" + maTuyenDuong +
                '}';
    }

}