package com.example.mobile_computing_project.ui.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_computing_project.Graph
import com.example.mobile_computing_project.data.entity.Reminder
import com.example.mobile_computing_project.data.repository.ReminderRepository
import com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder.CategoryReminderViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {

    suspend fun saveReminder(reminder: Reminder): Long {
        return reminderRepository.addReminder(reminder)
    }


}
