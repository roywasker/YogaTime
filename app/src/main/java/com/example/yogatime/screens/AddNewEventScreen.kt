package com.example.yogatime.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.yogatime.R
import com.example.yogatime.components.ButtonComponent
import com.example.yogatime.components.ClickableTextComponent
import com.example.yogatime.components.DateFromTodayCompose
import com.example.yogatime.components.DateFromTodayPicker
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.MyEmailField
import com.example.yogatime.components.MyTextField
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.PasswordTextField
import com.example.yogatime.components.PickImageFromGallery
import com.example.yogatime.data.login.LoginUIEvent
import com.example.yogatime.data.sighup.SignupUIEvent
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter


@Composable
fun AddNewEventScreen() {

    Surface(color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NormalTextComponent(value = "Event")
            HeadingTextComponent(value = "Add New Event")
            Spacer(modifier = Modifier.height(40.dp))
            MyTextField(
                labelValue = "Event Name",
                painterResource = painterResource(id = R.drawable.message),
                onTextSelected = {

                },
                errorStatus = false
            )
            DateFromTodayCompose()
            MyTextField(
                labelValue = "Location",
                painterResource = painterResource(id = R.drawable.phone),
                onTextSelected = {

                },
                errorStatus = false
            )
            Spacer(modifier = Modifier.height(80.dp))

            ButtonComponent(value = "Add Event",
                onButtonClicked ={

                },
                isEnabled = true
            )
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}