package com.route.todo.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todo.DetailTaskActivity
import com.route.todo.adapters.tasksAdapter
import com.route.todo.database.TaskDao
import com.route.todo.database.TaskDataBase
import com.route.todo.database.models.Task
import com.route.todo.databinding.FragmentTasksBinding
import java.time.LocalDateTime


class TasksFragment : Fragment() {

    lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: tasksAdapter
    private lateinit var tasksList: MutableList<Task>
    var today = LocalDateTime.now()
    lateinit var taskDao: TaskDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskDao = TaskDataBase.getInstance(requireContext()).getDao()

        initializeRecyclerView()

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            val dateTime = LocalDateTime.of(date.year, date.month, date.day, 0, 0)
            tasksList = taskDao.getTasksInDay(dateTime, dateTime.plusDays(1)).toMutableList()
            adapter.updateData(tasksList)
        }

        showAllTasks()

        hideAllTasks()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeRecyclerView() {
        today = today.withHour(0).withMinute(0)
        adapter = tasksAdapter(null)

        adapter.onDeleteListener = { task, position ->
            taskDao.deleteTask(task)
            tasksList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        adapter.onDoneListener = { task, position ->
            task.isDone = !task.isDone
            taskDao.updateTask(task)
            adapter.notifyItemChanged(position)
        }
        adapter.onCardClickListener = { task, position ->
            val intent = Intent(requireActivity(), DetailTaskActivity::class.java)
            intent.putExtra("id", task.id)
            startActivity(intent)
        }

        binding.recycler.adapter = adapter
        tasksByDate(today)
    }

    fun tasksByDate(date: LocalDateTime) {
        val day = CalendarDay.from(date.year, date.monthValue, date.dayOfMonth)
        binding.calendarView.selectedDate = day
        binding.calendarView.setCurrentDate(day, true)
        tasksList = taskDao.getTasksInDay(date, date.plusDays(1)).toMutableList()
        adapter.updateData(tasksList)
    }

    private fun showAllTasks() {
        binding.calendarView.setOnTitleClickListener {
            binding.calendarView.visibility = View.GONE
            binding.allTasks.visibility = View.VISIBLE
            tasksList = taskDao.getAllTasks().toMutableList()
            adapter.updateData(tasksList)
        }
    }

    private fun hideAllTasks() {
        binding.allTasks.setOnClickListener {
            binding.allTasks.visibility = View.GONE
            binding.calendarView.visibility = View.VISIBLE
            with(binding.calendarView.selectedDate) {
                val dateTime = LocalDateTime.of(this?.year!!, this.month, this.day, 0, 0)
                tasksList = taskDao.getTasksInDay(dateTime, dateTime.plusDays(1)).toMutableList()
                adapter.updateData(tasksList)
            }
        }
    }
}