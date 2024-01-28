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
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.ButtonComponent
import com.example.yogatime.components.ClickableTextComponent
import com.example.yogatime.components.DateFromTodayCompose
import com.example.yogatime.components.DateFromTodayPicker
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.MyEmailField
import com.example.yogatime.components.MyTextField
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.PasswordTextField
import com.example.yogatime.components.PickImageFromGallery
import com.example.yogatime.components.SinglePhotoPicker
import com.example.yogatime.data.Manager.AddEventScreenViewModel
import com.example.yogatime.data.Manager.AddEventUIEvent
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.gallery.GalleryScreenViewModel
import com.example.yogatime.data.gallery.GalleryUIEvent
import com.example.yogatime.data.login.LoginUIEvent
import com.example.yogatime.data.sighup.SignupUIEvent
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import kotlinx.coroutines.launch


@Composable
fun AddNewEventScreen(addEventViewModel: AddEventScreenViewModel = viewModel()) {


    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ToolBar.getUserData()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar ={
            AppToolbar(toolbarTitle = stringResource(R.string.train),
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerHeader(ToolBar.fullNameId.value)
            NavigationDrawerBody(navigationDrawerItems = ToolBar.navigationItemsList,
                onNavigationItemClicked = {
                    if (it.itemId == "LogoutButton"){
                        addEventViewModel.onEvent(AddEventUIEvent.LogoutButtonClicked)
                    }else if (it.itemId == "homeScreen"){
                        addEventViewModel.onEvent(AddEventUIEvent.HomeButtonClicked)
                    }else if (it.itemId == "profileScreen"){
                        addEventViewModel.onEvent(AddEventUIEvent.ProfileButtonClicked)
                    }
                }
            )
        }
    ) {paddingValues ->
        Surface(color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
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
}