package com.example.mobile_computing_project.ui.reminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_computing_project.data.entity.Reminder
import com.google.accompanist.insets.systemBarsPadding
import java.util.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.mobile_computing_project.util.Converters
import com.example.mobile_computing_project.util.Graph
import com.google.android.gms.maps.model.LatLng

@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel(),
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val message = rememberSaveable { mutableStateOf("") }

    //val location_x = rememberSaveable { mutableStateOf(Float) }
    //val location_y = rememberSaveable { mutableStateOf(Float) }

    val year: Int
    val month: Int
    val day: Int
    val hour:Int
    val minute: Int

    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value


    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    val date = rememberSaveable { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        Graph.activityContext,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = assignCorrectDate(year,month,dayOfMonth)
        }, year, month, day
    )

    val time = rememberSaveable {mutableStateOf("")}
    hour = calendar.get(Calendar.HOUR_OF_DAY)
    minute = calendar.get(Calendar.MINUTE)
    val timePickerDialog = TimePickerDialog(
        Graph.activityContext,
        {_, hour : Int, minute: Int ->
            time.value = "$hour:$minute:00"
        }, hour, minute, true
    )
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
                Text(
                    text = "Selected date: ${date.value}"
                )

                Button(
                    enabled = true,
                    onClick = {
                        datePickerDialog.show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(40.dp)
                ) {
                    Text("Pick a date")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Selected date: ${time.value}"
                )
                Button(
                    enabled = true,
                    onClick = {
                        timePickerDialog.show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(40.dp)
                ) {
                    Text("Pick a time")
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (latlng == null) {
                    OutlinedButton(
                        onClick = { navController.navigate("map") },
                        modifier = Modifier.height(55.dp)
                    ) {
                        Text(text = "Payment location")
                    }
                } else {
                    Text(
                        text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
                    )
                }
            }
            var reminder:Reminder

            if (latlng != null && date.value != ""){
                reminder = Reminder(
                    message = message.value,
                    reminder_time = Converters.stringToCalendar(date.value + " " + time.value),
                    location_x = latlng!!.latitude,
                    location_y = latlng!!.longitude,
                    creation_time = Calendar.getInstance(),
                    creator_id = 0,
                    reminder_seen = false
                )
            }
            else if(latlng == null && date.value!= "") {
                reminder = Reminder(
                    message = message.value,
                    reminder_time = Converters.stringToCalendar(date.value + " " + time.value),
                    location_x = 0.0,
                    location_y = 0.0,
                    creation_time = Calendar.getInstance(),
                    creator_id = 0,
                    reminder_seen = false
                )
            }
            else if(latlng != null && date.value == "") {
                reminder = Reminder(
                    message = message.value,
                    reminder_time = Calendar.getInstance(),
                    location_x = latlng!!.latitude,
                    location_y = latlng!!.longitude,
                    creation_time = Calendar.getInstance(),
                    creator_id = 0,
                    reminder_seen = false
                )
            }
            else{
                reminder = Reminder(
                    message = message.value,
                    reminder_time = Calendar.getInstance(),
                    location_x = 0.0,
                    location_y = 0.0,
                    creation_time = Calendar.getInstance(),
                    creator_id = 0,
                    reminder_seen = false
                )

            }

                Button(
                    enabled = true,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveReminder(
                                reminder
                                )
                        }
                        onBackPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text(text= "Save reminder")
                }

            }
        }
    }
private fun assignCorrectDate(year: Int, month: Int, dayOfMonth: Int): String {
    var monthString = "${month+1}"
    var dayOfMonthString = "$dayOfMonth"
    if(month < 9) {
        monthString = "0${month+1}"
    }
    if(dayOfMonth < 10){
        dayOfMonthString = "0$dayOfMonth"
    }

    return "$dayOfMonthString-$monthString-$year"

}



/*Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)
                        .padding(top = 10.dp)
                        .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
                        .clickable {
                            datePickerDialog.show()
                        }
                ) {

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        val (lable, iconView) = createRefs()

                        Text(
                            text = "Date Picker",
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(lable) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(iconView.start)
                                    width = Dimension.fillToConstraints
                                }
                        )

                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp, 20.dp)
                                .constrainAs(iconView) {
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                },
                            tint = MaterialTheme.colors.onSurface
                        )

                    }
                }
                */
