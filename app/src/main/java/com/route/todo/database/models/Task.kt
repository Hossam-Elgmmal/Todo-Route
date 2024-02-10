package com.route.todo.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "todos")
data class Task(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var details: String? = null,
    var date: LocalDateTime? = null,
    var isDone: Boolean = false

)
