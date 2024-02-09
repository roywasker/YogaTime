package com.example.yogatime.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.DisplayHomeBackgroundImage
import com.example.yogatime.components.DisplayUserData
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.NormalTextToLeftCornerComponent
import com.example.yogatime.components.RatingBar
import com.example.yogatime.components.ReviewTextField
import com.example.yogatime.components.SmallButtonComponent
import com.example.yogatime.data.Client.ClientProfileUIEvent
import com.example.yogatime.data.EditUser.EditUserUIEvent
import com.example.yogatime.data.EditUser.EditUserViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.sighup.SignupUIEvent
import kotlinx.coroutines.launch




@Composable
fun EditUserDataScreen(editUserViewModel: EditUserViewModel = viewModel()) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar ={
            AppToolbar(toolbarTitle = stringResource(R.string.profile),
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
                        editUserViewModel.onEvent(EditUserUIEvent.LogoutButtonClicked)
                    }else if (it.itemId == "homeScreen"){
                        editUserViewModel.onEvent(EditUserUIEvent.HomeButtonClicked)
                    }else if (it.itemId == "profileScreen"){
                        editUserViewModel.onEvent(EditUserUIEvent.ProfileButtonClicked)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxWidth()) {
            DisplayHomeBackgroundImage(
                painterResource = painterResource(id = R.drawable.homescreenbackground)
            )
            Surface(
                color = Color.Black.copy(alpha = 0.4f), // Adjust opacity and color
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ToolBar.fullNameId.value?.let {
                        DisplayUserData(
                            value = it, label = stringResource(id = R.string.name),
                            onTextSelected = {
                                editUserViewModel.onEvent(EditUserUIEvent.fullNameChanged(it))
                            },
                            errorStatus = editUserViewModel.editUserDataUiState.value.fullNameError
                        )
                    }

                    ToolBar.emailId.value?.let {
                        DisplayUserData(
                            value = it, label = stringResource(id = R.string.email),
                            onTextSelected = {
                                editUserViewModel.onEvent(EditUserUIEvent.emailChanged(it))
                            },
                            errorStatus = editUserViewModel.editUserDataUiState.value.emailError
                        )
                    }

                    ToolBar.phoneId.value?.let {
                        DisplayUserData(
                            value = it, label = stringResource(id = R.string.phone),
                            onTextSelected = {
                                editUserViewModel.onEvent(EditUserUIEvent.phoneChanged(it))
                            },
                            errorStatus = editUserViewModel.editUserDataUiState.value.phoneError
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    SmallButtonComponent(
                        value = "Save",
                        onButtonClicked = {
                            editUserViewModel.onEvent(EditUserUIEvent.EditButtonClicked)
                        },
                        isEnabled = editUserViewModel.allValidationsPassed.value
                    )
                }
            }
        }
    }
}