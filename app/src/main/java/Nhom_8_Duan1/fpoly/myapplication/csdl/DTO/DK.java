package Nhom_8_Duan1.fpoly.myapplication.csdl.DTO;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

import Nhom_8_Duan1.fpoly.myapplication.Convertor.DateTypeConvertor;


@Entity(tableName = "DK",
        foreignKeys = {
        @ForeignKey(entity = HV.class, parentColumns = "id_HV", childColumns = "id_HV", onDelete = ForeignKey.CASCADE)
                ,@ForeignKey(entity = KT.class, parentColumns = "id_KhoaTap", childColumns = "id_KhoaTap", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = PT.class, parentColumns = "id_PT", childColumns = "id_PT", onDelete = ForeignKey.CASCADE)
})
public class DK {
    @PrimaryKey(autoGenerate = true)
    private int id_DK;
    private int id_HV;
    private int id_KhoaTap;
    private int id_PT;
    private String mDate;
    private int gia;
    private int thanhToan;

    public DK() {
    }

    public DK(int id_HV, int id_KhoaTap, int id_PT, String mDate, int gia, int thanhToan) {
        this.id_HV = id_HV;
        this.id_KhoaTap = id_KhoaTap;
        this.id_PT = id_PT;
        this.mDate = mDate;
        this.gia = gia;
        this.thanhToan = thanhToan;
    }

    public int getId_DK() {
        return id_DK;
    }

    public void setId_DK(int id_DK) {
        this.id_DK = id_DK;
    }

    public int getId_HV() {
        return id_HV;
    }

    public void setId_HV(int id_HV) {
        this.id_HV = id_HV;
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

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(int thanhToan) {
        this.thanhToan = thanhToan;
    }
}
