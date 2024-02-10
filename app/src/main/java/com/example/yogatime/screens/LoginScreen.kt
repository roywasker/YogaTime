package com.example.yogatime.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.ButtonComponent
import com.example.yogatime.components.ClickableTextComponent
import com.example.yogatime.components.DisplayHomeBackgroundImage
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.MyEmailField
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.PasswordTextField
import com.example.yogatime.data.login.LoginUIEvent
import com.example.yogatime.data.login.LoginViewModel
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter



/****************** Login Screen ******************/

/**
 * The open page of the app, the first interaction with the app,
 * the user can login or navigate to the sign up screen.
 * The is the start of the navigation graph for the app
 *
 * @param loginViewModel the view model for the login screen
 */
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxWidth()) {
        DisplayHomeBackgroundImage(painterResource = painterResource(id = R.drawable.homescreenbackground)
        )
        Surface(
            color = Color.Black.copy(alpha = 0.0f), // Adjust opacity and color
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id = R.string.login))
                HeadingTextComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(40.dp))
                MyEmailField(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUiState.value.emailError
                )
                PasswordTextField(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUiState.value.passwordError
                )
                Spacer(modifier = Modifier.height(80.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                ClickableTextComponent(tryingToLogin = false, onTextSelected = {
                    YogaTimeAppRouter.navigateTo(Screen.SignUpScreen)
                })
            }
        }
    }

        if (loginViewModel.popupMessage.value != null) {
            AlertDialog(
                onDismissRequest = { loginViewModel.popupMessage.value = null },
                title = { Text("Error") },
                text = { Text(loginViewModel.popupMessage.value!!) },
                confirmButton = {
                    TextButton(onClick = {
                        loginViewModel.popupMessage.value = null
                    }) { Text("OK") }
                }
            )
        }
}