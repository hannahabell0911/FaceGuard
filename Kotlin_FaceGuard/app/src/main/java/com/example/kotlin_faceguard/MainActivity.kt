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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.sp
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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
fun SettingsScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(onClose = { scope.launch { drawerState.close() } })
        },
        content = {
            SettingsContent(onOpenDrawer = { scope.launch { drawerState.open() } })
        }
    )
}

@Composable
fun DrawerContent(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close Drawer")
        }
        Spacer(Modifier.height(16.dp))
        Text("About Us", fontWeight = FontWeight.Bold)
        Text("Theme", fontWeight = FontWeight.Bold)
        Text("Logout", fontWeight = FontWeight.Bold)
        Text("Help", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SettingsContent(onOpenDrawer: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onOpenDrawer) {
            Text("Open Settings")
        }
    }
}



sealed class NavScreen(val route: String) {
    object History : NavScreen("History")
    object Home : NavScreen("Home")

    object Settings : NavScreen("Settings")
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(navController: NavHostController) {
    var filterOption by remember { mutableStateOf("Today") }
    val historyItems = listOf(
        HistoryItem("John Doe", "Dec 2, 2023, 9:00 AM", true, R.drawable.hannah),
        HistoryItem("John Doe", "Dec 2, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Doe", "Dec 2, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Doe", "Dec 2, 2023, 10:00 AM", true, R.drawable.newimage),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("John Doe", "Nov 28, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Doe", "Nov 28, 2023, 10:00 AM", true, R.drawable.photo_output),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.hannah),
        HistoryItem("John Doe", "Dec 1, 2023, 10:00 AM", true, R.drawable.john_image_),
        HistoryItem("John Doe", "Nov 28, 2023, 10:00 AM", true, R.drawable.newimage),


    )
    val filteredItems = when (filterOption) {
        "Today" -> historyItems.filter { it.dateTime.isToday() }
        "Yesterday" -> historyItems.filter { it.dateTime.isYesterday() }
        "Last Week" -> historyItems.filter { it.dateTime.isLastWeek() }
        else -> historyItems
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    // Wrap Text in Row for center alignment
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "FaceGuard",
                            color = Color.White,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            )
        },
        backgroundColor = CustomGray // bg color
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("History", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
                DropdownMenuExample(filterOption) { selectedOption ->
                    filterOption = selectedOption
                }
            }
            LazyColumn {
                items(filteredItems) { item ->
                    HistoryCard(item)
                }
            }
        }
    }
}

@Composable
fun DropdownMenuExample(selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Today", "Yesterday", "Last Week")

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Text(selectedOption, modifier = Modifier.clickable { expanded = true })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onOptionSelected(option)
                }) {
                    Text(option)
                }
            }
        }
    }
}

// Dummy extension functions for date checks, replace with your actual logic
fun String.isToday(): Boolean = this.contains("Dec 2, 2023") // Replace with actual logic
fun String.isYesterday(): Boolean = this.contains("Nov 30, 2023") // Replace with actual logic
fun String.isLastWeek(): Boolean = this.contains("Nov") // Replace with actual logic


@Composable
fun HistoryCard(historyItem: HistoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp) // Rounded corners
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular image
            Image(
                painter = painterResource(id = historyItem.imageResId),
                contentDescription = "Photo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            // Middle content (Name and Known/Unknown label)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = historyItem.name, fontWeight = FontWeight.Bold)
                Text(text = if (historyItem.isKnown) "Known" else "Unknown")
            }

            // Date and time
            Text(text = historyItem.dateTime, textAlign = TextAlign.End)
        }
    }
}


data class HistoryItem(
    val name: String,
    val dateTime: String,
    val isKnown: Boolean,
    val imageResId: Int
)






@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.History.route) { HistoryScreen(navController) }
            composable(Screen.Settings.route) { SettingsScreen(navController) }
//            composable(Screen.Login.route) { LoginForm(navController) }
//            composable(Screen.Registration.route) { RegistrationForm(navController) }
            composable(Screen.LiveFeed.route) { LiveFeedScreen(navController) }
            composable(Screen.AddNewFace.route) { AddNewFace(navController) }
            // Add other composables with navigation as needed
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White) },
            label = { Text("Home", color = Color.White) },
            selected = navController.currentDestination?.route == "home",
            onClick = {
                if (navController.currentDestination?.route != "home") {
                    navController.popBackStack("home", inclusive = false)
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Warning, contentDescription = "History", tint = Color.White) },
            label = { Text("History", color = Color.White) },
            selected = navController.currentDestination?.route == "history",
            onClick = {
                if (navController.currentDestination?.route != "history") {
                    navController.navigate("history") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White) },
            label = { Text("Settings", color = Color.White) },
            selected = navController.currentDestination?.route == "settings",
            onClick = {
                if (navController.currentDestination?.route != "settings") {
                    navController.navigate("settings") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}

enum class Screen(val route: String, val icon: ImageVector, val title: String) {
    Home("home", Icons.Filled.Home, "Home"),
    History("history", Icons.Filled.Warning, "History"),
    Settings("settings", Icons.Filled.Settings, "Settings"),
    Login("login", Icons.Filled.Warning, "Login"),
    Registration("registration", Icons.Filled.Warning, "Register"),
    LiveFeed("liveFeed", Icons.Filled.Warning, "Live Feed"),
    AddNewFace("addNewFace", Icons.Filled.Face, "Add New Face")
    // Define other screens as needed
}





@Composable
fun LoginForm(navController: NavHostController, onSignUpClicked: () -> Unit) {
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
                // Implement your login logic here
                // On successful login, navigate to the home screen
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
val Customtop = Color(rgb(133, 147, 122)) // Custom gray color


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "FaceGuard",
                            color = Color.White,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomGray)
                .padding(30.dp)
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
                FeatureCard(title = "View Live Feed", icon = Icons.Default.Warning) {
                    navController.navigate("liveFeed")
                }
                FeatureCard(title = "Known Faces", icon = Icons.Default.Face) {
                    // Handle navigation or action for "Known Faces"
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


//enum class Screen {
//    Login,
//    Registration,
//    HomePage,
//    LiveFeed,
//    AddNewFace
//}