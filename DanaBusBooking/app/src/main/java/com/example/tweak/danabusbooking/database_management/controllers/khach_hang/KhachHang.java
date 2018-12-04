package com.example.tweak.danabusbooking.database_management.controllers.khach_hang;

import java.util.Arrays;

public class KhachHang {

    // The name of this table
    public static final String KHACH_HANG_TABLE_NAME = "KHACH_HANG_TABLE_NAME";

    // The fields of this table
    public static final String MA_KHACH_HANG = "MA_KHACH_HANG";
    public static final String TEN_KHACH_HANG = "TEN_KHACH_HANG";
    public static final String TUOI = "TUOI";
    public static final String GIOI_TINH = "GIOI_TINH";
    public static final String ANH_NHAN_DIEN = "ANH_NHAN_DIEN";
    public static final String DIA_CHI = "DIA_CHI";
    public static final String NGHE_NGHIEP = "NGHE_NGHIEP";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String MA_UU_DAI = "MA_UU_DAI";

    // The reference columns of this table in db
    public static final String[] COLUMNS_NAMES = {
            MA_KHACH_HANG,
            TEN_KHACH_HANG,
            TUOI,
            GIOI_TINH,
            ANH_NHAN_DIEN,
            DIA_CHI,
            NGHE_NGHIEP,
            EMAIL,
            PASSWORD,
            MA_UU_DAI
    };

    // The string query for creating the table
    public static final String getStringQueryToCreateThisTable() {
        return "CREATE TABLE IF NOT EXISTS " + KHACH_HANG_TABLE_NAME
                + " ( "
                + MA_KHACH_HANG + " INTEGER PRIMARY KEY, "
                + TEN_KHACH_HANG + " TEXT, "
                + TUOI + " TEXT, "
                + GIOI_TINH + " TEXT, "
                + ANH_NHAN_DIEN + " BLOB, "
                + DIA_CHI + " TEXT, "
                + NGHE_NGHIEP + " TEXT, "
                + EMAIL + " TEXT, "
                + PASSWORD + " TEXT, "
                + MA_UU_DAI + " INTEGER"
                + " ) ";
    }


    public int maKhachHang;
    public String tenKhachHang;
    public String tuoi;
    public String gioiTinh;
    public byte[] anhNhanDien;
    public String diaChi;
    public String ngheNghiep;
    public String email;
    public String password;
    public int maUuDai;

    public KhachHang(String tenKhachHang, String tuoi, String gioiTinh, byte[] anhNhanDien, String diaChi, String ngheNghiep, String email, String password, int maUuDai) {
        this.tenKhachHang = tenKhachHang;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.anhNhanDien = anhNhanDien;
        this.diaChi = diaChi;
        this.ngheNghiep = ngheNghiep;
        this.email = email;
        this.password = password;
        this.maUuDai = maUuDai;
    }

    public KhachHang(int maKhachHang, String tenKhachHang, String tuoi, String gioiTinh, byte[] anhNhanDien, String diaChi, String ngheNghiep, String email, String password, int maUuDai) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.anhNhanDien = anhNhanDien;
        this.diaChi = diaChi;
        this.ngheNghiep = ngheNghiep;
        this.email = email;
        this.password = password;
        this.maUuDai = maUuDai;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKhachHang=" + maKhachHang +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", tuoi='" + tuoi + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", anhNhanDien=" + Arrays.toString(anhNhanDien) +
                ", diaChi='" + diaChi + '\'' +
                ", ngheNghiep='" + ngheNghiep + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", maUuDai=" + maUuDai +
                '}';
    }

}