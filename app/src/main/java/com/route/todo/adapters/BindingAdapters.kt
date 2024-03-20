package com.route.todo.adapters

import android.text.Editable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("formatTaskTime")
fun TextView.setTaskTime(dateTime: LocalDateTime) {
    val timeFormat = DateTimeFormatter.ofPattern("E hh:mm a")
    text = dateTime.format(timeFormat)
}

@BindingAdapter("error")
fun TextInputEditText.setErrorMessage(errorMessage: String?) {
    error = errorMessage
}

@BindingAdapter("formatDate")
fun TextInputEditText.setFormatDate(dateTime: LocalDateTime) {
    val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    text = Editable.Factory.getInstance()
        .newEditable(dateTime.format(dateFormat))
}

@BindingAdapter("formatTime")
fun TextInputEditText.setFormatTime(dateTime: LocalDateTime) {
    val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")
    text = Editable.Factory.getInstance()
        .newEditable(dateTime.format(timeFormat))
}