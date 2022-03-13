package com.example.mobile_computing_project.data.room

import androidx.room.*
import com.example.mobile_computing_project.data.entity.Reminder
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class ReminderDao {

    @Query(value = "DELETE FROM reminders")
    abstract suspend fun clearReminders(): Void

    @Query(value = "UPDATE reminders SET message = :message, location_x = :location_x, location_y = :location_y, reminder_time = :reminder_time WHERE id = :reminderId")
    abstract suspend fun updateReminder(
        message: String?,
        location_x: Double?,
        location_y: Double?,
        reminder_time: Calendar?,
        reminderId: Long?
    ): Void

    @Query(value = "DELETE FROM reminders WHERE id = :reminderId")
    abstract suspend fun deleteReminder(reminderId: Long): Void

    @Query(value = "SELECT * FROM reminders LIMIT 15")
    abstract fun getReminders(): Flow<List<Reminder>>

    @Query(value = "SELECT * FROM reminders WHERE reminder_time <= :currentTime LIMIT 15")
    abstract fun getAlreadyOccurredReminders(currentTime: Long): Flow<List<Reminder>>

    @Query(value = "SELECT * FROM reminders WHERE id = :reminderId")
    abstract suspend fun getReminderById(reminderId: Long?): Reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entities: Collection<Reminder>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder): Int

}