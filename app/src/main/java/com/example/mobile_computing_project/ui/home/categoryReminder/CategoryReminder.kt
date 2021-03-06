package com.example.mobile_computing_project.ui.home.categoryReminder

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.ListenableWorker
import androidx.work.Operation
import com.example.mobile_computing_project.R
import com.example.mobile_computing_project.data.entity.Reminder
import com.example.mobile_computing_project.ui.home.HomeViewModel
import com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder.CategoryReminderViewModel
import com.example.mobile_computing_project.util.Converters
import com.example.mobile_computing_project.util.Graph
import java.util.*

@Composable
fun CategoryReminder(
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    //val viewModel: CategoryReminderViewModel = viewModel()
    val viewState by homeViewModel.state.collectAsState()

    Column(modifier = modifier) {
        ReminderList(homeViewModel,
            list = viewState.reminders,
            navController
        )
    }
}

@Composable
private fun ReminderList(
    homeViewModel: HomeViewModel,
    list: List<Reminder>,
    navController: NavController
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            ReminderListItem(
                reminder = item,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth(),
                viewModel = homeViewModel,
                navController
            )
        }
    }
}


@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavController
) {
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, reminderTitle, reminderDescription, editicon,deleteicon, date, topspacer,bottomspacer, ttsicon) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )
        Spacer(modifier = Modifier.constrainAs(topspacer){
            linkTo(
                start = parent.start,
                end = parent.absoluteRight,
                startMargin = 24.dp,
                endMargin = 16.dp,
                bias = 0f // float this towards the start. this was is the fix we needed
            )
            top.linkTo(parent.top, margin = 15.dp)
            height = Dimension.fillToConstraints
            width = Dimension.preferredWrapContent
        })
        // title
        Text(
            text = Converters.calendarToString(reminder.reminder_time),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderTitle) {
                linkTo(
                    start = parent.start,
                    end = editicon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        //description
        Text(
            text = reminder.message,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(reminderDescription) {
                linkTo(
                    start = parent.start,
                    end = editicon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(reminderTitle.top, margin = 40.dp)
                width = Dimension.preferredWrapContent
            }
        )
        // date
        Text(
            text = Converters.calendarToString(reminder.reminder_time),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(date) {
                linkTo(
                    start = reminderTitle.end,
                    end = parent.absoluteRight,
                    startMargin = 120.dp,
                    endMargin = 10.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(reminderDescription.bottom, 40.dp)
                bottom.linkTo(bottomspacer.top, 10.dp)
            }
        )

        IconButton(
            onClick = { navController.navigate("editReminder/" +
                    "${reminder.id}") },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(editicon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(ttsicon.start)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(R.string.check_mark)
            )
        }

        // icon
        IconButton(
            onClick = {viewModel.deleteReminder(reminder.id)},
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(deleteicon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.check_mark)
            )
        }
        IconButton(
            onClick = { textToSpeech(reminder) },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(ttsicon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(deleteicon.start)
                }
        ){
            Icon(
                imageVector = Icons.Filled.Call,
                contentDescription = "tts"
            )
        }
        Spacer(modifier = Modifier.constrainAs(bottomspacer){
            linkTo(
                start = parent.start,
                end = parent.absoluteRight,
                startMargin = 24.dp,
                endMargin = 16.dp,
                bias = 0f // float this towards the start. this was is the fix we needed
            )
            bottom.linkTo(parent.bottom, margin = 10.dp)

            width = Dimension.preferredWrapContent
        })
    }

}

private fun textToSpeech(reminder: Reminder){
    lateinit var tts: TextToSpeech
    tts = TextToSpeech(Graph.appContext,TextToSpeech.OnInitListener {
        if(it == TextToSpeech.SUCCESS){
            tts.language = Locale.US
            tts.setSpeechRate(1.0f)
            tts.speak(reminder.message, TextToSpeech.QUEUE_ADD, null)
        }
    })
}


