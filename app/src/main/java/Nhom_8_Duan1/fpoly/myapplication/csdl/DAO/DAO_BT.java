package Nhom_8_Duan1.fpoly.myapplication.csdl.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.BT;

@Dao
public interface DAO_BT {
    @Insert
    void insertBT(BT bt);
    @Update
    void updateBT(BT bt);
    @Delete
    void deleteBT(BT bt);
    @Query("SELECT * FROM BT")
    List<BT> getAllBT();
    @Query("SELECT * FROM BT where id_KhoaTap = :id")
    List<BT> getAllBTTheoID_KT(int id);
}
