package com.route.todo.fragments.addtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todo.database.TaskDataBase
import com.route.todo.database.models.Task
import com.route.todo.databinding.FragmentAddTaskBinding
import java.time.LocalDateTime

class AddTaskFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding

    lateinit var onTaskAddedListener: (LocalDateTime) -> Unit

    private lateinit var vm: AddTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater)
        vm = ViewModelProvider(this)[AddTaskViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        writeTimeToTextView()

        writeDateToTextView()

        getTimePicker(vm.dateTime.hour, vm.dateTime.minute)

        getDatePicker(vm.dateTime.year, vm.dateTime.monthValue, vm.dateTime.dayOfMonth)

        binding.submitBtn.setOnClickListener {
            submitNewTask()
        }
    }

    private fun writeTimeToTextView() {
        binding.theTime.text = Editable.Factory.getInstance()
            .newEditable(vm.dateTime.format(vm.timeFormat))
    }

    private fun writeDateToTextView() {
        binding.theDate.text = Editable.Factory.getInstance()
            .newEditable(vm.dateTime.format(vm.dateFormat))
    }

    private fun getTimePicker(hour: Int, min: Int) {
        binding.theTime.setOnClickListener {
            val picker = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    vm.dateTime = vm.dateTime.withHour(hourOfDay).withMinute(minute)
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
                    vm.dateTime =
                        vm.dateTime.withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth)
                    writeDateToTextView()
                }, year, month - 1, day
            )
            picker.datePicker.minDate = System.currentTimeMillis()
            picker.show()
        }
    }

    private fun submitNewTask() {

        if (validateFields()) {

            val task = Task(
                title = binding.name.text.toString(),
                details = binding.details.text.toString(),
                date = vm.dateTime
            )

            TaskDataBase.getInstance()
                .getDao()
                .insertTask(task)
            vm.dateTime = vm.dateTime.withHour(0).withMinute(0)
            onTaskAddedListener(vm.dateTime)
            dismiss()
        }

    }

    private fun validateFields(): Boolean {
        if (binding.name.text?.trim()?.isEmpty() == true) {
            binding.name.error = "required"
            return false
        } else {
            binding.name.error = null
        }

        return true
    }
}