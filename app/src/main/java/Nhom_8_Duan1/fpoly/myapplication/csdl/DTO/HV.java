package Nhom_8_Duan1.fpoly.myapplication.csdl.DTO;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "HV",
        indices = @Index(value = "username_HV", unique = true))
public class HV {
    @PrimaryKey(autoGenerate = true)
    private int id_HV;
    private String username_HV;
    private String name_HV;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] avatar_HV;
    private String pass_HV;
    private String email_HV;
    @ColumnInfo(defaultValue = "03...")
    private String phone_HV;
    private int maPin_HV;
    @ColumnInfo(defaultValue = "0")
    private int monney;

    public HV() {
    }

    public HV(String username_HV, String name_HV, byte[] avatar_HV, String pass_HV, String email_HV, int maPin_HV) {
        this.username_HV = username_HV;
        this.name_HV = name_HV;
        this.avatar_HV = avatar_HV;
        this.pass_HV = pass_HV;
        this.email_HV = email_HV;
        this.maPin_HV = maPin_HV;
    }

    public int getId_HV() {
        return id_HV;
    }

    public void setId_HV(int id_HV) {
        this.id_HV = id_HV;
    }

    public String getUsername_HV() {
        return username_HV;
    }

    public void setUsername_HV(String username_HV) {
        this.username_HV = username_HV;
    }

    public String getName_HV() {
        return name_HV;
    }

    public void setName_HV(String name_HV) {
        this.name_HV = name_HV;
    }

    public byte[] getAvatar_HV() {
        return avatar_HV;
    }

    public void setAvatar_HV(byte[] avatar_HV) {
        this.avatar_HV = avatar_HV;
    }

    public String getPass_HV() {
        return pass_HV;
    }

    public void setPass_HV(String pass_HV) {
        this.pass_HV = pass_HV;
    }

    public String getEmail_HV() {
        return email_HV;
    }

    public void setEmail_HV(String email_HV) {
        this.email_HV = email_HV;
    }

    public String getPhone_HV() {
        return phone_HV;
    }

    public void setPhone_HV(String phone_HV) {
        this.phone_HV = phone_HV;
    }

    public int getMaPin_HV() {
        return maPin_HV;
    }

    public void setMaPin_HV(int maPin_HV) {
        this.maPin_HV = maPin_HV;
    }

    public int getMonney() {
        return monney;
    }

    public void setMonney(int monney) {
        this.monney = monney;
    }

    public String toString(){
        return "Họ tên: " +name_HV + "\n"+ "User: "+ username_HV+"\n" + "Pass: " +pass_HV;
    }
}