package com.route.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.route.todo.database.models.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(value = [DateTimeConverter::class])
abstract class TaskDataBase : RoomDatabase() {
    abstract fun getDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDataBase? = null
        private val DATABASE_NAME = "Task database"

        fun getInstance(context: Context): TaskDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, TaskDataBase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}