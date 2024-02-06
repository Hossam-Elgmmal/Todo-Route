package com.route.todo.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String? = null,
    val description: String? = null,
    //val date: Date? = null,
    val isDone: Boolean = false

)
