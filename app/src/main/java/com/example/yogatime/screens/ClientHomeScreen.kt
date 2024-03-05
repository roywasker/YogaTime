package com.example.yogatime.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.DisplayHomeBackgroundImage
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.HorizontalRecyclerView
import com.example.yogatime.components.HorizontalRecyclerViewForRate
import com.example.yogatime.components.HorizontalRecyclerViewForTrain
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextToLeftCornerComponent
import com.example.yogatime.components.SmallButtonComponent
import com.example.yogatime.data.Client.ClienHomeUIEvent
import com.example.yogatime.data.Client.ClientHomeViewModel
import com.example.yogatime.data.ToolBar
import kotlinx.coroutines.launch

/***************************** Client Home Screen *******************************/
/**
 *  ClientHomeScreen is a composable function which is used to display the home screen of the client.
 *  All the user options are displayed in this screen.
 *  In this screen, the user can see the available trains, gallery, and rate.
 *  The user can also register for the train.
 *  The user can also logout from the app or go to the profile screen.
 *
 *  @param clientHomeViewModel is the view model for the client home screen.
 */
@Composable
fun ClientHomeScreen (clientHomeViewModel: ClientHomeViewModel = viewModel()) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ToolBar.getUserData()
    clientHomeViewModel.getTrainsForUser()
    clientHomeViewModel.getImage()
    clientHomeViewModel.getRate()

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
        Box(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            DisplayHomeBackgroundImage(painterResource = painterResource(id = R.drawable.sun)
            )
            Surface(
                color = Color.Black.copy(alpha = 0.0f), // Adjust opacity and color
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    HeadingTextComponent(value = "Our train : ")

                    HorizontalRecyclerViewForTrain(clientHomeViewModel.trainList,
                        onImageClick = {
                            clientHomeViewModel.onEvent(ClienHomeUIEvent.trainId(it))
                        })
                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        SmallButtonComponent(value = "Register",
                            onButtonClicked = {
                                clientHomeViewModel.onEvent(ClienHomeUIEvent.regToTrainButtonClicked)
                            })
                    }


                    Spacer(modifier = Modifier.height(20.dp))
                    HeadingTextComponent(value = "Our Gallery : ")
                    HorizontalRecyclerView(imageList = clientHomeViewModel.imageList)

                    Spacer(modifier = Modifier.height(30.dp))
                    HeadingTextComponent(value = "Our rate : ")
                    Spacer(modifier = Modifier.height(5.dp))
                    NormalTextToLeftCornerComponent(
                        value = "Avg rate : ${
                            String.format(
                                "%.2f",
                                clientHomeViewModel.avgRate.doubleValue
                            )
                        }"
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    HorizontalRecyclerViewForRate(rateList = clientHomeViewModel.rateList)
                }
            }
        }
    }
    if (clientHomeViewModel.popupMessage.value != null) {
        AlertDialog(
            onDismissRequest = { clientHomeViewModel.popupMessage.value = null },
            text = { Text(clientHomeViewModel.popupMessage.value!!) },
            confirmButton = { TextButton(onClick = { clientHomeViewModel.popupMessage.value = null }) { Text("OK") } }
        )
    }
}

@Preview
@Composable
fun DefaultPreviewOfClientHomeScreen(){
    ClientHomeScreen()
}