package com.example.mobile_computing_project

import android.util.Log
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    companion object {
        fun calendarToString(value: Calendar?): String {
            var toStore = ""
            toStore += value?.get(Calendar.DAY_OF_MONTH)
            toStore += "-" + value?.get(Calendar.MONTH - 1)
            toStore += "-" + value?.get(Calendar.YEAR)
            toStore += " " + value?.get(Calendar.HOUR_OF_DAY)
            toStore += ":" + value?.get(Calendar.MINUTE)
            toStore += ":" + value?.get(Calendar.SECOND)

            return toStore
        }

        fun stringToCalendar(value: String): Calendar {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)
            cal.time = sdf.parse(value)
            return cal
        }
    }
    @TypeConverter
    fun calendarToString(value: Calendar): String {
        var toStore = ""
        toStore += value.get(Calendar.DAY_OF_MONTH)
        toStore += "-" + value.get(Calendar.MONTH + 1)
        toStore += "-" + value.get(Calendar.YEAR)
        toStore += " " + value.get(Calendar.HOUR_OF_DAY)
        toStore += ":" + value.get(Calendar.MINUTE)
        toStore += ":" + value.get(Calendar.SECOND)

        return toStore
    }

    @TypeConverter
    public fun stringToCalendar(value: String): Calendar {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)
        cal.time = sdf.parse(value)
        return cal
    }

}
