package com.example.mobile_computing_project.ui.editReminder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_computing_project.Graph
import com.example.mobile_computing_project.data.entity.Reminder
import com.example.mobile_computing_project.data.repository.ReminderRepository
import com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder.CategoryReminderViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class EditReminderViewModel(
    private val reminderId: Long?,
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
): ViewModel()
{
    private val _state = MutableStateFlow(EditReminderViewState())
    val state: StateFlow<EditReminderViewState>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = EditReminderViewState(
                reminder = reminderRepository.getReminderById(reminderId)
            )

        }
    }

    suspend fun updateReminder(message: String?, location_x: Int?, location_y: Int?, reminder_time: Calendar?, reminderId: Long?){
        reminderRepository.updateReminder(message, location_x, location_y, reminder_time,reminderId)
    }
}

data class EditReminderViewState(
    val reminder: Reminder? = null
)