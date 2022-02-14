package com.example.mobile_computing_project.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mobile_computing_project.Converters
import com.example.mobile_computing_project.data.entity.Reminder

@Database(
    entities = [Reminder::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}