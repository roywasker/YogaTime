package com.example.yogatime.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.data.Manager.ManagerHomeUIEvent
import com.example.yogatime.data.Manager.ManagerHomeViewModel
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import kotlinx.coroutines.launch
import androidx.compose.material.Surface
import androidx.compose.ui.unit.dp
import com.example.yogatime.components.HorizontalRecyclerViewForTrain
import com.example.yogatime.data.AddEvent.AddNewEventScreenViewModel
import com.example.yogatime.data.ToolBar


@Composable
fun ManagerHomeScreen (managerHomeViewModel: ManagerHomeViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    managerHomeViewModel.getTrains()


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
                        managerHomeViewModel.onEvent(ManagerHomeUIEvent.LogoutButtonClicked)
                    }else if (it.itemId == "profileScreen"){
                        managerHomeViewModel.onEvent(ManagerHomeUIEvent.ProfileButtonClicked)
                    }
                }
            )
        }
    ) {paddingValues ->
        Surface(color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues).padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingTextComponent(value = "Home page")
                Button(onClick = {
                    YogaTimeAppRouter.navigateTo(Screen.AddNewEventScreen)
                }) {
                    Text("Go to Add New Event")
                }

                Button(onClick = {
                    YogaTimeAppRouter.navigateTo(Screen.GalleryScreen)
                }) {
                    Text("Go to Gallery")
                }

                HeadingTextComponent(value = "elor part")

               HorizontalRecyclerViewForTrain(managerHomeViewModel.trainList)



            }
        }
    }
}