package com.example.banlinhkien;

public class LichSu {
    String ten;
    int gia;
    int soluong;
    String Ngay;

    public LichSu(String ten, int gia,int soluong, String ngay) {
        this.ten = ten;
        this.gia = gia;
        this.soluong = soluong;
        Ngay = ngay;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
