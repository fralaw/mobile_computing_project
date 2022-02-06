package com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_computing_project.data.entity.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CategoryReminderViewModel : ViewModel() {
    private val _state = MutableStateFlow(CategoryReminderViewState())

    val state: StateFlow<CategoryReminderViewState>
        get() = _state

    init {
        val list = mutableListOf<Reminder>()
        for (x in 1..20) {
            list.add(
                Reminder(
                    reminderId = x.toLong(),
                    reminderTitle = "$x reminder",
                    reminderDescription = "Description $x",
                    reminderDateTime = LocalDateTime.now()
                )
            )
        }

        viewModelScope.launch {
            _state.value = CategoryReminderViewState(
                payments = list
            )
        }
    }
}

data class CategoryReminderViewState(
    val payments: List<Reminder> = emptyList()
)