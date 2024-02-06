package com.route.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.route.todo.database.models.Task

@Dao
interface TaskDao {

    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT * FROM todos")
    fun getAllTasks(): List<Task>

//    @Query("SELECT * FROM todos WHERE date = :timeDate ")
//    fun getTasksByDate(timeDate: Date): List<Task>
}