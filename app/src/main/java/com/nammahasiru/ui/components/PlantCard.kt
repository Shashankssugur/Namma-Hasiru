package com.nammahasiru.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nammahasiru.data.PlantEntity
import com.nammahasiru.data.PlantStatus
import com.nammahasiru.util.formatEpochDay

@Composable
fun PlantCard(
    plant: PlantEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${plant.type} • Planted ${formatEpochDay(plant.datePlantedEpochDay)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            val statusText = when (plant.status) {
                PlantStatus.UNKNOWN -> "Pending"
                PlantStatus.SURVIVED -> "Survived"
                PlantStatus.DEAD -> "Dead"
            }
            Text(
                text = statusText,
                style = MaterialTheme.typography.labelLarge,
                color = when (plant.status) {
                    PlantStatus.SURVIVED -> MaterialTheme.colorScheme.primary
                    PlantStatus.DEAD -> MaterialTheme.colorScheme.error
                    PlantStatus.UNKNOWN -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

