package com.example.yogatime.data.Client

import android.util.Log
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.gallery.GallertUIStateForDisplay
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ClientHomeViewModel :ViewModel() {

    val imageList = mutableStateListOf<GallertUIStateForDisplay>()
    val rateList = mutableStateListOf<ClientProfileUIState>()
    var avgRate = mutableDoubleStateOf(0.0)
    private val TAG = ClientHomeViewModel::class.simpleName

    fun onEvent(event : ClienHomeUIEvent) {
        when (event) {
            is ClienHomeUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is  ClienHomeUIEvent.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ClientProfileScreen)
            }
        }
    }

    fun getImage() {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("images")
        imageList.clear()
        storageRef.listAll().addOnSuccessListener { listResult ->
            for (item in listResult.items) {
                val image = GallertUIStateForDisplay("", "")
                item.downloadUrl.addOnSuccessListener { uri ->
                    if(!imageList.any { it.name == item.name}) {
                        image.url = uri.toString()
                        image.name = item.name
                        imageList.add(image)
                    }
                }
            }
        }
    }


    fun getRate() {
        val database = FirebaseDatabase.getInstance()
        val databaseRef = database.reference.child("rate")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val newRateList = mutableListOf<ClientProfileUIState>()

                for (snapshot in dataSnapshot.children) {
                    try {
                        val rate = snapshot.getValue(ClientProfileUIState::class.java)
                        rate?.let {
                            when(it.rateInString){
                                "one" -> { it.rating = "1" }
                                "two" -> { it.rating = "2" }
                                "three" -> { it.rating = "3" }
                                "four" -> { it.rating = "4" }
                                "five" -> { it.rating = "5" }
                            }
                            newRateList.add(it)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing data: ${e.message}")
                    }
                }
                rateList.clear()
                rateList.addAll(newRateList)
                rateList.reverse()
                calAvg()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching data: ${error.message}")
            }
        })
    }

    private fun calAvg() {
        var count = 0
        var sumRate = 0
        for (rate in rateList){
            count++
            sumRate += rate.rating.toInt()
        }
        avgRate.doubleValue = sumRate.toDouble()/count
    }

}