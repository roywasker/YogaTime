package com.example.yogatime.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.data.Client.ClienHomeUIEvent
import com.example.yogatime.data.Client.ClientHomeViewModel
import com.example.yogatime.data.ToolBar
import kotlinx.coroutines.launch


@Composable
fun ClientHomeScreen (clientHomeViewModel: ClientHomeViewModel = viewModel()) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ToolBar.getUserData()
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
            NavigationDrawerHeader(ToolBar.fullNameId.value)
            NavigationDrawerBody(navigationDrawerItems = ToolBar.navigationItemsList,
                onNavigationItemClicked = {
                    if (it.itemId == "LogoutButton"){
                        clientHomeViewModel.onEvent(ClienHomeUIEvent.LogoutButtonClicked)
                    }else if (it.itemId == "profileScreen"){
                        clientHomeViewModel.onEvent(ClienHomeUIEvent.ProfileButtonClicked)
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

                HeadingTextComponent(value = "Home page")
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfClientHomeScreen(){
    ClientHomeScreen()
}