package Nhom_8_Duan1.fpoly.myapplication.csdl.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;

@Dao
public interface DAO_PT {
    //pt
    @Insert
    void insertPT(PT pt);
    @Update
    void updataPT(PT pt);
    @Delete
    void deletePT(PT pt);
    @Query("select * from PT")
    List<PT> getAllPT();

    @Query("select * from PT where username_PT= :user and pass_PT = :pass")
    List<PT> CheckLogin(String user, String pass);

    @Query("select * from PT where username_PT= :userForget and maPin_PT = :pinForget")
    List<PT> checkForgetPass(String userForget, int pinForget);

    @Query("select * from PT where username_PT= :user")
    List<PT> getPTtheoUser(String user);

    @Query("select * from PT where id_PT= :id")
    List<PT> getPTtheoID(int id);
    @Query("SELECT *, count(DK.id_PT) as 'soLuong' FROM PT \n" +
            "INNER JOIN DK on DK.id_PT = PT.id_PT \n" +
            "GROUP by PT.id_PT \n" +
            "ORDER by PT.id_PT ASC")
    List<PT> getPTTop();
    @Query("SELECT count(DK.id_PT) as 'soLuong' FROM PT \n" +
            "INNER JOIN DK on DK.id_PT = PT.id_PT \n" +
            "where DK.id_PT = :id")
    int getPTTopSoLuong(int id);
}
