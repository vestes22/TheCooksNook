package com.codelovely.thecooksnook.data;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;


public class Converters {

    @TypeConverter
    public static LocalDate toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDate.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}