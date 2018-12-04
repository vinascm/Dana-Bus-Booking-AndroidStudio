package com.example.tweak.danabusbooking.database_management.controllers.uu_dai;

public class UuDai {

    // The name of this table
    public static final String UU_DAI_TABLE_NAME = "UU_DAI_TABLE_NAME";

    // The fields of this table
    public static final String MA_UU_DAI = "MA_UU_DAI";
    public static final String MO_TA = "MO_TA";
    public static final String TI_LE_GIAM_GIA = "TI_LE_GIAM_GIA";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_UU_DAI,
            MO_TA,
            TI_LE_GIAM_GIA
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + UU_DAI_TABLE_NAME
                + " ( "
                + MA_UU_DAI + " INTEGER PRIMARY KEY, "
                + MO_TA + " TEXT, "
                + TI_LE_GIAM_GIA + " INTEGER"
                + " ) ";
    }

    public int maUuDai;
    public String moTa;
    public int tiLeGiamGia;

    public UuDai(int maUuDai, String moTa, int tiLeGiamGia) {
        this.maUuDai = maUuDai;
        this.moTa = moTa;
        this.tiLeGiamGia = tiLeGiamGia;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maUuDai=" + maUuDai +
                ", moTa='" + moTa + '\'' +
                ", tiLeGiamGia=" + tiLeGiamGia +
                '}';
    }

}