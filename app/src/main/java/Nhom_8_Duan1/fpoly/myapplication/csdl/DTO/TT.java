package Nhom_8_Duan1.fpoly.myapplication.csdl.DTO;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "TT", foreignKeys = {@ForeignKey(entity = HV.class, parentColumns = "id_HV", childColumns = "id_HV", onDelete = ForeignKey.CASCADE)})
public class TT {
    @PrimaryKey(autoGenerate = true)
    private int id_TT;
    private int tien_TT;
    private int TrangThai_TT;
    private int id_HV;
    private String time;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] img_Naptien;

    public TT(int id_TT, int tien_TT, int trangThai_TT, int id_HV, String time) {
        this.id_TT = id_TT;
        this.tien_TT = tien_TT;
        TrangThai_TT = trangThai_TT;
        this.id_HV = id_HV;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TT() {
    }

    public int getId_TT() {
        return id_TT;
    }

    public void setId_TT(int id_TT) {
        this.id_TT = id_TT;
    }

    public int getTien_TT() {
        return tien_TT;
    }

    public void setTien_TT(int tien_TT) {
        this.tien_TT = tien_TT;
    }

    public int getTrangThai_TT() {
        return TrangThai_TT;
    }

    public void setTrangThai_TT(int trangThai_TT) {
        TrangThai_TT = trangThai_TT;
    }

    public int getId_HV() {
        return id_HV;
    }

    public void setId_HV(int id_HV) {
        this.id_HV = id_HV;
    }

    public byte[] getImg_Naptien() {
        return img_Naptien;
    }

    public void setImg_Naptien(byte[] img_Naptien) {
        this.img_Naptien = img_Naptien;
    }

    public String toString(){
        return "Id: " +id_TT + "\n"+ "id_hv: "+ id_HV+"\n" + "Số tiền nạp: " + tien_TT + "\n" + "Trạng thái: " + TrangThai_TT;
    }
}
