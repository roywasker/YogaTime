package com.example.yogatime.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.data.gallery.GalleryScreenViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.HorizontalRecyclerViewForDelImage
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.SinglePhotoPicker
import com.example.yogatime.components.SmallButtonComponent
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.gallery.GalleryUIEvent
import kotlinx.coroutines.launch


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
                    if (it.itemId == "LogoutButton"){
                        galleryScreenViewModel.onEvent(GalleryUIEvent.LogoutButtonClicked)
                    }else if (it.itemId == "homeScreen"){
                        galleryScreenViewModel.onEvent(GalleryUIEvent.HomeButtonClicked)
                    }else if (it.itemId == "profileScreen"){
                        galleryScreenViewModel.onEvent(GalleryUIEvent.ProfileButtonClicked)
                    }
                }
            )
        }
    ) {paddingValues ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                HeadingTextComponent(value = "Upload Image to Gallery")
                SinglePhotoPicker()

                HeadingTextComponent(value = "Gallery")
                HorizontalRecyclerViewForDelImage(imageList = galleryScreenViewModel.imageList,
                    onImageClick ={
                        galleryScreenViewModel.onEvent(GalleryUIEvent.delImageName(it))
                    })
                SmallButtonComponent(value = "Delete",
                    onButtonClicked = {
                        galleryScreenViewModel.onEvent(GalleryUIEvent.DelButtonClicked)
                 })
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