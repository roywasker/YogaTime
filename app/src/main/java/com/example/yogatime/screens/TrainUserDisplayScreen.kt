package com.example.yogatime.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.DisplayUserRegisterForTrain
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextToLeftCornerComponent
import com.example.yogatime.data.Manager.ManagerHomeViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.TrainUserDisplay.TrainUserDisplayUIState
import com.example.yogatime.data.TrainUserDisplay.TrainUserDisplayViewModel
import kotlinx.coroutines.launch

@Composable
fun TrainUserDisplayScreen(trainUserDisplayViewModel: TrainUserDisplayViewModel = viewModel() , managerHomeViewModel: ManagerHomeViewModel = viewModel()){

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
                        trainUserDisplayViewModel.onEvent(TrainUserDisplayUIState.LogoutButtonClicked)
                    }else if (it.itemId == "homeScreen"){
                        trainUserDisplayViewModel.onEvent(TrainUserDisplayUIState.HomeButtonClicked)
                    }else if (it.itemId == "profileScreen"){
                        trainUserDisplayViewModel.onEvent(TrainUserDisplayUIState.ProfileButtonClicked)
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
                .padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                managerHomeViewModel.trainClick?.let { DisplayUserRegisterForTrain(it) }
            }
        }
    }
}