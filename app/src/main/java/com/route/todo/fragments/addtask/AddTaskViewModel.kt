package com.route.todo.fragments.addtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddTaskViewModel : ViewModel() {

    var dateTime: LocalDateTime = LocalDateTime.now()
    val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")

    val titleLiveData = MutableLiveData("")
    val descriptionLiveData = MutableLiveData("")
    val dateLiveData = MutableLiveData("")
    val timeLiveData = MutableLiveData("")

}