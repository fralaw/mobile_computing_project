package com.example.mobile_computing_project.ui.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.mobile_computing_project.R
import com.example.mobile_computing_project.util.Graph
import com.example.mobile_computing_project.data.entity.Reminder
import com.example.mobile_computing_project.data.repository.ReminderRepository
import com.example.mobile_computing_project.util.NotificationWorker
import java.time.Duration
import java.util.*

import android.widget.RemoteViews




class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {
    init {
        createNotificationChannel(context = Graph.appContext)
    }
    suspend fun saveReminder(reminder: Reminder): Long {
        scheduleReminderNotification(reminder)
        scheduleDoubleNotification(reminder)
        return reminderRepository.addReminder(reminder)
    }

}

private fun scheduleReminderNotification(reminder: Reminder) {
    var delay:Long = reminder.reminder_time.time.time - reminder.creation_time.time.time
    val workManager = WorkManager.getInstance(Graph.appContext)
    var notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(Duration.ofMillis(delay))
        .build()
    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                reminderNotification(reminder)
            }
        }
}

private fun scheduleDoubleNotification(reminder: Reminder) {
    var delay:Long = reminder.reminder_time.time.time - reminder.creation_time.time.time + 60000
    Log.i("delay", delay.toString())
    val workManager = WorkManager.getInstance(Graph.appContext)
    var notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(Duration.ofMillis(delay))
        .build()
    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                doubleReminderNotification(reminder)
            }
        }
}

private fun createNotificationChannel(context: Context){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val name = "New Reminder"
        val descriptionText = "Get a notification on newely created reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("NewReminder", name, importance).apply {
            description = descriptionText;
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private fun setSuccessNotification(){
    val notificationId = 1
    val builder = NotificationCompat.Builder(Graph.appContext, "NewReminder")
        .setSmallIcon(R.drawable.logo)
        .setContentTitle("Reminder created successfully")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(Graph.appContext)) {
        //notificationId is unique for each notification that you define
        notify(notificationId, builder.build())
    }
}

private fun reminderNotification(reminder: Reminder){
    val expandedView = RemoteViews(
        Graph.appContext.packageName,
        R.layout.notification_expanded
    )
    val collapsedView = RemoteViews(
        Graph.appContext.packageName,
        R.layout.notification_collapsed
    )
    expandedView.setTextViewText(R.id.title_view_expanded, "Reminder!");
    expandedView.setTextViewText(R.id.message_view_expanded, reminder.message)

    val notificationId = 2
    val builder = NotificationCompat.Builder(Graph.appContext, "NewReminder")
        .setSmallIcon(R.drawable.logo)
        .setCustomBigContentView(expandedView)
        .setCustomContentView(collapsedView)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}

private fun doubleReminderNotification(reminder: Reminder){

    val notificationId = 3
    val builder = NotificationCompat.Builder(Graph.appContext, "NewReminder")
        .setSmallIcon(R.drawable.logo)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentTitle("Do you remember?")
        .setContentText(reminder.message)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())

    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}
