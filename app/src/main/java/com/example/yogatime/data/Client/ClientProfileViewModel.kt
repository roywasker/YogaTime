package com.example.yogatime.data.Client

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.rules.NavigationItem
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClientProfileViewModel : ViewModel() {

    fun onEvent(event: ClientProfileUIEvent) {
        when (event) {
            is ClientProfileUIEvent.LogoutButtonClicked -> {
                logout()
            }
            is ClientProfileUIEvent.HomeButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ClientHomeScreen)
            }
        }
    }

    val navigationItemsList = listOf<NavigationItem>(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            description = "Home Screen",
            itemId = "homeScreen"
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.AssignmentInd,
            description = "Profile Screen",
            itemId = "profileScreen"
        ),
        NavigationItem(
            title = "Logout",
            icon = Icons.Default.Logout,
            description = "Logout",
            itemId = "LogoutButton"
        )
    )

    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                YogaTimeAppRouter.navigateTo(Screen.LoginScreen)
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)

    }

    val fullNameId: MutableLiveData<String> = MutableLiveData()
    val emailId: MutableLiveData<String> = MutableLiveData()
    val phoneId: MutableLiveData<String> = MutableLiveData()

    fun getUserData(){
        val user = FirebaseAuth.getInstance().currentUser
        val databaseReference = FirebaseDatabase.getInstance().reference
        val userReference = user?.let { it1 ->
            databaseReference.child("users").child(it1.uid)
        }
        userReference?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    fullNameId.value = dataSnapshot.child("fullName").value as String
                    emailId.value = dataSnapshot.child("email").value as String
                    phoneId.value = dataSnapshot.child("phone").value as String
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}