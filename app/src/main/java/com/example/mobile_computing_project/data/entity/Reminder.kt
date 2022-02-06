package com.example.mobile_computing_project.data.entity
import java.util.Date
import java.util.Locale
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.ZoneOffset
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

data class Reminder(
    val reminderId: Long,
    val reminderTitle: String,
    val reminderDescription: String,
    val reminderDateTime: LocalDateTime
)
