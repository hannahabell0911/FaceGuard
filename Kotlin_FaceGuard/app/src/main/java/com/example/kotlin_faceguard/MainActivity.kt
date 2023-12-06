package com.example.kotlin_faceguard

import android.annotation.SuppressLint
import android.graphics.Color.rgb
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kotlin_faceguard.ui.theme.Kotlin_FaceGuardTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController


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

enum class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    Home("home", Icons.Filled.Home, "Home"),
    LiveFeed("liveFeed", Icons.Filled.PlayArrow, "Live Feed"),
    AddNewFace("addNewFace", Icons.Filled.Add, "Add New Face")
    // Add other navigation items if needed
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Login) }

    Scaffold(
//        bottomBar = {
//            if (currentScreen != Screen.Login && currentScreen != Screen.Registration) {
//                BottomNavigationBar(currentScreen) { screen ->
//                    currentScreen = screen
//                }
//            }
//        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray
        ) {
            when (currentScreen) {
                Screen.Login -> LoginForm(
                    onLoginSuccess = { currentScreen = Screen.HomePage },
                    onSignUpClicked = { currentScreen = Screen.Registration }
                )
                Screen.Registration -> RegistrationForm { currentScreen = Screen.Login }
                Screen.HomePage -> HomeScreen()
//                Screen.LiveFeed -> LiveFeedScreen()
                Screen.AddNewFace -> AddNewFace()
                // Other cases as necessary
            }
        }
    }
}


//@Composable
//fun BottomNavigationBar(currentScreen: Screen, onItemSelected: (Screen) -> Unit) {
//    BottomNavigation {
//        BottomNavItem.values().forEach { item ->
//            BottomNavigationItem(
//                icon = { Icon(item.icon, contentDescription = null) },
//                label = { Text(item.label) },
//                selected = currentScreen.name == item.route,
//                onClick = { onItemSelected(Screen.valueOf(item.route)) }
//            )
//        }
//    }
//}




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
            Text("Don't have an account yet?", color = Color.White)
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

        // Additional Spacer for pushing the button down
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLoginClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Register", Modifier.padding(vertical = 8.dp))
        }

        // Pushing the divider further down
        Spacer(modifier = Modifier.height(48.dp))

        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp) // Adjust padding as needed
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an Account?", color = Color.White)
            TextButton(onClick = onLoginClicked) {
                Text("Login")
            }
        }
    }
}

val CustomGray = Color(rgb(136, 136, 136)) // Custom gray color
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun HomeScreen() {
    var currentScreen by remember { mutableStateOf("home") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "John's Home",
                        color = Color.Black,
                        style = MaterialTheme.typography.displaySmall// Custom typography
                    )
                },
//                backgroundColor = MaterialTheme.colors.primary, // Use theme's primary color
                actions = {
                    // Add an action icon if necessary
                    IconButton(onClick = { /* Handle action */ }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                }
            )
        },
        content = {
            when (currentScreen) {
                "home" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(CustomGray)
                            .padding(40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.photooutputbgchange__1_),
                            contentDescription = "Center Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(50.dp)
                                .height(300.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FeatureCard(title = "View Live Feed", icon = Icons.Default.Warning, onClick = { currentScreen = "liveFeed" })
                            FeatureCard(title = "Known Faces", icon = Icons.Default.Face) {

                            }

                        }
                    }
                }
                "liveFeed" -> LiveFeedScreen(
                    onAddFaceClicked = { currentScreen = "addNewFace" }
                )
                "addNewFace" -> AddNewFace()

            }
        }
    )
}




@Composable
fun FeatureCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    val cardSize = 150.dp
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .size(cardSize)
            .aspectRatio(1f)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontWeight = FontWeight.Medium)
        }
    }
}


@Composable
fun LiveFeedScreen(onAddFaceClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray) // Set the background color to gray
            .padding(30.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp)) // Adjust the height to lower the position of the text and button

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {Text(
            text = "Unknown",
            color = Color.White,
            style = TextStyle(fontSize = 40.sp), // Increased font size
            modifier = Modifier.weight(4f)
        )

            Button(
                onClick = onAddFaceClicked,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .height(250.dp) // Adjust the height as needed
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.hannah), // Replace with your actual drawable resource
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
            Text("Face detected: Unknown", color = Color.White, style = MaterialTheme.typography.headlineSmall)
            Text("Relationship: Unknown", color = Color.White, style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.weight(1f))


        ChatMessageBox()
    }
}
@Composable
fun AddNewFace() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Gray) // Set background color to gray
            .padding(horizontal = 12.dp), // Added horizontal padding
        verticalArrangement = Arrangement.Top, // Changed to Top for pulling contents upward
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp)) // Increased height for upper spacing

        Text(
            text = "Add Face",
            style = MaterialTheme.typography.displaySmall,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Image(
            painter = painterResource(id = R.drawable.hannah), // Replace with the actual image resource
            contentDescription = "Logo",
            modifier = Modifier.size(300.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextInput(InputType.FirstName)
        Spacer(modifier = Modifier.height(8.dp))
        TextInput(InputType.Surname)
        Spacer(modifier = Modifier.height(8.dp))
        TextInput(InputType.relation)

        Spacer(modifier = Modifier.height(30.dp)) // Additional spacer for spacing before the button

        Button(
            onClick = { /* Implement add face logic */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Face", Modifier.padding(10.dp))
        }

        Spacer(modifier = Modifier.height(30.dp)) // Additional bottom spacer for navigation space
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(inputType: InputType) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), // Added padding for spacing between text fields
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null) },
        label = { Text(inputType.label) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            containerColor = Color.Transparent, // You can adjust this color for a different background
            textColor = Color.Black, // Text color
            focusedLabelColor = Color.White, // Label color when focused
            unfocusedLabelColor = Color.White // Label color when unfocused
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        shape = RoundedCornerShape(16.dp) // Set the shape to be more rounded
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
    HomePage,
    LiveFeed,
    AddNewFace
}