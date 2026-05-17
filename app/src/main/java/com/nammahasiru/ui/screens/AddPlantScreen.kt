package com.nammahasiru.ui.screens

import android.app.DatePickerDialog
import android.location.Location
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.nammahasiru.data.PlantType
import com.nammahasiru.ui.viewmodel.AddPlantViewModel
import com.nammahasiru.util.createCacheImageUri
import com.nammahasiru.util.formatEpochDay
import java.time.LocalDate

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddPlantScreen(
    vm: AddPlantViewModel,
    onSaved: (Long) -> Unit
) {
    val context = LocalContext.current
    val ui by vm.ui.collectAsState()
    val scroll = rememberScrollState()

    val locationPermission = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) vm.setPhotoUri(tempCameraUri?.toString())
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        vm.setPhotoUri(uri?.toString())
    }

    LaunchedEffect(ui.savedId) {
        val id = ui.savedId
        if (id != null) {
            vm.clearSavedFlag()
            onSaved(id)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Add a Plant", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Add a seed/sapling with photo + GPS, then track survival over time.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = ui.name,
            onValueChange = vm::setName,
            label = { Text("Plant name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true
        )

        PlantTypePicker(
            value = ui.type,
            onChange = vm::setType
        )

        DatePickerRow(
            date = ui.plantedDate,
            onPick = vm::setDate
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Photo (optional)", style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = {
                        pickImageLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text("Gallery")
                    }
                    Button(
                        onClick = {
                            if (!cameraPermission.status.isGranted) {
                                cameraPermission.launchPermissionRequest()
                            } else {
                                val uri = createCacheImageUri(context)
                                tempCameraUri = uri
                                takePictureLauncher.launch(uri)
                            }
                        }
                    ) {
                        Text("Camera")
                    }
                }

                if (ui.plantedPhotoUri != null) {
                    AsyncImage(
                        model = ui.plantedPhotoUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Geo-tag (optional)", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = if (ui.latitude != null && ui.longitude != null) {
                        "Location: ${ui.latitude}, ${ui.longitude}"
                    } else {
                        "No location captured yet."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Button(
                    onClick = {
                        if (!locationPermission.status.isGranted) {
                            locationPermission.launchPermissionRequest()
                        } else {
                            val client = LocationServices.getFusedLocationProviderClient(context)
                            client.lastLocation
                                .addOnSuccessListener { loc: Location? ->
                                    if (loc != null) vm.setLocation(loc.latitude, loc.longitude)
                                }
                        }
                    }
                ) {
                    Text("Use current location")
                }
            }
        }

        if (ui.error != null) {
            Text(ui.error ?: "", color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = vm::savePlant,
            enabled = !ui.isSaving,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (ui.isSaving) "Saving..." else "Save Plant")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Tip: After ~90 days, the app will remind you to update the plant status.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PlantTypePicker(
    value: PlantType,
    onChange: (PlantType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("Plant type", style = MaterialTheme.typography.labelLarge)
        TextButton(onClick = { expanded = true }) {
            Text(value.name)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("SEED") },
                onClick = { expanded = false; onChange(PlantType.SEED) }
            )
            DropdownMenuItem(
                text = { Text("SAPLING") },
                onClick = { expanded = false; onChange(PlantType.SAPLING) }
            )
        }
    }
}

@Composable
private fun DatePickerRow(
    date: LocalDate,
    onPick: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("Date planted", style = MaterialTheme.typography.labelLarge)
        TextButton(
            onClick = {
                val dialog = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        onPick(LocalDate.of(year, month + 1, dayOfMonth))
                    },
                    date.year,
                    date.monthValue - 1,
                    date.dayOfMonth
                )
                dialog.show()
            }
        ) {
            Text(formatEpochDay(date.toEpochDay()))
        }
    }
}

