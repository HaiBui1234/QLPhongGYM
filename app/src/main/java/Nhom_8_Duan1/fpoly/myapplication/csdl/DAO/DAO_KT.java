package Nhom_8_Duan1.fpoly.myapplication.csdl.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;


@Dao
public interface DAO_KT {
    @Insert
    void insertKT(KT kt);
    @Update
    void updateKT(KT kt);
    @Delete
    void deleteKT(KT kt);
    @Query("SELECT * FROM KT where id_PT = :id")
    List<KT> getAllKT(int id);
    @Query("SELECT * FROM KT")
    List<KT> getAllKT1();

    @Query("select * from KT where Capquyen like 0")
    List<KT> getKTChuaCapQuyen();
    @Query("select * from KT where Capquyen like 1")
    List<KT> getKTDaCapQuyen();
    @Query("select * from KT " +
            "where KT.id_KhoaTap not in (select id_KhoaTap from DK) " +
            "and Capquyen like 1")
    List<KT> getKTNotInDK();
    @Query("select * from KT where id_KhoaTap = :id")
    List<KT> getKTTheoID(int id);

}
