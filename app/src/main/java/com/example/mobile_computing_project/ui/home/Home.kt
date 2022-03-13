package com.example.mobile_computing_project.ui.home

import android.util.Log
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
import androidx.compose.material.icons.filled.*
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
import com.example.mobile_computing_project.ui.home.categoryReminder.CategoryReminder
import com.example.mobile_computing_project.ui.home.categoryReminder.categoryReminder.CategoryReminderViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng

@Composable
fun Home(
    navController: NavController
)
    {
        val viewModel: HomeViewModel = viewModel()
        val viewState by viewModel.state.collectAsState()

        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(
                navController = navController,
                viewModel = viewModel
            )
        }
    }


@Composable
fun HomeContent(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val viewState by viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.padding(bottom = 36.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("reminder")},
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
                viewModel,
                navController
            )

            CategoryReminder(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                homeViewModel = viewModel
            )
        }
    }
}

@Composable
private fun HomeAppBar(
    backgroundColor: Color,
    viewModel: HomeViewModel,
    navController: NavController
) {
    var latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value

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
            IconButton( onClick = {viewModel.updateReminders()} ){
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Refresh")
            }
            IconButton( onClick = {navController.navigate("map")}){
                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Map")
            }
        }
    )
    if (latlng != null){
        Log.i("LatLng: ", latlng.toString())
        if(latlng.latitude == 65.06 && latlng.longitude == 25.47){
            viewModel.updateReminders()

        }
        else{
            viewModel.locationReminders(latlng.latitude, latlng.longitude)
        }

    }

}
