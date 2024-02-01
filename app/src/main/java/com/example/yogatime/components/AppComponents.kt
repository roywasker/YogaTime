package com.example.yogatime.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yogatime.ui.theme.Primary
import com.example.yogatime.ui.theme.TextColor
import com.example.yogatime.ui.theme.componentShapes
import com.example.yogatime.R
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.example.yogatime.data.rules.NavigationItem
import com.example.yogatime.ui.theme.AccentColor
import com.example.yogatime.ui.theme.Secondary
import com.example.yogatime.ui.theme.WhiteColor
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.DatePicker
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.example.yogatime.data.gallery.GalleryScreenViewModel
import java.util.*
import coil.compose.AsyncImage
import com.example.yogatime.data.Client.ClientProfileUIState
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.gallery.GallertUIStateForDisplay
import com.example.yogatime.data.AddEvent.AddNewEventState
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await




@Composable
fun NormalTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 24.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
        , color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun NormalTextToLeftCornerComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 24.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
        , color = TextColor,
        textAlign = TextAlign.Left
    )
}

@Composable
fun HeadingTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        )
        , color = TextColor,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelValue: String, painterResource: Painter,
                onTextSelected: (String) -> Unit,
                errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,

        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventTextField(labelValue: String,
                onTextSelected: (String) -> Unit,
                errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,

            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        isError = !errorStatus
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPhoneField(labelValue: String, painterResource: Painter,
                onTextSelected: (String) -> Unit,
                errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,

            ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(labelValue: String, painterResource: Painter,
                      onTextSelected: (String) -> Unit,
                      errorStatus: Boolean = false) {

    val localFocusManager = LocalFocusManager.current

    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue) },
        value = password.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        singleLine = true,
        keyboardActions = KeyboardActions{
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }

            val description = if(passwordVisible.value){
                "Hide password"
            }else{
                "Show password"
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else
        PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MyEmailField(labelValue: String,
                 painterResource: Painter,
                 onTextSelected: (String) -> Unit,
                 errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,

            ), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFromTodayPicker(
    labelValue: String,
    painterResource: Painter,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        onDateSelected(selectedDate) // Pass the selected date back to the caller
        // Optionally display a message or handle the date selection as required
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = { /* Read-only field */ },
        readOnly = true,
        modifier = modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        textStyle = TextStyle(fontSize = 18.sp),
        label = { Text(text = labelValue) },

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ), singleLine = true,
        maxLines = 1,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                modifier = Modifier.clickable {
                    DatePickerDialog(
                        context,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).apply {
                        datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future dates
                        show()
                    }
                }
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayDatePicker(
    labelValue: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorStatus: Boolean = false) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    // Adjust the calendar to start 100 years back for a birthday picker
    val YearsBack = calendar.apply {
        add(Calendar.YEAR, -100)
    }.timeInMillis

    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        onDateSelected(selectedDate) // Pass the selected date back to the caller
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = { /* Read-only field */ },
        readOnly = true,
        modifier = modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        textStyle = TextStyle(fontSize = 18.sp),
        label = { Text(text = labelValue) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                modifier = Modifier.clickable {
                    DatePickerDialog(
                        context,
                        dateSetListener,
                        calendar.get(Calendar.YEAR) - 70, // Start 70 years back
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).apply {
                        datePicker.maxDate = System.currentTimeMillis() // Ensure future dates cannot be picked
                        datePicker.minDate = YearsBack // Optional: Set a minimum date if needed
                                show()
                    }
                }
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        isError = !errorStatus
    )
}




@Composable
fun ButtonComponent(value:String, onButtonClicked : () -> Unit, isEnabled : Boolean = false){
    Button(onClick = {
       onButtonClicked.invoke()
    },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                    shape = RoundedCornerShape(50.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            }
    }
}


@Composable
fun DateFromTodayCompose(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()

    // Prepare the listener for date set
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        // Use the selected date as needed
        Toast.makeText(context, "Selected date: $selectedDate", Toast.LENGTH_LONG).show()
    }

    Button(onClick = {
        DatePickerDialog(
            context,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future
            show()
        }
    }, modifier = modifier) {
        Text(if (selectedDate.isEmpty()) "Select Date" else selectedDate)
    }

}


@Composable
fun ClickableTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText =
        if (tryingToLogin) "Already have an account? " else "Don’t have an account yet? "
    val loginText = if (tryingToLogin) "Login" else "Register"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }

        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(toolbarTitle: String,navigationIconClicked :() ->Unit) {

    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Primary,
        ),
        title = {
            Text(
                text = toolbarTitle, color = WhiteColor,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navigationIconClicked.invoke()
            },
                modifier = Modifier.size(24.dp)
            ) {

            }
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = stringResource(R.string.menu),
                tint = WhiteColor,
            )
        }
    )
}

@Composable
fun NavigationDrawerHeader(value: String?) {
    Box(
        modifier = Modifier
            .background(
                Brush.horizontalGradient(
                    listOf(Primary, Secondary)
                )
            )
            .fillMaxWidth()
            .height(120.dp)
            .padding(32.dp)
    ) {

        NavigationDrawerText(
            title = value?:stringResource(R.string.yoga_time), 28.sp , AccentColor
        )

    }
}

@Composable
fun PickImageFromGallery(viewModel: GalleryScreenViewModel) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(400.dp)
                        .padding(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Pick Image")
        }

        // Conditionally display the Upload button only if an image has been picked
        if (imageUri != null) {
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                imageUri?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri)
                    inputStream?.let {
                        // Assuming you have a fileName strategy, for example using a timestamp
                        val fileName = "upload_${System.currentTimeMillis()}.jpg"
                        viewModel.uploadImageToFirebaseStorage(it, fileName)
                    }
                }
            }) {
                Text(text = "Upload")
            }
        }
    }

}

@Composable
fun SinglePhotoPicker(){
    var uri by remember{
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val context = LocalContext.current


    Column{


        AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(248.dp))

        Button(onClick = {
            singlePhotoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )

        }){
            Text("Pick Image")
        }

        if(uri != null) {

            Button(onClick = {
                uri?.let {

                    GalleryScreenViewModel.uploadToStorage(uri = it, context = context)
                }

            }) {
                Text("Upload")
            }
        }




    }
}

@Composable
fun NavigationDrawerBody(navigationDrawerItems: List<NavigationItem>,
                         onNavigationItemClicked:(NavigationItem) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        items(navigationDrawerItems) {
            NavigationItemRow(item = it,onNavigationItemClicked)
        }

    }
}

@Composable
fun NavigationItemRow(item: NavigationItem,
                      onNavigationItemClicked:(NavigationItem) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigationItemClicked.invoke(item)
            }
            .padding(all = 16.dp)
    ) {

        Icon(
            imageVector = item.icon,
            contentDescription = item.description,
        )

        Spacer(modifier = Modifier.width(18.dp))

        NavigationDrawerText(title = item.title, 18.sp, Primary)
    }
}
@Composable
fun NavigationDrawerText(title: String, textUnit: TextUnit,color: Color) {

    Text(
        text = title, style = TextStyle(
            color = Color.Black,
            fontSize = textUnit,
            fontStyle = FontStyle.Normal,
        )
    )
}






@Composable
fun showDatePicker(){

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/$month/$year"
        }, year, month, day
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Selected Date: ${date.value}")
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Open Date Picker")
        }
    }

}
/*fun dropDownMenu() {

    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Kotlin", "Java", "Dart", "Python")
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Label") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
        }
    }
}*/

@Composable
fun HorizontalRecyclerView(imageList: List<GallertUIStateForDisplay>) {
    /*LazyRow  {
        items(imageList) { GallertUIStateForDisplay ->
            ImageToDisplay(GallertUIStateForDisplay)
        }
    }*/

    Row (modifier = Modifier.horizontalScroll(rememberScrollState())) {
        for (image in imageList){
            ImageToDisplay(image)
        }
    }
}

@Composable
fun ImageToDisplay(imageData: GallertUIStateForDisplay) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(120.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        AsyncImage(
            model = imageData.url,
            contentDescription = imageData.name,
            modifier = Modifier
                .width(200.dp)
                .height(120.dp)
        )
    }
}


@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 5,
    stars: Int = 5,
    onRatingChanged: (Double) -> Unit,
    starsColor: Color = Primary
) {

    Row {
        for (index in 1..stars) {
            Icon(
                imageVector =
                if (index <= rating) {
                    Icons.Rounded.Star
                } else {
                    Icons.Rounded.StarOutline
                },
                contentDescription = null,
                tint = starsColor,
                modifier = modifier
                    .clickable { onRatingChanged(index.toDouble()) }
            )
        }
    }
}


@Composable
fun DateFromToday(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()

    // Prepare the listener for date set
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        // Use the selected date as needed
        Toast.makeText(context, "Selected date: $selectedDate", Toast.LENGTH_LONG).show()
    }

    // Function to show the date picker dialog
    val showDatePicker = {
        DatePickerDialog(
            context,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future
            show()
        }
    }

    // OutlinedTextField to display the selected date
    OutlinedTextField(
        value = selectedDate,
        onValueChange = { selectedDate = it },
        label = { Text("Date") },
        readOnly = true, // Make the text field read-only
        modifier = modifier
            .clickable { showDatePicker() }, // Open date picker on text field click
        trailingIcon = {
            IconButton(onClick = { showDatePicker() }) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Select Date")
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickDateFromToday(
    labelValue: String,
    onDateSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()

    // Prepare the listener for date set
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        onDateSelected(selectedDate) // Pass the selected date back
    }

    // Function to show the date picker dialog
    val showDatePicker = {
        DatePickerDialog(
            context,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future
            show()
        }
    }
    // OutlinedTextField to display the selected date
    OutlinedTextField(
        value = selectedDate,
        onValueChange = { selectedDate = it },
        label = { Text(labelValue) },
        readOnly = true, // Make the text field read-only
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small)
            .clickable(onClick = { showDatePicker() }), // Open date picker on text field click // Open date picker on text field click
        trailingIcon = {
            IconButton(onClick = { showDatePicker() }) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Select Date")
            }
        },isError = !errorStatus,
        singleLine = true,
        maxLines = 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewTextField(labelValue: String, onTextSelected: (String) -> Unit) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(componentShapes.small),
        label = { Text(text = labelValue)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,

            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        singleLine = false,
        maxLines = 4,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
    )
}

@Composable
fun SmallButtonComponent(value:String, onButtonClicked : () -> Unit, isEnabled: Boolean = true){
    Button(onClick = {
        onButtonClicked.invoke()
    },
        modifier = Modifier
            .width(120.dp)
            .heightIn(40.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
    ) {
        Box(modifier = Modifier
            .width(120.dp)
            .heightIn(40.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                shape = RoundedCornerShape(50.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HorizontalRecyclerViewForRate(rateList: List<ClientProfileUIState>) {
    Row (modifier = Modifier.horizontalScroll(rememberScrollState())) {
        for (rate in rateList){
            RateToDisplay(rate)
        }
    }
}

@Composable
fun RateToDisplay(rateData: ClientProfileUIState) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(120.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row {
            for (index in 1..rateData.rating.toInt()) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Primary,
                )
            }
        }
        Text(text = rateData.review)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayUserData(value : String,
                    label :String,
                    onTextSelected: (String) -> Unit,
                    errorStatus: Boolean){
    val textValue = remember {
        mutableStateOf(value)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = label)},
        value = textValue.value,
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickTime(
    labelValue: String,
    onTimeSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val context = LocalContext.current
    var selectedTime by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()

    // Prepare the listener for time set
    val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        // Format the time in a preferred format, e.g., 24-hour format here
        selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        onTimeSelected(selectedTime) // Pass the selected time back
    }

    // Function to show the time picker dialog
    val showTimePicker = {
        TimePickerDialog(
            context,
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // for 24-hour time format, set to false for 12-hour format with AM/PM
        ).show()
    }

    // OutlinedTextField to display the selected time
    OutlinedTextField(
        value = selectedTime,
        onValueChange = { selectedTime = it },
        label = { Text(labelValue) },
        readOnly = true, // Make the text field read-only
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small)
            .clickable(onClick = { showTimePicker() }),
        trailingIcon = {
            IconButton(onClick = { showTimePicker() }) {
                Icon(Icons.Default.AccessTime, contentDescription = "Select Time")
            }
        },
        isError = !errorStatus,
        singleLine = true,
        maxLines = 1
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberOfParticipante(
    labelValue: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        value = textValue.value,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        label = { Text(text = labelValue) },
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        trailingIcon = { // Moved the icon to trailingIcon
            Icon(painter = painterResource, contentDescription = null)
        },
        isError = !errorStatus
    )
}
@Composable
fun HorizontalRecyclerViewForTrain(TrainList: List<AddNewEventState>) {
    Row (modifier = Modifier.horizontalScroll(rememberScrollState())) {
        for (train in TrainList){
            TrainToDisplay(train)
        }
    }
}


@Composable
fun TrainToDisplay(trainData: AddNewEventState) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(120.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Train details")
            Text(text = trainData.EventName)
            Text(text =trainData.EventDate)
            Text(text =trainData.EventTime)
        }


    }
}