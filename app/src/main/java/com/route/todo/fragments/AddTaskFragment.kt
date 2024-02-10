package com.route.todo.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todo.database.TaskDataBase
import com.route.todo.database.models.Task
import com.route.todo.databinding.FragmentAddTaskBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddTaskFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private var dateTime = LocalDateTime.now()
    private var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private var timeFormat = DateTimeFormatter.ofPattern("hh:mm a")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        writeTimeToTextView()

        writeDateToTextView()

        getTimePicker(dateTime.hour, dateTime.minute)

        getDatePicker(dateTime.year, dateTime.monthValue, dateTime.dayOfMonth)

        submitNewTask()
    }

    private fun writeTimeToTextView() {
        binding.theTime.text = Editable.Factory.getInstance()
            .newEditable(dateTime.format(timeFormat))
    }

    private fun writeDateToTextView() {
        binding.theDate.text = Editable.Factory.getInstance()
            .newEditable(dateTime.format(dateFormat))
    }

    private fun getTimePicker(hour: Int, min: Int) {
        binding.theTime.setOnClickListener {
            val picker = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    dateTime = dateTime.withHour(hourOfDay).withMinute(minute)
                    writeTimeToTextView()
                }, hour, min, false
            )
            picker.show()
        }
    }

    private fun getDatePicker(year: Int, month: Int, day: Int) {
        binding.theDate.setOnClickListener {
            val picker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    dateTime =
                        dateTime.withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth)
                    writeDateToTextView()
                }, year, month - 1, day
            )
            picker.datePicker.minDate = System.currentTimeMillis()
            picker.show()
        }
    }

    private fun submitNewTask() {
        binding.submitBtn.setOnClickListener {
            if (validateFields()) {

                val task = Task(
                    title = binding.name.text.toString(),
                    details = binding.details.text.toString(),
                    date = dateTime
                )

                TaskDataBase.getInstance(requireContext())
                    .getDao()
                    .insertTask(task)
                dismiss()
            }
        }
    }
    private fun validateFields(): Boolean {
        if (binding.name.text?.isEmpty() == true || binding.name.text?.isBlank() == true) {
            binding.name.error = "required"
            return false
        } else {
            binding.name.error = null
        }
        if (binding.details.text?.isEmpty() == true || binding.details.text?.isBlank() == true) {
            binding.details.error = "required"
            return false
        } else {
            binding.details.error = null
        }
        return true
    }
}