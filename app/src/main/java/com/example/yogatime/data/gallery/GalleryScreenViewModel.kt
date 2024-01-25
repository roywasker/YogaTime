package com.example.yogatime.data.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.InputStream

class GalleryScreenViewModel : ViewModel() {

    // Function to upload image to Firebase Storage and then save its metadata to Firebase Realtime Database
    fun uploadImageToFirebaseStorage(imageStream: InputStream, fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val storageRef = FirebaseStorage.getInstance().reference.child("gallery")

            try {
                // Attempt to upload the image to Firebase Storage
                val uploadTask = storageRef.putStream(imageStream).await()
                val downloadUrl = uploadTask.storage.downloadUrl.await().toString()

                // Once the upload is successful, save the image metadata to the Realtime Database
                saveImageInfoToDatabase(downloadUrl, uid)

                // Switch back to the Main thread if you need to update any UI components
                withContext(Dispatchers.Main) {
                    // Success handling, print a success message and update the UI and clear the image selection
                    YogaTimeAppRouter.navigateTo(Screen.HomeScreen)


                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Failure handling, e.g., show error message or offer retry
                }
            }
        }
    }

    private fun saveImageInfoToDatabase(imageUrl: String, userId: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("gallery/$userId")
        val imageId = databaseRef.push().key ?: return

        val imageInfo = hashMapOf(
            "imageUrl" to imageUrl,
            "timestamp" to System.currentTimeMillis()
        )

        databaseRef.child(imageId).setValue(imageInfo).addOnSuccessListener {
            // Optionally handle success, such as logging or additional UI feedback

        }.addOnFailureListener {
            // Optionally handle failure, such as retry mechanisms
        }
    }
}
