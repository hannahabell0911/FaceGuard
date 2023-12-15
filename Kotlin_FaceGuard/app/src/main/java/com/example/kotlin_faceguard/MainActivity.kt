package com.yourpackage.api.com.example.kotlin_faceguard


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color.rgb
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kotlin_faceguard.ui.theme.Kotlin_FaceGuardTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TextFieldDefaults
import kotlinx.coroutines.launch
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kotlin_faceguard.R
import com.example.kotlin_faceguard.ui.ReminderScreen
import com.example.kotlin_faceguard.ui.auth.LoginForm
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.History.HistoryScreen
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.auth.RegistrationForm
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.constants.ChatMessageBox
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.constants.InputType
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.knownFaces.KnownFacesScreen
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.reminders.AppDatabase
import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.reminders.Reminder

import com.yourpackage.api.com.example.kotlin_faceguard.ui.theme.settings.SettingsScreen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate started")
        setContent {
            Kotlin_FaceGuardTheme {

                // Main content goes here
                MainScreen()
            }
        }
    }
}



sealed class NavScreen(val route: String) {
    object History : NavScreen("History")
    object Home : NavScreen("Home")

    object Settings : NavScreen("Settings")
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            if (getCurrentRoute(navController) !in listOf("splash", Screen.Login.route, Screen.Registration.route)) {
                BottomNavigationBar(navController, drawerState, coroutineScope)
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") { SplashScreen(navController) }
            composable(Screen.Login.route) { LoginForm(navController) }
            composable(Screen.Registration.route) { RegistrationForm(navController) }
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.History.route) { HistoryScreen(navController) }
            composable(Screen.Settings.route) { SettingsScreen(navController) }
            composable(Screen.LiveFeed.route) { LiveFeedScreen(navController) }
            composable(Screen.AddNewFace.route) { AddNewFace(navController) }
            composable(Screen.KnownFaces.route) { KnownFacesScreen(navController) }
            composable(Screen.ReminderScreen.route) {
                val viewModel: ReminderViewModel = viewModel() // This line is important
                ReminderScreen(navController, viewModel)
            }
            // ... other composable routes ...
        }
    }
}




@Composable
fun SplashScreen(navController: NavController) {
    val visible = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        delay(2000) // Duration of the splash screen
        visible.value = false
        delay(500) // Additional delay for the fade-out animation
        navController.navigate(Screen.Login.route) {
            popUpTo("splash") { inclusive = true }
        }
    }

    AnimatedVisibility(
        visible = visible.value,
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        // Splash screen UI with background image positioned higher and text at the bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            contentAlignment = Alignment.TopCenter
        ) {

            Image(
                painter = painterResource(id = R.drawable.faceguard_icon),
                contentDescription = "Splash Background",
                modifier = Modifier
                    .size(320.dp)
                    .padding(top = 130.dp)
            )

            // FaceGuard Text
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "FaceGuard",
                    style = TextStyle(
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        fontSize = 90.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(bottom = 270.dp)
                )
            }
        }
    }
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    BottomNavigation(
        backgroundColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White) },
            label = { Text("Home", color = Color.White) },
            selected = navController.currentDestination?.route == Screen.Home.route,
            onClick = {
                if (navController.currentDestination?.route != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Warning, contentDescription = "History", tint = Color.White) },
            label = { Text("History", color = Color.White) },
            selected = navController.currentDestination?.route == Screen.History.route,
            onClick = {
                if (navController.currentDestination?.route != Screen.History.route) {
                    navController.navigate(Screen.History.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Add Reminder", tint = Color.White ) },
            label = { Text("Add Reminder") },
            selected = navController.currentDestination?.route == "reminder",
            onClick = {
                navController.navigate("reminder") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}




enum class Screen(val route: String) {
    Login("login"),
    Registration("registration"),
    Home("home"),
    History("history"),
    Settings("settings"),
    LiveFeed("liveFeed"),
    AddNewFace("addNewFace"),
    KnownFaces("knownFaces"),
    ReminderScreen("reminder")
    // ... other screen routes ...
}




val CustomGray = Color(rgb(180, 192, 193)) // Custom gray color
val Customtop = Color(rgb(133, 147, 122)) // Custom gray color


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), // Add padding if needed
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f)) // Pushes the text to the center
                        Text(
                            "FaceGuard",
                            color = Color.White,
                            style = MaterialTheme.typography.h4
                        )
                        Spacer(modifier = Modifier.weight(1f)) // Balances the space on the right
                        IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                            Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
                        }
                    }
                }
            )
        }
    )  {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.untitled_photoroom_mswp8xk7e_transformed),
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
                FeatureCard(title = "View Live Feed", icon = Icons.Default.Warning) {
                    navController.navigate("liveFeed")
                }
                FeatureCard(title = "Known Faces", icon = Icons.Default.Face) {
                    navController.navigate(Screen.KnownFaces.route) // Navigate to Known Faces screen
                }
            }
        }
    }
}







@Composable
fun FeatureCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    val cardSize = 150.dp
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = CustomGray,
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
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontWeight = FontWeight.Medium)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LiveFeedScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Back button
                        IconButton(onClick = { navController.navigate("home") }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        // Spacer to push the title to the center
                        Spacer(modifier = Modifier.weight(0.55f))
                        // Title
                        Text(
                            "FaceGuard",
                            color = Color.White,
                            style = MaterialTheme.typography.h4
                        )
                        // Another spacer to balance the layout
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Unknown",
                    color = Color.White,
                    style = TextStyle(fontSize = 28.sp),
                    modifier = Modifier.weight(4f)
                )

                Button(
                    onClick = { navController.navigate("addNewFace") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hannah),
                    contentDescription = "Live feed image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Today", color = Color.White, style = MaterialTheme.typography.h5)
                Text("Motion detected at: 6.28 am", color = Color.White, style = MaterialTheme.typography.h6)
                Text("Face detected: Unknown", color = Color.White, style = MaterialTheme.typography.h6)
                Text("Relationship: Unknown", color = Color.White, style = MaterialTheme.typography.h6)
            }
            Spacer(modifier = Modifier.height(25.dp))

            ChatMessageBox()
        }
    }
}







@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddNewFace(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Back button
                        IconButton(onClick = { navController.navigate("liveFeed") }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        // Spacer to push the title to the center
                        Spacer(modifier = Modifier.weight(0.55f))
                        // Title
                        Text(
                            "FaceGuard",
                            color = Color.White,
                            style = MaterialTheme.typography.h4
                        )
                        // Another spacer to balance the layout
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            )
        }
    )
    {

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
                style = MaterialTheme.typography.h5,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Image(
                painter = painterResource(id = R.drawable.hannah), // Replace with the actual image resource
                contentDescription = "Logo",
                modifier = Modifier.size(280.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(2.dp))

            TextInput(InputType.FirstName)
            Spacer(modifier = Modifier.height(8.dp))
            TextInput(InputType.Surname)
            Spacer(modifier = Modifier.height(8.dp))
            TextInput(InputType.relation)

            Spacer(modifier = Modifier.height(30.dp)) // Additional spacer for spacing before the button


            Button(
                onClick = {
                    // Your logic to add a new face
                    navController.popBackStack("home", inclusive = false)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Face", Modifier.padding(10.dp))
            }

            Spacer(modifier = Modifier.height(30.dp)) // Additional bottom spacer for navigation space
        }
    }
}


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

class ReminderViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun setReminder(reminder: Reminder) {
        val calendar = Calendar.getInstance().apply {
            time = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse("${reminder.date} ${reminder.time}")
        }

        val intent = Intent(context, ReminderBroadcastReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        viewModelScope.launch {
            AppDatabase.getDatabase(context).reminderDao().insertReminder(reminder)
        }
    }


}