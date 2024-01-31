package com.example.yogatime.data.Manager

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.AddEvent.AddNewEventState
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.rules.NavigationItem
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ManagerHomeViewModel :ViewModel() {
    var trainList = mutableStateListOf<AddNewEventState>()


    fun onEvent(event : ManagerHomeUIEvent) {
        when (event) {
            is ManagerHomeUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is  ManagerHomeUIEvent.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerProfileScreen)
            }
        }
    }



    fun getTrains() {
        val database = FirebaseDatabase.getInstance()
        val databaseRef = database.reference.child("AddNewEvent")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val newTrainList = mutableListOf<AddNewEventState>()

                for (snapshot in dataSnapshot.children) {
                    try {
                        val train = snapshot.getValue(AddNewEventState::class.java)
                        train?.let {
                            newTrainList.add(it)
                            Log.e(TAG, "Error parsing data: ${train.EventName}")

                        }
                    } catch (e: Exception) {

                        Log.e(TAG, "Error parsing data: ${e.message}")
                    }
                }
                trainList.clear()
                trainList.addAll(newTrainList)
                Log.e(TAG, "Error parsing data: ${trainList.size}")


            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching data: ${error.message}")
            }
        })

    }

    fun getTrainsList(): List<AddNewEventState> {
        return trainList
    }



}