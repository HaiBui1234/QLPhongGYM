package Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DAO.DAO_BT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DAO.DAO_DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DAO.DAO_HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DAO.DAO_KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DAO.DAO_PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DAO.DAO_TD;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DAO.DAO_TT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.BT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TD;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TT;

@Database(entities = {PT.class, HV.class, BT.class, TD.class, KT.class, TT.class, DK.class}, version = 1)
public abstract class DatabaseGym extends RoomDatabase {
    private static final String DATABASE_NAME = "gym.db7";
    private static DatabaseGym instance;
    public static synchronized DatabaseGym getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseGym.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract DAO_PT dao_pt();
    public abstract DAO_HV dao_hv();
    public abstract DAO_BT dao_bt();
    public abstract DAO_TD dao_td();
    public abstract DAO_KT dao_kt();
    public abstract DAO_TT dao_tt();
    public abstract DAO_DK dao_dk();


}
