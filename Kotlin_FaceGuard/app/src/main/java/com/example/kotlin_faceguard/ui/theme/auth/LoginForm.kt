package com.example.kotlin_faceguard.ui.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.kotlin_faceguard.R
import com.example.kotlin_faceguard.TextInput
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.constants.InputType


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginForm(navController: NavHostController) {
    Log.d("LoginForm", "Composing LoginForm")
    Scaffold(
        backgroundColor = Color.Gray // Set the background color for Scaffold
    ) {
        Column(
            Modifier
                .padding(30.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.faceguard_icon),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )

            TextInput(InputType.Username)
            TextInput(InputType.Password)

            Button(
                onClick = {

                    navController.navigate("home")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login", Modifier.padding(vertical = 8.dp))
            }

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account yet?", color = Color.White)
                TextButton(onClick = { navController.navigate("registration") }) {
                    Text("Sign Up")
                }
            }
        }
    }
}

