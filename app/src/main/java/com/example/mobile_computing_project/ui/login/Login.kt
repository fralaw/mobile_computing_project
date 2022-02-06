package com.example.mobile_computing_project.ui.login;

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController;
import com.google.accompanist.insets.systemBarsPadding
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.mobile_computing_project.R

@Composable
fun Login(
        navController: NavController,
        sp: SharedPreferences
) {

        Surface(modifier = Modifier.fillMaxSize()) {
                val username = rememberSaveable { mutableStateOf("") }
                val password = rememberSaveable { mutableStateOf("") }
                Image(
                        painter = painterResource(id = R.drawable.login_background),
                        contentDescription = null,
                        modifier = Modifier
                                .fillMaxSize()
                                .fillMaxHeight(),
                        alpha = 0.2f
                )
                Column(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .systemBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                ) {
                        Image(
                                painter = painterResource(id = R.drawable.logo_with_background),
                                contentDescription = null,
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .size(100.dp),
                                alpha = 0.6f
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                                value = username.value,
                                onValueChange = { data -> username.value = data },
                                label = { Text("Username") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text
                                )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                                value = password.value,
                                onValueChange = { data -> password.value = data },
                                label = { Text("Password") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password
                                ),
                                visualTransformation = PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                                onClick = { userCheck(username,password,sp, navController) },
                                enabled = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.small
                        ) {
                                Text(text = "Login")
                        }
                }

        }
}
fun userCheck(username: MutableState<String>, password: MutableState<String>, sp: SharedPreferences, navController: NavController){
        var key = String()
        key = username.value + password.value
        if(sp.contains(key)){
                navController.navigate("home")
        }
        else{
                return
        }
}