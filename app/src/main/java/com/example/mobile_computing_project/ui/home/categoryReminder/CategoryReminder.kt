package com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder.CategoryReminderViewModel
import com.example.mobile_computing_project.R
import com.example.mobile_computing_project.data.entity.Reminder
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun CategoryReminder(
    modifier: Modifier = Modifier
) {
    val viewModel: CategoryReminderViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        ReminderList(
            list = viewState.payments
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ReminderList(
    list: List<Reminder>
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
            )
        }
    }
}


@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, reminderTitle, reminderDescription, icon, date, topspacer,bottomspacer) = createRefs()
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
            text = reminder.reminderTitle,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderTitle) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
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
            text = reminder.reminderDescription,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(reminderDescription) {
                linkTo(
                    start = reminderTitle.end,
                    end = icon.start,
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
            text = reminder.reminderDateTime.format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),

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

        // icon
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(R.string.check_mark)
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
