package Nhom_8_Duan1.fpoly.myapplication.Convertor;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConvertor {
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static long fromDate(Date date){
        return date == null ? null :date.getTime();
    }
}
