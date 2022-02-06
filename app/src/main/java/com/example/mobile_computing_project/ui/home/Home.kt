package com.example.mobile_computing_project.ui.home

import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_computing_project.R
import com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder.CategoryReminder
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Home(
    navController: NavController
) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(
                navController = navController
            )
        }
    }


@Composable
fun HomeContent(
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 36.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                contentColor = Color.Blue,
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.onPrimary

            HomeAppBar(
                backgroundColor = appBarColor,
            )

            CategoryReminder(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun HomeAppBar(
    backgroundColor: Color
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.reminders),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            IconButton( onClick = {} ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = stringResource(R.string.search))
            }
            IconButton( onClick = {} ) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = stringResource(R.string.logout))
            }
        }
    )
}
