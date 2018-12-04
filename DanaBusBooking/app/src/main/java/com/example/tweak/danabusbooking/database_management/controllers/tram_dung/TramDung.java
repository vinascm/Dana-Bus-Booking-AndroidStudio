package com.example.tweak.danabusbooking.database_management.controllers.tram_dung;

public class TramDung {

    // The name of this table
    public static final String TRAM_DUNG_TABLE_NAME = "TRAM_DUNG_TABLE_NAME";

    // The fields of this table
    public static final String MA_TRAM_DUNG = "MA_TRAM_DUNG";
    public static final String TEN_TRAM_DUNG = "TEN_TRAM_DUNG";
    public static final String DIA_CHI = "DIA_CHI";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_TRAM_DUNG,
            TEN_TRAM_DUNG,
            DIA_CHI
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + TRAM_DUNG_TABLE_NAME
                + " ( "
                + MA_TRAM_DUNG + " INTEGER PRIMARY KEY, "
                + TEN_TRAM_DUNG + " TEXT, "
                + DIA_CHI + " TEXT"
                + " ) ";
    }

    public int maTramDung;
    public String tenTramDung;
    public String diaChi;

    public TramDung(int maTramDung, String tenTramDung, String diaChi) {
        this.maTramDung = maTramDung;
        this.tenTramDung = tenTramDung;
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "TramDung{" +
                "maTramDung=" + maTramDung +
                ", tenTramDung='" + tenTramDung + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }

}