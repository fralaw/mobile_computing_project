package com.example.mobile_computing_project.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_computing_project.ui.theme.Mobile_computing_projectTheme
import com.example.mobile_computing_project.util.Graph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Graph.provideActivityContext(this)
        super.onCreate(savedInstanceState)
        val sp = getSharedPreferences("users", MODE_PRIVATE);
        with (sp.edit()) {
            putString("userpassword","userpassword")
            apply()
        }
        setContent {
            Mobile_computing_projectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RemindersApp(sp)
                }
            }
        }
    }
}
