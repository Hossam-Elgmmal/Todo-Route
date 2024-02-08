package com.route.todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todo.databinding.FragmentAddTaskBinding

class AddTaskFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAddTaskBinding
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

        binding.submitBtn.setOnClickListener {
            if (validateFields()) {

                // save
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