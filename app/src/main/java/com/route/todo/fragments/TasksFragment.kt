package com.route.todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.todo.adapters.tasksAdapter
import com.route.todo.database.TaskDataBase
import com.route.todo.database.models.Task
import com.route.todo.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: tasksAdapter
    private lateinit var tasksList: List<Task>
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

        adapter = tasksAdapter(null)
        binding.recycler.adapter = adapter

        tasksList = TaskDataBase.getInstance(requireContext()).getDao().getAllTasks()
        adapter.updateData(tasksList)

    }
}