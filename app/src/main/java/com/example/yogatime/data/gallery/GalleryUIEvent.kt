package com.example.yogatime.data.gallery



sealed class GalleryUIEvent{

    data class delImageName(val name : String): GalleryUIEvent()

    object DelButtonClicked : GalleryUIEvent()
    object LogoutButtonClicked : GalleryUIEvent()
    object ProfileButtonClicked : GalleryUIEvent()
    object HomeButtonClicked : GalleryUIEvent()
    object BackButtonClicked : GalleryUIEvent()


}