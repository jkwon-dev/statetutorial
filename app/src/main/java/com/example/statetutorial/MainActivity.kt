package com.example.statetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.statetutorial.ui.theme.StatetutorialTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val myViewModel by viewModels<MyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatetutorialTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(scaffoldState = scaffoldState) {
                        MyScreen(myViewModel, scope, scaffoldState)
                    }
                }
            }
        }
    }
}

@Composable
fun MyScreen(myViewModel: MyViewModel, scope: CoroutineScope, scaffoldState: ScaffoldState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyTextField(
            label = "User Name",
            value = myViewModel.name,
            onValueChanged = {myViewModel.onTextChanged(it)}
        )
        MyTextField(
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            value = myViewModel.password,
            onValueChanged = {myViewModel.onPasswordChanged(it)}
            )
        Button(onClick = { scope.launch { scaffoldState
                    .snackbarHostState
                    .showSnackbar("Hello, ${myViewModel.name}") } },
            modifier = Modifier.align(Alignment.End),
            enabled = myViewModel.name.isNotBlank() && myViewModel.password.isNotBlank()
            ) {
            Text(text = "Submit")
        }
    }
}

@Composable
fun MyTextField(
    label:String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    value: String,
    onValueChanged:(String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label)},
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )

}

class MyViewModel: ViewModel() {
    //state
    var name by mutableStateOf("")
    var password by mutableStateOf("")

    //event
    fun onTextChanged(newString:String) {
        name = newString
    }
    fun onPasswordChanged(newString:String) {
        password = newString
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StatetutorialTheme {
        //MyScreen()
    }
}