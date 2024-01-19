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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.MyTextField
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.R
import com.example.yogatime.app.YogaTimeApp
import com.example.yogatime.components.ButtonComponent
import com.example.yogatime.components.ClickableTextComponent
import com.example.yogatime.components.PasswordTextField
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.nativemobilebits.loginflow.navigation.SystemBackButtonHandler


@Composable
fun SighUpScreen() {
    Surface(color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id = R.string.hello ))
                HeadingTextComponent(value = stringResource(id = R.string.Create_an_Account))
                Spacer(modifier = Modifier.height(40.dp))
                MyTextField(
                    labelValue = stringResource(id = R.string.name),
                    painterResource = painterResource(id = R.drawable.profile)
                )
                MyTextField(labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message)
                )
                MyTextField(
                    labelValue = stringResource(id = R.string.phone),
                    painterResource = painterResource(id = R.drawable.profile)
                )
                PasswordTextField(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock)
                )
                Spacer(modifier = Modifier.height(80.dp))
                ButtonComponent(value = stringResource(id = R.string.register))
                Spacer(modifier = Modifier.height(40.dp))
                ClickableTextComponent(tryingToLogin = true , onTextSelected = {
                    YogaTimeAppRouter.navigateTo(Screen.LoginScreen)
                })
            }

    }

    SystemBackButtonHandler {
        YogaTimeAppRouter.navigateTo(Screen.LoginScreen)
    }
}

/*@Preview
@Composable
fun DefaultPreviewOfSighUpScreen(){
    SighUpScreen()
}*/