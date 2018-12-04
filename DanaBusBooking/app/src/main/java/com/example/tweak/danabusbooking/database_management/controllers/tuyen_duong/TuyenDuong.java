package com.example.tweak.danabusbooking.database_management.controllers.tuyen_duong;

public class TuyenDuong {

    // The name of this table
    public static final String TUYEN_DUONG_TABLE_NAME = "TUYEN_DUONG_TABLE_NAME";

    // The fields of this table
    public static final String MA_TUYEN_DUONG = "MA_TUYEN_DUONG";
    public static final String TEN_TUYEN_DUONG = "TEN_TUYEN_DUONG";
    public static final String KHOACH_CACH_VAT_LY = "KHOACH_CACH_VAT_LY";
    public static final String BEN_DI = "BEN_DI";
    public static final String BEN_DO = "BEN_DO";
    public static final String TAN_SUAT = "TAN_SUAT";
    public static final String GIA_VE = "GIA_VE";
    public static final String GIO_MO_BEN = "GIO_MO_BEN";
    public static final String GIO_DONG_BEN = "GIO_DONG_BEN";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_TUYEN_DUONG,
            TEN_TUYEN_DUONG,
            KHOACH_CACH_VAT_LY,
            BEN_DI,
            BEN_DO,
            TAN_SUAT,
            GIA_VE,
            GIO_MO_BEN,
            GIO_DONG_BEN
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + TUYEN_DUONG_TABLE_NAME
                + " ( "
                + MA_TUYEN_DUONG + " INTEGER PRIMARY KEY, "
                + TEN_TUYEN_DUONG + " TEXT, "
                + KHOACH_CACH_VAT_LY + " FLOAT, "
                + BEN_DI + " TEXT, "
                + BEN_DO + " TEXT, "
                + TAN_SUAT + " TEXT, "
                + GIA_VE + " FLOAT, "
                + GIO_MO_BEN + " TEXT, "
                + GIO_DONG_BEN + " TEXT"
                + " ) ";
    }

    public int maTuyenDuong;
    public String tenTuyenDuong;
    public float khoangCachVatLy;
    public String benDi;
    public String benDo;
    public String tanSuat;
    public float giaVe;
    public String gioMoBen;
    public String gioDongBen;
    public boolean daHetCho;

    public TuyenDuong(int maTuyenDuong, String tenTuyenDuong, float khoangCachVatLy, String benDi, String benDo, String tanSuat, float giaVe, String gioMoBen, String gioDongBen) {
        this.maTuyenDuong = maTuyenDuong;
        this.tenTuyenDuong = tenTuyenDuong;
        this.khoangCachVatLy = khoangCachVatLy;
        this.benDi = benDi;
        this.benDo = benDo;
        this.tanSuat = tanSuat;
        this.giaVe = giaVe;
        this.gioMoBen = gioMoBen;
        this.gioDongBen = gioDongBen;

        daHetCho = true;
    }

    @Override
    public String toString() {
        return "TuyenDuong{" +
                "maTuyenDuong=" + maTuyenDuong +
                ", tenTuyenDuong='" + tenTuyenDuong + '\'' +
                ", khoangCachVatLy=" + khoangCachVatLy +
                ", benDi='" + benDi + '\'' +
                ", benDo='" + benDo + '\'' +
                ", tanSuat='" + tanSuat + '\'' +
                ", giaVe=" + giaVe +
                ", gioMoBen='" + gioMoBen + '\'' +
                ", gioDongBen='" + gioDongBen + '\'' +
                '}';
    }

}