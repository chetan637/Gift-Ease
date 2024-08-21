
package com.giftease.chethan

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import com.giftease.chethan.ui.theme.GiftEaseTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.min

class ProfileActivity : ComponentActivity() {

    enum class CameraPermissionStatus { NoPermission, PermissionGranted, PermissionDenied }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cameraPermissionStatusState = mutableStateOf(CameraPermissionStatus.NoPermission)
        val photoUriState: MutableState<Uri?> = mutableStateOf(null)
        val hasPhotoState = mutableStateOf(value = false)
        val resolver = applicationContext.contentResolver

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    cameraPermissionStatusState.value = CameraPermissionStatus.PermissionGranted
                } else {
                    cameraPermissionStatusState.value = CameraPermissionStatus.PermissionDenied
                }
            }

        val takePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
                hasPhotoState.value = isSaved
            }

        val takePhoto: () -> Unit = {
            hasPhotoState.value = false

            val values = ContentValues().apply {
                val title = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
                put(MediaStore.Images.Media.TITLE, "Compose Camera Example Image - $title")
                put(MediaStore.Images.Media.DISPLAY_NAME, title)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            }

            val uri = resolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            takePhotoLauncher.launch(uri)
            photoUriState.value = uri
        }

        // Ideally these would be cached instead of reloaded
        val getThumbnail: (Uri?) -> ImageBitmap? = { uri ->
            val targetSize = 256f
            uri?.let {
                resolver.openInputStream(it)
            }?.let {
                BitmapFactory.decodeStream(it)
            }?.let {
                val height = it.height.toFloat()
                val width = it.width.toFloat()
                val scaleFactor = min(targetSize / height, targetSize / width)
                Bitmap.createScaledBitmap(it, (scaleFactor * width).toInt(), (scaleFactor * height).toInt(), true)
            }?.let {
                val rotation = getImageRotation(resolver, uri)
                Bitmap.createBitmap(it, 0, 0, it.width, it.height, Matrix().apply { postRotate(rotation.toFloat()) }, true)
            }?.asImageBitmap()
        }

        val getFullImage: (Uri?) -> ImageBitmap? = { uri ->
            uri?.let {
                resolver.openInputStream(it)
            }?.let {
                BitmapFactory.decodeStream(it)
            }?.let {
                val rotation = getImageRotation(resolver, uri)
                Bitmap.createBitmap(it, 0, 0, it.width, it.height, Matrix().apply { postRotate(rotation.toFloat()) }, true)
            }?.asImageBitmap()
        }

        setContent {
            val cameraPermissionStatus by remember { cameraPermissionStatusState }
            val hasPhoto by remember { hasPhotoState }
            var shouldShowFullImage by remember { mutableStateOf(false) }
            var isEditMode by remember { mutableStateOf(false) }

            var name by remember { mutableStateOf("Chethan") }
            var email by remember { mutableStateOf("Chethan@gmail.com") }
            var location by remember { mutableStateOf("UK") }

            GiftEaseTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (isEditMode) {
                            EditableField(label = "Name", value = name) { name = it }
                            EditableField(label = "Email", value = email) { email = it }
                            EditableField(label = "Location", value = location) { location = it }
                        } else {
                            DisplayField(label = "Name", value = name)
                            DisplayField(label = "Email", value = email)
                            DisplayField(label = "Location", value = location)
                        }

                        TakePhotoButton(
                            cameraPermissionStatus = cameraPermissionStatus,
                            requestPermissionLauncher = requestPermissionLauncher,
                            takePhoto = takePhoto
                        )

                        if (hasPhoto) {
                            val bitmap = getThumbnail(photoUriState.value)
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = "Thumbnail of Saved Photo",
                                    modifier = Modifier.clickable {
                                        shouldShowFullImage = true
                                    }
                                )
                            }
                        }

                        if (isEditMode) {
                            SaveButton { isEditMode = false }
                        } else {
                            EditButton { isEditMode = true }
                        }

                        BackButton { finish() }
                    }

                    if (shouldShowFullImage && hasPhoto) {
                        val bitmap = getFullImage(photoUriState.value)
                        if (bitmap != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        shouldShowFullImage = false
                                    }
                            ) {
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = "Full image of Saved Photo",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                                androidx.compose.material.Surface(
                                    modifier = Modifier
                                        .background(androidx.compose.material.MaterialTheme.colors.background)
                                        .align(Alignment.Center)
                                        .padding(8.dp)
                                ) {
                                    androidx.compose.material.Text(
                                        text = "Click to Close",
                                        style = androidx.compose.material.MaterialTheme.typography.h4.copy(
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )
                                }
                            }
                        } else {
                            shouldShowFullImage = false
                        }
                    }
                }
            }
        }
    }

    private fun getImageRotation(resolver: ContentResolver, uri: Uri): Int {
        val cursor = resolver.query(uri, arrayOf(MediaStore.Images.Media.ORIENTATION), null, null, null)
        var result = 0

        cursor?.apply {
            moveToFirst()
            val index = getColumnIndex(MediaStore.Images.Media.ORIENTATION)
            result = getInt(index)
            close()
        }
        return result
    }
}

@Composable
fun EditableField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* handle done action */ }),
            modifier = Modifier.padding(top = 4.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = androidx.compose.ui.graphics.Color.Transparent
            )
        )
    }
}

@Composable
fun DisplayField(label: String, value: String) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$label: $value", fontWeight = FontWeight.Normal, fontSize = 18.sp)
    }
}

@Composable
fun EditButton(onClick: () -> Unit) {
    OutlinedButton(onClick = onClick) {
        Text(text = "Edit")
    }
}

@Composable
fun SaveButton(onClick: () -> Unit) {
    OutlinedButton(onClick = onClick) {
        Text(text = "Save")
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    OutlinedButton(onClick = onClick) {
        Text(text = "Back")
    }
}

@Composable
fun TakePhotoButton(
    cameraPermissionStatus: ProfileActivity.CameraPermissionStatus,
    requestPermissionLauncher: ActivityResultLauncher<String>,
    takePhoto: () -> Unit
) {
    OutlinedButton(
        onClick = {
            when (cameraPermissionStatus) {
                ProfileActivity.CameraPermissionStatus.NoPermission ->
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)

                ProfileActivity.CameraPermissionStatus.PermissionGranted ->
                    takePhoto()

                ProfileActivity.CameraPermissionStatus.PermissionDenied -> {
                    /* Handle permission denial */
                }
            }
        }
    ) {
        when (cameraPermissionStatus) {
            ProfileActivity.CameraPermissionStatus.NoPermission ->
                Text(text = "Request Camera Permissions")

            ProfileActivity.CameraPermissionStatus.PermissionDenied ->
                Text(text = "Camera Permissions Have Been Denied")

            ProfileActivity.CameraPermissionStatus.PermissionGranted ->
                Text(text = "Take a Photo")
        }
    }
}
