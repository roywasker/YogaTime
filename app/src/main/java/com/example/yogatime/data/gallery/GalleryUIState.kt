package com.example.yogatime.data.gallery

data class GalleryUIState(
        var image :String = "",
        var userId:String = "",
        var imageError :Boolean = false,
        var userIdError : Boolean = false

)