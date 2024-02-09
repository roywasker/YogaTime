package com.example.yogatime.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.example.yogatime.components.DisplayNumberOfParticipanteforTrain
import com.example.yogatime.components.DisplayTrainTime
import com.example.yogatime.components.DisplayUserData
import com.example.yogatime.components.DisplayUserRegisterForTrain
import com.example.yogatime.components.DisplaydateforTrain
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextToLeftCornerComponent
import com.example.yogatime.components.SmallButtonComponent
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
        Box(modifier = Modifier.fillMaxWidth()) {
            DisplayHomeBackgroundImage(
                painterResource = painterResource(id = R.drawable.sun)
            )
            androidx.compose.material.Surface(
                color = Color.Black.copy(alpha = 0.4f), // Adjust opacity and color
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    managerHomeViewModel.trainClick?.let {
                        var currnetTrain = it
//                    DisplayUserRegisterForTrain(it)
                        DisplayUserData(value = currnetTrain.EventName,
                            label = "Train Name",
                            onTextSelected = {
                                trainUserDisplayViewModel.onEvent(
                                    TrainUserDisplayUIState.TrainNameChanged(it)
                                )
                            },
                            errorStatus = trainUserDisplayViewModel.editTrainDataUiState.value.EventNameError)
                        DisplaydateforTrain(
                            value = currnetTrain.EventDate, labelValue = "Event Date",
                            onDateSelected = {
                                trainUserDisplayViewModel.onEvent(
                                    TrainUserDisplayUIState.DateChanged(it)
                                )
                            },
                            errorStatus = trainUserDisplayViewModel.editTrainDataUiState.value.EventDateError
                        )
                        DisplayTrainTime(
                            value = it.EventTime, labelValue = "Event Time",
                            onTimeSelected = {
                                trainUserDisplayViewModel.onEvent(
                                    TrainUserDisplayUIState.TimeChanged(
                                        it
                                    )
                                )
                            },
                            errorStatus = trainUserDisplayViewModel.editTrainDataUiState.value.EventTimeError
                        )
                        DisplayNumberOfParticipanteforTrain(
                            value = currnetTrain.NumberOfParticipants,
                            labelValue = "Number of Participants",
                            painterResource = painterResource(id = R.drawable.profile),
                            onTextSelected = {
                                trainUserDisplayViewModel.onEvent(
                                    TrainUserDisplayUIState.NumberOfParticipantesChanged(it)
                                )
                            },
                            errorStatus = trainUserDisplayViewModel.editTrainDataUiState.value.NumberOfParticipantsError
                        )
                        Spacer(modifier = Modifier.height(40.dp))

                        SmallButtonComponent(
                            value = "Save",
                            onButtonClicked = {
                                trainUserDisplayViewModel.onEvent(
                                    TrainUserDisplayUIState.EditButtonClicked(currnetTrain)
                                )
                            },
                            isEnabled = trainUserDisplayViewModel.allValidationsPassed.value
                        )

                        SmallButtonComponent(value = "Back",
                            onButtonClicked = {
                                trainUserDisplayViewModel.onEvent(
                                    TrainUserDisplayUIState.BackButtonClicked
                                )
                            })

                        SmallButtonComponent(value = "delete",
                            onButtonClicked = {
                                trainUserDisplayViewModel.onEvent(
                                    TrainUserDisplayUIState.DeleteButtonClicked(currnetTrain)
                                )
                            })

                        NormalTextToLeftCornerComponent(value = "Participants :")
                        if (it.userList != null) {
                            val userList = it.userList
                            if (userList != null) {
                                for (user in userList) {
                                    NormalTextToLeftCornerComponent(value = user.userName)

                                }
                            }
                        }


                    }
                }
            }
        }
    }
}