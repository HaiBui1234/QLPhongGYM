package Nhom_8_Duan1.fpoly.myapplication.csdl.DAO;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.Convertor.DateTypeConvertor;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;


@Dao
@TypeConverters({DateTypeConvertor.class})
public interface DAO_DK {
    @Insert
    void insertDK(DK dk);
    @Update
    void updateDK(DK dk);
    @Delete
    void deleteDK(DK dk);
    @Query("SELECT * FROM DK")
    List<DK> getAllDK();
    @Query("select * from Dk where id_HV = :id")
    List<DK> getAllDKWhereID(int id);
    @Query("SELECT * FROM DK \n" +
            "INNER JOIN HV on HV.id_HV = DK.id_HV \n" +
            "INNER JOIN KT on KT.id_KhoaTap = DK.id_KhoaTap \n" +
            "INNER JOIN PT on PT.id_PT = KT.id_PT\n" +
            "WHERE PT.id_PT = :id")
    List<DK> getHVDki(int id);
    @Query("select * from DK where mDate between :tuNgay and :denNgay")
    List<DK> getDoanhThu(String tuNgay, String denNgay);
    @Query("select * from DK where id_KhoaTap = :id_KT and id_HV = :id_hv")
    List<DK> checkKTDK(int id_KT, int id_hv);
}
