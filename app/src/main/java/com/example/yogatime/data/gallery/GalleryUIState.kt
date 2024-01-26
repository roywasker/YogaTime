package com.example.yogatime.data.gallery

import java.io.InputStream

data class GalleryUIState(
        var imageStream: InputStream? = null,
        var userId:String = "",
        var imageError :Boolean = false,
        var userIdError : Boolean = false

)