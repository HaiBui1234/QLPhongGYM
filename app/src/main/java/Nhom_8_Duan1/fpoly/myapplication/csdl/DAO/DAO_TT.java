package Nhom_8_Duan1.fpoly.myapplication.csdl.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TT;

@Dao
public interface DAO_TT {
    @Insert
    void insertTT(TT tt);
    @Update
    void updateTT(TT tt);
    @Delete
    void deleteTT(TT tt);
    @Query("SELECT * FROM TT")
    List<TT> getAllTT();
    @Query("SELECT * FROM TT where TrangThai_TT like 0")
    List<TT> getAllTTChuaTT();
}
