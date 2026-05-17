package com.nammahasiru.ui.screens

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nammahasiru.ui.components.PlantCard
import com.nammahasiru.ui.components.SurvivalPieChart
import com.nammahasiru.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel,
    onOpenPlant: (Long) -> Unit
) {
    val summary by vm.summary.collectAsState()
    val notifPermission =
        if (Build.VERSION.SDK_INT >= 33) rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
        else null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Welcome to Namma-Hasiru",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Track tree plantation efforts, growth, and survival — together.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (notifPermission != null && !notifPermission.status.isGranted) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Enable reminders", fontWeight = FontWeight.SemiBold)
                        Text(
                            "Allow notifications so we can remind you to update plant status after 90 days.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    androidx.compose.material3.Button(onClick = { notifPermission.launchPermissionRequest() }) {
                        Text("Allow")
                    }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total plants", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = summary.totalPlants.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Survival score", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = "${summary.survivalRatePercent}%",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                SurvivalPieChart(survivalPercent = summary.survivalRatePercent)
            }
        }

        Text(
            text = "Recent activity",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        AnimatedVisibility(
            visible = summary.recent.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(250)),
            exit = fadeOut(animationSpec = tween(250))
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                summary.recent.forEach { plant ->
                    PlantCard(plant = plant, onClick = { onOpenPlant(plant.id) })
                }
            }
        }

        if (summary.recent.isEmpty()) {
            Text(
                text = "No activity yet. Add your first plant from the Add tab.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

