package com.example.yogatime.data.gallery

import com.example.yogatime.data.Client.ClienHomeUIEvent


sealed class GalleryUIEvent{

    data class ImageSelected(val image:String): GalleryUIEvent()
    data class ImageUnSelected(val image:String): GalleryUIEvent()
    data class delImageName(val name : String): GalleryUIEvent()

    object UploadButtonClicked : GalleryUIEvent()
    object DelButtonClicked : GalleryUIEvent()
    object LogoutButtonClicked : GalleryUIEvent()
    object ProfileButtonClicked : GalleryUIEvent()
    object HomeButtonClicked : GalleryUIEvent()
}