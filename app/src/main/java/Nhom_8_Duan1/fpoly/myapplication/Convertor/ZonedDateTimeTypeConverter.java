package Nhom_8_Duan1.fpoly.myapplication.Convertor;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeTypeConverter {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static ZonedDateTime toZonedDateTime(Long value) {
        return value == null ? null : ZonedDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long toString(ZonedDateTime value) {
        return value == null ? null : value.toInstant().toEpochMilli();
    }
}
