package com.route.todo.fragments.taskslist

import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todo.database.TaskDataBase
import com.route.todo.database.models.Task
import java.time.LocalDateTime

class TasksViewModel : ViewModel() {

    lateinit var tasksList: MutableList<Task>
    var today: LocalDateTime = LocalDateTime.now()
    private val taskDao = TaskDataBase.getInstance().getDao()

    fun getTasksByCalendarDay(calendarDay: CalendarDay) {
        val dateTime = LocalDateTime.of(calendarDay.year, calendarDay.month, calendarDay.day, 0, 0)
        getTasksByLocalDate(dateTime)
    }

    fun getTasksByLocalDate(dateTime: LocalDateTime) {

        tasksList = taskDao.getTasksInDay(dateTime, dateTime.plusDays(1)).toMutableList()
    }

    fun getAllTasks() {
        tasksList = taskDao.getAllTasks().toMutableList()
    }

    fun deleteTask(task: Task, position: Int) {
        taskDao.deleteTask(task)
        tasksList.removeAt(position)
    }

    fun toggleTaskDone(task: Task) {
        task.isDone = !task.isDone
        taskDao.updateTask(task)
    }
}