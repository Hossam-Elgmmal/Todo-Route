package com.route.todo.fragments.taskslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todo.database.TaskDataBase
import com.route.todo.database.models.Task
import java.time.LocalDateTime

class TasksViewModel : ViewModel() {

    val tasksList = MutableLiveData<List<Task>>(emptyList())
    var today: LocalDateTime = LocalDateTime.now()
    private val taskDao = TaskDataBase.getInstance().getDao()

    fun getTasksByCalendarDay(calendarDay: CalendarDay) {
        val dateTime = LocalDateTime.of(calendarDay.year, calendarDay.month, calendarDay.day, 0, 0)
        getTasksByLocalDate(dateTime)
    }

    fun getTasksByLocalDate(dateTime: LocalDateTime) {

        tasksList.value = taskDao.getTasksInDay(dateTime, dateTime.plusDays(1))
    }

    fun getAllTasks() {
        tasksList.value = taskDao.getAllTasks()
    }

    fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun toggleTaskDone(task: Task) {
        task.isDone = !task.isDone
        taskDao.updateTask(task)
    }
}