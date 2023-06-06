package Nhom_8_Duan1.fpoly.myapplication.csdl.DTO;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "PT",indices  =@Index(value = "username_PT",unique = true))
public class PT {
    @PrimaryKey(autoGenerate = true)
    private int id_PT;
    private String username_PT;
    private String name_PT;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] avatar_PT;
    private String pass_PT;
    private String email_PT;
    @ColumnInfo(defaultValue = " ")
    private String phone_PT;
    private int maPin_PT;

    public PT() {
    }

    public PT(String username_PT, String name_PT, byte[] avatar_PT, String pass_PT, String email_PT, int maPin_PT) {
        this.username_PT = username_PT;
        this.name_PT = name_PT;
        this.avatar_PT = avatar_PT;
        this.pass_PT = pass_PT;
        this.email_PT = email_PT;
        this.maPin_PT = maPin_PT;
    }

    public int getId_PT() {
        return id_PT;
    }

    public void setId_PT(int id_PT) {
        this.id_PT = id_PT;
    }

    public String getUsername_PT() {
        return username_PT;
    }

    public void setUsername_PT(String username_PT) {
        this.username_PT = username_PT;
    }

    public String getName_PT() {
        return name_PT;
    }

    public void setName_PT(String name_PT) {
        this.name_PT = name_PT;
    }

    public byte[] getAvatar_PT() {
        return avatar_PT;
    }

    public void setAvatar_PT(byte[] avatar_PT) {
        this.avatar_PT = avatar_PT;
    }

    public String getPass_PT() {
        return pass_PT;
    }

    public void setPass_PT(String pass_PT) {
        this.pass_PT = pass_PT;
    }

    public String getEmail_PT() {
        return email_PT;
    }

    public void setEmail_PT(String email_PT) {
        this.email_PT = email_PT;
    }

    public String getPhone_PT() {
        return phone_PT;
    }

    public void setPhone_PT(String phone_PT) {
        this.phone_PT = phone_PT;
    }

    public int getMaPin_PT() {
        return maPin_PT;
    }

    public void setMaPin_PT(int maPin_PT) {
        this.maPin_PT = maPin_PT;
    }

    public String toString(){
        return "Họ tên: " +name_PT + "\n"+ "User: "+ username_PT+"\n" + "Pass: " +pass_PT;
    }
}
