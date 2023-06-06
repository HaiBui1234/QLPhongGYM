package Nhom_8_Duan1.fpoly.myapplication.csdl.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;

@Dao
public interface DAO_HV {
    @Insert
    void insertHV(HV hv);
    @Update
    void updataHV(HV hv);
    @Delete
    void deleteHV(HV hv);
    @Query("select * from HV")
    List<HV> getAllHV();
    @Query("select * from HV where username_HV= :user and pass_HV = :pass")
    List<HV> CheckLogin(String user, String pass);
    @Query("select * from HV where username_HV= :userForget and maPin_HV = :pinForget")
    List<HV> checkForgetPassHV(String userForget, int pinForget);
    @Query("select * from hv where username_HV= :user")
    List<HV> getHVtheoUser(String user);
    @Query("select * from hv where id_HV= :id")
    List<HV> getHVtheoId(int id);
}
