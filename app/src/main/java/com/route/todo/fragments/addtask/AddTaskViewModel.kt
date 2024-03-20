package com.route.todo.fragments.addtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.route.todo.database.TaskDataBase
import com.route.todo.database.models.Task
import java.time.LocalDateTime

class AddTaskViewModel : ViewModel() {

    val dateLiveData = MutableLiveData(LocalDateTime.now())
    val titleLiveData = MutableLiveData("")
    val titleErrorLiveData = MutableLiveData<String?>(null)
    val descriptionLiveData = MutableLiveData("")
    val isDoneLiveData = MutableLiveData(false)

    fun addTask() {
        if (validateFields()) {
            val task = Task(
                title = titleLiveData.value.toString(),
                details = descriptionLiveData.value.toString(),
                date = dateLiveData.value
            )
            TaskDataBase.getInstance()
                .getDao()
                .insertTask(task)
            isDoneLiveData.value = true
        }
    }

    private fun validateFields(): Boolean {
        if (titleLiveData.value?.trim()?.isEmpty() == true) {
            titleErrorLiveData.value = "required"
            return false
        } else {
            titleErrorLiveData.value = null
        }
        return true
    }
}