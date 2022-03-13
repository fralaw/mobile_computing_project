package com.example.mobile_computing_project.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_computing_project.util.Graph
import com.example.mobile_computing_project.data.entity.Reminder
import com.example.mobile_computing_project.data.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import java.lang.Math.pow
import java.util.*
import kotlin.math.sqrt

class HomeViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        viewModelScope.launch {
            val currentTime = Calendar.getInstance().timeInMillis;
            reminderRepository.clearReminders()
            reminderRepository.getAlreadyOccurredReminders(currentTime).collect { list ->
                _state.value = HomeViewState(
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
                _state.value = HomeViewState(
                    reminders = list
                )
            }
        }
    }

    fun updateReminders() {
        viewModelScope.launch {
            val currentTime = Calendar.getInstance().timeInMillis;
            reminderRepository.getAlreadyOccurredReminders(currentTime).collect { list ->
                _state.value = HomeViewState(
                    reminders = list,
                    null,
                    null
                )
            }
        }
    }
    fun locationReminders(location_x: Double, location_y: Double){
        viewModelScope.launch {
            val range: Double = 0.5
            reminderRepository.getReminders().collect { list ->
                val locationReminders : MutableList<Reminder> = arrayListOf()
                list.forEach {
                    var dist = sqrt(pow(location_x - it.location_x, 2.0) + pow(location_y - it.location_y, 2.0))
                    if(dist <= range){
                        locationReminders.add(it)
                    }
                }
                _state.value = HomeViewState(
                    reminders = locationReminders,
                    null,
                    null
                )
            }
        }
    }
}


data class HomeViewState(
    val reminders: List<Reminder> = emptyList(),
    var location_x: Double? = null,
    var location_y: Double? = null
)