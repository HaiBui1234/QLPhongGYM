package Nhom_8_Duan1.fpoly.myapplication.csdl.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TD;

@Dao
public interface DAO_TD {
    @Insert
    void insertTD(TD td);
    @Update
    void updateTD(TD td);
    @Delete
    void deleteTD(TD td);
    @Query("SELECT * FROM TD")
    List<TD> getAllTD();
    @Query("SELECT * FROM TD WHERE id_KhoaTap= :id")
    List<TD> getid(int id);
}
