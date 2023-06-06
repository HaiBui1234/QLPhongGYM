package Nhom_8_Duan1.fpoly.myapplication.csdl.DTO;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "TD", foreignKeys = {@ForeignKey(entity = KT.class,parentColumns = "id_KhoaTap",childColumns = "id_KhoaTap",onDelete = ForeignKey.CASCADE)})
public class TD {
    @PrimaryKey(autoGenerate = true)
    private int id_TD;
    private String name_TD;
    private byte[] img_avatarTD;
    private String Ghi_Chu;
    private int id_KhoaTap;

    public TD() {
    }

    public TD(int id_TD, String name_TD, byte[] img_avatarTD, String ghi_Chu, int id_KhoaTap) {
        this.id_TD = id_TD;
        this.name_TD = name_TD;
        this.img_avatarTD = img_avatarTD;
        Ghi_Chu = ghi_Chu;
        this.id_KhoaTap = id_KhoaTap;
    }

    public TD(String name_TD, byte[] img_avatarTD, String ghi_Chu, int id_KhoaTap) {
        this.name_TD = name_TD;
        this.img_avatarTD = img_avatarTD;
        Ghi_Chu = ghi_Chu;
        this.id_KhoaTap = id_KhoaTap;
    }

    public int getId_TD() {
        return id_TD;
    }

    public String getName_TD() {
        return name_TD;
    }

    public String getGhi_Chu() {
        return Ghi_Chu;
    }

    public void setId_TD(int id_TD) {
        this.id_TD = id_TD;
    }

    public void setName_TD(String name_TD) {
        this.name_TD = name_TD;
    }

    public byte[] getImg_avatarTD() {
        return img_avatarTD;
    }

    public void setImg_avatarTD(byte[] img_avatarTD) {
        this.img_avatarTD = img_avatarTD;
    }

    public void setGhi_Chu(String ghi_Chu) {
        Ghi_Chu = ghi_Chu;
    }

    public int getId_KhoaTap() {
        return id_KhoaTap;
    }

    public void setId_KhoaTap(int id_KhoaTap) {
        this.id_KhoaTap = id_KhoaTap;
    }

    public String toString(){
        return name_TD+"\n"+Ghi_Chu;
    }
}
