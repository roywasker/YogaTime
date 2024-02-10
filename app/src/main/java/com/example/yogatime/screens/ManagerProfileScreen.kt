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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.DisplayHomeBackgroundImage
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.MyTextField
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.SmallButtonComponent
import com.example.yogatime.data.Manager.ManagerProfileUIEvent
import com.example.yogatime.data.Manager.ManagerProfileViewModel
import com.example.yogatime.data.ToolBar
import kotlinx.coroutines.launch

/***************************** Manager Profile Screen *******************************/
/**
 *  ManagerProfileScreen is a composable function which is used to display the profile screen of the manager.
 *  In this screen, the manager can see his/her profile details.
 *  The manager can also edit his/her profile details.
 *  The manager can also give coach access to be a manager of the app.
 *  The manager can also logout from the app or go to the home screen.
 *
 *  @param managerProfileViewModel is the view model for the manager profile screen.
 */
@Composable
fun ManagerProfileScreen(managerProfileViewModel: ManagerProfileViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ToolBar.getUserData()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
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
                    if (it.itemId == "LogoutButton") {
                        managerProfileViewModel.onEvent(ManagerProfileUIEvent.LogoutButtonClicked)
                    } else if (it.itemId == "homeScreen") {
                        managerProfileViewModel.onEvent(ManagerProfileUIEvent.HomeButtonClicked)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            DisplayHomeBackgroundImage(
                painterResource = painterResource(id = R.drawable.homescreenbackground)
            )
            Surface(
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
                    SmallButtonComponent(value = "Edit",
                        onButtonClicked = {
                            managerProfileViewModel.onEvent(ManagerProfileUIEvent.EditButtonClicked)
                        })

                    Spacer(modifier = Modifier.height(40.dp))

                    NormalTextComponent(value = "Give coach access to :")
                    Spacer(modifier = Modifier.height(20.dp))
                    MyTextField(
                        labelValue = stringResource(id = R.string.email),
                        painterResource = painterResource(id = R.drawable.message),
                        onTextSelected = {
                            managerProfileViewModel.onEvent(ManagerProfileUIEvent.EmailAdd(it))
                        },
                        errorStatus = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    SmallButtonComponent(
                        value = "Add Coach",
                        onButtonClicked = { managerProfileViewModel.onEvent(ManagerProfileUIEvent.AddCoachButtonClicked) },
                        isEnabled = managerProfileViewModel.emailPassed.value
                    )
                }
            }

        }
        if (managerProfileViewModel.addCoachPopUp.value != null) {
            AlertDialog(
                onDismissRequest = { managerProfileViewModel.addCoachPopUp.value = null },
                text = { Text(managerProfileViewModel.addCoachPopUp.value!!) },
                confirmButton = {
                    TextButton(onClick = {
                        managerProfileViewModel.addCoachPopUp.value = null
                    }) { Text("OK") }
                }
            )
        }
    }
}