package Model;

import java.io.Serializable;

public class LinhKien implements Serializable {
    int id;
    String ten;
    int gia;
    String anh;
    String loai;
    int soluong;
    String mota;

    public LinhKien(){}
    public LinhKien(int id, String ten, int gia, String anh, String loai, int soluong, String mota) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.anh = anh;
        this.loai = loai;
        this.soluong = soluong;
        this.mota = mota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
