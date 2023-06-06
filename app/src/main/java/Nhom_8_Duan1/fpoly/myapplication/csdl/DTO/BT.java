package Nhom_8_Duan1.fpoly.myapplication.csdl.DTO;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "BT",foreignKeys = {@ForeignKey(entity = KT.class,parentColumns = "id_KhoaTap",childColumns = "id_KhoaTap",onDelete = ForeignKey.CASCADE)})
public class BT {
    @PrimaryKey (autoGenerate = true)
    private int id_BT;
    private String name_BT;
    private byte[] img_Avartar;
    private String Ghi_Chu;
    private int id_KhoaTap;

    public BT() {
    }

    public BT(int id_BT, String name_BT, byte[] img_Avartar, String ghi_Chu, int id_KhoaTap) {
        this.id_BT = id_BT;
        this.name_BT = name_BT;
        this.img_Avartar = img_Avartar;
        Ghi_Chu = ghi_Chu;
        this.id_KhoaTap = id_KhoaTap;
    }

    public BT(String name_BT, byte[] img_Avartar, String ghi_Chu, int id_KhoaTap) {
        this.name_BT = name_BT;
        this.img_Avartar = img_Avartar;
        Ghi_Chu = ghi_Chu;
        this.id_KhoaTap = id_KhoaTap;
    }

    public int getId_BT() {
        return id_BT;
    }

    public void setId_BT(int id_BT) {
        this.id_BT = id_BT;
    }

    public String getName_BT() {
        return name_BT;
    }

    public void setName_BT(String name_BT) {
        this.name_BT = name_BT;
    }

    public byte[] getImg_Avartar() {
        return img_Avartar;
    }

    public void setImg_Avartar(byte[] img_Avartar) {
        this.img_Avartar = img_Avartar;
    }

    public String getGhi_Chu() {
        return Ghi_Chu;
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
        return name_BT+"\n"+Ghi_Chu;
    }
}
