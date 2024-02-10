package com.example.yogatime.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.DisplayHomeBackgroundImage
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.HorizontalRecyclerViewForRate
import com.example.yogatime.components.HorizontalRecyclerViewForTrainForManager
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.data.Manager.ManagerHomeUIEvent
import com.example.yogatime.data.Manager.ManagerHomeViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import kotlinx.coroutines.launch


/***************************** Manager Home Screen *******************************/
/**
 *  ManagerHomeScreen is a composable function which is used to display the home screen of the manager.
 *  All the manager options are displayed in this screen.
 *  In this screen, the manager can see the available trains, gallery and rate.
 *  The manager can add new train and able to edit the train details and delete the train.
 *  The manager can delete pictures from the gallery or add new pictures to the gallery.
 *  The manager can also logout from the app or go to the profile screen.
 *
 *  @param managerHomeViewModel is the view model for the manager home screen.
 */
@Composable
fun ManagerHomeScreen (managerHomeViewModel: ManagerHomeViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        managerHomeViewModel.getTrains()
        managerHomeViewModel.getRate()
    }
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
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            DisplayHomeBackgroundImage(
                painterResource = painterResource(id = R.drawable.sun)
            )
            Surface(
                color = Color.Black.copy(alpha = 0.0f), // Adjust opacity and color
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    HeadingTextComponent(value = "My trains")
                    HorizontalRecyclerViewForTrainForManager(managerHomeViewModel.trainList,
                        onImageClick = {
                            managerHomeViewModel.onEvent(ManagerHomeUIEvent.cardClicked(it))
                        })
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = {
                            YogaTimeAppRouter.navigateTo(Screen.AddNewEventScreen)
                        }) {
                            Text("Go to Add New Event")
                        }
                        Spacer(modifier = Modifier.width(25.dp))
                        Button(onClick = {
                            YogaTimeAppRouter.navigateTo(Screen.GalleryScreen)
                        }) {
                            Text("Go to Gallery")
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    HeadingTextComponent(value = "Our rate : ")
                    Spacer(modifier = Modifier.height(5.dp))
                    HorizontalRecyclerViewForRate(rateList = managerHomeViewModel.rateList)
                }
            }
        }
    }
}