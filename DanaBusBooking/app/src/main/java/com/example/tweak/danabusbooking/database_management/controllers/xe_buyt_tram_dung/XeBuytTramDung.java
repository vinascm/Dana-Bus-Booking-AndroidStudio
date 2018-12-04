package com.example.tweak.danabusbooking.database_management.controllers.xe_buyt_tram_dung;

public class XeBuytTramDung {

    // The name of this table
    public static final String XE_BUYT_TRAM_DUNG_TABLE_NAME = "XE_BUYT_TRAM_DUNG_TABLE_NAME";

    // The fields of this table
    public static final String MA_XE_BUYT = "MA_XE_BUYT";
    public static final String MA_TRAM_DUNG = "MA_TRAM_DUNG";
    public static final String MO_TA = "MO_TA";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_XE_BUYT,
            MA_TRAM_DUNG,
            MO_TA
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + XE_BUYT_TRAM_DUNG_TABLE_NAME
                + " ( "
                + MA_XE_BUYT + " INTEGER NOT NULL, "
                + MA_TRAM_DUNG + " INTEGER NOT NULL, "
                + MO_TA + " TEXT, "
                + "PRIMARY KEY ( " + MA_XE_BUYT + "," + MA_TRAM_DUNG + ")"
                + " ) ";
    }

    public int maXeBuyt;
    public int maTramDung;
    public String moTa;

    public XeBuytTramDung(int maXeBuyt, int maTramDung, String moTa) {
        this.maXeBuyt = maXeBuyt;
        this.maTramDung = maTramDung;
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "XeBuytTramDung{" +
                "maXeBuyt=" + maXeBuyt +
                ", maTramDung=" + maTramDung +
                ", moTa='" + moTa + '\'' +
                '}';
    }

}