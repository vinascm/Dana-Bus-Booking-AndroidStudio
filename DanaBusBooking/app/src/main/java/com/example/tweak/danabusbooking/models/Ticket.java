package com.example.tweak.danabusbooking.models;

public class Ticket {

    public int tinhTrang;
    public String tenTuyen;
    public String thongTinXe;
    public String thongTinNgay;
    public int maVe;
    public int maGheNgoi;

    public Ticket(int tinhTrang, String tenTuyen, String thongTinXe, String thongTinNgay, int maVe, int maGheNgoi) {
        this.tinhTrang = tinhTrang;
        this.tenTuyen = tenTuyen;
        this.thongTinXe = thongTinXe;
        this.thongTinNgay = thongTinNgay;
        this.maVe = maVe;
        this.maGheNgoi = maGheNgoi;
    }

    // This is used to search around
    public String getTheStringForSearching() {
        String chunk_1 = this.tinhTrang == 1 ? "Đã đặt" : "Đã hủy";
        String chunk_2 = this.tenTuyen + " " + this.thongTinXe;
        String chunk_3 = this.tinhTrang == 1 ? "Ngày đặt" : "Ngày hủy";
        String chunk_4 = this.thongTinNgay;

        return chunk_1 + " " + chunk_2 + " " + chunk_3 + " " + chunk_4;
    }

    public boolean doesThisSearchStringContainThisLetter(String letter) {
        return getTheStringForSearching().toLowerCase().contains(letter.toLowerCase());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "tinhTrang=" + tinhTrang +
                ", tenTuyen='" + tenTuyen + '\'' +
                ", thongTinXe='" + thongTinXe + '\'' +
                ", thongTinNgay='" + thongTinNgay + '\'' +
                ", maVe=" + maVe +
                ", maGheNgoi=" + maGheNgoi +
                '}';
    }

}
