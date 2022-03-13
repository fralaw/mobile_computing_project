package com.example.mobile_computing_project.util

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.room.Room
import com.example.mobile_computing_project.data.repository.ReminderRepository
import com.example.mobile_computing_project.data.room.AppDatabase

object Graph {
    lateinit var database: AppDatabase
        private set

    lateinit var appContext: Context

    lateinit var activityContext: Context

    lateinit var activity: Activity

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    var stt = mutableStateOf("")

    fun provide(context: Context){
        appContext = context
        database = Room.databaseBuilder(context, AppDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    fun provideActivityContext(context: Context){
        activityContext = context
    }
    fun provideActivity(activity: Activity){
        this.activity = activity
    }
    fun provideStt(string: String){
        stt.value = string
    }
}