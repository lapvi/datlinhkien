package Model;

public class DanhMuc {
    String ma;
    String ten;
    String anh;

    public DanhMuc(String ma, String ten, String anh) {
        this.ma = ma;
        this.ten = ten;
        this.anh = anh;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
