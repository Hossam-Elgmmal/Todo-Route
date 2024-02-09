package com.route.todo.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todos")
data class Task(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var details: String? = null,
    var date: Date? = null,
    var isDone: Boolean = false

)
