package com.example.yogatime.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.HorizontalRecyclerViewForTrain
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.RatingBar
import com.example.yogatime.components.ReviewTextField
import com.example.yogatime.components.SmallButtonComponent
import com.example.yogatime.data.Client.ClientHomeViewModel
import com.example.yogatime.data.Client.ClientProfileUIEvent
import com.example.yogatime.data.Client.ClientProfileViewModel
import com.example.yogatime.data.ToolBar
import kotlinx.coroutines.launch

/***************************** Client Profile Screen *******************************/
/**
 *  ClientProfileScreen is a composable function which is used to display the profile screen of the client.
 *  In this screen, the user can see his/her profile details.
 *  The user can also edit his/her profile details.
 *  The user can also delete the train that he/she has registered for.
 *  The user can also rate the app.
 *  The user can also logout from the app or go to the home screen.
 *
 *  @param clientProfileViewModel is the view model for the client profile screen.
 *  @param clientHomeViewModel is the view model for the client home screen.
 */
@Composable
fun ClientProfileScreen(clientProfileViewModel: ClientProfileViewModel = viewModel(), clientHomeViewModel: ClientHomeViewModel = viewModel()){
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var rating by remember { mutableIntStateOf(4) }

    ToolBar.getUserData()
    clientHomeViewModel.getTrainsForUser()
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
                        clientProfileViewModel.onEvent(ClientProfileUIEvent.LogoutButtonClicked)
                    }else if (it.itemId == "homeScreen"){
                        clientProfileViewModel.onEvent(ClientProfileUIEvent.HomeButtonClicked)
                    }
                }
            )
        }
    ) {paddingValues ->
        Box(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            DisplayHomeBackgroundImage(
                painterResource = painterResource(id = R.drawable.sun)
            )
            androidx.compose.material3.Surface(
                color = Color.Black.copy(alpha = 0.0f), // Adjust opacity and color
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextComponent(value = "Hey , ${ToolBar.fullNameId.value}")
                    NormalTextComponent(value = "Email : ${ToolBar.emailId.value}")
                    NormalTextComponent(value = "Phone : ${ToolBar.phoneId.value}")
                    Spacer(modifier = Modifier.height(10.dp))
                    SmallButtonComponent(value = "Edit",
                        onButtonClicked = {
                            clientProfileViewModel.onEvent(ClientProfileUIEvent.EditButtonClicked)
                        })

                    Spacer(modifier = Modifier.height(10.dp))

                    HeadingTextComponent(value = "My train :")

                    HorizontalRecyclerViewForTrain(clientHomeViewModel.trainListForUser,
                        onImageClick = {
                            clientProfileViewModel.onEvent(ClientProfileUIEvent.trainToDelete(it))
                        })

                    Spacer(modifier = Modifier.height(10.dp))
                    SmallButtonComponent(value = "Delete",
                        onButtonClicked = {
                            clientProfileViewModel.onEvent(ClientProfileUIEvent.unRegToTrainButtonClicked)
                        })

                    Spacer(modifier = Modifier.height(10.dp))

                    Card(
                        modifier = Modifier.padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        HeadingTextComponent(value = "Rate us ")
                        RatingBar(
                            modifier = Modifier
                                .size(50.dp),
                            rating = rating,
                            onRatingChanged = {
                                rating = it.toInt()
                                clientProfileViewModel.onEvent(ClientProfileUIEvent.addRating(it.toInt()))
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        ReviewTextField(labelValue = "Write some word",
                            onTextSelected = {
                                clientProfileViewModel.onEvent(ClientProfileUIEvent.addReview(it))
                            })

                        Spacer(modifier = Modifier.height(10.dp))
                        SmallButtonComponent(value = "Send",
                            onButtonClicked = {
                                clientProfileViewModel.onEvent(ClientProfileUIEvent.RatingButtonClicked)
                            })
                    }
                }
            }
        }
    }
    if (clientProfileViewModel.popupMessage.value != null) {
        AlertDialog(
            onDismissRequest = { clientProfileViewModel.popupMessage.value = null },
            title = { Text("") },
            text = { Text(clientProfileViewModel.popupMessage.value!!) },
            confirmButton = { TextButton(onClick = { clientProfileViewModel.popupMessage.value = null }) { Text("OK") } }
        )
    }
}