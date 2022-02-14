package com.example.mobile_computing_project.ui.editReminder

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_computing_project.Converters
import com.example.mobile_computing_project.data.entity.Reminder
import com.google.accompanist.insets.systemBarsPadding
import java.util.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mobile_computing_project.ui.editReminder.EditReminderViewModel
import com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder.CategoryReminderViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay


@Composable
fun EditReminder(
    onBackPress: () -> Unit,
    reminderId: Long?,
) {

    val viewModel: EditReminderViewModel = EditReminderViewModel(reminderId)
    val viewState by viewModel.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val message = rememberSaveable { mutableStateOf("") }

    val location_x = rememberSaveable { mutableStateOf(0) }
    val location_y = rememberSaveable { mutableStateOf(0) }

    val reminder_time = rememberSaveable { mutableStateOf("") }
    val creation_time = rememberSaveable { mutableStateOf("") }

    if(viewState.reminder != null){
        Log.d("tag", viewState.reminder.toString())
        message.value = viewState.reminder!!.message

        location_x.value = viewState.reminder!!.location_x
        location_y.value = viewState.reminder!!.location_y

        reminder_time.value = viewState.reminder!!.reminder_time
        creation_time.value = viewState.reminder!!.creation_time
    }

    Surface {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Edit Reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {

                OutlinedTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    label = { Text(text = "Reminder Message") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = reminder_time.value,
                    onValueChange = { reminder_time.value = it },
                    label = { Text(text = "Reminder time") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    enabled = true,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateReminder(
                                message.value,
                                location_x.value,
                                location_y.value,
                                Converters.stringToCalendar(reminder_time.value),
                                viewState.reminder?.id
                            )
                        }
                        onBackPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Save reminder")
                }
            }
        }
    }
}
