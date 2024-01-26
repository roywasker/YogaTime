package com.example.yogatime.data.gallery


sealed class GalleryUIEvent{

    data class ImageSelected(val image:String): GalleryUIEvent()
    data class ImageUnSelected(val image:String): GalleryUIEvent()

    object UploadButtonClicked : GalleryUIEvent()
    object LogoutButtonClicked : GalleryUIEvent()
    object ProfileButtonClicked : GalleryUIEvent()
    object HomeButtonClicked : GalleryUIEvent()
}