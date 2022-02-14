package com.example.mobile_computing_project.ui.reminder

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_computing_project.data.entity.Reminder
import com.google.accompanist.insets.systemBarsPadding
import java.util.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.example.mobile_computing_project.Converters


@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val message = rememberSaveable { mutableStateOf("") }

    //val location_x = rememberSaveable { mutableStateOf(Float) }
    //val location_y = rememberSaveable { mutableStateOf(Float) }

    val reminder_time = rememberSaveable { mutableStateOf("") }
    val creation_time = rememberSaveable { mutableStateOf("") }
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
                Text(text = "New Reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {

                OutlinedTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    label = { Text(text = "") },
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
                            viewModel.saveReminder(
                                Reminder(
                                    message = message.value,
                                    reminder_time = reminder_time.value,
                                    location_x = 0,
                                    location_y = 0,
                                    creation_time = Converters.calendarToString(Calendar.getInstance()),
                                    creator_id = 0,
                                    reminder_seen = false
                                )
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
