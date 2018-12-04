package com.example.tweak.danabusbooking.database_management.controllers.ve;

public class Ve {

    // The name of this table
    public static final String VE_TABLE_NAME = "VE_TABLE_NAME";

    // The fields of this table
    public static final String MA_VE = "MA_VE";
    public static final String NGAY_SAN_XUAT = "NGAY_SAN_XUAT";
    public static final String NGAY_HET_HAN = "NGAY_HET_HAN";
    public static final String MA_TUYEN_DUONG = "MA_TUYEN_DUONG";
    public static final String TINH_TRANG = "TINH_TRANG";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_VE,
            NGAY_SAN_XUAT,
            NGAY_HET_HAN,
            MA_TUYEN_DUONG,
            TINH_TRANG
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + VE_TABLE_NAME
                + " ( "
                + MA_VE + " INTEGER PRIMARY KEY, "
                + NGAY_SAN_XUAT + " TEXT, "
                + NGAY_HET_HAN + " TEXT, "
                + MA_TUYEN_DUONG + " INTEGER, "
                + TINH_TRANG + " INTEGER"
                + " ) ";
    }

    public int maVe;
    public String ngaySanXuat;
    public String ngayHetHan;
    public int maTuyenDuong;
    public int tinhTrang;

    public Ve(int maVe, String ngaySanXuat, String ngayHetHan, int maTuyenDuong, int tinhTrang) {
        this.maVe = maVe;
        this.ngaySanXuat = ngaySanXuat;
        this.ngayHetHan = ngayHetHan;
        this.maTuyenDuong = maTuyenDuong;
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "Ve{" +
                "maVe=" + maVe +
                ", ngaySanXuat='" + ngaySanXuat + '\'' +
                ", ngayHetHan='" + ngayHetHan + '\'' +
                ", maTuyenDuong=" + maTuyenDuong +
                ", tinhTrang=" + tinhTrang +
                '}';
    }

}