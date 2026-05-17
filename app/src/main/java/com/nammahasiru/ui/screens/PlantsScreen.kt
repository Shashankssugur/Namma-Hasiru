package com.nammahasiru.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nammahasiru.ui.components.PlantCard
import com.nammahasiru.ui.viewmodel.PlantsViewModel

@Composable
fun PlantsScreen(
    vm: PlantsViewModel,
    onOpenPlant: (Long) -> Unit
) {
    val context = LocalContext.current
    val plants by vm.plants.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Tree Map (Community)", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Browse all planted saplings/seeds saved on this device.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total: ${plants.size}", style = MaterialTheme.typography.titleMedium)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(plants, key = { it.id }) { plant ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    PlantCard(plant = plant, onClick = { onOpenPlant(plant.id) })

                    val lat = plant.latitude
                    val lon = plant.longitude
                    if (lat != null && lon != null) {
                        Button(
                            onClick = {
                                val uri = Uri.parse("geo:$lat,$lon?q=$lat,$lon(${Uri.encode(plant.name)})")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            }
                        ) {
                            Text("Open in Maps")
                        }
                    }
                }
            }
        }
    }
}

