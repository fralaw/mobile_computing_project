package com.example.mobile_computing_project

import android.content.Context
import androidx.room.Room
import com.example.mobile_computing_project.data.repository.ReminderRepository
import com.example.mobile_computing_project.data.room.AppDatabase

object Graph {
    lateinit var database: AppDatabase
        private set

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, AppDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}