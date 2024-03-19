package com.route.todo.application

import android.app.Application
import com.route.todo.database.TaskDataBase

class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TaskDataBase.init(this)
    }
}