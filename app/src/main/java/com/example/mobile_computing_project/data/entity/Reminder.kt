package com.example.mobile_computing_project.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity(
    tableName = "reminders",
    indices = [
        Index("id", unique = true)
    ]

)
data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "location_x") val location_x: Double,
    @ColumnInfo(name = "location_y") val location_y: Double,
    @ColumnInfo(name = "reminder_time") val reminder_time: Calendar,
    @ColumnInfo(name = "creation_time") val creation_time: Calendar,
    @ColumnInfo(name = "creator_id") val creator_id: Long,
    @ColumnInfo(name = "reminder_seen") val reminder_seen: Boolean,
)

//Message, location_x, location_y, reminder_time, creation_time, creator_id, reminder_seen