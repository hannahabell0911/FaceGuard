package com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kotlin_faceguard.R
import com.yourpackage.api.com.example.kotlin_faceguard.TextInput

import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.constants.InputType


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegistrationForm(navController: NavHostController) {
    Log.d("RegistrationForm", "Composing Registeration")
    Scaffold(
        backgroundColor = Color.Gray // Set the background color for Scaffold
    ) {
        Column(
            Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.faceguard_icon),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )

            TextInput(InputType.FirstName)
            TextInput(InputType.Surname)
            TextInput(InputType.Email)
            TextInput(InputType.Password)
            TextInput(InputType.ConfirmPassword)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { navController.navigate("login") }, modifier = Modifier.fillMaxWidth()) {
                Text("Register", Modifier.padding(vertical = 8.dp))
            }

            Spacer(modifier = Modifier.height(48.dp))

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an Account?", color = Color.White)
                TextButton(onClick = { navController.navigate("login") }) {
                    Text("Login")
                }
            }
        }
    }
}