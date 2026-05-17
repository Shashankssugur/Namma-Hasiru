package com.nammahasiru.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nammahasiru.ui.viewmodel.GuideViewModel
import com.nammahasiru.ui.viewmodel.SoilType

@Composable
fun GuideScreen(vm: GuideViewModel) {
    var soil by remember { mutableStateOf(SoilType.LOAMY) }
    var expanded by remember { mutableStateOf(false) }
    val suggestions = remember(soil) { vm.suggestionsFor(soil) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Species Guide", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Pick your soil type to see beginner-friendly suggestions.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Soil type", style = MaterialTheme.typography.labelLarge)
                TextButton(onClick = { expanded = true }) {
                    Text(soil.name)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    SoilType.entries.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.name) },
                            onClick = { expanded = false; soil = type }
                        )
                    }
                }
            }
        }

        suggestions.forEach { s ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(s.name, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleMedium)
                    Text(s.why, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

