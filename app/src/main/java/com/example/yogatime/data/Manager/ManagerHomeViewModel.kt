package com.example.yogatime.data.Manager

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.AddEvent.AddNewEventState
import com.example.yogatime.data.Client.RegToTrainState
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.rules.NavigationItem
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class ManagerHomeViewModel :ViewModel() {
    var trainList = mutableStateListOf<TrainUiState>()
    var trainClick : TrainUiState? = null

    fun onEvent(event : ManagerHomeUIEvent) {
        when (event) {
            is ManagerHomeUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is  ManagerHomeUIEvent.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerProfileScreen)
            }
            is ManagerHomeUIEvent.cardClicked ->{
                trainClick = event.train
                YogaTimeAppRouter.navigateTo(Screen.TrainUserDisplayScreen)
            }
        }
    }

    fun getTrains() {
        val database = FirebaseDatabase.getInstance()
        val databaseRef = database.reference.child("AddNewEvent")
        val currentDate = Date()
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val newTrainList = mutableListOf<TrainUiState>()
                for (snapshot in dataSnapshot.children) {
                    try {
                        val train = snapshot.getValue(TrainUiState::class.java)
                        train?.let {
                            if (train.UserEmail == ToolBar.emailId.value) {
                                val eventDate = parseDate(train.EventDate)
                                if (eventDate >= currentDate) {
                                    val userList = mutableListOf<userRegState>()
                                    val databaseRefToUserList = snapshot.key?.let { it1 ->
                                        database.reference.child("AddNewEvent").child(it1).child("userRegister")
                                    }
                                    databaseRefToUserList?.addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            val userData = mutableStateOf(userRegState())
                                            for (miniSnapshot in dataSnapshot.children) {
                                                userData.value.userEmail = miniSnapshot.child("userEmail").value as String
                                                userData.value.userName = miniSnapshot.child("userName").value as String
                                                userList.add(userData.value)
                                                Log.e(TAG, "user : ${userData.value}")
                                            }
                                        }
                                        override fun onCancelled(e: DatabaseError) {
                                            Log.e(TAG, "Error parsing data: ${e.message}")
                                        }
                                    })
                                    it.userList = userList
                                    newTrainList.add(it)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing data: ${e.message}")
                    }
                }
                newTrainList.sortBy { parseDate(it.EventDate) }
                trainList.clear()
                trainList.addAll(newTrainList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching data: ${error.message}")
            }
        })
    }

    // Function to parse the date string
    private fun parseDate(dateString: String): Date {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.parse(dateString) ?: Date(0)
    }
}