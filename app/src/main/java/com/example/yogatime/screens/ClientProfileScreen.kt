package com.example.yogatime.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.HorizontalRecyclerViewForTrain
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.NormalTextToLeftCornerComponent
import com.example.yogatime.components.RatingBar
import com.example.yogatime.components.ReviewTextField
import com.example.yogatime.components.SmallButtonComponent
import com.example.yogatime.data.Client.ClienHomeUIEvent
import com.example.yogatime.data.Client.ClientHomeViewModel
import com.example.yogatime.data.Client.ClientProfileUIEvent
import com.example.yogatime.data.Client.ClientProfileViewModel
import com.example.yogatime.data.ToolBar
import kotlinx.coroutines.launch

@Composable
fun ClientProfileScreen(clientProfileViewModel: ClientProfileViewModel = viewModel(), clientHomeViewModel: ClientHomeViewModel = viewModel()){
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var rating by remember { mutableIntStateOf(4) }

    ToolBar.getUserData()
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
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadingTextComponent(value = "Hey , ${ToolBar.fullNameId.value}")
                NormalTextComponent(value = "Email : ${ToolBar.emailId.value}")
                NormalTextComponent(value = "Phone : ${ToolBar.phoneId.value}")
                Spacer(modifier = Modifier.height(20.dp))
                SmallButtonComponent(value = "Edit",
                    onButtonClicked = {
                        clientProfileViewModel.onEvent(ClientProfileUIEvent.EditButtonClicked)
                    })

                Spacer(modifier = Modifier.height(40.dp))


                HeadingTextComponent(value = "My train :")

                HorizontalRecyclerViewForTrain(clientHomeViewModel.trainListForUser,
                    onImageClick = {
                    })

                Spacer(modifier = Modifier.height(40.dp))

                Card(
                    modifier = Modifier.padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    NormalTextToLeftCornerComponent(value = "Rate us ")
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
                        onTextSelected = {clientProfileViewModel.onEvent(ClientProfileUIEvent.addReview(it))
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
    if (clientProfileViewModel.ratePopupMessage.value != null) {
        AlertDialog(
            onDismissRequest = { clientProfileViewModel.ratePopupMessage.value = null },
            title = { Text("") },
            text = { Text(clientProfileViewModel.ratePopupMessage.value!!) },
            confirmButton = { TextButton(onClick = { clientProfileViewModel.ratePopupMessage.value = null }) { Text("OK") } }
        )
    }
}