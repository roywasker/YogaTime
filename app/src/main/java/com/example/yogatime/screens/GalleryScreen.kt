package com.example.yogatime.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.DisplayHomeBackgroundImage
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.HorizontalRecyclerViewForDelImage
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.SinglePhotoPicker
import com.example.yogatime.components.SmallButtonComponent
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.gallery.GalleryScreenViewModel
import com.example.yogatime.data.gallery.GalleryUIEvent
import kotlinx.coroutines.launch


/***************************** Gallery Screen *******************************/
/**
 *  GalleryScreen is a composable function which is used to display the gallery images for the manager.
 *  In this screen, the manager can see the gallery images and delete the images.
 *  The manager can also add new images to the gallery.
 *  The manager can also logout from the app or go to the profile screen.
 *  The manager can also go to the home screen.
 *
 *  @param galleryScreenViewModel is the view model for the gallery screen.
 */
@Composable
fun GalleryScreen(galleryScreenViewModel: GalleryScreenViewModel = viewModel()) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ToolBar.getUserData()
    galleryScreenViewModel.getImage()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar ={
            AppToolbar(toolbarTitle = stringResource(R.string.gallery),
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
                    when (it.itemId) {
                        "LogoutButton" -> {
                            galleryScreenViewModel.onEvent(GalleryUIEvent.LogoutButtonClicked)
                        }
                        "homeScreen" -> {
                            galleryScreenViewModel.onEvent(GalleryUIEvent.HomeButtonClicked)
                        }
                        "profileScreen" -> {
                            galleryScreenViewModel.onEvent(GalleryUIEvent.ProfileButtonClicked)
                        }
                    }
                }
            )
        }
    ) {paddingValues ->
        Box(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            DisplayHomeBackgroundImage(
                painterResource = painterResource(id = R.drawable.sun)
            )
            androidx.compose.material.Surface(
                color = Color.Black.copy(alpha = 0.0f), // Adjust opacity and color
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextComponent(value = "Upload Image to Gallery")
                    SinglePhotoPicker()

                    HeadingTextComponent(value = "Gallery")
                    HorizontalRecyclerViewForDelImage(imageList = galleryScreenViewModel.imageList,
                        onImageClick = {
                            galleryScreenViewModel.onEvent(GalleryUIEvent.delImageName(it))
                        })
                    SmallButtonComponent(value = "Delete",
                        onButtonClicked = {
                            galleryScreenViewModel.onEvent(GalleryUIEvent.DelButtonClicked)
                        })
                    SmallButtonComponent(value = "back",
                        onButtonClicked = {
                            galleryScreenViewModel.onEvent(GalleryUIEvent.BackButtonClicked)
                        })
                }
            }
        }
    }
    if (galleryScreenViewModel.popupMessage.value != null) {
        AlertDialog(
            onDismissRequest = { galleryScreenViewModel.popupMessage.value = null },
            text = { Text(galleryScreenViewModel.popupMessage.value!!) },
            confirmButton = { TextButton(onClick = { galleryScreenViewModel.popupMessage.value = null }) { Text("OK") } }
        )
    }
}