package com.example.yogatime.data.gallery

sealed class GalleryUIEventUIEvent{

    data class ImageSelected(val image:String): GalleryUIEventUIEvent()
    data class ImageUnSelected(val image:String): GalleryUIEventUIEvent()

    object UploadButtonClicked : GalleryUIEventUIEvent()
}