package com.example.yogatime.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.data.Client.ClientProfileUIEvent
import com.example.yogatime.data.Client.ClientProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ClientProfileScreen(clientProfileViewModel: ClientProfileViewModel = viewModel()){
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    clientProfileViewModel.getUserData()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar ={
            AppToolbar(toolbarTitle = stringResource(R.string.home),
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerHeader(clientProfileViewModel.fullNameId.value)
            NavigationDrawerBody(navigationDrawerItems = clientProfileViewModel.navigationItemsList,
                onNavigationItemClicked = {
                    if (it.itemId == "LogoutButton"){
                        clientProfileViewModel.onEvent(ClientProfileUIEvent.LogoutButtonClicked)
                    }else if (it.itemId == "homeScreen"){
                        clientProfileViewModel.onEvent(ClientProfileUIEvent.HomeButtonClicked)
                    }
                }
            )
        }
    ) {paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingTextComponent(value = "Hey , ${clientProfileViewModel.fullNameId.value}")
                NormalTextComponent(value = "Email : ${clientProfileViewModel.emailId.value}")
                NormalTextComponent(value = "Phone : ${clientProfileViewModel.phoneId.value}")
            }
        }
    }
}