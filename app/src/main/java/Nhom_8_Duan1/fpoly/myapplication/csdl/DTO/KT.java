package Nhom_8_Duan1.fpoly.myapplication.csdl.DTO;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "KT", foreignKeys = {@ForeignKey(entity = PT.class, parentColumns = "id_PT", childColumns = "id_PT", onDelete = ForeignKey.CASCADE)})
public class KT {
    @PrimaryKey(autoGenerate = true)
    private int id_KhoaTap;
    private int id_PT;
    private String name_KhoaTap;
    private byte[] img_avatarTD;
    private int soNgayTap;
    private int giaKhoaTap;
    private int Capquyen;

    public KT() {
    }

    public KT(int id_PT, String name_KhoaTap, byte[] img_avatarTD, int soNgayTap, int giaKhoaTap, int capquyen) {
        this.id_PT = id_PT;
        this.name_KhoaTap = name_KhoaTap;
        this.img_avatarTD = img_avatarTD;
        this.soNgayTap = soNgayTap;
        this.giaKhoaTap = giaKhoaTap;
        Capquyen = capquyen;
    }

    public KT(int id_KhoaTap, int id_PT, String name_KhoaTap, byte[] img_avatarTD, int soNgayTap, int giaKhoaTap, int capquyen) {
        this.id_KhoaTap = id_KhoaTap;
        this.id_PT = id_PT;
        this.name_KhoaTap = name_KhoaTap;
        this.img_avatarTD = img_avatarTD;
        this.soNgayTap = soNgayTap;
        this.giaKhoaTap = giaKhoaTap;
        Capquyen = capquyen;
    }

    public byte[] getImg_avatarTD() {
        return img_avatarTD;
    }

    public void setImg_avatarTD(byte[] img_avatarTD) {
        this.img_avatarTD = img_avatarTD;
    }


    public int getId_KhoaTap() {
        return id_KhoaTap;
    }

    public void setId_KhoaTap(int id_KhoaTap) {
        this.id_KhoaTap = id_KhoaTap;
    }

    public int getId_PT() {
        return id_PT;
    }

    public void setId_PT(int id_PT) {
        this.id_PT = id_PT;
    }

    public String getName_KhoaTap() {
        return name_KhoaTap;
    }

    public void setName_KhoaTap(String name_KhoaTap) {
        this.name_KhoaTap = name_KhoaTap;
    }

    public int getSoNgayTap() {
        return soNgayTap;
    }

    public void setSoNgayTap(int soNgayTap) {
        this.soNgayTap = soNgayTap;
    }

    public int getGiaKhoaTap() {
        return giaKhoaTap;
    }

    public void setGiaKhoaTap(int giaKhoaTap) {
        this.giaKhoaTap = giaKhoaTap;
    }

    public int getCapquyen() {
        return Capquyen;
    }

    public void setCapquyen(int capquyen) {
        Capquyen = capquyen;
    }
    public String toString(){
        return name_KhoaTap;
    }
}
