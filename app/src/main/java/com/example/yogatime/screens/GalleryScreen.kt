package com.example.yogatime.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.data.gallery.GalleryScreenViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.yogatime.R
import com.example.yogatime.components.AppToolbar
import com.example.yogatime.components.NavigationDrawerBody
import com.example.yogatime.components.NavigationDrawerHeader
import com.example.yogatime.components.NormalTextComponent
import com.example.yogatime.components.SinglePhotoPicker
import com.example.yogatime.data.Client.ClientProfileUIEvent
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.gallery.GalleryUIEvent
import kotlinx.coroutines.launch


@Composable
fun GalleryScreen(galleryScreenViewModel: GalleryScreenViewModel = viewModel()) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ToolBar.getUserData()
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
                .padding(paddingValues).padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingTextComponent(value = "Upload Image to Gallery")
                SinglePhotoPicker()
            }
        }
    }
}
