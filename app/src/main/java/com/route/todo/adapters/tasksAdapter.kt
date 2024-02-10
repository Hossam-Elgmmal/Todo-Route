package com.route.todo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todo.database.models.Task
import com.route.todo.databinding.ItemTaskBinding
import java.time.format.DateTimeFormatter

class tasksAdapter(private var tasksList: List<Task>?) : Adapter<tasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = tasksList?.get(position) ?: return
        holder.bind(item)

    }

    override fun getItemCount() = tasksList?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(tasksList: List<Task>?) {
        this.tasksList = tasksList
        notifyDataSetChanged()
    }

    class TaskViewHolder(val binding: ItemTaskBinding) : ViewHolder(binding.root) {

        private var timeFormat = DateTimeFormatter.ofPattern("E hh:mm a dd/MM/yyyy")
        fun bind(task: Task) {
            binding.taskText.text = task.title
            binding.timeText.text = task.date?.format(timeFormat)
        }
    }
}