@file:Suppress("DEPRECATION")

package com.example.yogatime.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.yogatime.R
import com.example.yogatime.data.Client.ClientProfileUIState
import com.example.yogatime.data.Client.RegToTrainState
import com.example.yogatime.data.Manager.TrainUiState
import com.example.yogatime.data.gallery.GallertUIStateForDisplay
import com.example.yogatime.data.gallery.GalleryScreenViewModel
import com.example.yogatime.data.rules.NavigationItem
import com.example.yogatime.ui.theme.Primary
import com.example.yogatime.ui.theme.Secondary
import com.example.yogatime.ui.theme.TextColor
import com.example.yogatime.ui.theme.WhiteColor
import com.example.yogatime.ui.theme.componentShapes
import java.util.Calendar


/**
 * Creates a composable that displays a normal text with consistent styling.
 *
 * @param value The text content to be displayed.
 */
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

/**
 * Creates a composable that displays a normal text with consistent styling to the left corner.
 *
 * @param value The text content to be displayed.
 */
@Composable
fun NormalTextToLeftCornerComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 24.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
        , color = TextColor,
        textAlign = TextAlign.Left
    )
}

/**
 * Creates a composable that displays a heading text with consistent styling.
 *
 * @param value The text content to be displayed.
 */
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

/**
 * Textbox for user input with a label and icon.
 *
 * @param labelValue The label to be displayed.
 * @param painterResource The icon to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 * @param errorStatus The status of the text input.
 */
@Composable
fun MyTextField(labelValue: String, painterResource: Painter,
                onTextSelected: (String) -> Unit,
                errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.2f)) // Set background opacity
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(componentShapes.small),
            label = { Text(text = labelValue) },
            value = textValue.value,
            textStyle = TextStyle(fontSize = 18.sp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Primary,
                focusedBorderColor = Primary,
                focusedLabelColor = Primary,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            onValueChange = {
                textValue.value = it
                onTextSelected(it)
            },
            leadingIcon = {
                Icon(painter = painterResource, contentDescription = "")
            },
            isError = !errorStatus
        )
    }
}


/**
 * Textbox for user to enter the event name.
 *
 * @param labelValue The label to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 * @param errorStatus The status of the text input.
 */

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
        colors = OutlinedTextFieldDefaults.
        colors(
            cursorColor = Primary,
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
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


/**
 * Textbox for user to enter the his phone number.
 *
 * @param labelValue The label to be displayed.
 * @param painterResource The icon to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 * @param errorStatus The status of the text input.
 */
@Composable
fun MyPhoneField(labelValue: String, painterResource: Painter,
                onTextSelected: (String) -> Unit,
                errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.2f)) // Set background opacity
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(componentShapes.small),
            label = { Text(text = labelValue) },
            value = textValue.value,
            textStyle = TextStyle(fontSize = 16.sp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Primary,
                focusedBorderColor = Primary,
                focusedLabelColor = Primary,
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
                Icon(painter = painterResource, contentDescription = "")
            },
            isError = !errorStatus
        )
    }
}

/**
 * Textbox for user to enter the his password.
 *
 * @param labelValue The label to be displayed.
 * @param painterResource The icon to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 * @param errorStatus The status of the text input.
 */
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
    Box(
        modifier = Modifier
            .fillMaxWidth(0.99f)
            .background(Color.White.copy(alpha = 0.2f)) // Set background opacity
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(componentShapes.small),
            label = { Text(text = labelValue) },
            value = password.value,
            textStyle = TextStyle(fontSize = 18.sp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Primary,
                focusedBorderColor = Primary,
                focusedLabelColor = Primary,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            },
            maxLines = 1,
            onValueChange = {
                password.value = it
                onTextSelected(it)
            },
            leadingIcon = {
                Icon(painter = painterResource, contentDescription = "")
            },
            trailingIcon = {
                val iconImage = if (passwordVisible.value) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }

                val description = if (passwordVisible.value) {
                    "Hide password"
                } else {
                    "Show password"
                }

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = iconImage, contentDescription = description)
                }
            },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else
                PasswordVisualTransformation(),
            isError = !errorStatus
        )
    }
}

/**
 * Textbox for user to enter the his email.
 *
 * @param labelValue The label to be displayed.
 * @param painterResource The icon to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 * @param errorStatus The status of the text input.
 */
@Composable
fun MyEmailField(labelValue: String,
                 painterResource: Painter,
                 onTextSelected: (String) -> Unit,
                 errorStatus:Boolean = false) {
    val textValue = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(0.99f)
            .background(Color.White.copy(alpha = 0.2f)) // Set background opacity
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(componentShapes.small),
            label = { Text(text = labelValue) },
            value = textValue.value,
            textStyle = TextStyle(fontSize = 18.sp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Primary,
                focusedBorderColor = Primary,
                focusedLabelColor = Primary,
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
                Icon(painter = painterResource, contentDescription = "")
            },
            isError = !errorStatus
        )
    }
}


/**
 * Textbox for user to enter the his birthdate.
 *
 * @param labelValue The label to be displayed.
 * @param onDateSelected The callback to be invoked when the date is selected.
 * @param modifier The modifier to be applied to the text field.
 * @param errorStatus The status of the text input.
 */
@Composable
fun BirthdayDatePicker(
    labelValue: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorStatus: Boolean = false) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    // Adjust the calendar to start 20 years back for a birthday picker
    val yearsBack = calendar.apply {
        add(Calendar.YEAR, -20)
    }.timeInMillis

    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        // Note: month is 0 based
        selectedDate = "$dayOfMonth/${month + 1}/$year"
        onDateSelected(selectedDate) // Pass the selected date back to the caller
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.2f)) // Set background opacity
    ) {
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
                            datePicker.maxDate =
                                System.currentTimeMillis() // Ensure future dates cannot be picked
                            datePicker.minDate = yearsBack // Optional: Set a minimum date if needed
                            show()
                        }
                    }
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Primary,
                focusedBorderColor = Primary,
                focusedLabelColor = Primary,
            ),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            isError = !errorStatus
        )
    }
}




/**
 * The main button style for the app
 *
 * @param value The text content to be displayed.
 * @param onButtonClicked The callback to be invoked when the button is clicked.
 * @param isEnabled The status of the button.
 */
@Composable
fun ButtonComponent(value:String, onButtonClicked : () -> Unit, isEnabled : Boolean = false){
    Button(onClick = {
       onButtonClicked.invoke()
    },
        modifier = Modifier
            .fillMaxWidth(0.99f)
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




/**
 * The clickable text component for the login and register screen.
 *
 * @param tryingToLogin The status of the user.
 * @param onTextSelected The callback to be invoked when the text is clicked.
 */
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


/**
 * Main toolbar for the app organized the pages for navigation.
 *
 * @param toolbarTitle The title of the toolbar.
 * @param navigationIconClicked The callback to be invoked when the icon is clicked.
 */
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

/**
 * Header for the navigation drawer.
 *
 * @param value The title of the header.
 */
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
            title = value?:stringResource(R.string.yoga_time), 28.sp
        )

    }
}


/**
 * composable for picking a single photo from the gallery
 */
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


/**
 * The component for the navigation in the app.
 *
 * @param navigationDrawerItems The list of items to be displayed.
 * @param onNavigationItemClicked The callback to be invoked when the item is clicked.
 */
@Composable
fun NavigationDrawerBody(navigationDrawerItems: List<NavigationItem>,
                         onNavigationItemClicked:(NavigationItem) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        items(navigationDrawerItems) {
            NavigationItemRow(item = it,onNavigationItemClicked)
        }

    }
}


/**
 * The component for Navigation Drawer Items to be displayed.
 *
 * @param item The item to be displayed.
 * @param onNavigationItemClicked The callback to be invoked when the item is clicked.
 */
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

        NavigationDrawerText(title = item.title, 18.sp)
    }
}

/**
 * Text for the navigation drawer items for the title of the item.
 *
 * @param title The title of the item.
 * @param textUnit The size of the text.
 */
@Composable
fun NavigationDrawerText(title: String, textUnit: TextUnit) {

    Text(
        text = title, style = TextStyle(
            color = Color.Black,
            fontSize = textUnit,
            fontStyle = FontStyle.Normal,
        )
    )
}







/**
 * The gallery representation for the user to see the images in the gallery.
 *
 * @param imageList The list of images to be displayed.
 */
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

/**
 * The image to be displayed in the gallery.
 *
 * @param imageData The image to be displayed.
 */
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


/**
 * Rating bar for the user to rate the event.
 *
 * @param rating The rating of the event.
 * @param stars The number of stars to be displayed.
 * @param onRatingChanged The callback to be invoked when the rating is changed.
 * @param starsColor The color of the stars.
 */
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


/**
 * Picker for the user to select the event date.
 *
 * @param labelValue The label to be displayed.
 * @param onDateSelected The callback to be invoked when the date is selected.
 * @param errorStatus The status of the text input.
 */
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

/**
 * Textbox for user to enter the his review on the event.
 *
 * @param labelValue The label to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 */
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

/**
 * The main small button style for the app
 *
 * @param value The text content to be displayed.
 * @param onButtonClicked The callback to be invoked when the button is clicked.
 * @param isEnabled The status of the button.
 */
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
        ) {
            Text(text = value,
                modifier = Modifier.align(Alignment.Center), // Center the text within the Box
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Present the users rating for the event.
 *
 * @param rateList The list of ratings to be displayed.
 */
@Composable
fun HorizontalRecyclerViewForRate(rateList: List<ClientProfileUIState>) {
    Row (modifier = Modifier.horizontalScroll(rememberScrollState())) {
        for (rate in rateList){
            RateToDisplay(rate)
        }
    }
}

/**
 * The rating to be displayed for the event.
 *
 * @param rateData The rating to be displayed.
 */
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

/**
 * Present the user data like name, email, and phone number to be displayed with options to edit.
 *
 * @param value The value to be displayed.
 * @param label The label to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 * @param errorStatus The status of the text input.
 */
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

/**
 * Textbox for manager to pick the event time.
 *
 * @param labelValue The label to be displayed.
 * @param onTimeSelected The callback to be invoked when the time is selected.
 * @param errorStatus The status of the text input.
 */
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

/**
 * Textbox for manager for Add Event to pick the number of participants.
 *
 * @param labelValue The label to be displayed.
 * @param painterResource The icon to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 * @param errorStatus The error status of the text input.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberOfParticipants(
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

/**
 * Present the existing trains for user to select.
 *
 * @param trainList The list of trains to be displayed.
 * @param onImageClick The callback to be invoked when the image is clicked.
 */
@Composable
fun HorizontalRecyclerViewForTrain(trainList: List<RegToTrainState>, onImageClick: (RegToTrainState) -> Unit) {
    var selectedCardIndex by remember { mutableIntStateOf(-1) }

    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        for ((index, train) in trainList.withIndex()) {
            TrainToDisplay(train, onImageClick, isSelected = index == selectedCardIndex) {
                selectedCardIndex = index
            }
        }
    }
}

/**
 * The train to be displayed for the user to select.
 *
 * @param trainData The train to be displayed.
 * @param onImageClick The callback to be invoked when the image is clicked.
 */
@Composable
fun TrainToDisplay(trainData: RegToTrainState, onImageClick: (RegToTrainState) -> Unit, isSelected: Boolean, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(120.dp)
            .clickable {
                onCardClick()
                onImageClick(trainData)
            }
            .background(if (isSelected) Color.Gray else Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Text(
                text = trainData.EventName,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.W800,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.5.em,
                    textDecoration = TextDecoration.Underline
                )

            )
            Text(
                text = "Date: ${trainData.EventDate}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic,
                )
            )
            Text(
                text = "Time: ${trainData.EventTime}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic,
                )
            )

        }
    }
}


/**
 * Present the existing picture to delete.
 *
 * @param imageList The list of images to be displayed.
 * @param onImageClick The callback to be invoked when the image is clicked.
 */
@Composable
fun HorizontalRecyclerViewForDelImage(imageList: List<GallertUIStateForDisplay>, onImageClick: (String) -> Unit) {
    var selectedCardIndex by remember { mutableIntStateOf(-1) }

    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        /*for (image in imageList) {
            ImageToDisplayForDelImage(image, onImageClick)
        }*/
        for ((index, image) in imageList.withIndex()) {
            ImageToDisplayForDelImage(image, onImageClick, isSelected = index == selectedCardIndex) {
                selectedCardIndex = index
            }
        }
    }
}

/**
 * The image to be displayed for the manager to delete.
 *
 * @param imageData The image to be displayed.
 * @param onImageClick The callback to be invoked when the image is clicked.
 */
@Composable
fun ImageToDisplayForDelImage(imageData: GallertUIStateForDisplay, onImageClick: (String) -> Unit,isSelected: Boolean, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(120.dp)
            .clickable {
                onCardClick()
                onImageClick(imageData.name) }  // Add click handling here
            .background(if (isSelected) Color.Gray else Color.White),
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

/**
 * Present the existing trains for manager to select.
 *
 * @param trainList The list of trains to be displayed.
 * @param onImageClick The callback to be invoked when the image is clicked.
 */
@Composable
fun HorizontalRecyclerViewForTrainForManager(trainList: List<TrainUiState>, onImageClick: (TrainUiState) -> Unit) {
    var selectedCardIndex by remember { mutableIntStateOf(-1) }

    Row (modifier = Modifier.horizontalScroll(rememberScrollState())) {
        /*for (train in TrainList){
            TrainToDisplayForManager(train, onImageClick)
        }*/
        for ((index, train) in trainList.withIndex()) {
            TrainToDisplayForManager(train, onImageClick, isSelected = index == selectedCardIndex) {
                selectedCardIndex = index
            }
        }
    }
}


/**
 * The train to be displayed for the manager to select.
 *
 * @param trainData The train to be displayed.
 * @param onImageClick The callback to be invoked when the image is clicked.
 */
@Composable
fun TrainToDisplayForManager(trainData: TrainUiState, onImageClick: (TrainUiState) -> Unit,isSelected: Boolean, onCardClick: () -> Unit) {
    val numberOfParticipants: Int =
        trainData.NumberOfParticipants.toIntOrNull() ?: 0  // Safely convert to Int, default to 0 if conversion fails
    val userListSize: Int = trainData.userList?.size ?: 0 // Safely handle nullable userList, default to 0 if null
    numberOfParticipants - userListSize
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(120.dp)
            .clickable {
                onCardClick()
                onImageClick(trainData) }
            .background(if (isSelected) Color.Gray else Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White),

        ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Text(
                text = trainData.EventName,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.W800,

                    letterSpacing = 0.5.em,
                    textDecoration = TextDecoration.Underline
                )

            )
            Text(
                text = "Date: ${trainData.EventDate}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic,
                )
            )
            Text(
                text = "Time: ${trainData.EventTime}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic,
                )
            )

            Text(
                text = "Participants that left : ${(trainData.NumberOfParticipants).toInt()-(trainData.userList?.size?:0)}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic,
                )
            )
        }
    }
}

/**
 * Present the selected train to see the details.
 * @param value The value to be displayed.
 * @param labelValue The label to be displayed.
 * @param onTimeSelected The callback to be invoked when the date is selected.
 * @param errorStatus The status of the text input.
 */
@Composable
fun DisplayTrainTime(
    value: String,
    labelValue: String,
    onTimeSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val context = LocalContext.current
    var selectedTime by remember { mutableStateOf(value) }
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

/**
 * Present the selected train to see the details.
 * @param value The value to be displayed.
 * @param labelValue The label to be displayed.
 * @param onDateSelected The callback to be invoked when the date is selected.
 * @param errorStatus The status of the text input.
 */
@Composable
fun DisplayDateForTrain(value: String,
                        labelValue: String,
                        onDateSelected: (String) -> Unit,
                        errorStatus: Boolean = false
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf(value) }
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

/**
 * Present the selected train to see the number of participants for the event.
 *
 * @param value The value to be displayed.
 * @param labelValue The label to be displayed.
 * @param painterResource The icon to be displayed.
 * @param onTextSelected The callback to be invoked when the text is selected.
 * @param errorStatus The status of the text input.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayNumberOfParticipantsForTrain(value: String,
                                        labelValue: String,
                                        painterResource: Painter,
                                        onTextSelected: (String) -> Unit,
                                        errorStatus: Boolean = false) {
    val textValue = remember { mutableStateOf(value) }

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



/**
 * Present the background image for each screen in the app.
 *
 * @param painterResource The painter resource for the image.
 */
@Composable
fun DisplayHomeBackgroundImage(painterResource: Painter)
{
    Image(
        painter = painterResource,
        contentDescription = "Background image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds // Adjust image scaling as needed
    )
}


/**
 * All the unused composable functions
 *
 *
 * @OptIn(ExperimentalMaterial3Api::class)
 * @Composable
 * fun DateFromTodayPicker(
 *     labelValue: String,
 *     painterResource: Painter,
 *     onDateSelected: (String) -> Unit,
 *     modifier: Modifier = Modifier,
 * ) {
 *     val context = LocalContext.current
 *     var selectedDate by remember { mutableStateOf("") }
 *     val calendar = Calendar.getInstance()
 *     val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
 *         // Note: month is 0 based
 *         selectedDate = "$dayOfMonth/${month + 1}/$year"
 *         onDateSelected(selectedDate) // Pass the selected date back to the caller
 *         // Optionally display a message or handle the date selection as required
 *     }
 *
 *     OutlinedTextField(
 *         value = selectedDate,
 *         onValueChange = { /* Read-only field */ },
 *         readOnly = true,
 *         modifier = modifier
 *             .fillMaxWidth()
 *             .clip(componentShapes.small),
 *         textStyle = TextStyle(fontSize = 18.sp),
 *         label = { Text(text = labelValue) },
 *         colors = OutlinedTextFieldDefaults.colors(
 *             cursorColor = Primary,
 *             focusedBorderColor = Primary,
 *             focusedLabelColor = Primary,
 *         ),
 *         keyboardOptions = KeyboardOptions.Default.copy(
 *             imeAction = ImeAction.Next
 *         ), singleLine = true,
 *         maxLines = 1,
 *         trailingIcon = {
 *             Icon(
 *                 imageVector = Icons.Default.DateRange,
 *                 contentDescription = "Select Date",
 *                 modifier = Modifier.clickable {
 *                     DatePickerDialog(
 *                         context,
 *                         dateSetListener,
 *                         calendar.get(Calendar.YEAR),
 *                         calendar.get(Calendar.MONTH),
 *                         calendar.get(Calendar.DAY_OF_MONTH)
 *                     ).apply {
 *                         datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future dates
 *                         show()
 *                     }
 *                 }
 *             )
 *         })
 * }
 *
 *
 *
 *
 *
 * @Composable
 * fun DateFromTodayCompose(
 *     modifier: Modifier = Modifier
 * ) {
 *     val context = LocalContext.current
 *     var selectedDate by remember { mutableStateOf("") }
 *     val calendar = Calendar.getInstance()
 *
 *     // Prepare the listener for date set
 *     val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
 *         // Note: month is 0 based
 *         selectedDate = "$dayOfMonth/${month + 1}/$year"
 *         // Use the selected date as needed
 *         Toast.makeText(context, "Selected date: $selectedDate", Toast.LENGTH_LONG).show()
 *     }
 *
 *     Button(onClick = {
 *         DatePickerDialog(
 *             context,
 *             dateSetListener,
 *             calendar.get(Calendar.YEAR),
 *             calendar.get(Calendar.MONTH),
 *             calendar.get(Calendar.DAY_OF_MONTH)
 *         ).apply {
 *             datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future
 *             show()
 *         }
 *     }, modifier = modifier) {
 *         Text(if (selectedDate.isEmpty()) "Select Date" else selectedDate)
 *     }
 *
 * }
 *
 *
 *
 * @Composable
 * fun PickImageFromGallery(viewModel: GalleryScreenViewModel) {
 *
 *     var imageUri by remember { mutableStateOf<Uri?>(null) }
 *     val context = LocalContext.current
 *     val bitmap = remember { mutableStateOf<Bitmap?>(null) }
 *
 *     val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
 *         imageUri = uri
 *     }
 *
 *     Column(
 *         modifier = Modifier.fillMaxSize(),
 *         verticalArrangement = Arrangement.Center,
 *         horizontalAlignment = Alignment.CenterHorizontally
 *     ) {
 *         imageUri?.let {
 *             if (Build.VERSION.SDK_INT < 28) {
 *                 bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
 *             } else {
 *                 val source = ImageDecoder.createSource(context.contentResolver, it)
 *                 bitmap.value = ImageDecoder.decodeBitmap(source)
 *             }
 *
 *             bitmap.value?.let { btm ->
 *                 Image(
 *                     bitmap = btm.asImageBitmap(),
 *                     contentDescription = null,
 *                     modifier = Modifier
 *                         .size(400.dp)
 *                         .padding(20.dp)
 *                 )
 *             }
 *         }
 *
 *         Spacer(modifier = Modifier.height(12.dp))
 *
 *         Button(onClick = { launcher.launch("image/*") }) {
 *             Text(text = "Pick Image")
 *         }
 *
 *         // Conditionally display the Upload button only if an image has been picked
 *         if (imageUri != null) {
 *             Spacer(modifier = Modifier.height(8.dp))
 *
 *             Button(onClick = {
 *                 imageUri?.let { uri ->
 *                     val inputStream = context.contentResolver.openInputStream(uri)
 *                     inputStream?.let {
 *                         // Assuming you have a fileName strategy, for example using a timestamp
 *                         val fileName = "upload_${System.currentTimeMillis()}.jpg"
 *                         viewModel.uploadImageToFirebaseStorage(it, fileName)
 *                     }
 *                 }
 *             }) {
 *                 Text(text = "Upload")
 *             }
 *         }
 *     }
 *
 * }
 *
 *
 * @Composable
 * fun DatePickerShow(){
 *
 *     val year: Int
 *     val month: Int
 *     val day: Int
 *
 *     val calendar = Calendar.getInstance()
 *     val context = LocalContext.current
 *     year = calendar.get(Calendar.YEAR)
 *     month = calendar.get(Calendar.MONTH)
 *     day = calendar.get(Calendar.DAY_OF_MONTH)
 *     calendar.time = Date()
 *
 *     val date = remember { mutableStateOf("") }
 *     val datePickerDialog = DatePickerDialog(
 *         context,
 *         { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
 *             date.value = "$dayOfMonth/$month/$year"
 *         }, year, month, day
 *     )
 *
 *     Column(
 *         modifier = Modifier.fillMaxSize(),
 *         verticalArrangement = Arrangement.Center,
 *         horizontalAlignment = Alignment.CenterHorizontally
 *     ) {
 *
 *         Text(text = "Selected Date: ${date.value}")
 *         Spacer(modifier = Modifier.size(16.dp))
 *         Button(onClick = {
 *             datePickerDialog.show()
 *         }) {
 *             Text(text = "Open Date Picker")
 *         }
 *     }
 *
 * }
 * fun dropDownMenu() {
 *
 *     var expanded by remember { mutableStateOf(false) }
 *     val suggestions = listOf("Kotlin", "Java", "Dart", "Python")
 *     var selectedText by remember { mutableStateOf("") }
 *
 *     var textfieldSize by remember { mutableStateOf(Size.Zero) }
 *
 *     val icon = if (expanded)
 *         Icons.Filled.KeyboardArrowUp
 *     else
 *         Icons.Filled.KeyboardArrowDown
 *
 *
 *     Column(Modifier.padding(20.dp)) {
 *         OutlinedTextField(
 *             value = selectedText,
 *             onValueChange = { selectedText = it },
 *             modifier = Modifier
 *                 .fillMaxWidth()
 *                 .onGloballyPositioned { coordinates ->
 *                     //This value is used to assign to the DropDown the same width
 *                     textfieldSize = coordinates.size.toSize()
 *                 },
 *             label = { Text("Label") },
 *             trailingIcon = {
 *                 Icon(icon, "contentDescription",
 *                     Modifier.clickable { expanded = !expanded })
 *             }
 *         )
 *         DropdownMenu(
 *             expanded = expanded,
 *             onDismissRequest = { expanded = false },
 *             modifier = Modifier
 *                 .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
 *         ) {
 *         }
 *     }
 * }
 *
 *
 * @Composable
 * fun DateFromToday(
 *     modifier: Modifier = Modifier
 * ) {
 *     val context = LocalContext.current
 *     var selectedDate by remember { mutableStateOf("") }
 *     val calendar = Calendar.getInstance()
 *
 *     // Prepare the listener for date set
 *     val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
 *         // Note: month is 0 based
 *         selectedDate = "$dayOfMonth/${month + 1}/$year"
 *         // Use the selected date as needed
 *         Toast.makeText(context, "Selected date: $selectedDate", Toast.LENGTH_LONG).show()
 *     }
 *
 *     // Function to show the date picker dialog
 *     val showDatePicker = {
 *         DatePickerDialog(
 *             context,
 *             dateSetListener,
 *             calendar.get(Calendar.YEAR),
 *             calendar.get(Calendar.MONTH),
 *             calendar.get(Calendar.DAY_OF_MONTH)
 *         ).apply {
 *             datePicker.minDate = System.currentTimeMillis() - 1000 // Restrict to today or future
 *             show()
 *         }
 *     }
 *
 *     // OutlinedTextField to display the selected date
 *     OutlinedTextField(
 *         value = selectedDate,
 *         onValueChange = { selectedDate = it },
 *         label = { Text("Date") },
 *         readOnly = true, // Make the text field read-only
 *         modifier = modifier
 *             .clickable { showDatePicker() }, // Open date picker on text field click
 *         trailingIcon = {
 *             IconButton(onClick = { showDatePicker() }) {
 *                 Icon(Icons.Default.CalendarToday, contentDescription = "Select Date")
 *             }
 *         }
 *     )
 * }
 *
 *
 *
 * @Composable
 * fun DisplayUserRegisterForTrain(trainData: TrainUiState){
 *     val userList = trainData.userList
 *     Column(modifier = Modifier.padding(8.dp)) {
 *         NormalTextToLeftCornerComponent(value = "User register :")
 *         Spacer(modifier = Modifier.height(20.dp))
 *         if (userList != null) {
 *             for (user in userList){
 *
 *                 NormalTextToLeftCornerComponent(value = user.userName)
 *             }
 *         }
 *     }
 * }
 *
 *
 */