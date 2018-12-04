package com.example.tweak.danabusbooking.database_management.controllers.khach_hang_ve;

public class KhachHangVe {

    // The name of this table
    public static final String KHACH_HANG_VE_TABLE_NAME = "KHACH_HANG_VE_TABLE_NAME";

    // The fields of this table
    public static final String MA_VE = "MA_VE";
    public static final String MA_KHACH_HANG = "MA_KHACH_HANG";
    public static final String MA_GHE_NGOI = "MA_GHE_NGOI";
    public static final String GIA_TIEN_VE = "GIA_TIEN_VE";
    public static final String TINH_TRANG = "TINH_TRANG";
    public static final String NGAY_DAT = "NGAY_DAT";
    public static final String NGAY_HUY = "NGAY_HUY";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_VE,
            MA_KHACH_HANG,
            MA_GHE_NGOI,
            GIA_TIEN_VE,
            TINH_TRANG,
            NGAY_DAT,
            NGAY_HUY
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + KHACH_HANG_VE_TABLE_NAME
                + " ( "
                + MA_VE + " INTEGER PRIMARY KEY, "
                + MA_KHACH_HANG + " INTEGER, "
                + MA_GHE_NGOI + " INTEGER, "
                + GIA_TIEN_VE + " FLOAT, "
                + TINH_TRANG + " INTEGER, "
                + NGAY_DAT + " TEXT, "
                + NGAY_HUY + " TEXT"
                + " ) ";
    }

    public int maVe;
    public int maKhachHang;
    public int maGheNgoi;
    public float giaTienVe;
    public int tinhTrang;
    public String ngayDat;
    public String ngayHuy;

    public KhachHangVe(int maVe, int maKhachHang, int maGheNgoi, float giaTienVe, int tinhTrang, String ngayDat, String ngayHuy) {
        this.maVe = maVe;
        this.maKhachHang = maKhachHang;
        this.maGheNgoi = maGheNgoi;
        this.giaTienVe = giaTienVe;
        this.tinhTrang = tinhTrang;
        this.ngayDat = ngayDat;
        this.ngayHuy = ngayHuy;
    }

    @Override
    public String toString() {
        return "KhachHangVe{" +
                "maVe=" + maVe +
                ", maKhachHang=" + maKhachHang +
                ", maGheNgoi=" + maGheNgoi +
                ", giaTienVe=" + giaTienVe +
                ", tinhTrang=" + tinhTrang +
                ", ngayDat=" + ngayDat +
                ", ngayHuy=" + ngayHuy +
                '}';
    }

}