package com.nammahasiru.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nammahasiru.data.PlantStatus
import com.nammahasiru.ui.viewmodel.PlantDetailViewModel
import com.nammahasiru.util.createCacheImageUri
import com.nammahasiru.util.formatEpochDay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PlantDetailScreen(
    plantId: Long,
    vm: PlantDetailViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val ui by vm.ui.collectAsState()
    val scroll = rememberScrollState()

    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) vm.updateGrowthPhoto(tempCameraUri?.toString())
    }

    val pickGrowthLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        vm.updateGrowthPhoto(uri?.toString())
    }

    LaunchedEffect(plantId) {
        vm.load(plantId)
    }

    val plant = ui.plant

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("Plant Details", style = MaterialTheme.typography.headlineSmall)
        }

        if (plant == null) {
            Text("Loading...", color = MaterialTheme.colorScheme.onSurfaceVariant)
            return
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(plant.name, style = MaterialTheme.typography.titleLarge)
                Text(
                    "${plant.type} • Planted ${formatEpochDay(plant.datePlantedEpochDay)}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                val lat = plant.latitude
                val lon = plant.longitude
                Text(
                    text = if (lat != null && lon != null) "Location: $lat, $lon" else "Location: not set",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Planted photo", style = MaterialTheme.typography.titleMedium)
                if (plant.plantedPhotoUri != null) {
                    AsyncImage(
                        model = plant.plantedPhotoUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                } else {
                    Text("No photo added.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Update status", style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = { vm.updateStatus(PlantStatus.SURVIVED) }) { Text("Survived") }
                    OutlinedButton(onClick = { vm.updateStatus(PlantStatus.DEAD) }) { Text("Dead") }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Growth photo (optional)", style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = {
                        pickGrowthLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) { Text("Gallery") }
                    Button(onClick = {
                        if (!cameraPermission.status.isGranted) {
                            cameraPermission.launchPermissionRequest()
                        } else {
                            val uri = createCacheImageUri(context)
                            tempCameraUri = uri
                            takePictureLauncher.launch(uri)
                        }
                    }) { Text("Camera") }
                }

                if (plant.growthPhotoUri != null) {
                    AsyncImage(
                        model = plant.growthPhotoUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                } else {
                    Text("No growth photo yet.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            "Survival Score: marking plants helps calculate your area survival rate.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

