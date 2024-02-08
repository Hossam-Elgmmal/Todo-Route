package com.route.todo.database

import androidx.room.TypeConverter
import java.util.Date

class DateTimeConverter {

    @TypeConverter
    fun toDate(dateTime: Long): Date {
        return Date(dateTime)
    }

    @TypeConverter
    fun fromDate(dateTime: Date): Long {
        return dateTime.time
    }


}