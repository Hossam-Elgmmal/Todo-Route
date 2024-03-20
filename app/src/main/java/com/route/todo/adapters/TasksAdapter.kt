package com.route.todo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todo.R
import com.route.todo.database.models.Task
import com.route.todo.databinding.ItemTaskBinding
import java.time.format.DateTimeFormatter

class TasksAdapter(private var tasksList: MutableList<Task>) :
    Adapter<TasksAdapter.TaskViewHolder>() {

    lateinit var onDeleteListener: (Task, Int) -> Unit
    lateinit var onDoneListener: (Task, Int) -> Unit
    lateinit var onCardClickListener: (Task, Int) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskItem = tasksList[position]
        holder.bind(taskItem)
        holder.binding.deleteView.setOnClickListener {
            onDeleteListener(taskItem, holder.adapterPosition)
            tasksList.remove(taskItem)
        }
        holder.binding.doneView.setOnClickListener {
            onDoneListener(taskItem, holder.adapterPosition)
        }
        holder.binding.cardDragged.setOnClickListener {
            onCardClickListener(taskItem, position)
        }

    }

    override fun getItemCount() = tasksList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTasksList: List<Task>?) {
        tasksList.clear()
        if (newTasksList != null) {
            tasksList.addAll(newTasksList)
        }
        notifyDataSetChanged()
    }

    class TaskViewHolder(val binding: ItemTaskBinding) : ViewHolder(binding.root) {

        private var timeFormat = DateTimeFormatter.ofPattern("E hh:mm a dd/MM/yyyy")
        private val greenRect =
            ContextCompat.getDrawable(this.itemView.context, R.drawable.green_rect)
        private val blueRect =
            ContextCompat.getDrawable(this.itemView.context, R.drawable.blue_rect)
        private val green = ContextCompat.getColor(this.itemView.context, R.color.green_color)
        private val blue = ContextCompat.getColor(this.itemView.context, R.color.main_blue)
        fun bind(task: Task) {
            binding.taskText.text = task.title
            binding.timeText.text = task.date?.format(timeFormat)
            binding.doneView.background = if (task.isDone) greenRect else blueRect

            binding.lineDrag.background = if (task.isDone)
                ContextCompat.getDrawable(this.itemView.context, R.drawable.green_rect)
            else
                ContextCompat.getDrawable(this.itemView.context, R.drawable.blue_rect)

            binding.taskText.setTextColor(if (task.isDone) green else blue)
        }
    }
}