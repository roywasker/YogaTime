package com.example.yogatime.data.Client

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.gallery.GallertUIStateForDisplay
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ClientHomeViewModel :ViewModel() {

    val imageList = mutableStateListOf<GallertUIStateForDisplay>()
    val rateList = mutableStateListOf<ClientProfileUIState>()
    var avgRate = mutableDoubleStateOf(0.0)
    var trainList = mutableStateListOf<RegToTrainState>()
    var trainListForUser = mutableStateListOf<RegToTrainState>()
    private var trainToReg : RegToTrainState? = null
    val popupMessage = mutableStateOf<String?>(null)

    private val TAG = ClientHomeViewModel::class.simpleName

    fun onEvent(event : ClienHomeUIEvent) {
        when (event) {
            is ClienHomeUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is  ClienHomeUIEvent.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ClientProfileScreen)
            }
            is ClienHomeUIEvent.trainId ->{
                trainToReg = event.trainId
                Log.e(TAG, "train id: ${trainToReg!!.trainId}")
            }
            is ClienHomeUIEvent.regToTrainButtonClicked ->{
                RegisterToTrain()
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

    fun getTrains() {
        getTrainsForUser()
        val database = FirebaseDatabase.getInstance()
        val databaseRef = database.reference.child("AddNewEvent")
        val currentDate = Date()
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val newTrainList = mutableListOf<RegToTrainState>()

                for (snapshot in dataSnapshot.children) {
                    try {
                        val trainId =snapshot.key
                        val train = snapshot.getValue(RegToTrainState::class.java)
                        train?.let {
                            if (train.NumberOfParticipants !="0") {
                                val eventDate = parseDate(train.EventDate)
                                if (eventDate >= currentDate) {
                                    var userRegForThisTrain = false
                                    for (trainUser in trainListForUser) {
                                        if (trainUser.trainId == trainId){
                                            userRegForThisTrain =true
                                        }
                                    }
                                    if (!userRegForThisTrain){
                                        it.trainId = trainId.toString()
                                        newTrainList.add(it)
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "Error parsing data: ${e.message}")
                    }
                }
                newTrainList.sortBy { parseDate(it.EventDate) }
                trainList.clear()
                trainList.addAll(newTrainList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Error fetching data: ${error.message}")
            }
        })
    }

    // Function to parse the date string
    private fun parseDate(dateString: String): Date {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.parse(dateString) ?: Date(0)
    }

    private fun RegisterToTrain(){
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            val database = FirebaseDatabase.getInstance()
            val usersReference = database.reference.child("users").child(uid).child("trains").child(
                trainToReg?.trainId.toString())
            usersReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userMap = hashMapOf(
                        "EventName" to trainToReg?.EventName,
                        "EventDate" to trainToReg?.EventDate,
                        "EventTime" to trainToReg?.EventTime,
                    )
                    usersReference.setValue(userMap)
                        .addOnSuccessListener {
                            Log.d(TAG, "Inside add train ")
                            UpdateTrain()
                            popupMessage.value = "You register to this train"
                        }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
    private fun getTrainsForUser() {
        val user = FirebaseAuth.getInstance().currentUser
        val currentDate = Date()
        user?.let { it ->
            val uid = it.uid
            val database = FirebaseDatabase.getInstance()
            val usersReference = database.reference.child("users").child(uid).child("trains")
            usersReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val newTrainList = mutableListOf<RegToTrainState>()
                    for (snapshot in dataSnapshot.children) {
                        try {
                            val trainId =snapshot.key
                            val train = snapshot.getValue(RegToTrainState::class.java)
                            train?.let {
                                if (train.NumberOfParticipants !="0") {
                                    val eventDate = parseDate(train.EventDate)
                                    if (eventDate >= currentDate) {
                                        it.trainId = trainId.toString()
                                        newTrainList.add(it)
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(ContentValues.TAG, "Error parsing data: ${e.message}")
                        }
                    }
                    newTrainList.sortBy { parseDate(it.EventDate) }
                    trainListForUser.clear()
                    trainListForUser.addAll(newTrainList)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    private fun UpdateTrain() {
        val database = FirebaseDatabase.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        var userId : String? = null
        user?.let {
             userId = it.uid
        }
        val databaseRef = trainToReg?.let { database.reference.child("AddNewEvent").child(it.trainId) }
        databaseRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var newNumOfParticipants = trainToReg?.NumberOfParticipants?.toInt()
                newNumOfParticipants = newNumOfParticipants?.minus(1)
                val userMap = hashMapOf(
                    "NumberOfParticipants" to newNumOfParticipants.toString()
                )
                databaseRef.updateChildren(userMap as Map<String, Any>)
                    .addOnSuccessListener {
                        Log.d(TAG, "Inside --number")
                    }
                val databaseRefToUser = trainToReg?.let {
                    userId?.let { it1 ->
                        database.reference.child("AddNewEvent").child(it.trainId)
                            .child("userRegister").child(it1)
                    }
                }
                databaseRefToUser?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userMap = hashMapOf(
                            "userEmail" to ToolBar.emailId.value,
                            "userName" to ToolBar.fullNameId.value,
                        )
                        databaseRefToUser.setValue(userMap)
                            .addOnSuccessListener {
                                Log.d(TAG, "Inside add user to train ")
                            }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e(ContentValues.TAG, "Error fetching data: ${error.message}")
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Error fetching data: ${error.message}")
            }
        })
    }
}