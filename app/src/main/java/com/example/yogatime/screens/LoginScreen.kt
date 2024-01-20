package com.example.yogatime.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.R
import com.example.yogatime.components.ButtonComponent
import com.example.yogatime.components.ClickableTextComponent
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.MyTextField
import com.example.yogatime.components.PasswordTextField
import com.example.yogatime.data.login.LoginUIEvent
import com.example.yogatime.data.login.LoginViewModel
import com.example.yogatime.data.sighup.SignupUIEvent
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Surface(color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NormalTextComponent(value = stringResource(id = R.string.login))
            HeadingTextComponent(value = stringResource(id = R.string.welcome))
            Spacer(modifier = Modifier.height(40.dp))
            MyTextField(labelValue = stringResource(id = R.string.email),
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
            ButtonComponent(value = stringResource(id = R.string.login),
                onButtonClicked ={
                    loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                },
                isEnabled = loginViewModel.allValidationsPassed.value
            )
            Spacer(modifier = Modifier.height(20.dp))
            ClickableTextComponent(tryingToLogin = false , onTextSelected ={
                YogaTimeAppRouter.navigateTo(Screen.SignUpScreen)
            })
        }
    }

}