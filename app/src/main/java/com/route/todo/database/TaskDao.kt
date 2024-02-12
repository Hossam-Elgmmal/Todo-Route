package com.route.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.route.todo.database.models.Task
import java.time.LocalDateTime

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

    @Query("SELECT * FROM todos WHERE date >= :dayStart AND date < :dayEnd ")
    fun getTasksInDay(dayStart: LocalDateTime, dayEnd: LocalDateTime): List<Task>

    @Query("SELECT * FROM todos WHERE id = :id")
    fun getTaskById(id: Int): Task
}