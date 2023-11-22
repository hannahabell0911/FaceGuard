package com.example.kotlin_faceguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_faceguard.ui.theme.Kotlin_FaceGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kotlin_FaceGuardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black  // changed the background color to black
                ) {
                    Login()
                }
            }
        }
    }
}

@Composable
fun Login()
{


    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Icon(painter=painterResource(id = R.drawable.faceguard_icon),
            null,Modifier.size(100.dp),
           )

        TextInput(InputType.Name)
        TextInput(InputType.Password)
        Button(onClick = {}, modifier = Modifier.fillMaxWidth())
        {
            Text("Login", Modifier.padding(vertical = 8.dp))
        }
        Divider(color=Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp))

        Row (verticalAlignment = Alignment.CenterVertically)
        {
            Text("Dont have an Account yet ?", color= Color.White)
            TextButton(onClick = {}) {
                Text("Sign Up")
            }
        }


    }

}

sealed class InputType(
    val label:String,
    val icon:ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
)
{
    object Name:InputType
        (label = "Username",
        icon =Icons.Default.Person,
        KeyboardOptions(imeAction=ImeAction.Next),
        visualTransformation = VisualTransformation.None)
    object Password:InputType
        (label = "Password",
        icon = Icons.Default.Lock,
        KeyboardOptions(imeAction=ImeAction.Next),
        visualTransformation = VisualTransformation.None

                )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(inputType: InputType)
{
    var value by remember { mutableStateOf("") }
    TextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth(),

        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label) },
//        shape = Shapes.small,
        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,

    )

//

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Kotlin_FaceGuardTheme {
        Login()
    }
}