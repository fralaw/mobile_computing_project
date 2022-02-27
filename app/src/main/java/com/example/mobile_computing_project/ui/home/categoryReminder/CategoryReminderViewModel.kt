package com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_computing_project.util.Graph
import com.example.mobile_computing_project.data.entity.Reminder
import com.example.mobile_computing_project.data.repository.ReminderRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import java.util.*

class CategoryReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryReminderViewState())

    val state: StateFlow<CategoryReminderViewState>
        get() = _state

    init {
        viewModelScope.launch {
            val currentTime = Calendar.getInstance().timeInMillis;
            reminderRepository.clearReminders()
            reminderRepository.getAlreadyOccurredReminders(currentTime).collect { list ->
                _state.value = CategoryReminderViewState(
                    reminders = list
                )
            }
        }
    }
    fun deleteReminder(id: Long) {
        viewModelScope.launch {
            val currentTime = Calendar.getInstance().timeInMillis;
            reminderRepository.deleteReminder(id)
            reminderRepository.getAlreadyOccurredReminders(currentTime).collect { list ->
                _state.value = CategoryReminderViewState(
                    reminders = list
                )
            }
        }
    }

}


data class CategoryReminderViewState(
    val reminders: List<Reminder> = emptyList()
)