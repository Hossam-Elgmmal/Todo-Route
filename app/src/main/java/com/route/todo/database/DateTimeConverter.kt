package com.route.todo.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateTimeConverter {

    @TypeConverter
    fun toDate(dateTime: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneOffset.UTC)
    }

    @TypeConverter
    fun fromDate(dateTime: LocalDateTime): Long {
        return dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    }


}