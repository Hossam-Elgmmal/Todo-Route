package com.route.todo.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.route.todo.database.TaskDao
import com.route.todo.database.TaskDataBase
import com.route.todo.database.models.Task
import com.route.todo.databinding.ActivityDetailTaskBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTaskBinding
    private lateinit var task: Task
    private lateinit var taskDao: TaskDao
    private var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private var timeFormat = DateTimeFormatter.ofPattern("hh:mm a")
    private val factory = Editable.Factory.getInstance()
    private lateinit var dateTime: LocalDateTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", -1)
        taskDao = TaskDataBase.getInstance(baseContext).getDao()
        if (id > 0) getTask(id)

        getTimePicker(dateTime.hour, dateTime.minute)
        getDatePicker(dateTime.year, dateTime.monthValue, dateTime.dayOfMonth)

        binding.detailToolBar.setNavigationOnClickListener {
            finish()
        }
        binding.saveBtn.setOnClickListener {
            if (binding.editTitle.text?.isEmpty() == true || binding.editTitle.text?.isBlank() == true) {
                binding.editTitle.error = "required"
                return@setOnClickListener
            } else {
                binding.editTitle.error = null
            }
            task.title = binding.editTitle.text.toString()
            task.details = binding.editDescription.text.toString()
            task.date = dateTime
            taskDao.updateTask(task)
            finish()
        }
    }

    private fun getTask(id: Int) {
        task = taskDao.getTaskById(id)
        dateTime = task.date!!
        binding.editTitle.text = factory.newEditable(task.title)
        binding.editDescription.text = factory.newEditable(task.details)
        binding.editTime.text = factory.newEditable(dateTime.format(timeFormat))
        binding.editDate.text = factory.newEditable(dateTime.format(dateFormat))
    }

    private fun getTimePicker(hour: Int, min: Int) {
        binding.editTime.setOnClickListener {
            val picker = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    dateTime = dateTime.withHour(hourOfDay).withMinute(minute)
                    binding.editTime.text = factory.newEditable(dateTime.format(timeFormat))
                }, hour, min, false
            )
            picker.show()
        }
    }

    private fun getDatePicker(year: Int, month: Int, day: Int) {
        binding.editDate.setOnClickListener {
            val picker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    dateTime =
                        dateTime.withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth)
                    binding.editDate.text = factory.newEditable(dateTime.format(dateFormat))
                }, year, month - 1, day
            )
            picker.datePicker.minDate = System.currentTimeMillis()
            picker.show()
        }
    }
}