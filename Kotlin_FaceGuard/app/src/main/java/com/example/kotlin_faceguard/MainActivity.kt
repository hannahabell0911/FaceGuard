package com.example.kotlin_faceguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kotlin_faceguard.ui.theme.Kotlin_FaceGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kotlin_FaceGuardTheme {
                // Main content goes here
                MainScreen()
            }
        }
    }
}

@Composable

fun MainScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Login) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray // Set the background color to grey here
    ) {
        when (currentScreen) {
            Screen.Login -> LoginForm(
                onLoginSuccess = { currentScreen = Screen.LiveFeed },
                onSignUpClicked = { currentScreen = Screen.Registration }
            )
            Screen.Registration -> RegistrationForm { currentScreen = Screen.Login }
            Screen.LiveFeed -> LiveFeedScreen { currentScreen = Screen.AddNewFace }
            Screen.AddNewFace -> AddNewFace { currentScreen = Screen.LiveFeed }
        }
    }
}


@Composable
fun LoginForm(onLoginSuccess: () -> Unit, onSignUpClicked: () -> Unit) {
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

        Button(onClick = onLoginSuccess, modifier = Modifier.fillMaxWidth()) {
            Text("Login", Modifier.padding(vertical = 8.dp))
        }

        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an Account yet?", color = Color.White)
            TextButton(onClick = onSignUpClicked) {
                Text("Sign Up")
            }
        }
    }
}

@Composable
fun RegistrationForm(onLoginClicked: () -> Unit) {
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

        Button(onClick = onLoginClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Register", Modifier.padding(vertical = 8.dp))
        }

        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 48.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an Account?", color = Color.White)
            TextButton(onClick = onLoginClicked) {
                Text("Login")
            }
        }
    }
}

@Composable
fun LiveFeedScreen(onAddFaceClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Not Recognised",
                color = Color.White,
                style = MaterialTheme.typography.displaySmall
            )

            Button(onClick = onAddFaceClicked) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Box(
            modifier = Modifier
                .size(280.dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.hannah), // Replace with actual resource
                contentDescription = "Live feed image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Today", color = Color.White, style = MaterialTheme.typography.headlineSmall)
            Text("Motion detected at: 6.28 am", color = Color.White, style = MaterialTheme.typography.headlineSmall)
            Text("Face detected: Mary Smith", color = Color.White, style = MaterialTheme.typography.headlineSmall)
            Text("Relationship: Homeowner", color = Color.White, style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.weight(1f))

        ChatMessageBox()
    }
}

@Composable
fun AddNewFace(onBackToLiveFeed: () -> Unit) {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(inputType: InputType) {
    var value by remember { mutableStateOf("") }
    TextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null) },
        label = { Text(inputType.label) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatMessageBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White, shape = MaterialTheme.shapes.small)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Send message at the Door", color = Color.Gray) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    placeholderColor = Color.Gray
                ),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent)
            )

            IconButton(onClick = { /* Handle send */ }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Message",
                    tint = Color.Black
                )
            }
        }
    }
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Username : InputType(
        "Username",
        Icons.Default.Person,
        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        VisualTransformation.None
    )
    object Password : InputType(
        "Password",
        Icons.Default.Lock,
        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        VisualTransformation.None
    )
    object FirstName : InputType(
        "First Name",
        Icons.Default.Person,
        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        VisualTransformation.None
    )
    object Surname : InputType(
        "Surname",
        Icons.Default.Person,
        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        VisualTransformation.None
    )
    object Email : InputType(
        "Email",
        Icons.Default.Email,
        KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        VisualTransformation.None
    )
    object ConfirmPassword : InputType(
        "Confirm Password",
        Icons.Default.Lock,
        KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        VisualTransformation.None
    )
    object relation : InputType(
        "Relation",
        Icons.Default.Person,
        KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        VisualTransformation.None
    )

}

enum class Screen {
    Login,
    Registration,
    LiveFeed,
    AddNewFace
}
