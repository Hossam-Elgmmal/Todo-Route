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
import java.util.Calendar

class AddTaskFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAddTaskBinding
    lateinit var calendar: Calendar
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

        calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR)
        var min = calendar.get(Calendar.MINUTE)
        var amPm = if (calendar.get(Calendar.AM_PM) == 0) {
            "AM"
        } else
            "PM"
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var month = calendar.get(Calendar.MONTH)
        var year = calendar.get(Calendar.YEAR)

        binding.theTime.text = Editable.Factory.getInstance()
            .newEditable("$hour:$min $amPm")

        binding.theDate.text = Editable.Factory.getInstance()
            .newEditable("$day/${month + 1}/$year")

        binding.theTime.setOnClickListener {
            val picker = TimePickerDialog(requireContext(),
                { _, hourOfDay, minute ->
                    hour = if (hourOfDay > 12) {
                        hourOfDay - 12
                    } else
                        hourOfDay
                    amPm = if (hourOfDay >= 12) {
                        "PM"
                    } else
                        "AM"
                    binding.theTime.text = Editable.Factory.getInstance()
                        .newEditable("$hour:$minute $amPm")
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                }, hour, min, false
            )
            picker.show()
        }

        binding.theDate.setOnClickListener {

            val picker = DatePickerDialog(requireContext(),
                { _, year, month, dayOfMonth ->
                    binding.theDate.text = Editable.Factory.getInstance()
                        .newEditable("$dayOfMonth/${month + 1}/$year")
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                }, year, month, day
            )

            picker.datePicker.minDate = System.currentTimeMillis()
            picker.show()
        }

        binding.submitBtn.setOnClickListener {
            if (validateFields()) {

                val task = Task(
                    title = binding.name.text.toString(),
                    details = binding.details.text.toString(),
                    date = calendar.time
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