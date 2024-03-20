package com.route.todo.fragments.addtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
        binding.lifecycleOwner = viewLifecycleOwner
        vm = ViewModelProvider(this)[AddTaskViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = vm
        binding.theTime.setOnClickListener {
            getTimePicker()
        }
        binding.theDate.setOnClickListener {
            getDatePicker()
        }
        subscribeToLiveData()
    }

    private fun getTimePicker() {
        val picker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                vm.dateLiveData.value =
                    vm.dateLiveData.value!!
                        .withHour(hourOfDay)
                        .withMinute(minute)
            }, vm.dateLiveData.value!!.hour, vm.dateLiveData.value!!.minute, false
        )
        picker.show()
    }

    private fun getDatePicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { _, newYear, newMonth, dayOfMonth ->
                vm.dateLiveData.value =
                    vm.dateLiveData.value!!
                        .withYear(newYear)
                        .withMonth(newMonth + 1)
                        .withDayOfMonth(dayOfMonth)
            },
            vm.dateLiveData.value!!.year,
            vm.dateLiveData.value!!.monthValue - 1,
            vm.dateLiveData.value!!.dayOfMonth
        )
        picker.datePicker.minDate = System.currentTimeMillis()
        picker.show()
    }

    private fun subscribeToLiveData() {
        vm.isDoneLiveData.observe(viewLifecycleOwner) {
            if (it) {
                vm.dateLiveData.value = vm.dateLiveData.value!!.withHour(0).withMinute(0)
                onTaskAddedListener(vm.dateLiveData.value!!)
                dismiss()
            }
        }
    }
}