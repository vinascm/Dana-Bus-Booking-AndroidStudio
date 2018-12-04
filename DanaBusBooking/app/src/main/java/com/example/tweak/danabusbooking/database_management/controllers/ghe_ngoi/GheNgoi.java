package com.example.tweak.danabusbooking.database_management.controllers.ghe_ngoi;

public class GheNgoi {

    // The name of this table
    public static final String GHE_NGOI_TABLE_NAME = "GHE_NGOI_TABLE_NAME";

    // The fields of this table
    public static final String MA_GHE_NGOI = "MA_GHE_NGOI";
    public static final String SO_HIEU_GHE = "SO_HIEU_GHE";
    public static final String VI_TRI_VAT_LY = "VI_TRI_VAT_LY";
    public static final String TINH_TRANG = "TINH_TRANG";
    public static final String MA_XE_BUYT = "MA_XE_BUYT";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_GHE_NGOI,
            SO_HIEU_GHE,
            VI_TRI_VAT_LY,
            TINH_TRANG,
            MA_XE_BUYT
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + GHE_NGOI_TABLE_NAME
                + " ( "
                + MA_GHE_NGOI + " INTEGER PRIMARY KEY, "
                + SO_HIEU_GHE + " TEXT, "
                + VI_TRI_VAT_LY + " TEXT, "
                + TINH_TRANG + " INTEGER, "
                + MA_XE_BUYT + " INTEGER"
                + " ) ";
    }

    public int maGheNgoi;
    public String soHieuGhe;
    public String viTriVatLy;
    public int tinhTrang;
    public int maXeBuyt;

    public GheNgoi(int maGheNgoi, String soHieuGhe, String viTriVatLy, int tinhTrang, int maXeBuyt) {
        this.maGheNgoi = maGheNgoi;
        this.soHieuGhe = soHieuGhe;
        this.viTriVatLy = viTriVatLy;
        this.tinhTrang = tinhTrang;
        this.maXeBuyt = maXeBuyt;
    }

    @Override
    public String toString() {
        return "GheNgoi{" +
                "maGheNgoi=" + maGheNgoi +
                ", soHieuGhe='" + soHieuGhe + '\'' +
                ", viTriVatLy='" + viTriVatLy + '\'' +
                ", tinhTrang='" + tinhTrang + '\'' +
                ", maXeBuyt='" + maXeBuyt + '\'' +
                '}';
    }

}