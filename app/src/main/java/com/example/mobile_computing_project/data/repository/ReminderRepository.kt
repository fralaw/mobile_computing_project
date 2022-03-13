package com.example.mobile_computing_project.data.repository

import com.example.mobile_computing_project.data.entity.Reminder
import com.example.mobile_computing_project.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow
import java.util.*

class ReminderRepository(
    private val reminderDao : ReminderDao
) {
    fun getReminders(): Flow<List<Reminder>> = reminderDao.getReminders()
    suspend fun getReminderById(reminderId: Long?): Reminder? = reminderDao.getReminderById(reminderId)

    suspend fun addReminder(reminder: Reminder): Long{
        return when (val local = reminderDao.getReminderById(reminder.id)){
            null -> reminderDao.insert(reminder)
            else -> reminderDao.insert(reminder)
        }
    }

    fun getAlreadyOccurredReminders(currentTime: Long): Flow<List<Reminder>> = reminderDao.getAlreadyOccurredReminders(currentTime)
    suspend fun clearReminders(): Void = reminderDao.clearReminders()
    suspend fun deleteReminder(reminderId: Long): Void = reminderDao.deleteReminder(reminderId)
    suspend fun updateReminder(message: String?, location_x: Double?, location_y: Double?, reminder_time: Calendar?, reminderId: Long?):Void
                = reminderDao.updateReminder(message,location_x,location_y,reminder_time,reminderId)

}