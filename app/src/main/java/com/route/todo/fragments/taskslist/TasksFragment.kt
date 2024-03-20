package com.route.todo.fragments.taskslist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todo.activities.DetailTaskActivity
import com.route.todo.adapters.TasksAdapter
import com.route.todo.databinding.FragmentTasksBinding
import java.time.LocalDateTime


class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: TasksAdapter
    private lateinit var vm: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater)
        vm = ViewModelProvider(this)[TasksViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            vm.getTasksByCalendarDay(date)
        }

        binding.calendarView.setOnTitleClickListener {
            showAllTasks()
        }

        binding.allTasks.setOnClickListener {
            hideAllTasks()
        }

        subscribeToTasksLiveData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeRecyclerView() {

        vm.today = vm.today.withHour(0).withMinute(0)
        adapter = TasksAdapter(mutableListOf())

        adapter.onDeleteListener = { task, position ->
            vm.deleteTask(task)
            adapter.notifyItemRemoved(position)
        }

        adapter.onDoneListener = { task, position ->
            vm.toggleTaskDone(task)
            adapter.notifyItemChanged(position)
        }

        adapter.onCardClickListener = { task, _ ->
            val intent = Intent(requireActivity(), DetailTaskActivity::class.java)
            intent.putExtra("id", task.id)
            startActivity(intent)
        }

        binding.recycler.adapter = adapter
        getTasksByDate(vm.today)

    }

    fun getTasksByDate(date: LocalDateTime) {

        val day = CalendarDay.from(date.year, date.monthValue, date.dayOfMonth)
        binding.calendarView.selectedDate = day
        binding.calendarView.setCurrentDate(day, true)

        if (binding.allTasks.visibility == View.VISIBLE) {
            binding.allTasks.visibility = View.GONE
            binding.calendarView.visibility = View.VISIBLE
        }
        vm.getTasksByLocalDate(date)
    }

    private fun showAllTasks() {

        binding.calendarView.visibility = View.GONE
        binding.allTasks.visibility = View.VISIBLE
        vm.getAllTasks()

    }

    private fun hideAllTasks() {

        binding.allTasks.visibility = View.GONE
        binding.calendarView.visibility = View.VISIBLE
        binding.calendarView.selectedDate?.let {
            val dateTime = LocalDateTime.of(it.year, it.month, it.day, 0, 0)
            vm.getTasksByLocalDate(dateTime)
        }
    }

    private fun subscribeToTasksLiveData() {
        vm.tasksList.observe(viewLifecycleOwner) { tasksList ->
            adapter.updateData(tasksList)

        }
    }
}