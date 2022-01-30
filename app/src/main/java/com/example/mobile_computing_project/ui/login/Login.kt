package com.example.mobile_computing_project.ui.login;

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable;
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

@Composable
fun Login(
        navController: NavController
) {
        Surface(modifier = Modifier.fillMaxSize()) {
                val username = rememberSaveable { mutableStateOf("") }
                val password = rememberSaveable { mutableStateOf("") }

                Column(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .systemBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                ) {
                        Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.size(200.dp)
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
                                onClick = { navController.navigate("payment") },
                                enabled = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.small
                        ) {
                                Text(text = "Login")
                        }
                }
        }
}
